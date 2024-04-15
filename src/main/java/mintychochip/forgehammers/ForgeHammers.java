package mintychochip.forgehammers;

import mintychochip.forgehammers.typeadapter.RuntimeTypeAdapterFactory;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class ForgeHammers extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        RuntimeTypeAdapterFactory<Hammer> hammerFactory = RuntimeTypeAdapterFactory.of(Hammer.class, "type").registerSubtype(Hammer.Traditional.class, "traditional");
        NamespacedKey hammerKey = new NamespacedKey(this, "hammer");
        Bukkit.getPluginManager().registerEvents(new HammerListener(this,new GrasperImpl(hammerFactory,hammerKey)), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
