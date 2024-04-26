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

package mintychochip.forgehammers.container.gem.strategies;

import mintychochip.forgehammers.container.gem.GemEnum;
import mintychochip.forgehammers.strategies.StrategySelector;

public interface GemStrategySelector extends StrategySelector<GemStrategy, GemEnum> {
  default GemStrategy selectStrategy(GemEnum gemEnum) {
    return switch(gemEnum) {
      case AUTO_SMELT, MAGNETIC, HELL_FORGED -> gem -> 1;
      case GOLD_DIGGER, VEIN_MINER, COMPACTOR -> new SimpleGemStrategy();
    };
  }
}
