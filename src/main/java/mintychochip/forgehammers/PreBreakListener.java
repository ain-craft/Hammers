package mintychochip.forgehammers;

import java.util.function.Predicate;
import mintychochip.forgehammers.container.ForgeHammers;
import mintychochip.forgehammers.container.Hammer;
import mintychochip.forgehammers.events.HammerBreakEvent;
import mintychochip.forgehammers.events.HammerPreBreakEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public final class PreBreakListener extends AbstractListener {

  Predicate<HammerPreBreakEvent> blockIsUnbreakable = event -> {
    Material type = event.getBlock().getType();
    return type == Material.BEDROCK;
  };
  Predicate<HammerPreBreakEvent> blockTooHard = event -> {
    Material type = event.getBlock().getType();
    return type.getHardness() > event.getHammer().getStrength();
  };

  Predicate<HammerPreBreakEvent> blockBlacklisted = event -> event.getHammer()
      .blockBlacklisted(event.getBlock());
  Predicate<HammerPreBreakEvent> blockIsOre = event -> {
    Material type = event.getBlock().getType();
    return Constants.ORE_MATERIALS.contains(type);
  };
  Predicate<HammerPreBreakEvent> blockIsLowerThanOriginHardness = event -> {
    float originHardness = event.getOriginHardness();
    return originHardness < event.getBlock().getType().getHardness();
  };
  private final Predicate<HammerPreBreakEvent> check = blockIsUnbreakable.or(blockTooHard)
      .or(blockBlacklisted).or(blockIsOre).or(blockIsLowerThanOriginHardness);

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
        .callEvent(new HammerBreakEvent(event.getPlayer(), event.getItemStack(), event.getBlock()));
  }

  @EventHandler(priority = EventPriority.HIGH)
  private void checkHandler(final HammerPreBreakEvent event) {
    if (event.isCancelled()) {
      return;
    }
    if (check.test(event)) {
      event.setCancelled(true);
    }
  }
}
