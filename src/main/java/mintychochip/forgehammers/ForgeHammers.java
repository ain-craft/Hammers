package mintychochip.forgehammers;

import mintychochip.forgehammers.commands.ForgeHammerCreation;
import mintychochip.forgehammers.commands.SetRadiusCommand;
import mintychochip.forgehammers.config.HammerConfig;
import mintychochip.forgehammers.typeadapter.RuntimeTypeAdapterFactory;
import mintychochip.genesis.commands.abstraction.GenericMainCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class ForgeHammers extends JavaPlugin {

  private static ForgeHammers instance;

  public static ForgeHammers getInstance() {
    return instance;
  }

  @Override
  public void onEnable() {
    // Plugin startup logic
    final String DESER_TYPE_STRING = "deserialization-type";
    instance = this;
    RuntimeTypeAdapterFactory<Hammer> hammerFactory = RuntimeTypeAdapterFactory.of(Hammer.class,
        DESER_TYPE_STRING).registerSubtype(Hammer.Traditional.class, "traditional");
    NamespacedKey hammerKey = new NamespacedKey(this, "hammer");
    GrasperImpl grasper = new GrasperImpl(hammerFactory, hammerKey);
    Bukkit.getPluginManager().registerEvents(new HammerListener(this, grasper), this);
    HammerConfig hammerConfig = new HammerConfig("hammer.yml", this);
    GenericMainCommandManager genericMainCommandManager = new GenericMainCommandManager("forge",
        "asd");
    genericMainCommandManager.addSubCommand(
        new ForgeHammerCreation("hammer", "asd", hammerConfig, grasper));
    genericMainCommandManager.addSubCommand(
        new SetRadiusCommand("radius", "sets radius", hammerConfig, grasper));
    getCommand("forge").setExecutor(genericMainCommandManager);
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}
