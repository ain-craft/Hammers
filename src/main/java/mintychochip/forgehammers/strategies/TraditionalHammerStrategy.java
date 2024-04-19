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

package mintychochip.forgehammers.strategies;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import mintychochip.forgehammers.container.HammerLike;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;
import org.bukkit.entity.Player;

public final class TraditionalHammerStrategy implements HammerStrategy {

  public static final TraditionalHammerStrategy INSTANCE = new TraditionalHammerStrategy();

  private TraditionalHammerStrategy() {

  }

  public enum Cardinal {
    UP_DOWN, EAST_WEST, NORTH_SOUTH
  }

  @Override
  public void accept(Cardinal cardinal, BlockFace blockFace, Location origin, HammerLike hammerLike,
      Consumer<Block> blockConsumer) {
    if (!(hammerLike instanceof HammerLike.Traditional traditional)) {
      return;
    }
    final int radius = traditional.getRadius();
    for (int offset = -radius; offset <= radius; offset++) {
      for (int j = -radius; j <= radius; j++) {
        final Vector v = switch (cardinal) {
          case UP_DOWN -> new Vector(offset, 0, j);
          case EAST_WEST -> new Vector(0, offset, j);
          case NORTH_SOUTH -> new Vector(offset, j, 0);
        };
        if(!v.equals(new Vector(0,0,0))) {
          blockConsumer.accept(origin.clone().add(v).getBlock());
        }
      }
    }
  }
}
