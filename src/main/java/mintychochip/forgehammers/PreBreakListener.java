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

package mintychochip.forgehammers;

import java.util.function.Predicate;
import mintychochip.forgehammers.container.ForgeHammers;
import mintychochip.forgehammers.container.Hammer;
import mintychochip.forgehammers.events.HammerBreakEvent;
import mintychochip.forgehammers.events.HammerPreBreakEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public final class PreBreakListener extends AbstractListener {

  private final Predicate<HammerPreBreakEvent> blockIsUnbreakable = event -> {
    Material type = event.getBlock().getType();
    return type == Material.BEDROCK;
  };
  private final Predicate<HammerPreBreakEvent> blockTooHard = event -> {
    Material type = event.getBlock().getType();
    return type.getHardness() > event.getHammer().getStrength();
  };

  private final Predicate<HammerPreBreakEvent> blockBlacklisted = event -> event.getHammer()
      .blockBlacklisted(event.getBlock());
  private final Predicate<HammerPreBreakEvent> blockIsOre = event -> {
    Material type = event.getBlock().getType();
    return Constants.ORE_MATERIALS.contains(type);
  };
  private final Predicate<HammerPreBreakEvent> blockIsLowerThanOriginHardness = event -> {
    float originHardness = event.getOriginHardness();
    return originHardness < event.getBlock().getType().getHardness();
  };
  private final Predicate<HammerPreBreakEvent> check = blockIsUnbreakable.or(blockTooHard)
      .or(blockBlacklisted).or(blockIsOre).or(blockIsLowerThanOriginHardness);

  public PreBreakListener(ForgeHammers instance) {
    super(instance);
  }

  /**
   * Is called if the event is not cancelled
   *
   * @param event
   */
  @EventHandler(priority = EventPriority.MONITOR)
  private void initializeBreakEvent(final HammerPreBreakEvent event) {
    if (event.isCancelled()) {
      return;
    }
    Bukkit.getPluginManager()
        .callEvent(new HammerBreakEvent(event.getPlayer(), event.getItemStack(), event.getBlock()));
  }

  @EventHandler(priority = EventPriority.HIGH)
  private void checkHandler(final HammerPreBreakEvent event) {
    if (event.isCancelled()) {
      return;
    }
    if (check.test(event)) {
      event.setCancelled(true);
    }
  }
}
