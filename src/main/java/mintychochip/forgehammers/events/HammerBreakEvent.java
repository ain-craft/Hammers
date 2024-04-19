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
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import mintychochip.forgehammers.strategies.TraditionalHammerStrategy.Cardinal;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class HammerBreakEvent extends ForgeBreakBaseEvent implements PostfacingBlockEvent {

  private final ItemStack itemStack;

  private final Consumer<Collection<ItemStack>> successful;

  public HammerBreakEvent(Cardinal cardinal, Player player, ItemStack itemStack, Block block,
      Consumer<Collection<ItemStack>> successful) {
    super(cardinal, player);
    this.itemStack = itemStack;
    this.block = block;
    this.successful = successful;
  }

  public void accept(Collection<ItemStack> drops) {
    this.successful.accept(drops);
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
