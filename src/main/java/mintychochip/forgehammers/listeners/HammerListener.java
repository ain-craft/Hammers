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
import mintychochip.forgehammers.Constants;
import mintychochip.forgehammers.Grasper;
import mintychochip.forgehammers.container.BlockFaceGrabber;
import mintychochip.forgehammers.container.ForgeHammers;
import mintychochip.forgehammers.container.HammerLike;
import mintychochip.forgehammers.container.gem.GemContainer;
import mintychochip.forgehammers.container.gem.GemGrasper;
import mintychochip.forgehammers.events.HammerBreakEvent;
import mintychochip.forgehammers.events.HammerPreBreakEvent;
import mintychochip.forgehammers.events.PreBlockDropEvent;
import mintychochip.forgehammers.strategies.HammerStrategySelector;
import mintychochip.forgehammers.strategies.StrategySelector;
import mintychochip.forgehammers.strategies.TraditionalHammerStrategy.Cardinal;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class HammerListener extends AbstractListener implements HammerStrategySelector,
    BlockFaceGrabber {

  private final ForgeHammers instance;

  private final GemGrasper gemGrasper;
  private final Grasper grasper;


  public HammerListener(ForgeHammers instance, Grasper grasper, GemGrasper gemGrasper) {
    super(instance);
    this.instance = instance;
    this.gemGrasper = gemGrasper;
    this.grasper = grasper;
  }

  @EventHandler(priority = EventPriority.MONITOR)
  private void hammerBreakBlocks(final BlockBreakEvent event) {
    Player player = event.getPlayer();
    PlayerInventory inventory = player.getInventory();
    ItemStack item = grasper.getItemInUse(inventory.getItemInMainHand(),
        inventory.getItemInOffHand());
    if (item.getType() == Material.AIR) {
      return;
    }
    HammerLike grab = grasper.grab(item);
    if (grab == null) {
      return;
    }
    Cardinal cardinal = this.getCardinal(player);
    if (cardinal == null) {
      return;
    }
    GemContainer gemContainer = gemGrasper.grab(item.getItemMeta(), Constants.GEM_CONTAINER);
    event.setDropItems(false);
    final Block origin = event.getBlock();
    final float hardness = origin.getType().getHardness();
    Bukkit.getPluginManager().callEvent(
        new PreBlockDropEvent(origin.getLocation(), origin.getDrops(), grab, gemContainer
            , event.getPlayer()));
    if (!grab.blockWhitelisted(origin)) {
      return;
    }
    this.selectStrategy(grab).accept(cardinal, origin.getLocation(),
        grab, block -> {
          if (!block.equals(origin)) {
            Bukkit.getPluginManager()
                .callEvent(new HammerPreBreakEvent(block, cardinal, player, grab, item,
                    hardness, drops -> {
                  Bukkit.getPluginManager()
                      .callEvent(
                          new PreBlockDropEvent(block.getLocation(), drops, grab, gemContainer,
                              player));
                }));
          }
        });
  }

}
