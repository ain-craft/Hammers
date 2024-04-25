/*
 *
 *  Copyright (C) 2024 mintychochip
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package mintychochip.forgehammers.container.gem.sub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mintychochip.forgehammers.container.gem.Gem;
import mintychochip.forgehammers.container.gem.GemAnno;
import mintychochip.forgehammers.container.gem.GemAnno.ExecutionPriority;
import mintychochip.forgehammers.container.gem.GemEnum;
import mintychochip.forgehammers.container.gem.sub.triggers.TriggerOnDropCreation;
import mintychochip.forgehammers.events.CreateItemEvent;
import mintychochip.genesis.util.Rarity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;

public class AutoSmelt extends Gem implements TriggerOnDropCreation {

  private Map<Material, Material> smeltables = new HashMap<>();

  public AutoSmelt(GemEnum gemEnum, String name,
      String description, int min, int max, Rarity rarity) {
    super(gemEnum, name, description, min, max, rarity);
    smeltables.put(Material.RAW_IRON, Material.IRON_INGOT);
    smeltables.put(Material.RAW_GOLD, Material.GOLD_INGOT);
    smeltables.put(Material.RAW_COPPER, Material.COPPER_INGOT);
    smeltables.put(Material.COBBLED_DEEPSLATE,Material.DEEPSLATE);
    smeltables.put(Material.COBBLESTONE,Material.STONE);
    smeltables.put(Material.NETHERRACK,Material.NETHER_BRICK);
    smeltables.put(Material.SAND,Material.GLASS);
    smeltables.put(Material.BIRCH_LOG,Material.CHARCOAL);
  }

  @Override
  @GemAnno(priority = ExecutionPriority.LOW)
  public void execute(CreateItemEvent event, int level) {
    boolean smelted = false;
    List<ItemStack> smeltedDrops = new ArrayList<>();
    for (ItemStack drop : event.getDrops()) {
      if (this.smeltable(drop.getType())) {
        smelted = true;
      }
      final Material material = this.smelt(drop.getType());
      final int amount = drop.getAmount();
      smeltedDrops.add(new ItemStack(material, amount));
    }
    event.setDrops(smeltedDrops);

    if (smelted) {
      Location blockLocation = event.getLocation();
      blockLocation.getWorld().spawnParticle(Particle.LAVA, blockLocation.add(0.5,0.5,0.5), 1, 0, 0, 0, 5);
    }
  }
  private boolean smeltable(Material material) {
    return smeltables.containsKey(material);
  }

  private Material smelt(Material material) {
    Material smelted = smeltables.get(material);
    return smelted == null || smelted.isAir() ? material : smelted;
  }
//  private Material smelt(Material material) {
//    boolean smelt = false;
//    mat -> switch(mat) {
//      case RAW_IRON -> Material.IRON_INGOT;
//      case RAW_GOLD -> Material.GOLD_INGOT;
//      case RAW_COPPER -> Material.COPPER_INGOT;
//      case ANCIENT_DEBRIS -> Material.NETHERITE_SCRAP;
//      case SAND -> Material.GLASS;
//      case COBBLESTONE -> Material.STONE;
//      case NETHERRACK -> Material.NETHER_BRICK;
//      case CLAY -> Material.TERRACOTTA;
//      case DEEPSLATE_EMERALD_ORE -> Material.EMERALD;
//      case DEEPSLATE_LAPIS_ORE -> Material.LAPIS_LAZULI;
//      case DEEPSLATE_DIAMOND_ORE -> Material.DIAMOND;
//      case DEEPSLATE_REDSTONE_ORE -> Material.REDSTONE;
//      default -> material;
//    }
//  }
}
