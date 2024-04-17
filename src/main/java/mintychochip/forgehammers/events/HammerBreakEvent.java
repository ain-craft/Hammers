package mintychochip.forgehammers.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class HammerBreakEvent extends ForgeBreakBaseEvent implements PostfacingBlockEvent {

  private final ItemStack itemStack;

  public HammerBreakEvent(Player player, ItemStack itemStack) {
    super(player);
    this.itemStack = itemStack;
  }
  public HammerBreakEvent(Player player, ItemStack itemStack, Block block) {
    super(player);
    this.itemStack = itemStack;
    this.block = block;
  }
  @Override
  public Block getBlock() {
    return block;
  }
  @Override
  public ItemStack getItemStack() {
    return itemStack;
  }
}
