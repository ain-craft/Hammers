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

package mintychochip.forgehammers.events;

import java.util.Collection;
import mintychochip.genesis.events.AbstractEvent;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
public class DropEvent extends AbstractEvent {
  private final ItemStack itemStack;
  private Collection<ItemStack> drops;
  private final Location location;
  private Inventory inventory = null;

  private boolean drop = false;
  public DropEvent(Location location, Collection<ItemStack> drops, ItemStack itemStack) {
    this.location = location;
    this.drops = drops;
    this.itemStack = itemStack;
  }

  public void setDrop(boolean drop) {
    this.drop = drop;
  }

  public boolean isDrop() {
    return drop;
  }

  public void setInventory(Inventory inventory) {
    this.inventory = inventory;
  }

  public Inventory getInventory() {
    return inventory;
  }

  public void setDrops(Collection<ItemStack> drops) {
    this.drops = drops;
  }

  public ItemStack getItemStack() {
    return itemStack;
  }

  public Collection<ItemStack> getDrops() {
    return drops;
  }

  public Location getLocation() {
    return location;
  }
}
