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

package mintychochip.forgehammers.container.gem.sub.listeners;

import mintychochip.forgehammers.AbstractListener;
import mintychochip.forgehammers.Constants;
import mintychochip.forgehammers.GrasperImpl;
import mintychochip.forgehammers.container.ForgeHammers;
import mintychochip.forgehammers.container.Grasper;
import mintychochip.forgehammers.container.gem.Gem;
import mintychochip.forgehammers.container.gem.GemAnno.ExecutionPriority;
import mintychochip.forgehammers.container.gem.GemContainer;
import mintychochip.forgehammers.container.gem.sub.triggers.TriggerOnBlockDrop;
import mintychochip.forgehammers.container.gem.sub.triggers.TriggerOnBreakEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class BlockBreakEventListener extends AbstractListener {

  public BlockBreakEventListener(ForgeHammers instance) {
    super(instance);
  }

  @EventHandler
  private void onBreak(BlockBreakEvent event) {
    Player player = event.getPlayer();
    ItemStack itemInUse = this.getItemInUse(player.getInventory());

    final Grasper<ItemMeta, GemContainer> grasper = new Grasper<>() {
    };
    GemContainer grab = grasper.grab(itemInUse.getItemMeta(), Constants.GEM_CONTAINER,
        GemContainer.class);
    if (grab == null) {
      return;
    }
    for (ExecutionPriority value : ExecutionPriority.values()) {
      grab.keySet().stream().map(gem -> Gem.getGem(gem.getNamespace()))
          .filter(gem -> gem instanceof TriggerOnBreakEvent).map(gem -> (TriggerOnBreakEvent) gem)
          .filter(triggerOnBlockDrop -> triggerOnBlockDrop.getPrio() == value)
          .forEach(triggerOnBlockDrop -> triggerOnBlockDrop.execute(event,grab.getLevel(((Gem) triggerOnBlockDrop).getGemEnum())));
    }
  }

  private ItemStack getItemInUse(PlayerInventory inventory) {
    return this.getItemInUse(inventory.getItemInMainHand(), inventory.getItemInOffHand());
  }

  private ItemStack getItemInUse(ItemStack main, ItemStack off) {
    return main.getType().isAir() ? off : main;
  }
}
