package mintychochip.forgehammers;

import mintychochip.forgehammers.container.ForgeHammers;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class AbstractListener implements Listener {
  protected AbstractListener(ForgeHammers instance) {
    Bukkit.getPluginManager().registerEvents(this,instance);
  }
}
