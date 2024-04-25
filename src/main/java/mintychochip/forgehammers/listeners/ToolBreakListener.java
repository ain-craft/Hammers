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

import java.util.function.Predicate;
import mintychochip.forgehammers.AbstractListener;
import mintychochip.forgehammers.container.ForgeHammers;
import mintychochip.forgehammers.container.MaterialConverter;
import mintychochip.forgehammers.events.CreateItemEvent;
import mintychochip.forgehammers.events.FakeBreakEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class ToolBreakListener extends AbstractListener implements MaterialConverter {

  private static final int RANGE_ADJUSTMENT = 5;
  private final Predicate<FakeBreakEvent> blockUnbreakable = event -> {
    Material type = event.getBlock().getType();
    return type == Material.BEDROCK || type.isAir();
  };
  private final Predicate<FakeBreakEvent> blockHardness = event ->
      event.getHardness() + RANGE_ADJUSTMENT <= event.getBlock().getType().getHardness();

  public ToolBreakListener(ForgeHammers instance) {
    super(instance);
  }

  @EventHandler(priority = EventPriority.HIGH)
  private void checkIfBreakable(final FakeBreakEvent event) {
    if (event.isCancelled()) {
      return;
    }
    if (blockUnbreakable.or(blockHardness).test(event)) {
      event.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  private void onBreak(final FakeBreakEvent event) {
    if (event.isCancelled()) {
      return;
    }
    Bukkit.getPluginManager().callEvent(new CreateItemEvent(event.getPlayer(), event.getBlock()
        .getLocation(), this.drops(event.getItem(), event.getBlock()), event.getItem(),
        event::accept));

    Block block = event.getBlock();
    Location location = block.getLocation();
    location.getWorld()
        .spawnParticle(Particle.BLOCK_CRACK, location.add(0.5, 0.5, 0.5), 10, 0, 0, 0, 1.0,
            block.getBlockData());
    block.setType(Material.AIR);
  }

  @EventHandler(priority = EventPriority.MONITOR)
  private void onCreateItem(final CreateItemEvent event) {
    if(event.isCancelled()) {
      return;
    }
    event.send();
  }
}
