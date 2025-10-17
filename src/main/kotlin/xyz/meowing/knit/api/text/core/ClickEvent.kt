package xyz.meowing.knit.api.text.core

import java.net.URI

sealed interface ClickEvent {
    data class OpenUrl(val url: URI) : ClickEvent
    data class RunCommand(val command: String) : ClickEvent
    data class SuggestCommand(val command: String) : ClickEvent
    data class CopyToClipboard(val text: String) : ClickEvent
    data class ChangePage(val page: Int) : ClickEvent
}