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
import mintychochip.forgehammers.config.GemConfig;
import mintychochip.forgehammers.container.ForgeHammers;
import mintychochip.forgehammers.container.GemEntry;
import mintychochip.forgehammers.container.Grasper;
import mintychochip.forgehammers.container.gem.Gem;
import mintychochip.forgehammers.container.gem.GemContainer;
import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.genesis.items.container.AbstractItem;
import mintychochip.genesis.items.container.AbstractItem.EmbeddedDataBuilder;
import mintychochip.genesis.items.container.AbstractItem.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GetGemCommand extends GenericCommandObject implements SubCommand {

  private final GemConfig gemConfig;
  public GetGemCommand(String executor, String description, GemConfig gemConfig) {
    super(executor, description);
    this.gemConfig = gemConfig;
  }
  private final Grasper<ItemMeta, GemEntry> grasper = new Grasper<>() {};
  @Override
  public boolean execute(String[] strings, Player player) {
    if(strings.length < depth) {
      return false;
    }
    String executor = strings[depth - 1];
    Gem gem = Gem.getGem(executor);
    int value = gem.createValue();
    AbstractItem build = new ItemBuilder(ForgeHammers.getInstance(), Material.DIAMOND,
        false).setDisplayName(gem.getName() + " " + value).setUnstackable(true).build();
    ItemStack itemStack = build.getItemStack();
    ItemMeta itemMeta = itemStack.getItemMeta();
    grasper.toss(itemMeta,new GemEntry(gem.getGemEnum(),value), Constants.GEM);
    itemStack.setItemMeta(itemMeta);
    player.getInventory().addItem(itemStack);
    return true;
  }
}
