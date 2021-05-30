package org.venterok.advancedblocks

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable


class MudBlocks {
    fun timer() {
        val mudBlockList = AdvancedBlocks.inst?.config?.getStringList("mudBlock.blockList")
        object: BukkitRunnable() {
            override fun run() {
                val worldweather = Bukkit.getWorlds()[0].hasStorm()
                if (!worldweather) return
                val players = Bukkit.getOnlinePlayers()
                for (Player in players) {
                    val pl = Player is org.bukkit.entity.Player
                    val underplayerblock = Player.location.block.getRelative(BlockFace.DOWN).type
                    var leveleffect = AdvancedBlocks.inst?.config?.getInt("mudBlock.effectLevel")
                    if (mudBlockList != null) {
                        if (mudBlockList.contains(underplayerblock.name.capitalize())) Player.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 60, 2))
                    }
                }
            }
        }.runTaskTimer(AdvancedBlocks.inst!!, 0, 20)
    }
}