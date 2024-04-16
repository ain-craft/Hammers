package mintychochip.forgehammers.commands;

import mintychochip.forgehammers.ForgeHammers;
import mintychochip.forgehammers.Grasper;
import mintychochip.forgehammers.Hammer.Traditional;
import mintychochip.forgehammers.HammerType;
import mintychochip.forgehammers.config.HammerConfig;
import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import mintychochip.genesis.items.container.AbstractItem;
import mintychochip.genesis.items.container.AbstractItem.ConfigurationItemBuilder;
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
    AbstractItem abstractItem = new ConfigurationItemBuilder(ForgeHammers.getInstance(),
        Material.DIAMOND_PICKAXE, hammer, false).defaultBuild();
    ItemStack itemStack = abstractItem.getItemStack();
    grasper.toss(itemStack, Traditional.create(2,
        HammerType.TRADITIONAL));
    player.getInventory().addItem(itemStack);
    return true;
  }

}
