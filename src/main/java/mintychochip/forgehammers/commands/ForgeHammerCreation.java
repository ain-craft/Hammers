package mintychochip.forgehammers.commands;

import mintychochip.forgehammers.Grasper;
import mintychochip.forgehammers.config.HammerConfig;
import mintychochip.forgehammers.container.ForgeHammers;
import mintychochip.forgehammers.container.Hammer;
import mintychochip.forgehammers.container.ToolMaterial;
import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.genesis.items.container.AbstractItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ForgeHammerCreation extends GenericCommandObject implements SubCommand {

  private final HammerConfig config;

  private final Grasper grasper;

  public ForgeHammerCreation(String executor, String description,
      HammerConfig hammerConfig, Grasper grasper) {
    super(executor, description);
    this.config = hammerConfig;
    this.grasper = grasper;
  }

  @Override
  public boolean execute(String[] strings, Player player) {
    if (strings.length < depth) {
      return false;
    }
    String executor = strings[depth - 1];
    GenesisConfigurationSection hammer = config.getSection(executor);
    if (hammer.isNull()) {
      return false;
    }
    AbstractItem abstractItem = new AbstractItem.ConfigurationItemBuilder(
        ForgeHammers.getInstance(),
        Material.DIAMOND_PICKAXE, hammer, false).defaultBuild();
    ItemStack itemStack = abstractItem.getItemStack();
    grasper.toss(itemStack, Hammer.Traditional.create(2, ToolMaterial.DIAMOND.getHardness(), hammer.getStringList("black-list")));
    player.getInventory().addItem(itemStack);
    return true;
  }

}
