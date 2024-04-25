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

package mintychochip.forgehammers.events;

import java.util.Collection;
import java.util.function.Consumer;
import mintychochip.forgehammers.container.gem.Gem;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GemBreakEvent extends FakeBreakEvent {

  private final Gem gem;

  public GemBreakEvent(@NotNull Block block,
      @NotNull Player player,
      ItemStack item, Gem gem, Consumer<Collection<ItemStack>> drops) {
    super(block, player, item, drops);
    this.gem = gem;
  }

  public Gem getGem() {
    return gem;
  }
}
