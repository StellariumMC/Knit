package xyz.meowing.knit.internal.events

import xyz.meowing.knit.api.events.Event

sealed class ClientEvent {
    class Start : Event()
    class Stop : Event()
}