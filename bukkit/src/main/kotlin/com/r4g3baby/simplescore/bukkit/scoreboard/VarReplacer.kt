package com.r4g3baby.simplescore.bukkit.scoreboard

import com.r4g3baby.simplescore.BukkitPlugin
import com.r4g3baby.simplescore.api.scoreboard.VarReplacer
import com.r4g3baby.simplescore.bukkit.util.Adventure
import com.r4g3baby.simplescore.bukkit.util.lazyReplace
import com.r4g3baby.simplescore.core.util.translateColorCodes
import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.entity.Player
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

class VarReplacer(plugin: BukkitPlugin) : VarReplacer<Player> {
    private val usePlaceholderAPI = plugin.server.pluginManager.getPlugin("PlaceholderAPI") != null

    override fun replace(text: String, viewer: Player): String {
        var result = if (usePlaceholderAPI) PlaceholderAPI.setPlaceholders(viewer, text) else text

        result = result.lazyReplace("%player_name%") { viewer.name }
            .lazyReplace("%player_displayName%") { viewer.displayName }
            .lazyReplace("%player_uuid%") { viewer.uniqueId.toString() }
            .lazyReplace("%player_level%") { viewer.level.toString() }
            .lazyReplace("%player_gameMode%") { viewer.gameMode.name.lowercase().replaceFirstChar { it.titlecase() } }
            .lazyReplace("%player_health%") { viewer.health.roundToInt().toString() }
            .lazyReplace("%player_maxHealth%") { viewer.maxHealth.roundToInt().toString() }
            .lazyReplace("%player_hearts%") {
                val hearts = min(10, max(0, ((viewer.health / viewer.maxHealth) * 10).roundToInt()))
                "<red>${"❤".repeat(hearts)}<black>${"❤".repeat(10 - hearts)}"
            }
            .lazyReplace("%server_online%") { viewer.server.onlinePlayers.size.toString() }
            .lazyReplace("%server_maxPlayers%") { viewer.server.maxPlayers.toString() }
            .lazyReplace("%world_name%") { viewer.world.name }
            .lazyReplace("%world_online%") { viewer.world.players.size.toString() }

        result = Adventure.parseToString(result)
        return translateColorCodes(result)
    }
}