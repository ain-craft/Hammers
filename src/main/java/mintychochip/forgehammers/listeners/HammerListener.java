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
import mintychochip.forgehammers.Grasper;
import mintychochip.forgehammers.container.BlockFaceGrabber;
import mintychochip.forgehammers.container.ForgeHammers;
import mintychochip.forgehammers.container.HammerLike;
import mintychochip.forgehammers.container.MaterialConverter;
import mintychochip.forgehammers.events.FakeBlockDropItemEvent;
import mintychochip.forgehammers.events.ToolBreakEvent;
import mintychochip.forgehammers.strategies.HammerStrategySelector;
import mintychochip.forgehammers.strategies.TraditionalHammerStrategy.Cardinal;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class HammerListener extends AbstractListener implements HammerStrategySelector,
    BlockFaceGrabber, MaterialConverter {

  private final ForgeHammers instance;
  private final Grasper grasper;

  public HammerListener(ForgeHammers instance, Grasper grasper) {
    super(instance);
    this.instance = instance;
    this.grasper = grasper;
  }

  @EventHandler(priority = EventPriority.MONITOR)
  private void onBreak(final BlockBreakEvent event) {
    if (event instanceof ToolBreakEvent) {
      return;
    }
    Block origin = event.getBlock();
    if (origin.getType().isAir()) {
      return;
    }
    Player player = event.getPlayer();
    ItemStack itemInUse = this.getItemInUse(player.getInventory());
    if (itemInUse.getType().isAir()) {
      return;
    }
    HammerLike grab = grasper.grab(itemInUse);
    if (grab == null) {
      return;
    }
    Cardinal cardinal = this.getCardinal(player);
    this.selectStrategy(grab).accept(cardinal, origin.getLocation(),
        grab, block -> {
          if (!block.equals(origin)) {
            Bukkit.getPluginManager().callEvent(new ToolBreakEvent(block, player, itemInUse, grab));
          }
        });
  }
  @EventHandler
  private void onBreakMiddle(final BlockBreakEvent event) {
    if(event instanceof ToolBreakEvent) {
      return;
    }
    Player player = event.getPlayer();
    ItemStack itemInUse = this.getItemInUse(player.getInventory());
    if(itemInUse.getType().isAir()) {
      return;
    }
    event.setDropItems(false);
    Bukkit.getPluginManager().callEvent(new FakeBlockDropItemEvent(player,event.getBlock().getLocation(),this.drops(itemInUse,event.getBlock()),itemInUse));
  }
  private ItemStack getItemInUse(PlayerInventory inventory) {
    return this.getItemInUse(inventory.getItemInMainHand(), inventory.getItemInOffHand());
  }

  private ItemStack getItemInUse(ItemStack main, ItemStack off) {
    return main.getType().isAir() ? off : main;
  }
}
