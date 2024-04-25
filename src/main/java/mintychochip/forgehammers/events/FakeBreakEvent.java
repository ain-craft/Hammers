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
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class FakeBreakEvent extends BlockBreakEvent {

  private final ItemStack item;
  private float hardness;

  private final Consumer<Collection<ItemStack>> drops;
  public FakeBreakEvent(@NotNull Block block,
      @NotNull Player player, ItemStack item, Consumer<Collection<ItemStack>> drops) {
    super(block, player);
    this.item = item;
    this.drops = drops;
  }

  public void accept(Collection<ItemStack> drops) {
    this.drops.accept(drops);
  }
  public void setHardness(float hardness) {
    this.hardness = hardness;
  }

  public float getHardness() {
    return hardness;
  }

  public ItemStack getItem() {
    return item;
  }
}
