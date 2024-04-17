package mintychochip.forgehammers.events;

import mintychochip.forgehammers.container.Hammer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public interface PrefacingBlockEvent extends Listener, BlockEvent {

    ItemStack getItemStack();
    Hammer getHammer();
    Player getPlayer();

    float getOriginHardness();
}
