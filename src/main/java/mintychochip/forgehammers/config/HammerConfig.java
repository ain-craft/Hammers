package mintychochip.forgehammers.config;

import mintychochip.genesis.config.abstraction.GenericConfig;
import mintychochip.genesis.config.abstraction.GenesisConfigurationSection;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class HammerConfig extends GenericConfig {

  public HammerConfig(String path, JavaPlugin plugin) {
    super(path, plugin);
  }

  public GenesisConfigurationSection getSection(String key) {
    return this.getMainConfigurationSection("items." + key);
  }


  public String getMessage(String key) {
    return this.getString("messages." + key);
  }

  //will be moved later

}
