package mintychochip.forgehammers;

import mintychochip.forgehammers.events.HammerBreakEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class BreakListener implements Listener {
    /**
     * Event is called for every hammer caused server-broken block
     * @param event
     */
    @EventHandler
    private void onBreakEvent(final HammerBreakEvent event) {
        ItemStack itemStack = event.getItemStack();
        if(itemStack == null) {
            return;
        }
        event.getBlock().breakNaturally(itemStack,true);
    }
}
