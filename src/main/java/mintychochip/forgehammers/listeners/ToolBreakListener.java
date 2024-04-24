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
import mintychochip.forgehammers.events.FakeBlockDropItemEvent;
import mintychochip.forgehammers.events.ToolBreakEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class ToolBreakListener extends AbstractListener implements MaterialConverter {

  private static final int RANGE_ADJUSTMENT = 5;
  private final Predicate<ToolBreakEvent> blockIsUnbreakable = event -> {
    Material type = event.getBlock().getType();
    return type == Material.BEDROCK;
  };
  private final Predicate<ToolBreakEvent> blockIsNotWhitelisted = event -> !event.getTool()
      .blockWhitelisted(event.getBlock());
  private final Predicate<ToolBreakEvent> check = blockIsUnbreakable
      .or(blockIsNotWhitelisted);

  public ToolBreakListener(ForgeHammers instance) {
    super(instance);
  }

  @EventHandler(priority = EventPriority.HIGH)
  private void checkIfBreakable(final ToolBreakEvent event) {
    if (event.isCancelled()) {
      return;
    }
    if (check.test(event)) {
      event.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  private void onBreak(final ToolBreakEvent event) {
    if (event.isCancelled()) {
      return;
    }
    Bukkit.getPluginManager().callEvent(new FakeBlockDropItemEvent(event.getPlayer(),
        event.getBlock().getLocation().add(0.5, 0.5, 0.5),
        this.drops(event.getItem(), event.getBlock()), event.getItem()));
    this.breakBlock(event.getBlock());
  }

  @EventHandler(priority = EventPriority.MONITOR)
  private void blockDrops(final FakeBlockDropItemEvent event) {
    if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
      return;
    }

    Bukkit.getScheduler().runTaskLater(ForgeHammers.getInstance(), x -> {
      Location location = event.getLocation();
      event.getDrops().forEach(
          drop -> location.getWorld().dropItemNaturally(location, drop));
    }, 2L);
  }

  private void breakBlock(Block block) {
    Location location = block.getLocation();
    location.getWorld()
        .spawnParticle(Particle.BLOCK_CRACK, location.add(0.5, 0.5, 0.5), 10, 0, 0, 0, 1.0,
            block.getBlockData());
    block.setType(Material.AIR);
  }
}
