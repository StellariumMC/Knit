package xyz.meowing.knit.api

/**
 * @author: Deftu
 */
object KnitClipboard {
    var string: String
        get() {
            return KnitClient.client.keyboard.clipboard
        }
        set(value) {
            KnitClient.client.keyboard.clipboard = value
        }
}