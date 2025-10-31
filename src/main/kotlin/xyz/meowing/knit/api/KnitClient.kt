package xyz.meowing.knit.api

import net.minecraft.client.MinecraftClient
import net.minecraft.client.world.ClientWorld
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.network.PlayerListEntry
import net.minecraft.text.Text
import net.minecraft.world.GameMode
import net.minecraft.SharedConstants

import java.nio.file.Path
import xyz.meowing.knit.api.loader.KnitLoader

//#if MC > 1.20.1
import net.minecraft.scoreboard.ScoreboardDisplaySlot
//#endif

//#if FABRIC
import net.fabricmc.loader.api.FabricLoader
//#elseif FORGE
    //$$ import net.minecraftforge.fml.loading.FMLPaths
//#else
    //$$ import net.neoforged.fml.loading.FMLPaths
//#endif

object KnitClient {
    private val tabListComparator: Comparator<PlayerListEntry> = compareBy(
        { it.gameMode == GameMode.SPECTATOR },
        { it.scoreboardTeam?.name ?: "" },
        { it.profile.name.lowercase() }
    )

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

    @JvmStatic
    val tablist: List<PlayerListEntry>
        get() = client.networkHandler
            ?.listedPlayerListEntries
            ?.sortedWith(tabListComparator) ?: emptyList()

    @JvmStatic
    val players: List<PlayerListEntry>
        get() = tablist.filter { it.profile.id.version() == 4 }

    //#if MC > 1.20.1
    @JvmStatic
    val scoreboard: Collection<Text>
        get() {
            val scoreboard = world?.scoreboard ?: return emptyList()
            val objective = scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR) ?: return emptyList()
            return scoreboard.getScoreboardEntries(objective)
                .sortedBy { -it.value() }
                .map {
                    val ownerName = Text.literal(it.owner())
                    val team = scoreboard.getScoreHolderTeam(it.owner())
                    if (team == null) {
                        ownerName.copy()
                    } else {
                        Text.empty().also { main ->
                            main.append(team.prefix)
                            if (ownerName.string.isNotEmpty()) main.append(ownerName)
                            main.append(team.suffix)
                        }
                    }
                }
        }

    val scoreboardTitle get() = world?.scoreboard?.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR)?.displayName
    //#endif
}