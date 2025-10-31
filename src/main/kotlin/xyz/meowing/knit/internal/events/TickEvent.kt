package xyz.meowing.knit.internal.events

import xyz.meowing.knit.api.events.Event

sealed class TickEvent {
    sealed class Client {
        class Start : Event()
        class End : Event()
    }

    /**
     * Tested for Hypixel.
     */
    sealed class Server {
        class Start : Event()
        class End : Event()
    }
}