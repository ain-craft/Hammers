package mintychochip.forgehammers.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class HammerBreakEvent extends ForgeBreakBaseEvent implements PostfacingBlockEvent {

    private final ItemStack itemStack;

    public HammerBreakEvent(Block block, Player player, ItemStack itemStack) {
        super(block, player);
        this.itemStack = itemStack;
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }
}
