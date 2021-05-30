package org.venterok.advancedblocks.features.BlockHardness;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.venterok.advancedblocks.AdvancedBlocks;

import java.util.HashMap;

public class CustomHardnessBlock {

    Material material;
    int time;

    static HashMap<Material, CustomHardnessBlock> hardnessBlocks = new HashMap<>();
    HashMap<Material, PerInstrumentTime> perInstrumentTimeHashMap = new HashMap<>();

    public static void load(){
        hardnessBlocks.clear();

        FileConfiguration config = AdvancedBlocks.Companion.getInst().getConfig();
        ConfigurationSection cs = config.getConfigurationSection("blockHardness");

        for(String materialS : cs.getKeys(false)){
            Material material = Material.matchMaterial(materialS);
            if(material == null || hardnessBlocks.get(material) != null) continue;

            int time = cs.getInt(materialS + ".defaultTime");

            CustomHardnessBlock customHardnessBlock = new CustomHardnessBlock(material, time);

            for(String instrument : cs.getConfigurationSection(materialS + ".instruments").getKeys(false)){
                Material materialI = Material.matchMaterial(instrument);
                if(materialI == null) continue;

                int timeI = cs.getInt(materialS + ".instruments." + instrument + ".time");

                customHardnessBlock.addTimePerInstrument(materialI, timeI);
            }

            hardnessBlocks.put(material, customHardnessBlock);
        }
    }

    public int getTimePerInstrument(Material material){
        PerInstrumentTime perInstrumentTime = perInstrumentTimeHashMap.get(material);
        if(perInstrumentTime == null) return getTime();

        return perInstrumentTime.time;
    }

    public void addTimePerInstrument(Material material, int titime){
        perInstrumentTimeHashMap.put(material, new PerInstrumentTime(titime, material));
    }

    public CustomHardnessBlock(Material material, int time){
        this.material = material;
        this.time = time;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
