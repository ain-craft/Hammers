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

package mintychochip.forgehammers;

import mintychochip.forgehammers.container.ForgeHammers;
import mintychochip.forgehammers.container.HammerLike;
import mintychochip.forgehammers.events.DropBlockItemEvent;
import mintychochip.forgehammers.events.HammerPreBreakEvent;
import mintychochip.forgehammers.strategies.HammerStrategy;
import mintychochip.forgehammers.strategies.StrategySelector;
import mintychochip.forgehammers.strategies.TraditionalHammerStrategy.Cardinal;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class HammerListener extends AbstractListener implements StrategySelector {

  private final ForgeHammers instance;

  private final Grasper grasper;

  public HammerListener(ForgeHammers instance, Grasper grasper) {
    super(instance);
    this.instance = instance;
    this.grasper = grasper;
  }

  @EventHandler
  private void breakBlocks(final BlockBreakEvent event) {
    Player player = event.getPlayer();
    PlayerInventory inventory = player.getInventory();
    ItemStack itemInUse = grasper.getItemInUse(inventory.getItemInMainHand(),
        inventory.getItemInOffHand());
    if (itemInUse.getType() == Material.AIR) {
      return;
    }
    HammerLike grab = grasper.grab(itemInUse);
    if (grab == null) {
      return;
    }
    BlockFace targetBlockFace = player.getTargetBlockFace(7);
    if(targetBlockFace == null) {
      return;
    }
    event.setDropItems(false);
    final Block origin = event.getBlock();
    Cardinal cardinal = this.getCardinalFromBlockFace(targetBlockFace);

    Bukkit.getPluginManager().callEvent(new DropBlockItemEvent(cardinal,origin.getLocation(),origin.getDrops(itemInUse),grab,itemInUse));
    final float originHardness = origin.getType().getHardness();
    this.selectStrategy(grab)
        .accept(cardinal, event.getPlayer().getTargetBlockFace(7), origin.getLocation(),
            grab, (block) -> {
              Bukkit.getPluginManager()
                  .callEvent(new HammerPreBreakEvent(cardinal, block, player, grab, itemInUse,
                      originHardness));
            });
  }
  private Cardinal getCardinalFromBlockFace(BlockFace blockFace) {
    return switch (blockFace) {
      case UP, DOWN -> Cardinal.UP_DOWN;
      case EAST, WEST -> Cardinal.EAST_WEST;
      case NORTH, SOUTH -> Cardinal.NORTH_SOUTH;
      default -> null;
    };
  }
}
