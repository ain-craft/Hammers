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

import java.util.function.Consumer;
import mintychochip.forgehammers.container.Hammer;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public final class TraditionalHammerStrategy implements HammerStrategy {

  public static final TraditionalHammerStrategy INSTANCE = new TraditionalHammerStrategy();
  private TraditionalHammerStrategy() {

  }
  enum Cardinal {
    UP_DOWN, EAST_WEST, NORTH_SOUTH
  }
  @Override
  public void accept(Location origin, Player player, Hammer hammer,
      Consumer<Block> blockConsumer) {
    if (!(hammer instanceof Hammer.Traditional traditional)) {
      return;
    }
    final Cardinal cardinal = this.getCardinal(player);
    final int radius = traditional.getRadius();
    for (int offset = -radius; offset <= radius; offset++) {
      for (int j = -radius; j <= radius; j++) {
        Vector v = switch (cardinal) {
          case UP_DOWN -> new Vector(offset, 0, j);
          case EAST_WEST -> new Vector(0, offset, j);
          case NORTH_SOUTH -> new Vector(offset, j, 0);
        };
        blockConsumer.accept(origin.clone().add(v).getBlock());
      }
    }
  }

  private Cardinal getCardinal(Player player) {
    final Location playerLocation = player.getLocation();
    final float pitch = playerLocation.getPitch();
    final float yaw = (playerLocation.getYaw() + 360) % 360;
    if (Math.abs(pitch) > 32.5) {
      return Cardinal.UP_DOWN;
    }
    if (yaw >= 45 && yaw < 135 || yaw >= 225 && yaw < 315) {
      return Cardinal.EAST_WEST;
    }
    return Cardinal.NORTH_SOUTH;
  }
}
