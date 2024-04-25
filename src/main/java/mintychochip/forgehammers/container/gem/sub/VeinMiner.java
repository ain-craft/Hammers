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

package mintychochip.forgehammers.container.gem.sub;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import mintychochip.forgehammers.Constants;
import mintychochip.forgehammers.container.SynchronizedItemDrops;
import mintychochip.forgehammers.container.gem.Gem;
import mintychochip.forgehammers.container.gem.GemEnum;
import mintychochip.forgehammers.container.gem.sub.triggers.TriggerOnBreakEvent;
import mintychochip.forgehammers.events.FakeBreakEvent;
import mintychochip.forgehammers.events.GemBreakEvent;
import mintychochip.genesis.util.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class VeinMiner extends Gem implements TriggerOnBreakEvent {
  private static final int MAX_BLOCKS = 500;

  public VeinMiner(GemEnum gemEnum, String name,
      String description, int min, int max, Rarity rarity) {
    super(gemEnum, name, description, min, max, rarity);
  }

  private List<BlockFace> blockFaces = List.of(BlockFace.WEST,BlockFace.NORTH,BlockFace.EAST,BlockFace.SOUTH,BlockFace.UP,BlockFace.DOWN);
  @Override
  public void execute(BlockBreakEvent event, int level) {
    if(event instanceof FakeBreakEvent) {
      return;
    }
    Set<Block> seen = new HashSet<>();
    Block origin = event.getBlock();
    if(Constants.ORE_MATERIALS.contains(origin.getType())) {
      this.recurse(origin,seen,origin.getLocation());
    }
    if(seen.isEmpty()) {
      return;
    }
    Iterator<Block> iterator = seen.iterator();
    Player player = event.getPlayer();
    int count = 0;
    while(iterator.hasNext()) {
      if(count > MAX_BLOCKS) {
        return;
      }
      Bukkit.getPluginManager().callEvent(new GemBreakEvent(iterator.next(),player,this.getItem(player.getInventory()),this,
          stacks -> {
            SynchronizedItemDrops.INSTANCE.addEntry(origin.getLocation(),stacks,this.getItem(player.getInventory()));
          }));
      count++;
    }
  }

  private ItemStack getItem(PlayerInventory inventory) {
    ItemStack itemInMainHand = inventory.getItemInMainHand();
    ItemStack itemInOffHand = inventory.getItemInOffHand();
    return itemInOffHand.getType().isAir() ? itemInMainHand : itemInOffHand;
  }
  private void recurse(Block block, Set<Block> visited, Location origin) {
    if (visited.contains(block) || block.getType().isAir()) {
      return;
    }
    if(visited.size() >= MAX_BLOCKS) {
      return;
    }
    visited.add(block);
    List<Block> adjacent = this.getAdjacent(block);
    for (Block newBlock : adjacent) {
      Material type = newBlock.getType();
      if(Constants.ORE_MATERIALS.contains(type)) {
        recurse(newBlock,visited,origin);
      }
    }
  }
  private List<Block> getAdjacent(Block block) {
    List<Block> adjacent = new ArrayList<>();
    for (BlockFace value : this.blockFaces) {
      adjacent.add(block.getRelative(value));
    }
    return adjacent;
  }
}
