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

import mintychochip.forgehammers.container.Tool;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ToolBreakEvent extends BlockBreakEvent {

  private final ItemStack item;

  private final Tool tool;
  public ToolBreakEvent(@NotNull Block theBlock,
      @NotNull Player player, ItemStack item, Tool tool) {
    super(theBlock, player);
    this.item = item;
    this.tool = tool;
  }

  public Tool getTool() {
    return tool;
  }

  public ItemStack getItem() {
    return item;
  }
}
