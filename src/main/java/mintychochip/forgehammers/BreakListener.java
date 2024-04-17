package mintychochip.forgehammers;

import mintychochip.forgehammers.container.ForgeHammers;
import mintychochip.forgehammers.events.ForgeBreakBaseEvent;
import mintychochip.forgehammers.events.HammerBreakEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class BreakListener extends AbstractListener implements DurabilityReducer {

  public BreakListener(ForgeHammers instance) {
    super(instance);
  }


  @EventHandler
  private void onBreakDeductDurability(final HammerBreakEvent event) { //any break event
    Player player = event.getPlayer();
    if (player.getGameMode() == GameMode.SURVIVAL) {
      this.deductDurability(event.getItemStack());
    }
  }

  /**
   * Event is called for every hammer caused server-broken block
   *
   * @param event
   */
  @EventHandler
  private void onBreakEvent(final HammerBreakEvent event) {
    ItemStack itemStack = event.getItemStack();
    if (itemStack == null) {
      return;
    }
    event.getBlock().breakNaturally(itemStack, true);
  }
}