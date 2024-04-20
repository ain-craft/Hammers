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

package mintychochip.forgehammers.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public interface MaterialConverter {

  default List<ItemStack> drops(ItemStack itemStack, Block block) {
    List<ItemStack> drops = new ArrayList<>();
    for (ItemStack drop : block.getDrops(itemStack)) {
      final Material itemMaterial = this.getMaterial(itemStack, block, drop);
      final int amount = this.getAmount(drop, itemStack);
      drops.add(new ItemStack(itemMaterial, amount));
    }
    return drops;
  }

  private int getAmount(ItemStack drop, ItemStack itemStack) {
    int amount = drop.getAmount();
    if (itemStack.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
      amount += this.additionalDrops(itemStack.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS));
    }
    return !drop.getType().isBlock() ? amount : 1;
  }

  private int additionalDrops(int level) {
    return new Random().nextInt(level);
  }

  private Material getMaterial(ItemStack itemStack, Block block,
      ItemStack drop) {
    if (itemStack.containsEnchantment(Enchantment.SILK_TOUCH)) {
      return block.getType();
    }
    return drop.getType();
  }
}
