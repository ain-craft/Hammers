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

import mintychochip.forgehammers.container.gem.Gem;
import mintychochip.forgehammers.container.gem.GemAnno;
import mintychochip.forgehammers.container.gem.GemAnno.ExecutionPriority;
import mintychochip.forgehammers.container.gem.GemEnum;
import mintychochip.forgehammers.container.gem.sub.triggers.TriggerOnBlockDrop;
import mintychochip.forgehammers.events.PreBlockDropEvent;
import mintychochip.genesis.util.Rarity;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
public class AutoSmelt extends Gem implements TriggerOnBlockDrop {
  public AutoSmelt(GemEnum gemEnum, String name,
      String description, int min, int max, Rarity rarity) {
    super(gemEnum, name, description, min, max, rarity);
  }
  @Override
  @GemAnno (priority = ExecutionPriority.LOW)
  public void execute(PreBlockDropEvent event) {
    event.setDrops(event.getDrops().stream()
        .map(drop -> new ItemStack(this.getSmeltedItemType(drop.getType()), drop.getAmount()))
        .toList());
  }
  private Material getSmeltedItemType(Material material) {
    return switch (material) {
      case RAW_IRON -> Material.IRON_INGOT;
      case RAW_GOLD -> Material.GOLD_INGOT;
      case RAW_COPPER -> Material.COPPER_INGOT;
      case ANCIENT_DEBRIS -> Material.NETHERITE_SCRAP;
      case SAND -> Material.GLASS;
      case COBBLESTONE -> Material.STONE;
      case NETHERRACK -> Material.NETHER_BRICK;
      case CLAY -> Material.TERRACOTTA;
      case DEEPSLATE_EMERALD_ORE -> Material.EMERALD;
      case DEEPSLATE_LAPIS_ORE -> Material.LAPIS_LAZULI;
      case DEEPSLATE_DIAMOND_ORE -> Material.DIAMOND;
      case DEEPSLATE_REDSTONE_ORE -> Material.REDSTONE;
      default -> material;
    };
  }
}
