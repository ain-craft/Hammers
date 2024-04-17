package mintychochip.forgehammers.events;

import mintychochip.genesis.events.AbstractEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class ForgeBreakBaseEvent extends AbstractEvent {

    /**
     * Post event, is only called by 'ForgePreBlockBreakEvent'
     */


    protected final Player player;

    protected Block block;

    protected ForgeBreakBaseEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

}
