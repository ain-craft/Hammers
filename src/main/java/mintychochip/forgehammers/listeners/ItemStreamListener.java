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
  private void onDrop(final DropEvent event) {
    final Location location = event.getLocation();
    if(event.isDrop() || event.getInventory() == null) {
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
}
