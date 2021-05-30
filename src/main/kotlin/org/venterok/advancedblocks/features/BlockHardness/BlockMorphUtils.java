package org.venterok.advancedblocks.features.BlockHardness;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;

import java.util.concurrent.ThreadLocalRandom;

public class BlockMorphUtils {

    public static void playBlockSound(Block block){
        Sound sound = block.getBlockData().getSoundGroup().getBreakSound();
        block.getWorld().playSound(block.getLocation(), sound, SoundCategory.BLOCKS,1, 1f);
    }


    public static void playBlockParticles(Block block){
        block.getWorld().spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(0.5, 0.5, 0.5), 50, 0.24, 0.24, 0.25, 5, block.getBlockData());
    }

}
