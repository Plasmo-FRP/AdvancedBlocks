package org.venterok.advancedblocks.features

import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.block.Chest
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.Inventory
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import org.venterok.advancedblocks.AdvancedBlocks

class ChestListener: Listener {

    private val mapPlayerInChest = HashMap<Player, Inventory>()
    private val mapPlayerAndBukkitTask = HashMap<Player, BukkitTask>()

    @EventHandler
    fun ChestWater(e : PlayerInteractEvent){
        val blockChest = e.clickedBlock
        if(e.action != Action.RIGHT_CLICK_BLOCK) return

        if (blockChest != null && blockChest.type == Material.CHEST) {
            val upBlock = blockChest.getRelative(0,1,0).type
            if (upBlock == Material.WATER) {
                val chest = blockChest.state as Chest
                /*
                for (item in chest.blockInventory.contents) {
                    chest.location.world?.dropItem(chest.location.add(0.0, 1.0, 0.0), item)

                    chest.location.world?.spawnParticle(Particle.BUBBLE_COLUMN_UP, chest.location.add(0.0, 1.0, 0.0), 5)
                }
                 */
                mapPlayerInChest[e.player] = chest.blockInventory

                val task = object : BukkitRunnable() {
                    var slot = 0

                    override fun run() {
                        if(slot > chest.blockInventory.size - 1){
                            cancel();
                            mapPlayerInChest.remove(e.player)
                            mapPlayerAndBukkitTask.remove(e.player)
                            return
                        }

                        var item = chest.blockInventory.getItem(slot)
                        while((item == null || item.type.isAir) && slot < chest.blockInventory.size - 1){
                            slot++
                            item = chest.blockInventory.getItem(slot)
                        }
                        if(item == null){
                            mapPlayerInChest.remove(e.player)
                            mapPlayerAndBukkitTask.remove(e.player)
                            cancel()
                            return
                        }

                        chest.location.world?.dropItem(chest.location.add(0.0, 1.0, 0.0), item)
                        chest.location.world?.spawnParticle(Particle.BUBBLE_COLUMN_UP, chest.location.add(0.0, 1.0, 0.0), 5)
                        chest.blockInventory.removeItem(item)
                        e.player.updateInventory()

                        slot++
                    }
                }.runTaskTimer(AdvancedBlocks.inst!!, 10, 5)

                mapPlayerAndBukkitTask[e.player] = task
            }
        }
    }

    @EventHandler
    fun inventoryClose(e : InventoryCloseEvent){
        val inventoryClosed = e.inventory
        val player = e.player

        val invFromMap = mapPlayerInChest[player]
        if(inventoryClosed != invFromMap){
            return
        }

        val task = mapPlayerAndBukkitTask[e.player] ?: return
        task.cancel()

    }
}