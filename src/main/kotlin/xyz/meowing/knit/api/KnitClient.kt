package xyz.meowing.knit.api

import net.minecraft.client.MinecraftClient
import net.minecraft.client.world.ClientWorld
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.SharedConstants

import java.nio.file.Path
import xyz.meowing.knit.api.loader.KnitLoader

//#if FABRIC
import net.fabricmc.loader.api.FabricLoader
//#elseif FORGE
    //$$ import net.minecraftforge.fml.loading.FMLPaths
//#else
    //$$ import net.neoforged.fml.loading.FMLPaths
//#endif

object KnitClient {
    @JvmStatic
    val client: MinecraftClient = MinecraftClient.getInstance()

    @JvmStatic
    val world: ClientWorld? get() = client.world

    @JvmStatic
    val player: ClientPlayerEntity? get() = KnitPlayer.player

    @JvmStatic
    val isFabric: Boolean get() = KnitLoader.isFabric

    @JvmStatic
    val isForge: Boolean get() = KnitLoader.isForge

    @JvmStatic
    val isNeoForge: Boolean get() = KnitLoader.isNeoForge

    @JvmStatic
    val minecraftVersion: String by lazy {
        //#if FORGE-LIKE
            //#if MC >= 1.21.7
            //$$ SharedConstants.getCurrentVersion().name()
            //#else
            //$$ SharedConstants.getCurrentVersion().name
            //#endif
        //#elseif FABRIC
            //#if MC >= 1.21.7
            //$$ SharedConstants.getGameVersion().name()
            //#else
            SharedConstants.getGameVersion().name
            //#endif
        //#endif
    }

    @JvmStatic
    val gameDirectory: Path
        get() {
            //#if FABRIC
            return FabricLoader.getInstance().gameDir
            //#else
            //$$ return FMLPaths.GAMEDIR.get()
            //#endif
        }

    @JvmStatic
    val configDirectory: Path
        get() {
            //#if FABRIC
            return FabricLoader.getInstance().configDir
            //#else
            //$$ return FMLPaths.CONFIGDIR.get()
            //#endif
        }
}