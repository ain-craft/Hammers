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

package mintychochip.forgehammers.commands;

import mintychochip.forgehammers.Constants;
import mintychochip.forgehammers.container.Grasper;
import mintychochip.forgehammers.container.gem.GemContainer;
import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class ToolInfo extends GenericCommandObject implements SubCommand {

  Grasper<ItemMeta, GemContainer> containerGrasper = new Grasper<>(){};
  public ToolInfo(String executor, String description) {
    super(executor, description);
  }

  @Override
  public boolean execute(String[] strings, Player player) {

    PlayerInventory inventory = player.getInventory();
    ItemStack itemInMainHand = inventory.getItemInMainHand();
    ItemMeta itemMeta = itemInMainHand.getItemMeta();
    GemContainer grab = containerGrasper.grab(itemMeta, Constants.GEM_CONTAINER, GemContainer.class);
    grab.keySet().stream().forEach(gem -> {
      Bukkit.broadcast(Component.text(gem + " " + grab.getLevel(gem)));
    });
    return true;
  }
}
