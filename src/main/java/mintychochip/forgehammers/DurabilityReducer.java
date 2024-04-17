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

package mintychochip.forgehammers;

import java.util.Random;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public interface DurabilityReducer {
  default void deductDurability(ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    if(itemMeta instanceof Damageable damageable) {
      damageable.setDamage(damageable.getDamage() + this.getDamageByUnbreaking(itemStack));
      itemStack.setItemMeta(itemMeta);
    }
  }

  private int getDamageByUnbreaking(ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    int enchantLevel = itemMeta.getEnchantLevel(Enchantment.DURABILITY);
    if(enchantLevel == 0) {
      return 1;
    }
    return new Random().nextDouble() <= (double) 1 / (1 + enchantLevel) ? 1 : 0;
  }
}
