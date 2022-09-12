package com.r4g3baby.simplescore.scoreboard.placeholders

import com.r4g3baby.simplescore.SimpleScore
import org.bukkit.entity.Player
import java.util.logging.Level

object PlaceholderReplacer {
    private var lastException = System.currentTimeMillis()

    fun replace(input: String, player: Player): String {
        var result = input
        if (SimpleScore.usePlaceholderAPI) {
            result = applyPlaceholderAPI(result, player)
        }
        if (SimpleScore.useMVdWPlaceholderAPI) {
            result = applyMVdWPlaceholderAPI(result, player)
        }
        return result
    }

    private fun applyPlaceholderAPI(input: String, player: Player): String {
        try {
            return me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, input)
        } catch (ex: Exception) {
            if ((System.currentTimeMillis() - lastException) > 10 * 1000) {
                lastException = System.currentTimeMillis()
                SimpleScore.plugin.logger.log(
                    Level.WARNING, "Could not apply PlaceholderAPI placeholders", ex
                )
            }
        }
        return input
    }

    private fun applyMVdWPlaceholderAPI(input: String, player: Player): String {
        try {
            return be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(player, input)
        } catch (ex: Exception) {
            if ((System.currentTimeMillis() - lastException) > 10 * 1000) {
                lastException = System.currentTimeMillis()
                SimpleScore.plugin.logger.log(
                    Level.WARNING, "Could not apply MVdWPlaceholderAPI placeholders", ex
                )
            }
        }
        return input
    }

    fun String.replacePlaceholders(player: Player): String {
        return replace(this, player)
    }
}