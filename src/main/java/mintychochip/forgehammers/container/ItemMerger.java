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
import org.bukkit.inventory.ItemStack;

public class ItemMerger {

  public static Collection<ItemStack> merge(Collection<ItemStack> drops) {
    ArrayList<ItemStack> sorted = new ArrayList<>();
    for (ItemStack item : drops) {
      if (item == null) {
        continue;
      }
      boolean putInPlace = false;
      for (ItemStack sitem : sorted) {
        if (item.isSimilar(sitem)) {
          int maxStackSize = sitem.getMaxStackSize();
          int totalAmount = sitem.getAmount() + item.getAmount();
          if (totalAmount <= maxStackSize) {
            sitem.setAmount(totalAmount);
            putInPlace = true;
            break;
          } else if (sitem.getAmount() < maxStackSize) {
            int amountToAdd = maxStackSize - sitem.getAmount();
            sitem.setAmount(maxStackSize);
            item.setAmount(item.getAmount() - amountToAdd);
          }
        }
      }
      if (!putInPlace) {
        sorted.add(item.clone());
      }
    }
    return sorted;
  }
}
