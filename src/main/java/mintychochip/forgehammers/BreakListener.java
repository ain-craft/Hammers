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

import static org.bukkit.Material.IRON_INGOT;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import mintychochip.forgehammers.container.ForgeHammers;
import mintychochip.forgehammers.events.DropBlockItemEvent;
import mintychochip.forgehammers.events.ForgeBreakBaseEvent;
import mintychochip.forgehammers.events.HammerBreakEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public final class BreakListener extends AbstractListener implements DurabilityReducer {

  private final Predicate<DropBlockItemEvent> autoSmeltItem = event -> event.getHammerLike().getPerks().isAutoSmelt();
  public BreakListener(ForgeHammers instance) {
    super(instance);
  }


  @EventHandler
  private void onBreakDeductDurability(final HammerBreakEvent event) { //any break event
    Player player = event.getPlayer();
    if (player.getGameMode() == GameMode.SURVIVAL) {
      this.deductDurability(event.getItemStack());
    }
  }

  /**
   * Event is called for every hammer caused server-broken block
   *
   * @param event
   */
  @EventHandler (priority = EventPriority.MONITOR)
  private void onBreakEvent(final HammerBreakEvent event) {
    ItemStack itemStack = event.getItemStack();
    if (itemStack == null) {
      return;
    }

    Block block = event.getBlock();
    Location location = event.getBlock().getLocation();
    Vector v = switch(event.getCardinal()) {
      case EAST_WEST -> new Vector(0,0.2,0.3);
      case NORTH_SOUTH -> new Vector(0.3,0.2,0);
      case UP_DOWN -> new Vector(0.25,0,0.25);
    };
    location.getWorld().spawnParticle(Particle.BLOCK_CRACK,location,20,v.getX(),v.getY(),v.getZ(),1.0,block.getBlockData());
    Collection<ItemStack> drops = block.getDrops(event.getItemStack());
    event.getBlock().setType(Material.AIR);
    Bukkit.getScheduler().runTaskLater(ForgeHammers.getInstance(), x -> {
      event.accept(drops);
    },1L);
  }
  @EventHandler (priority = EventPriority.MONITOR)
  private void onDropItemEvent(final DropBlockItemEvent event) {
    Location dropLocation = event.getDropLocation();
    if(event.getHammerLike().getPerks().isAutoSmelt()) {
      List<ItemStack> collect = event.getDrops().stream()
          .map(drop -> new ItemStack(this.getSmeltedItemType(drop.getType()))).collect(
              Collectors.toList());
      Bukkit.broadcastMessage("here");
      event.setDrops(collect);
    }
    event.getDrops().forEach(drop -> dropLocation.getWorld().dropItemNaturally(dropLocation,drop));
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
