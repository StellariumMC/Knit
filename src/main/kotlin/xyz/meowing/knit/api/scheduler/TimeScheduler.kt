package xyz.meowing.knit.api.scheduler

import org.apache.logging.log4j.LogManager
import org.jetbrains.annotations.ApiStatus
import xyz.meowing.knit.Knit.EventBus
import xyz.meowing.knit.internal.events.ClientEvent
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

object TimeScheduler {
    private val executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors())
    private val tasks = ConcurrentHashMap<Long, ScheduledFuture<*>>()
    private val logger = LogManager.getLogger(TimeScheduler::class.java)
    private var nextId = 0L

    init {
        EventBus.register<ClientEvent.Stop> {
            shutdown()
        }
    }

    fun schedule(
        delayMillis: Long,
        action: () -> Unit
    ): Handle {
        val future = executor.schedule(safeRunnable(action), delayMillis, TimeUnit.MILLISECONDS)
        return HandleImpl(nextId++, future)
    }

    fun repeat(
        intervalMillis: Long,
        initialDelayMillis: Long = intervalMillis,
        action: () -> Unit
    ): Handle {
        val id = nextId++
        scheduleLoop(id, intervalMillis, { false }, action)
        return createHandle(id)
    }

    fun repeat(
        intervalMillis: Long,
        initialDelayMillis: Long = intervalMillis,
        stopCondition: () -> Boolean,
        action: () -> Unit
    ): Handle {
        val id = nextId++
        scheduleLoopWithDelay(id, initialDelayMillis, intervalMillis, stopCondition, action)
        return createHandle(id)
    }

    fun repeatDynamic(
        intervalProvider: () -> Long,
        stopCondition: () -> Boolean = { false },
        action: () -> Unit
    ): Handle {
        val id = nextId++
        scheduleDynamicLoop(id, intervalProvider, stopCondition, action)
        return createHandle(id)
    }

    @ApiStatus.Internal
    fun shutdown() {
        tasks.clear()
        executor.shutdown()
    }

    private fun scheduleLoop(
        id: Long,
        intervalMillis: Long,
        stopCondition: () -> Boolean,
        action: () -> Unit
    ) {
        scheduleLoop(id, { intervalMillis }, stopCondition, action)
    }

    private fun scheduleLoopWithDelay(
        id: Long,
        initialDelayMillis: Long,
        intervalMillis: Long,
        stopCondition: () -> Boolean,
        action: () -> Unit
    ) {
        val task = safeRunnable {
            action()
            if (!stopCondition()) {
                scheduleLoop(id, intervalMillis, stopCondition, action)
            } else {
                tasks.remove(id)
            }
        }
        
        tasks[id] = executor.schedule(task, initialDelayMillis, TimeUnit.MILLISECONDS)
    }

    private fun scheduleLoop(
        id: Long,
        intervalProvider: () -> Long,
        stopCondition: () -> Boolean,
        action: () -> Unit
    ) {
        val task = safeRunnable {
            action()
            if (!stopCondition()) {
                val nextDelay = intervalProvider()
                tasks[id] = executor.schedule({
                    scheduleLoop(id, intervalProvider, stopCondition, action)
                }, nextDelay, TimeUnit.MILLISECONDS)
            } else {
                tasks.remove(id)
            }
        }
        
        tasks[id] = executor.schedule(task, 0, TimeUnit.MILLISECONDS)
    }

    private fun scheduleDynamicLoop(
        id: Long,
        intervalProvider: () -> Long,
        stopCondition: () -> Boolean,
        action: () -> Unit
    ) {
        fun scheduleNext() {
            if (stopCondition()) {
                tasks.remove(id)
                return
            }

            val task = safeRunnable {
                action()
                scheduleNext()
            }
            
            tasks[id] = executor.schedule(task, intervalProvider(), TimeUnit.MILLISECONDS)
        }
        
        scheduleNext()
    }

    private fun createHandle(id: Long): Handle {
        return object : Handle {
            override val isCancelled: Boolean get() = !tasks.containsKey(id)

            override fun cancel() {
                tasks.remove(id)?.cancel(false)
            }
        }
    }

    private fun safeRunnable(action: () -> Unit): Runnable = Runnable {
        try {
            action()
        } catch (e: Exception) {
            logger.error("Caught error while trying to run action: $e")
        }
    }

    private class HandleImpl(
        private val id: Long,
        private val future: ScheduledFuture<*>
    ) : Handle {
        override val isCancelled: Boolean get() = future.isCancelled || future.isDone

        override fun cancel() {
            tasks.remove(id)
            future.cancel(false)
        }
    }

    @ApiStatus.Internal
    interface Handle {
        val isCancelled: Boolean
        fun cancel()
    }
}