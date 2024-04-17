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

public final class PatternedHammerStrategy implements HammerStrategy {

    public static final PatternedHammerStrategy INSTANCE = new PatternedHammerStrategy();

    private PatternedHammerStrategy() {

    }

    @Override
    public void accept(Location origin, Player player, Hammer hammer,
        Consumer<Block> blockConsumer) {

    }
}
