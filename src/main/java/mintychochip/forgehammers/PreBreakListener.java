package mintychochip.forgehammers;

import mintychochip.forgehammers.container.ForgeHammers;
import mintychochip.forgehammers.events.HammerBreakEvent;
import mintychochip.forgehammers.events.HammerPreBreakEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public final class PreBreakListener extends AbstractListener {

  public PreBreakListener(ForgeHammers instance) {
    super(instance);
  }

  /**
   * Is called if the event is not cancelled
   *
   * @param event
   */
  @EventHandler(priority = EventPriority.MONITOR)
  private void initializeBreakEvent(final HammerPreBreakEvent event) {
    if (event.isCancelled()) {
      return;
    }
    Bukkit.getPluginManager()
        .callEvent(new HammerBreakEvent(event.getPlayer(),event.getItemStack(),event.getBlock()));
  }

  @EventHandler(priority = EventPriority.LOW)
  private void onBlockIsUnbreakable(final HammerPreBreakEvent event) {
    if(event.isCancelled()) {
      return;
    }
    Material type = event.getBlock().getType();
    if (type == Material.BEDROCK) {
      event.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.HIGH)
  private void onOriginHardnessIsHigher(final HammerPreBreakEvent event) {
    if (event.isCancelled()) {
      return;
    }
    float originHardness = event.getOriginHardness();
    float hardness = event.getBlock().getType().getHardness();
    Bukkit.broadcastMessage(originHardness + " " + hardness);
    if (originHardness < event.getBlock().getType().getHardness()) {
      Bukkit.broadcast(Component.text("hardness cancelled"));
      event.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.LOW)
  private void onBlockIsTooHard(final HammerPreBreakEvent event) {
    if (event.isCancelled()) {
      return;
    }
    Material type = event.getBlock().getType();
    if (type.getHardness() > event.getHammer().getStrength()) {
      event.setCancelled(true);
    }
  }
  @EventHandler(priority = EventPriority.LOWEST)
  private void onBlockIsBlacklisted(final HammerPreBreakEvent event) {
    if (event.isCancelled()) {
      return;
    }
    if (event.getHammer().blockBlacklisted(event.getBlock())) {
      event.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.HIGH)
  private void onBlockBreakOre(final HammerPreBreakEvent event) {
    if (event.isCancelled()) {
      return;
    }
    Material type = event.getBlock().getType();
    if (Constants.ORE_MATERIALS.contains(type)) {
      event.setCancelled(true);
    }
  }
}
