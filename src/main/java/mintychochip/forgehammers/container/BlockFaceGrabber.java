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

package mintychochip.forgehammers.container;

import mintychochip.forgehammers.strategies.TraditionalHammerStrategy.Cardinal;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
public interface BlockFaceGrabber {

  default Cardinal getCardinal(Player player) {
    return this.getCardinalFromBlockFace(player.getTargetBlockFace(5));
  }
  private Cardinal getCardinalFromBlockFace(BlockFace blockFace) {
    return switch (blockFace) {
      case UP -> Cardinal.DOWN;
      case DOWN -> Cardinal.UP;
      case EAST -> Cardinal.WEST;
      case WEST -> Cardinal.EAST;
      case NORTH -> Cardinal.NORTH;
      case SOUTH -> Cardinal.SOUTH;
      default -> null;
    };
  }
}
