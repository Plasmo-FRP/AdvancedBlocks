package org.venterok.advancedblocks

import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.io.File
import java.util.regex.Matcher
import java.util.regex.Pattern

class AdvancedBlocks : JavaPlugin() {
    override fun onEnable() {
        setUpConfig()
        inst = this
        val fixPlacingIntheWater = config.get("doorFixer.enabled")
        if (fixPlacingIntheWater == true) {
            Bukkit.getPluginManager().registerEvents( DoorListener(),this )
        }
        val changeDropSeed = config.get("dropEvent.enabled")
        if (changeDropSeed == true) {
            Bukkit.getPluginManager().registerEvents( DropListener(),this )
        }
        Bukkit.getPluginManager().registerEvents( ChestListener(),this )
        val boatBreak = BoatBreak()
        val mudBlocks = MudBlocks()
        Bukkit.getPluginManager().registerEvents( boatBreak,this )
        boatBreak.timer()
        mudBlocks.timer()
        Bukkit.getPluginManager().registerEvents( PlaceListener(),this )
    }
    override fun onDisable() {

    }
    companion object {
        private val pattern: Pattern = Pattern.compile("#[a-fA-F0-9]{6}")
        fun formatColor(msg: String): String {
            var msg = msg
            var matcher: Matcher = pattern.matcher(msg)
            while (matcher.find()) {
                val color = msg.substring(matcher.start(), matcher.end())
                msg = msg.replace(color, ChatColor.of(color).toString() + "")
                matcher = pattern.matcher(msg)
            }
            return ChatColor.translateAlternateColorCodes('&', msg)
        }
        var inst: AdvancedBlocks? = null
    }
    private fun setUpConfig(): File? {
        val config = File(dataFolder.toString() + File.separator + "config.yml")
        if (!config.exists()) {
            logger.info("Creating config file...")
            getConfig().options().copyDefaults(true)
            saveDefaultConfig()
        }
        return config
    }
}