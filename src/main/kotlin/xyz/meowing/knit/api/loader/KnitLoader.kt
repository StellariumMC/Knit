package xyz.meowing.knit.api.loader

import java.util.Optional

//#if FABRIC
import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.api.ModContainer
//#elseif FORGE
//$$ import net.minecraftforge.fml.ModList
//$$ import net.minecraftforge.fml.ModContainer
//$$ import net.minecraftforge.fml.loading.FMLLoader
//#else
//$$ import net.neoforged.fml.ModList
//$$ import net.neoforged.fml.ModContainer
//$$ import net.neoforged.fml.loading.FMLLoader
//#endif

/**
 * @author Deftu
 */
object KnitLoader {
    @JvmStatic
    val loader: ModLoader
        get() {
            //#if FABRIC
            return ModLoader.FABRIC
            //#elseif FORGE
            //$$ return ModLoader.FORGE
            //#else
            //$$ return ModLoader.NEOFORGE
            //#endif
        }

    @JvmStatic
    val isFabric: Boolean get() = loader == ModLoader.FABRIC

    @JvmStatic
    val isForge: Boolean get() = loader == ModLoader.FORGE

    @JvmStatic
    val isNeoForge: Boolean get() = loader == ModLoader.NEOFORGE

    @JvmStatic
    val isDevelopment: Boolean
        get() {
            //#if FABRIC
            return FabricLoader.getInstance().isDevelopmentEnvironment
            //#else
                //#if MC >= 1.21.9
                    //#if FORGE
                    //$$ return !FMLLoader.isProduction()
                    //#else
                    //$$ return !FMLLoader.getCurrent().isProduction
                    //#endif
                //#else
                //$$ return !FMLLoader.isProduction()
                //#endif
            //#endif
        }


    @JvmStatic
    val mods: Set<KnitModInfo>
        get() = buildSet {
            //#if FABRIC
            FabricLoader.getInstance().allMods.map(KnitModInfo::wrap).forEach(::add)
            //#else
            //$$ ModList.get().applyForEachModContainer(KnitModInfo::wrap).collect(java.util.stream.Collectors.toSet()).forEach(::add)
            //#endif
        }

    @JvmStatic
    fun isLoaded(id: String, version: String): Boolean {
        //#if FABRIC
        return FabricLoader.getInstance().isModLoaded(id) && FabricLoader.getInstance().getModContainer(id).get().metadata.version.friendlyString == version
        //#else
        //$$ return ModList.get().isLoaded(id) && ModList.get().getModContainerById(id).get().getModInfo().getVersion().toString() == version
        //#endif
    }

    @JvmStatic
    fun isLoaded(id: String): Boolean {
        //#if FABRIC
        return FabricLoader.getInstance().isModLoaded(id)
        //#else
        //$$ return ModList.get().isLoaded(id)
        //#endif
    }

    @JvmStatic
    fun findContainer(id: String): Optional<out ModContainer> {
        //#if FABRIC
        return FabricLoader.getInstance().getModContainer(id)
        //#else
        //$$ return ModList.get().getModContainerById(id)
        //#endif
    }

    @JvmStatic
    fun findContainerOrNull(id: String): ModContainer? {
        return findContainer(id).orElse(null)
    }

    @JvmStatic
    fun findMod(id: String): Optional<KnitModInfo> {
        return findContainer(id).map(KnitModInfo::wrap)
    }

    @JvmStatic
    fun findModOrNull(id: String): KnitModInfo? {
        return findContainerOrNull(id)?.let(KnitModInfo::wrap)
    }
}