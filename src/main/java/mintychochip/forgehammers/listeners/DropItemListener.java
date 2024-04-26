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

package mintychochip.forgehammers.listeners;

import mintychochip.forgehammers.AbstractListener;
import mintychochip.forgehammers.Constants;
import mintychochip.forgehammers.container.ForgeHammers;
import mintychochip.forgehammers.container.GemEntry;
import mintychochip.forgehammers.container.Grasper;
import mintychochip.forgehammers.container.gem.GemContainer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.entity.Player;
public class DropItemListener extends AbstractListener {

  public DropItemListener(ForgeHammers instance) {
    super(instance);
  }

  private final Grasper<ItemMeta, GemContainer> grasper = new Grasper<>() {
  };
  private final Grasper<ItemMeta, GemEntry> entryGrasper = new Grasper<>() {
  };

  @EventHandler
  private void onDropItemOnTop(final InventoryClickEvent event) {
    if (event.getAction() != InventoryAction.PLACE_ALL) {
      return;
    }
    ItemStack invItem = event.getCurrentItem();
    if (invItem.getType().isAir()) {
      return;
    }

    ItemStack cursor = event.getCursor();
    GemEntry grab = entryGrasper.grab(cursor.getItemMeta(), Constants.GEM, GemEntry.class);
    if (grab == null) {
      return;
    }
    ItemMeta itemMeta = invItem.getItemMeta();
    GemContainer gemContainer = grasper.grab(itemMeta, Constants.GEM_CONTAINER,
        GemContainer.class);
    if (gemContainer == null) {
      gemContainer = new GemContainer();
    }
    event.get
    event.setCancelled(true);
    event.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
    gemContainer.add(grab.gemEnum(), grab.level());
    grasper.toss(itemMeta, gemContainer, Constants.GEM_CONTAINER);
    invItem.setItemMeta(itemMeta);
  }
}
