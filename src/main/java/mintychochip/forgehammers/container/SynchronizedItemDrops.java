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
import java.util.HashMap;
import java.util.Map;
import mintychochip.forgehammers.events.MergeEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class SynchronizedItemDrops {

  public static SynchronizedItemDrops INSTANCE = new SynchronizedItemDrops();
  Map<Location,Map<ItemStack,Collection<ItemStack>>> itemStacks = new HashMap<>();

  private SynchronizedItemDrops() {

  }

  public Map<Location, Map<ItemStack, Collection<ItemStack>>> getItemStacks() {
    return itemStacks;
  }

  public void addEntry(Location location, Collection<ItemStack> drops, ItemStack itemStack) {
    Map<ItemStack, Collection<ItemStack>> itemStackCollectionMap = itemStacks.get(location);
    if(itemStackCollectionMap == null) {
      itemStackCollectionMap = new HashMap<>();
    }
    Collection<ItemStack> stacks = itemStackCollectionMap.get(itemStack);
    if(stacks == null) {
      stacks = new ArrayList<>();
    }
    stacks.addAll(drops);
    itemStackCollectionMap.put(itemStack,stacks);
    itemStacks.put(location,itemStackCollectionMap);
  }
}
