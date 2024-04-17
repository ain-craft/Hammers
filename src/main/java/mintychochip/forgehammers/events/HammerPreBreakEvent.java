package mintychochip.forgehammers.events;

import mintychochip.forgehammers.container.Hammer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

public final class HammerPreBreakEvent extends ForgeBreakBaseEvent implements PrefacingBlockEvent, Cancellable {

    private final Hammer hammer;

    private boolean cancelled = false;

    private final ItemStack itemStack;
    private final float originHardness;
    private final Block block;
    public HammerPreBreakEvent(Block block,Player player, Hammer hammer, ItemStack itemStack, float originHardness) {
        super(player);
        this.block = block;
        this.hammer = hammer;
        this.itemStack = itemStack;
        this.originHardness = originHardness;
    }

    @Override
    public float getOriginHardness() {
        return originHardness;
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public Hammer getHammer() {
        return hammer;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public Block getBlock() {
        return block;
    }
}
