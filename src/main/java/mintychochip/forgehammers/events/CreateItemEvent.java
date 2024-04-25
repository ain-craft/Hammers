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
import java.util.function.Consumer;
import mintychochip.genesis.events.AbstractEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

public class CreateItemEvent extends AbstractEvent implements Cancellable {
  private final Player player;
  private final Location location;
  private final ItemStack item;
  private final Consumer<Collection<ItemStack>> itemConsumer;
  private Collection<ItemStack> drops;

  private boolean cancelled = false;
  public CreateItemEvent(Player player, Location location, Collection<ItemStack> drops, ItemStack item, Consumer<Collection<ItemStack>> itemConsumer) {
    this.player = player;
    this.location = location;
    this.drops = drops;
    this.item = item;
    this.itemConsumer = itemConsumer;
  }
  public void send() {
    itemConsumer.accept(drops);
  }
  public ItemStack getItem() {
    return item;
  }
  public void setDrops(Collection<ItemStack> drops) {
    this.drops = drops;
  }
  public Player getPlayer() {
    return player;
  }

  public Location getLocation() {
    return location;
  }

  public Collection<ItemStack> getDrops() {
    return drops;
  }

  @Override
  public boolean isCancelled() {
    return cancelled;
  }

  @Override
  public void setCancelled(boolean b) {
    this.cancelled = b;
  }
}
