package mintychochip.forgehammers.container;

import java.util.Arrays;
import java.util.List;
import mintychochip.forgehammers.BreakListener;
import mintychochip.forgehammers.Constants;
import mintychochip.forgehammers.GrasperImpl;
import mintychochip.forgehammers.HammerListener;
import mintychochip.forgehammers.PreBreakListener;
import mintychochip.forgehammers.commands.ForgeHammerCreation;
import mintychochip.forgehammers.commands.ForgeHammerRadius;
import mintychochip.forgehammers.config.HammerConfig;
import mintychochip.forgehammers.typeadapter.RuntimeTypeAdapterFactory;
import mintychochip.genesis.commands.abstraction.GenericMainCommandManager;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ForgeHammers extends JavaPlugin {

  private static ForgeHammers instance;

  public static ForgeHammers getInstance() {
    return instance;
  }

  @Override
  public void onEnable() {
    // Plugin startup logic
    instance = this;
    RuntimeTypeAdapterFactory<Hammer> hammerFactory = RuntimeTypeAdapterFactory.of(Hammer.class,
            Constants.DESERIALIZATION_TYPE)
        .registerSubtype(Hammer.Traditional.class, "traditional")
        .registerSubtype(Hammer.Patterned.class, "patterned");
    NamespacedKey hammerKey = new NamespacedKey(this, "hammer");
    GrasperImpl grasper = new GrasperImpl(hammerFactory, hammerKey);
    List<Listener> listeners = Arrays.asList(new BreakListener(this), new PreBreakListener(this),
        new HammerListener(this, grasper));
    HammerConfig hammerConfig = new HammerConfig("hammer.yml", this);
    GenericMainCommandManager genericMainCommandManager = new GenericMainCommandManager("forge",
        "asd");
    genericMainCommandManager.addSubCommand(
        new ForgeHammerCreation("hammer", "asd", hammerConfig, grasper));
    genericMainCommandManager.addSubCommand(
        new ForgeHammerRadius("radius", "sets radius", hammerConfig, grasper));
    getCommand("forge").setExecutor(genericMainCommandManager);
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}
