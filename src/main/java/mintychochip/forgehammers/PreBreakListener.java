package mintychochip.forgehammers;

import mintychochip.forgehammers.events.HammerBreakEvent;
import mintychochip.forgehammers.events.HammerPreBreakEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PreBreakListener implements Listener {

    /**
     * Is called if the event is not cancelled
     * @param event
     */
    @EventHandler (priority = EventPriority.MONITOR)
    private void initializeBreakEvent(final HammerPreBreakEvent event) {
        if(event.isCancelled()) {
            return;
        }
        Bukkit.getPluginManager().callEvent(new HammerBreakEvent(event.getBlock(),event.getPlayer()));
    }
    @EventHandler (priority = EventPriority.HIGH)
    private void onBlockIsUnbreakable(final HammerPreBreakEvent event) {
        Material type = event.getBlock().getType();
        if(type.isAir() || type == Material.BEDROCK) {
            event.setCancelled(true);
        }
    }
    @EventHandler (priority = EventPriority.HIGH)
    private void onBlockIsTooHard(final HammerPreBreakEvent event) {
        if(event.isCancelled()) {
            return;
        }
        Material type = event.getBlock().getType();
        if(type.getHardness() > event.getHammer().getHardness()) {
            event.setCancelled(true);
        }
    }
    @EventHandler (priority = EventPriority.HIGH)
    private void onBlockIsBlacklisted(final HammerPreBreakEvent event) {
        if(event.isCancelled()) {
            return;
        }
        if (event.getHammer().blockBlacklisted(event.getBlock())) {
            event.setCancelled(true);
        }
    }
    @EventHandler (priority = EventPriority.HIGH)
    private void onBlockBreakOre(final HammerPreBreakEvent event) {
        if(event.isCancelled()) {
            return;
        }
        Material type = event.getBlock().getType();
        if(Constants.ORE_MATERIALS.contains(type)) {
            event.setCancelled(true);
        }
    }


}
