package xyz.meowing.knit.api.events

import java.util.concurrent.ConcurrentHashMap

/**
 * Implementation inspired by DocilElm's impl
 */
object EventBus {
    val listeners = ConcurrentHashMap<Class<*>, MutableSet<PrioritizedCallback<*>>>()
    data class PrioritizedCallback<T>(val priority: Int, val callback: (T) -> Unit)

    inline fun <reified T : Event> register(
        priority: Int = 0,
        add: Boolean = true,
        noinline callback: (T) -> Unit
    ): EventCall {
        val handlers = listeners.getOrPut(T::class.java) { ConcurrentHashMap.newKeySet() }
        val prioritizedCallback = PrioritizedCallback(priority, callback)

        if (add) handlers.add(prioritizedCallback)

        return EventCallImpl(prioritizedCallback, handlers)
    }

    @JvmStatic
    fun <T : Event> registerJava(
        eventClass: Class<T>,
        priority: Int = 0,
        add: Boolean = true,
        callback: (T) -> Unit
    ): EventCall {
        val handlers = listeners.getOrPut(eventClass) { ConcurrentHashMap.newKeySet() }
        val prioritizedCallback = PrioritizedCallback(priority, callback)

        if (add) handlers.add(prioritizedCallback)

        return EventCallImpl(prioritizedCallback, handlers)
    }

    fun <T : Event> post(event: T): Boolean {
        val handlers = listeners[event::class.java] ?: return false
        if (handlers.isEmpty()) return false

        handlers.sortedBy { it.priority }.forEach { handler ->
            try {
                (handler.callback as (T) -> Unit)(event)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return event is CancellableEvent && event.cancelled
    }

    class EventCallImpl(
        private val callback: PrioritizedCallback<*>,
        private val handlers: MutableSet<PrioritizedCallback<*>>
    ) : EventCall {
        override fun unregister(): Boolean = handlers.remove(callback)
        override fun register(): Boolean = handlers.add(callback)
    }

    interface EventCall {
        fun unregister(): Boolean
        fun register(): Boolean
    }
}