package xyz.meowing.knit.api.loader

//#if MC != 1.16.5

import xyz.meowing.knit.api.KnitClient
import java.util.Optional

//#if FABRIC
//$$ import net.fabricmc.loader.api.ModContainer
//#elseif FORGE
    //#if MC >= 1.16.5
    //$$ import net.minecraftforge.fml.ModList
    //$$ import net.minecraftforge.fml.ModContainer
    //#else
    import net.minecraftforge.fml.common.ModContainer
    //#endif
//#else
    //$$ import net.neoforged.fml.ModList
    //$$ import net.neoforged.fml.ModContainer
//#endif

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
            //#if FABRIC
            //$$ return KnitModInfo(
            //$$     mod.metadata.id,
            //$$     mod.metadata.name,
            //$$     mod.metadata.version.friendlyString,
            //$$     Optional.of(mod),
            //$$ )
            //#else
            //#if MC >= 1.15.2
            //$$ val modFile = ModList.get().getModFileById(mod.modId)
            //$$ return KnitModInfo(
            //$$     mod.modId,
            //$$     mod.modInfo.displayName,
            //$$     mod.modInfo.version.toString(),
            //$$     Optional.of(mod),
            //$$ )
            //#else
            return KnitModInfo(
                mod.modId,
                mod.name,
                mod.version,
                Optional.of(mod),
            )
            //#endif
            //#endif
        }
    }

    override fun toString(): String {
        return "$name ($id@$version)"
    }
}

//#endif