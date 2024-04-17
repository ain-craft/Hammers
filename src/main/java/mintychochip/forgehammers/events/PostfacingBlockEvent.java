package mintychochip.forgehammers.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public interface PostfacingBlockEvent extends Listener, BlockEvent {
    ItemStack getItemStack();
    Player getPlayer(); // for incrementation
}
