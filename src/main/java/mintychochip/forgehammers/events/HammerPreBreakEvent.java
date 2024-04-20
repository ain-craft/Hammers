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
import mintychochip.forgehammers.container.HammerLike;
import mintychochip.forgehammers.strategies.TraditionalHammerStrategy.Cardinal;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

public final class HammerPreBreakEvent extends ForgeBreakBaseEvent implements PrefacingBlockEvent,
    Cancellable {

  private final HammerLike hammerLike;

  private boolean cancelled = false;

  private final ItemStack itemStack;
  private final float originHardness;
  private final Block block;

  private final Consumer<Collection<ItemStack>> itemConsumer;

  public HammerPreBreakEvent(Block block, Cardinal cardinal, Player player, HammerLike hammerLike,
      ItemStack itemStack,
      float originHardness, Consumer<Collection<ItemStack>> itemConsumer) {
    super(cardinal, player);
    this.block = block;
    this.hammerLike = hammerLike;
    this.itemStack = itemStack;
    this.originHardness = originHardness;
    this.itemConsumer = itemConsumer;
  }

  public void accept(Collection<ItemStack> drops) {
    itemConsumer.accept(drops);
  }

  @Override
  public float getOriginHardness() {
    return originHardness;
  }

  @Override
  public ItemStack getItemStack() {
    return itemStack;
  }

  @Override
  public HammerLike getHammer() {
    return hammerLike;
  }

  @Override
  public boolean isCancelled() {
    return cancelled;
  }

  @Override
  public void setCancelled(boolean b) {
    this.cancelled = b;
  }

  @Override
  public Block getBlock() {
    return block;
  }
}
