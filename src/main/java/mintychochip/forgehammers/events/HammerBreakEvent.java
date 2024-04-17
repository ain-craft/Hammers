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

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class HammerBreakEvent extends ForgeBreakBaseEvent implements PostfacingBlockEvent {

  private final ItemStack itemStack;

  public HammerBreakEvent(Player player, ItemStack itemStack) {
    super(player);
    this.itemStack = itemStack;
  }
  public HammerBreakEvent(Player player, ItemStack itemStack, Block block) {
    super(player);
    this.itemStack = itemStack;
    this.block = block;
  }
  @Override
  public Block getBlock() {
    return block;
  }
  @Override
  public ItemStack getItemStack() {
    return itemStack;
  }
}
