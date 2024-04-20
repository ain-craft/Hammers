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

import mintychochip.forgehammers.container.HammerLike;
import mintychochip.forgehammers.container.HammerLike.Traditional;

public interface HammerStrategySelector extends StrategySelector<HammerStrategy, HammerLike> {
  default HammerStrategy selectStrategy(HammerLike hammerLike) {
    if (hammerLike instanceof Traditional) {
      return TraditionalHammerStrategy.INSTANCE;
    }
    return null;
  }
}
