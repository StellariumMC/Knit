package xyz.meowing.knit.api.events

interface EventCall {
    fun unregister(): Boolean
    fun register(): Boolean
    fun isRegistered(): Boolean
}