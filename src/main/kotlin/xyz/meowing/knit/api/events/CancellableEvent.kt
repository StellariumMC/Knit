package xyz.meowing.knit.api.events

abstract class CancellableEvent : Event() {
    var cancelled = false
        private set

    fun cancel() {
        cancelled = true
    }
}