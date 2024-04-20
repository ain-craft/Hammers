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

import java.util.Set;
import mintychochip.forgehammers.Constants;
import mintychochip.forgehammers.container.gem.Gem;
import mintychochip.forgehammers.container.gem.GemContainer;
import mintychochip.forgehammers.container.gem.GemGrasper;
import mintychochip.forgehammers.container.gem.strategies.GemStrategySelector;
import mintychochip.genesis.commands.abstraction.GenericCommand;
import mintychochip.genesis.commands.abstraction.SubCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetGem extends GenericCommand implements SubCommand, GemStrategySelector {
  private final GemGrasper gemGrasper;

  public SetGem(String executor, String description, Set<String> strings, GemGrasper gemGrasper) {
    super(executor, description, strings);
    this.gemGrasper = gemGrasper;
  }

  @Override
  public boolean execute(String[] strings, Player player) {
    if(strings.length < depth) {
      return false;
    }
    ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
    ItemMeta itemMeta = itemInMainHand.getItemMeta();
    GemContainer grab = gemGrasper.grab(itemMeta, Constants.GEM_CONTAINER);
    if(grab == null) {
      grab = new GemContainer();
    }

    grab.add(Gem.getGem(strings[depth - 1]));
    gemGrasper.toss(itemMeta,grab,Constants.GEM_CONTAINER);
    itemInMainHand.setItemMeta(itemMeta);
    return true;
  }
}
