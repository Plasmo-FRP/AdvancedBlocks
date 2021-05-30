package org.venterok.advancedblocks.features.BlockHardness;

import net.minecraft.server.v1_16_R3.EntityPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;

public class BlockCBBListener implements Listener {

    @EventHandler
    public void onAnimation(PlayerAnimationEvent e){
        EntityPlayer entityplayer = ((CraftPlayer) e.getPlayer()).getHandle();
        Set<Material> transparentBlocks = new HashSet<>();
        transparentBlocks.add(Material.WATER);
        transparentBlocks.add(Material.AIR);
        Block block = entityplayer.getBukkitEntity().getTargetBlock(transparentBlocks, 5);
        Location blockPosition = block.getLocation();

        if (!BrokenBlocksService.isBrokenBlock(blockPosition)) return;

        Player player = entityplayer.getBukkitEntity();
        ItemStack itemStack = e.getPlayer().getItemInHand();

        double distanceX = blockPosition.getX() - entityplayer.locX();
        double distanceY = blockPosition.getY() - entityplayer.locY();
        double distanceZ = blockPosition.getZ() - entityplayer.locZ();

        if (distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ >= 1024.0D) return;
        BrokenBlock brokenBlock = BrokenBlocksService.getBrokenBlock(blockPosition);
        brokenBlock.incrementDamage(player, 1);
    }

    @EventHandler
    public void onDamage(BlockDamageEvent e){
        CustomHardnessBlock chb = CustomHardnessBlock.hardnessBlocks.get(e.getBlock().getType());
        removeSlowDig(e.getPlayer());
        BrokenBlocksService.removeBrokenBlock(e.getBlock().getLocation());
        if(chb == null) {
            return;
        }

        int time = chb.getTimePerInstrument(e.getItemInHand().getType());

        addSlowDig(e.getPlayer(), 2000000);
        BrokenBlocksService.createBrokenBlock(e.getBlock(), time);
    }

    public void addSlowDig(Player player, int duration) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, duration, -1, false, false), true);
    }

    public void removeSlowDig(Player player) {
        player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
    }
}
