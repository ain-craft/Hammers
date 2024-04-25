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

import java.util.Collection;
import java.util.Map;
import mintychochip.forgehammers.AbstractListener;
import mintychochip.forgehammers.Grasper;
import mintychochip.forgehammers.container.BlockFaceGrabber;
import mintychochip.forgehammers.container.ForgeHammers;
import mintychochip.forgehammers.container.HammerLike;
import mintychochip.forgehammers.container.ItemLocationKey;
import mintychochip.forgehammers.container.MaterialConverter;
import mintychochip.forgehammers.container.SynchronizedItemDrops;
import mintychochip.forgehammers.events.CreateItemEvent;
import mintychochip.forgehammers.events.DropEvent;
import mintychochip.forgehammers.events.FakeBreakEvent;
import mintychochip.forgehammers.events.MergeEvent;
import mintychochip.forgehammers.events.ToolBreakEvent;
import mintychochip.forgehammers.strategies.HammerStrategySelector;
import mintychochip.forgehammers.strategies.TraditionalHammerStrategy.Cardinal;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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

  @EventHandler(priority = EventPriority.HIGH)
  private void onBreak(final BlockBreakEvent event) {
    if (event instanceof FakeBreakEvent) {
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
            Bukkit.getPluginManager().callEvent(new ToolBreakEvent(block, player, itemInUse, grab,
                stacks -> {
                  SynchronizedItemDrops.INSTANCE.addEntry(origin.getLocation(), stacks, itemInUse);
                }));
          }
        });
  }

  @EventHandler(priority = EventPriority.MONITOR)
  private void asdasd(final BlockBreakEvent event) {
    if(event instanceof FakeBreakEvent) {
      return;
    }
    final Location location = event.getBlock().getLocation();
    Map<ItemStack, Collection<ItemStack>> map = SynchronizedItemDrops.INSTANCE.getItemStacks()
        .remove(location);
    for (ItemStack itemStack : map.keySet()) {
      Collection<ItemStack> stacks = map.remove(itemStack);
      Bukkit.getPluginManager().callEvent(new MergeEvent(location,stacks,drops -> {
        DropEvent dropEvent = new DropEvent(location, drops, itemStack);
        dropEvent.setInventory(event.getPlayer().getInventory());
        Bukkit.getPluginManager().callEvent(dropEvent);
      }));
    }
  }
  @EventHandler(priority = EventPriority.HIGH)
  private void breaasd(final BlockBreakEvent event) {
    if (event instanceof FakeBreakEvent) {
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
    Bukkit.getPluginManager().callEvent(
        new CreateItemEvent(player, origin.getLocation(), this.drops(itemInUse, origin), itemInUse,
            stacks -> {
              SynchronizedItemDrops.INSTANCE.addEntry(origin.getLocation(), stacks, itemInUse);
            }));
  }

  private ItemStack getItemInUse(PlayerInventory inventory) {
    return this.getItemInUse(inventory.getItemInMainHand(), inventory.getItemInOffHand());
  }

  private ItemStack getItemInUse(ItemStack main, ItemStack off) {
    return main.getType().isAir() ? off : main;
  }
}
