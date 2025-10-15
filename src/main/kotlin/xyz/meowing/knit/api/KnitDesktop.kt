package xyz.meowing.knit.api

import org.apache.logging.log4j.LogManager

/**
 * @author: Deftu
 */
object KnitDesktop {
    private val logger = LogManager.getLogger(KnitDesktop::class.java)

    @JvmStatic
    var isWindows: Boolean = false
        private set

    @JvmStatic
    var isMac: Boolean = false
        private set

    @JvmStatic
    var isLinux: Boolean = false
        private set

    @JvmStatic
    var isXdg: Boolean = false
        private set

    @JvmStatic
    var isKde: Boolean = false
        private set

    @JvmStatic
    var isGnome: Boolean = false
        private set

    init {
        val osName = try {
            System.getProperty("os.name").lowercase()
        } catch (e: Exception) {
            logger.error("Failed to get OS name", e)
            null
        }

        if (osName != null) {
            isWindows = osName.startsWith("Windows")
            isMac = osName.startsWith("Mac")
            isLinux = osName.startsWith("Linux") || osName.startsWith("LINUX")
            if (isLinux) {
                isXdg = System.getenv("XDG_SESSION_ID")?.isNotEmpty() == true
                val gnomeSession = System.getenv("GDMSESSION")?.lowercase()
                isGnome = gnomeSession?.contains("gnome") == true
                isKde = gnomeSession?.contains("kde") == true
            }
        } else {
            logger.error("OS name is null... Uh oh...")
        }
    }
}