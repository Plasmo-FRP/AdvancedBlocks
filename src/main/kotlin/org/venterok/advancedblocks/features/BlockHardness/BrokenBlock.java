package org.venterok.advancedblocks.features.BlockHardness;

import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockBreakAnimation;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class BrokenBlock {

    private int time;
    private int oldAnimation;
    private double damage = -1;
    private final Block block;
    private long lastDamage;

    public BrokenBlock(Block block) {
        this.time = -1;
        this.block = block;

        lastDamage = System.currentTimeMillis();
    }

    public BrokenBlock(int time, Block block) {
        this.time = time;
        this.block = block;

        lastDamage = System.currentTimeMillis();
    }

    public void incrementDamage(Player from, double multiplier){
        if(isBroken()) return;

        damage += multiplier;
        time--;

        int animation = getAnimation();

        if (animation != oldAnimation) {
            if (animation < 10) {
                sendBreakPacket(animation);
                lastDamage = System.currentTimeMillis();
            } else {
                breakBlock(from);
                return;
            }
        }

        oldAnimation = animation;
    }

    public void sendBreakPacket(int animation) {
        ((CraftServer) Bukkit.getServer()).getHandle().sendPacketNearby(null, block.getX(), block.getY(), block.getZ(), 120, ((CraftWorld) block.getLocation().getWorld()).getHandle().getDimensionKey(),
                new PacketPlayOutBlockBreakAnimation(getBlockEntityId(block), getBlockPosition(block), animation));
    }

    private int getBlockEntityId(Block block) {
        return ((block.getX() & 0xFFF) << 20 | (block.getZ() & 0xFFF) << 8) | (block.getY() & 0xFF);
    }

    public void breakBlock(Player breaker) {
        destroyBlockObject();
        //SoundPlayerUtils.playBlockSound(block);
        if (breaker == null) return;
        ((CraftPlayer) breaker).getHandle().playerInteractManager.breakBlock(getBlockPosition(block));
    }

    public void destroyBlockObject() {
        sendBreakPacket(-1);
        //  Here you have to remove your BrokenBlock using the BrokenBlocksService, on the next step

        BlockMorphUtils.playBlockSound(block);
        BlockMorphUtils.playBlockParticles(block);
        BrokenBlocksService.removeBrokenBlock(block.getLocation());
    }

    private BlockPosition getBlockPosition(Block block) {
        return new BlockPosition(block.getX(), block.getY(), block.getZ());
    }

    public boolean isBroken() {
        return getAnimation() >= 10;
    }

    public int getAnimation() {
        return (int) (damage / time * 11) - 1;
    }
}
