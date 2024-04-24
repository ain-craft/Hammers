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
import java.util.List;
import java.util.Set;
import mintychochip.forgehammers.container.gem.sub.triggers.TriggerOnBreakEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockBreakEvent;

public class Veinminer implements TriggerOnBreakEvent {

  private static final int threshold = 4;
  @Override
  public void execute(BlockBreakEvent event, int level) {
    Set<Block> seen = new HashSet<>();
    Block block = event.getBlock();
    if(origin.getBlock().getType().toString().contains("LOG")) {
      this.asd(origin.getBlock(),visited,origin);
      visited.forEach(blockConsumer);
    }
  }
  private void asd(Block block, Set<Block> visited, Location origin) {
    if (visited.contains(block) || block.getType().isAir()) {
      return;
    }
    visited.add(block);
    List<Block> adjacent = this.getAdjacent(block);
    for (Block newBlock : adjacent) {
      Material type = newBlock.getType();
      if(type.toString().contains("ORE") && this.isWithinDistance(origin,newBlock.getLocation())) {
        asd(newBlock,visited,origin);
      }
    }
  }
  private List<Block> getAdjacent(Block block) {
    List<Block> adjacent = new ArrayList<>();
    for (BlockFace value : BlockFace.values()) {
      adjacent.add(block.getRelative(value));
    }
    return adjacent;
  }
  private boolean isWithinDistance(Location loc1, Location loc2) {
    double distanceSquared = Math.pow(loc1.getX() - loc2.getX(), 2) + Math.pow(loc1.getZ() - loc2.getZ(), 2);
    return distanceSquared < threshold * threshold;
  }
}
