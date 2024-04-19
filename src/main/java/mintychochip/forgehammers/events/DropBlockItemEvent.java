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
import mintychochip.forgehammers.container.HammerLike;
import mintychochip.forgehammers.strategies.TraditionalHammerStrategy.Cardinal;
import mintychochip.genesis.events.AbstractEvent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class DropBlockItemEvent extends AbstractEvent  {

  private final Location dropLocation;

  private final Cardinal cardinal;
  private Collection<ItemStack> drops;

  private final HammerLike hammerLike;

  private final ItemStack itemStack;
  public DropBlockItemEvent(Cardinal cardinal, Location dropLocation, Collection<ItemStack> drops, HammerLike hammerLike, ItemStack itemStack) {
    this.dropLocation = dropLocation;
    this.drops = drops;
    this.hammerLike = hammerLike;
    this.itemStack = itemStack;
    this.cardinal = cardinal;
  }

  public void setDrops(Collection<ItemStack> drops) {
    this.drops = drops;
  }

  public Cardinal getCardinal() {
    return cardinal;
  }

  public HammerLike getHammerLike() {
    return hammerLike;
  }

  public Collection<ItemStack> getDrops() {
    return drops;
  }

  public Location getDropLocation() {
    return dropLocation;
  }

  public ItemStack getItemStack() {
    return itemStack;
  }
}
