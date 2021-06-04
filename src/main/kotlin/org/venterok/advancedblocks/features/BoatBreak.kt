package org.venterok.advancedblocks.features

import org.bukkit.Material
import org.bukkit.TreeSpecies
import org.bukkit.entity.Boat
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import org.spigotmc.event.entity.EntityDismountEvent
import org.spigotmc.event.entity.EntityMountEvent
import org.venterok.advancedblocks.AdvancedBlocks

class BoatBreak : Listener {
    val boatlist = mutableListOf<Entity>()

    @EventHandler
    fun boatMount(e : EntityMountEvent) {
        val boat = e.mount
        if (boat.type != EntityType.BOAT) return
        if (boatlist.contains(boat)) return
        boatlist.add(boat)
    }
    @EventHandler
    fun boatDisMount(e : EntityDismountEvent) {
        val boat = e.dismounted
        if (boat.type != EntityType.BOAT) return
        boatlist.remove(boat)
    }
    fun timer() {
        object: BukkitRunnable(){
            override fun run() {
                if (boatlist.isEmpty()) return
                for (boat in boatlist) {
                    val boatObj = boat as Boat
                    if (!boatObj.isInWater) {
                        val boatLoc = boatObj.location
                        if (boatObj.passengers.isEmpty()) continue
                        when (boatObj.woodType) {
                            TreeSpecies.ACACIA -> boatLoc.world?.dropItem(boat.location.add(0.0, 1.0, 0.0), ItemStack(Material.ACACIA_BOAT))
                            TreeSpecies.DARK_OAK -> boatLoc.world?.dropItem(boat.location.add(0.0, 1.0, 0.0), ItemStack(Material.DARK_OAK_BOAT))
                            TreeSpecies.BIRCH -> boatLoc.world?.dropItem(boat.location.add(0.0, 1.0, 0.0), ItemStack(Material.BIRCH_BOAT))
                            TreeSpecies.GENERIC -> boatLoc.world?.dropItem(boat.location.add(0.0, 1.0, 0.0), ItemStack(Material.OAK_BOAT))
                            TreeSpecies.JUNGLE -> boatLoc.world?.dropItem(boat.location.add(0.0, 1.0, 0.0), ItemStack(Material.JUNGLE_BOAT))
                            TreeSpecies.REDWOOD -> boatLoc.world?.dropItem(boat.location.add(0.0, 1.0, 0.0), ItemStack(Material.SPRUCE_BOAT))
                        }
                        boatObj.remove()
                    }
                }

            }
        }.runTaskTimer(AdvancedBlocks.inst!!, 0, 20)

    }
}
