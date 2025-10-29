package xyz.meowing.knit.api.loader

import java.util.Optional
import xyz.meowing.knit.api.KnitClient
import net.minecraftforge.fml.common.ModContainer

/**
 * @author Deftu
 */
data class KnitModInfo(
    val id: String,
    val name: String,
    val version: String,
    val container: Optional<out ModContainer> = KnitLoader.findContainer(id),
    val mcVersion: String = KnitClient.minecraftVersion
) {
    companion object {
        @JvmStatic
        fun wrap(mod: ModContainer): KnitModInfo {
            return KnitModInfo(
                mod.modId,
                mod.name,
                mod.version,
                Optional.of(mod),
            )
        }
    }

    override fun toString(): String {
        return "$name ($id@$version)"
    }
}