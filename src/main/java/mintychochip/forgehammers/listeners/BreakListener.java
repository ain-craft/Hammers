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

import mintychochip.forgehammers.AbstractListener;
import mintychochip.forgehammers.DurabilityReducer;
import mintychochip.forgehammers.container.ForgeHammers;
import mintychochip.forgehammers.container.MaterialConverter;
import mintychochip.forgehammers.events.HammerBreakEvent;
import mintychochip.forgehammers.strategies.TraditionalHammerStrategy.Cardinal;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public final class BreakListener extends AbstractListener implements DurabilityReducer,
    MaterialConverter {
  public BreakListener(ForgeHammers instance) {
    super(instance);
  }

  /**
   * Reduces item durability after a successful break event
   * @param event
   */
  @EventHandler
  private void onBreakDeductDurability(final HammerBreakEvent event) { //any break event
    if (CommonPredicate.GAMEMODE_SURVIVAL.test(event.getPlayer())) {
      this.deductDurability(event.getItemStack());
    }
  }
  /**
   * Event is called for every hammer caused server-broken block
   *
   * @param event
   */
  @EventHandler
  private void onBreakEvent(final HammerBreakEvent event) {
    ItemStack itemStack = event.getItemStack();
    if(itemStack == null) {
      return;
    }
    event.accept(this.drops(itemStack, event.getBlock()));
    this.breakBlock(event.getBlock(), event.getCardinal());
  }
  private void breakBlock(Block block, Cardinal cardinal) {
    Location location = block.getLocation();
    Vector v = switch (cardinal) {
      case EAST_WEST -> new Vector(0, 0.2, 0.5);
      case NORTH_SOUTH -> new Vector(0.5, 0.2, 0);
      case UP_DOWN -> new Vector(0.25, 0, 0.25);
    };
    location.getWorld()
        .spawnParticle(Particle.BLOCK_CRACK, location, 10, v.getX(), v.getY(), v.getZ(), 1.0,
            block.getBlockData());
    block.setType(Material.AIR);
  }
}
