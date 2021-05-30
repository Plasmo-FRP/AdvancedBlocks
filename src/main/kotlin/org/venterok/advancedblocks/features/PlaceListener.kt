package org.venterok.advancedblocks.features

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.venterok.advancedblocks.AdvancedBlocks

class PlaceListener : Listener {
    @EventHandler
    fun placingCancel(e : BlockPlaceEvent ) {
        val blocksForCancel = AdvancedBlocks.inst?.config?.getStringList("blocksForCancel")
        val block = e.blockPlaced.type
        val player = e.player
        if (blocksForCancel != null) {
            for (Block in blocksForCancel) {
                val blockObj = Material.matchMaterial(Block)
                val messageEnabled = AdvancedBlocks.inst?.config?.get("messageOnCancel.enabled")
                var message = AdvancedBlocks.inst?.config?.get("messageOnCancel.message")
                if (block == blockObj) {
                    e.isCancelled = true
                    if (messageEnabled == true)
                        message = message.toString().replace("%block%", "$block", true)
                        player.sendMessage(AdvancedBlocks.formatColor(message as String))
                }
                else
                    continue
            }
        }
    }
}