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

import mintychochip.forgehammers.Grasper;
import mintychochip.forgehammers.config.HammerConfig;
import mintychochip.forgehammers.container.Hammer;
import mintychochip.forgehammers.container.Hammer.Traditional;
import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ForgeHammerRadius extends GenericCommandObject implements SubCommand {
  private final HammerConfig hammerConfig;
  private final Grasper grasper;

  public ForgeHammerRadius(String executor, String description, HammerConfig hammerConfig,
      Grasper grasper) {
    super(executor, description);
    this.hammerConfig = hammerConfig;
    this.grasper = grasper;
  }

  @Override
  public boolean execute(String[] strings, Player player) {
    if (strings.length < depth) {
      return false;
    }
    PlayerInventory inventory = player.getInventory();
    ItemStack item = grasper.getItemInUse(inventory.getItemInMainHand(),
        inventory.getItemInOffHand());
    if (item.getType() == Material.AIR) {
      return false;
    }
    Hammer grab = grasper.grab(item);
    if (!(grab instanceof Traditional traditional)) {
      return false;
    }
    int oldRadius = traditional.getRadius();
    int radius;

    try {
      radius = Integer.parseInt(strings[depth - 1]);
    } catch (NumberFormatException e) {
      radius = oldRadius;
    }
    if (oldRadius == radius) {
      return false;
    }

    traditional.setRadius(radius);
    boolean toss = grasper.toss(item, traditional);
    if (toss) {
      player.sendMessage("Hammer radius was changed successfully: " + oldRadius + " to: "
          + radius); //move to config
    } else {
      player.sendMessage("Failed: " + oldRadius);
    }
    return toss;
  }
}
