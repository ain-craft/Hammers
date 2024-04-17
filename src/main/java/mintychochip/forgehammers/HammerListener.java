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

import mintychochip.forgehammers.container.ForgeHammers;
import mintychochip.forgehammers.container.Hammer;
import mintychochip.forgehammers.events.HammerPreBreakEvent;
import mintychochip.forgehammers.strategies.StrategySelector;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class HammerListener extends AbstractListener implements StrategySelector {

  private final ForgeHammers instance;

  private final Grasper grasper;

  public HammerListener(ForgeHammers instance, Grasper grasper) {
    super(instance);
    this.instance = instance;
    this.grasper = grasper;
  }

  @EventHandler
  private void breakBlocks(final BlockBreakEvent event) {
    Player player = event.getPlayer();
    PlayerInventory inventory = player.getInventory();
    ItemStack itemInUse = grasper.getItemInUse(inventory.getItemInMainHand(),
        inventory.getItemInOffHand());
    if (itemInUse.getType() == Material.AIR) {
      return;
    }
    Hammer grab = grasper.grab(itemInUse);
    if (grab == null) {
      return;
    }
    final Block origin = event.getBlock();
    final float originHardness = origin.getType().getHardness();
    this.selectStrategy(grab).accept(origin.getLocation(), player, grab, block -> {
      Bukkit.getPluginManager().callEvent(new HammerPreBreakEvent(block,player, grab, itemInUse,
          originHardness));
    });
  }

}
