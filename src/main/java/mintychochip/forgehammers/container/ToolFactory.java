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

public class ToolFactory {

  public static HammerLike createHammer(HammerType type, Tool whitelist) {
    if(!(whitelist == Tool.SHOVEL || whitelist == Tool.HAMMER)) {
      return null;
    }
    if(type == HammerType.TRADITIONAL) {
      return HammerLike.Traditional.create(type,whitelist);
    }
    return HammerLike.Patterned.create(type,whitelist);
  }
}
