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
