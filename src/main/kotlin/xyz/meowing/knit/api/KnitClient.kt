package xyz.meowing.knit.api

//#if MC != 1.16.5

import java.nio.file.Path
import xyz.meowing.knit.api.loader.KnitLoader

//#if MC >= 1.20.1
//$$ import net.minecraft.client.MinecraftClient
//$$ import net.minecraft.client.world.ClientWorld
//$$ import net.minecraft.client.network.ClientPlayerEntity
//$$ import net.minecraft.SharedConstants
//#else
import net.minecraft.client.Minecraft
import net.minecraft.world.World
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraftforge.common.ForgeVersion
//#endif

//#if FABRIC
//$$ import net.fabricmc.loader.api.FabricLoader
//#else
    //#if MC >= 1.16.5
        //#if FORGE
        //$$ import net.minecraftforge.fml.loading.FMLPaths
        //#else
        //$$ import net.neoforged.fml.loading.FMLPaths
        //#endif
    //#else
        import net.minecraft.launchwrapper.Launch
        import net.minecraftforge.fml.common.Loader
    //#endif
//#endif

object KnitClient {
    //#if MC >= 1.20.1
    //$$ val client: MinecraftClient = MinecraftClient.getInstance()
    //#else
    val client: Minecraft = Minecraft.getMinecraft()
    //#endif

    //#if MC >= 1.20.1
    //$$ val world: ClientWorld? get() = client.world
    //#else
    val world: World? get() = client.theWorld
    //#endif

    //#if MC >= 1.20.1
    //$$ val player: ClientPlayerEntity? get() = KnitPlayer.player
    //#else
    val player: EntityPlayerSP? get() = KnitPlayer.player
    //#endif

    val isFabric: Boolean get() = KnitLoader.isFabric

    val isForge: Boolean get() = KnitLoader.isForge

    val isNeoForge: Boolean get() = KnitLoader.isNeoForge

    val minecraftVersion: String by lazy {
        //#if MC >= 1.16.5
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
                //$$ SharedConstants.getGameVersion().name
                //#endif
            //#endif
        //#else
        ForgeVersion.mcVersion
        //#endif
    }

    val gameDir: Path
        get() {
            //#if FABRIC
            //$$ return FabricLoader.getInstance().gameDir
            //#else
                //#if MC >= 1.15.2
                //$$ return FMLPaths.GAMEDIR.get()
                //#else
                return Launch.minecraftHome.toPath()
                //#endif
            //#endif
        }

    val configDirectory: Path
        get() {
            //#if FABRIC
            //$$ return FabricLoader.getInstance().configDir
            //#else
                //#if MC >= 1.15.2
                //$$ return FMLPaths.CONFIGDIR.get()
                //#else
                return Loader.instance().configDir.toPath()
                //#endif
            //#endif
        }
}
//#endif