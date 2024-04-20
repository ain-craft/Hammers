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
import java.util.function.Predicate;
import mintychochip.forgehammers.AbstractListener;
import mintychochip.forgehammers.container.ForgeHammers;
import mintychochip.forgehammers.container.HammerLike;
import mintychochip.forgehammers.events.DropBlockItemEvent;
import mintychochip.forgehammers.events.PreBlockDropEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;

public class DropListener extends AbstractListener {

  private final Predicate<PreBlockDropEvent> gamemode = event -> event.getPlayer().getGameMode() != GameMode.SURVIVAL;
  private final Predicate<PreBlockDropEvent> check = gamemode;
  public DropListener(ForgeHammers instance) {
    super(instance);
  }
  @EventHandler(priority = EventPriority.MONITOR)
  private void onDropItemEvent(final DropBlockItemEvent event) {
    Location dropLocation = event.getDropLocation();
    event.getDrops().forEach(drop -> dropLocation.getWorld().dropItemNaturally(dropLocation, drop));
  }
  @EventHandler (priority = EventPriority.MONITOR)
  private void initializeBlockDrops(final PreBlockDropEvent event) {
    if(event.isCancelled()) {
      return;
    }
    Bukkit.getPluginManager().callEvent(new DropBlockItemEvent(event.getDropLocation(),event.getDrops()));
  }
  @EventHandler(priority = EventPriority.HIGH)
  private void checkPredicate(final PreBlockDropEvent event) {
    if(event.isCancelled()) {
      return;
    }
    if(check.test(event)) {
      event.setCancelled(true);
    }
  }
  @EventHandler (priority = EventPriority.HIGH)
  private void adjustIfAutoSmelt(final PreBlockDropEvent event) {
    if(event.isCancelled()) {
      return;
    }
    HammerLike hammerLike = event.getHammerLike();
    if(hammerLike.getPerks().isAutoSmelt()) {
      event.setDrops(event.getDrops().stream().map(drop -> new ItemStack(this.getSmeltedItemType(drop.getType()),drop.getAmount())).toList());
    }
  }
  private Material getSmeltedItemType(Material material) {
    return switch (material) {
      case IRON_ORE, RAW_IRON, DEEPSLATE_IRON_ORE -> Material.IRON_INGOT;
      case GOLD_ORE, RAW_GOLD, DEEPSLATE_GOLD_ORE -> Material.GOLD_INGOT;
      case COPPER_ORE, RAW_COPPER, DEEPSLATE_COPPER_ORE -> Material.COPPER_INGOT;
      case NETHER_GOLD_ORE -> Material.GOLD_NUGGET;
      case ANCIENT_DEBRIS -> Material.NETHERITE_SCRAP;
      case SAND -> Material.GLASS;
      case COBBLESTONE -> Material.STONE;
      case NETHERRACK -> Material.NETHER_BRICK;
      case CLAY -> Material.TERRACOTTA;
      case DEEPSLATE_EMERALD_ORE -> Material.EMERALD;
      case DEEPSLATE_LAPIS_ORE -> Material.LAPIS_LAZULI;
      case DEEPSLATE_DIAMOND_ORE -> Material.DIAMOND;
      case DEEPSLATE_REDSTONE_ORE -> Material.REDSTONE;
      default -> material;
    };
  }
}
