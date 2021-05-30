package org.venterok.advancedblocks

import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Item
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

class DropListener : Listener {
    @EventHandler
    fun dropseed(e : PlayerDropItemEvent) {
        val dropItem = e.itemDrop.itemStack.type
        val splitItems = AdvancedBlocks.inst?.config?.getStringList("dropEvent.items")
        if (splitItems != null) {
            if (splitItems.contains(dropItem.name.capitalize())) {
                    val amountDrop = e.itemDrop.itemStack.amount
                    if (amountDrop != 1) {
                        val splitMultiplier = AdvancedBlocks.inst?.config?.get("dropEvent.splitMultiplier") as Int
                        val maxMinusValue = AdvancedBlocks.inst?.config?.get("dropEvent.maxMinusValue") as Int
                        val minMinusValue = AdvancedBlocks.inst?.config?.get("dropEvent.minMinusValue") as Int
                        val randomValue = List(1) { Random.nextInt(minMinusValue, maxMinusValue) }
                        val value = randomValue[0]
                        e.itemDrop.itemStack.amount = amountDrop/splitMultiplier - value
                    }
                    else
                        e.itemDrop.remove()
            }
            else
                return
        }
    }
}
