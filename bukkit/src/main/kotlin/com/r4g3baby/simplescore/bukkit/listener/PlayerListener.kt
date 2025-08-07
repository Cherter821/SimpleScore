package com.r4g3baby.simplescore.bukkit.listener

import com.r4g3baby.simplescore.bukkit.BukkitManager
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.*

class PlayerListener(private val manager: BukkitManager) : Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    fun onPlayerJoin(e: PlayerJoinEvent) {
        manager.createViewer(e.player).let { viewer ->
            manager.onViewerChangeWorld(viewer, e.player.world)
            manager.onViewerChangeLocation(viewer, e.player.location)
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onPlayerQuit(e: PlayerQuitEvent) {
        manager.removeViewer(e.player.uniqueId)
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onPlayerKick(e: PlayerKickEvent) {
        manager.removeViewer(e.player.uniqueId)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onPlayerChangedWorld(e: PlayerChangedWorldEvent) {
        manager.getViewer(e.player.uniqueId)?.let { viewer ->
            manager.onViewerChangeWorld(viewer, e.player.world)
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
    fun onPlayerMove(e: PlayerMoveEvent) {
        if (e.from.blockX == e.to.blockX && e.from.blockY == e.to.blockY && e.from.blockZ == e.to.blockZ) return

        manager.getViewer(e.player.uniqueId)?.let { viewer ->
            manager.onViewerChangeLocation(viewer, e.to)
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onPlayerTeleport(e: PlayerTeleportEvent) {
        manager.getViewer(e.player.uniqueId)?.let { viewer ->
            if (e.from.world != e.to.world) {
                manager.onViewerChangeWorld(viewer, e.to.world)
            }
            manager.onViewerChangeLocation(viewer, e.to)
        }
    }
}