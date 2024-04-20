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
import mintychochip.genesis.events.AbstractEvent;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

public class PreBlockDropEvent extends AbstractEvent implements Cancellable {

  private boolean cancelled = false;
  private final Location dropLocation;
  private Collection<ItemStack> drops;

  private final HammerLike hammerLike;
  private final Player player;


  public PreBlockDropEvent(Location dropLocation, Collection<ItemStack> drops,
      HammerLike hammerLike, Player player) {
    this.dropLocation = dropLocation;
    this.drops = drops;
    this.hammerLike = hammerLike;
    this.player = player;
  }

  public Player getPlayer() {
    return player;
  }

  public Location getDropLocation() {
    return dropLocation;
  }

  public void setDrops(Collection<ItemStack> drops) {
    this.drops = drops;
  }

  public Collection<ItemStack> getDrops() {
    return drops;
  }

  public HammerLike getHammerLike() {
    return hammerLike;
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
