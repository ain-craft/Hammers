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

package mintychochip.forgehammers.listeners;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import mintychochip.forgehammers.AbstractListener;
import mintychochip.forgehammers.container.ForgeHammers;
import mintychochip.forgehammers.events.DropEvent;
import mintychochip.forgehammers.events.MergeEvent;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemStreamListener extends AbstractListener {

  public ItemStreamListener(ForgeHammers instance) {
    super(instance);
  }

  @EventHandler(priority = EventPriority.MONITOR)
  private void merge(final MergeEvent event) {
    event.accept(this.mergeItemStacks(event.getDrops()));
  }
  @EventHandler(priority = EventPriority.MONITOR)
  private void onDrop(final DropEvent event) {
    final Location location = event.getLocation();
    if(event.isDrop()) {
      this.drop(event.getDrops(),location);
    } else {
      Inventory inventory = event.getInventory();
      List<ItemStack> remaining = new ArrayList<>();
      event.getDrops().forEach(drop -> {
        HashMap<Integer, ItemStack> integerItemStackHashMap = inventory
            .addItem(drop);
        if (!integerItemStackHashMap.isEmpty()) {
          remaining.add(drop);
        }
      });
      if(!remaining.isEmpty()) {
        this.drop(remaining,location);
      }
    }
  }

  private void drop(Collection<ItemStack> drops, Location location) {
    drops.forEach(drop -> location.getWorld().dropItemNaturally(location,drop));
  }

  public Collection<ItemStack> mergeItemStacks(Collection<ItemStack> itemStacks) {
    ArrayList<ItemStack> sorted = new ArrayList<>();
    for (ItemStack item : itemStacks) {
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
