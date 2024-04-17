package mintychochip.forgehammers.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class ForgeBreakBaseEvent extends Event {

    /**
     * Post event, is only called by 'ForgePreBlockBreakEvent'
     */

    protected final Block block;

    protected final Player player;

    protected ForgeBreakBaseEvent(Block block, Player player) {
        this.block = block;
        this.player = player;
    }
    @Override
    public @org.jetbrains.annotations.NotNull HandlerList getHandlers() {
        return null;
    }

    public Player getPlayer() {
        return player;
    }

    public Block getBlock() {
        return block;
    }
}
