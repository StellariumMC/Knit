package xyz.meowing.knit.api

import java.nio.file.Path
import xyz.meowing.knit.api.loader.KnitLoader
import net.minecraft.client.Minecraft
import net.minecraft.world.World
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraftforge.common.ForgeVersion
import net.minecraft.launchwrapper.Launch
import net.minecraftforge.fml.common.Loader

object KnitClient {
    val client: Minecraft = Minecraft.getMinecraft()

    val world: World? get() = client.theWorld

    val player: EntityPlayerSP? get() = KnitPlayer.player

    val isFabric: Boolean get() = KnitLoader.isFabric

    val isForge: Boolean get() = KnitLoader.isForge

    val isNeoForge: Boolean get() = KnitLoader.isNeoForge

    val minecraftVersion: String by lazy {
        ForgeVersion.mcVersion
    }

    val gameDir: Path
        get() {
            return Launch.minecraftHome.toPath()
        }

    val configDirectory: Path
        get() {
            return Loader.instance().configDir.toPath()
        }
}