package org.venterok.advancedblocks.features

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

class DoorListener: Listener {
    @EventHandler
    fun placing(e: BlockPlaceEvent) {
        val block = e.block
        when (e.blockReplacedState.type) {
            Material.WATER -> {}
            Material.SEAGRASS -> {}
            Material.TALL_SEAGRASS -> {}
            else -> return
        }
        when (block.type) {
            Material.ACACIA_DOOR -> e.isCancelled = true
            Material.OAK_DOOR -> e.isCancelled = true
            Material.CRIMSON_DOOR -> e.isCancelled = true
            Material.BIRCH_DOOR -> e.isCancelled = true
            Material.DARK_OAK_DOOR -> e.isCancelled = true
            Material.JUNGLE_DOOR -> e.isCancelled = true
            Material.SPRUCE_DOOR -> e.isCancelled = true
            Material.WARPED_DOOR -> e.isCancelled = true
            Material.WALL_TORCH -> e.isCancelled = true
            Material.TORCH -> e.isCancelled = true
            else -> return
        }
    }
}