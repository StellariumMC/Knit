package xyz.meowing.knit.api.loader

import java.util.Optional
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.ModContainer
import net.minecraft.launchwrapper.Launch

/**
 * @author Deftu
 */
object KnitLoader {
    @JvmStatic
    val loader: ModLoader
        get() {
            return ModLoader.FORGE
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
            return Launch.blackboard["fml.deobfuscatedEnvironment"] as Boolean
        }


    @JvmStatic
    val mods: Set<KnitModInfo>
        get() = buildSet {
            Loader.instance().modList.map(KnitModInfo::wrap).forEach(::add)
        }

    @JvmStatic
    fun isLoaded(id: String, version: String): Boolean {
        return Loader.isModLoaded(id) && Loader.instance().indexedModList[id]?.version == version
    }

    @JvmStatic
    fun isLoaded(id: String): Boolean {
        return Loader.isModLoaded(id)
    }

    @JvmStatic
    fun findContainer(id: String): Optional<out ModContainer> {
        return Optional.ofNullable(Loader.instance().getIndexedModList()[id])
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