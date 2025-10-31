package xyz.meowing.knit.api.scheduler

import org.apache.logging.log4j.LogManager
import xyz.meowing.knit.Knit.EventBus
import xyz.meowing.knit.internal.events.TickEvent
import java.util.*

object TickScheduler {
    private val logger = LogManager.getLogger(TickScheduler::class.java)

    init {
        EventBus.register<TickEvent.Client.End> {
            Client.onTick()
        }

        EventBus.register<TickEvent.Server.End> {
            Server.onTick()
        }
    }

    interface Handle {
        val isCancelled: Boolean
        fun cancel()
    }

    data class Timer(
        val id: Long,
        var ticks: Int,
        val onTick: () -> Unit = {},
        val onComplete: () -> Unit = {}
    )

    abstract class Scheduler {
        private val queue = PriorityQueue<Task>(compareBy { it.executeTick })
        private val handles = mutableMapOf<Long, HandleImpl>()
        private val dynamicLoops = mutableMapOf<Long, DynamicLoop>()
        private val timers = mutableMapOf<Long, Timer>()
        private var nextId = 0L
        var currentTick = 0L
            private set

        private data class Task(
            val executeTick: Long,
            val action: () -> Unit,
            val interval: Long = 0,
            val id: Long = 0
        )

        private data class DynamicLoop(
            val intervalProvider: () -> Long,
            val action: () -> Unit
        )

        private inner class HandleImpl(val id: Long) : Handle {
            override var isCancelled = false
                private set

            override fun cancel() {
                isCancelled = true
                synchronized(handles) { handles.remove(id) }
                synchronized(dynamicLoops) { dynamicLoops.remove(id) }
            }
        }

        internal fun onTick() {
            currentTick++
            processPendingTasks()
            processTimers()
        }

        private fun processPendingTasks() {
            while (true) {
                val task = getNextReadyTask() ?: break
                val handle = getHandle(task.id)

                if (handle?.isCancelled == true) continue

                safeRunnable { task.action() }

                when {
                    shouldRepeat(task, handle) -> rescheduleTask(task)
                    shouldRepeatDynamic(task) -> rescheduleDynamicTask(task)
                }
            }
        }

        private fun processTimers() {
            val completed = mutableListOf<Long>()

            synchronized(timers) {
                timers.values.forEach { timer ->
                    if (timer.ticks > 0) {
                        timer.ticks--
                        safeRunnable { timer.onTick() }
                    } else {
                        safeRunnable { timer.onComplete() }
                        completed.add(timer.id)
                    }
                }
                completed.forEach { timers.remove(it) }
            }
        }

        private fun getNextReadyTask(): Task? = synchronized(queue) {
            queue.peek()
                ?.takeIf { currentTick >= it.executeTick }
                ?.let { queue.poll() }
        }

        private fun getHandle(id: Long): HandleImpl? = synchronized(handles) {
            handles[id]
        }

        private fun shouldRepeat(task: Task, handle: HandleImpl?): Boolean =
            task.interval > 0 && handle?.isCancelled == false

        private fun shouldRepeatDynamic(task: Task): Boolean =
            synchronized(dynamicLoops) { dynamicLoops.containsKey(task.id) }

        private fun rescheduleTask(task: Task) = synchronized(queue) {
            queue.offer(task.copy(executeTick = currentTick + task.interval))
        }

        private fun safeRunnable(action: () -> Unit): Runnable = Runnable {
            try {
                action()
            } catch (e: Exception) {
                logger.error("Caught error while trying to run action: $e")
            }
        }

        private fun rescheduleDynamicTask(task: Task) {
            val loop = synchronized(dynamicLoops) { dynamicLoops[task.id] } ?: return
            val nextInterval = loop.intervalProvider()

            synchronized(queue) {
                queue.offer(task.copy(executeTick = currentTick + nextInterval, interval = 0))
            }
        }

        fun post(action: () -> Unit) {
            schedule(1, action)
        }

        fun schedule(delay: Long, action: () -> Unit) {
            synchronized(queue) {
                queue.offer(Task(currentTick + delay, action))
            }
        }

        fun repeat(
            interval: Long,
            initialDelay: Long = interval,
            action: () -> Unit
        ): Handle {
            val id = nextId++
            val handle = HandleImpl(id)

            synchronized(handles) {
                handles[id] = handle
            }

            synchronized(queue) {
                queue.offer(Task(currentTick + initialDelay, action, interval, id))
            }

            return handle
        }

        fun repeatDynamic(
            intervalProvider: () -> Long,
            action: () -> Unit
        ): Handle {
            val id = nextId++
            val handle = HandleImpl(id)
            val loop = DynamicLoop(intervalProvider, action)
            val initialInterval = intervalProvider()

            synchronized(handles) {
                handles[id] = handle
            }

            synchronized(dynamicLoops) {
                dynamicLoops[id] = loop
            }

            synchronized(queue) {
                queue.offer(Task(currentTick + initialInterval, action, 0, id))
            }

            return handle
        }

        fun createTimer(
            ticks: Int,
            onTick: () -> Unit = {},
            onComplete: () -> Unit = {}
        ): Long {
            val id = nextId++
            val timer = Timer(id, ticks, onTick, onComplete)

            synchronized(timers) {
                timers[id] = timer
            }

            return id
        }

        fun cancelTimer(timerId: Long) {
            synchronized(timers) {
                timers.remove(timerId)
            }
        }

        fun getTimer(timerId: Long): Timer? = synchronized(timers) {
            timers[timerId]
        }
    }

    object Client : Scheduler()

    object Server : Scheduler()
}