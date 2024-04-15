package mintychochip.forgehammers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.palmergames.bukkit.towny.event.actions.TownyDestroyEvent;
import mintychochip.forgehammers.strategies.StrategySelector;
import mintychochip.forgehammers.typeadapter.RuntimeTypeAdapterFactory;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class HammerListener implements Listener, StrategySelector {

    private final ForgeHammers instance;

    private final Grasper grasper;

    public HammerListener(ForgeHammers instance, Grasper grasper) {
        this.instance = instance;
        this.grasper = grasper;
    }

    @EventHandler
    private void breakBlocks(final BlockBreakEvent event) {
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();
        ItemStack itemInUse = this.getItemInUse(inventory.getItemInMainHand(), inventory.getItemInOffHand());
        if (itemInUse.getType() == Material.AIR) {
            return;
        }
        Hammer grab = grasper.grab(itemInUse);
        if(grab == null) {
            return;
        }
        this.selectStrategy(grab.getType()).accept(event.getBlock().getLocation(),player,grab);
    }

    private ItemStack getItemInUse(ItemStack main, ItemStack off) {
        return main.getType() == Material.AIR ? off : main;
    }
}
