package mintychochip.forgehammers;

import com.google.gson.Gson;
import com.palmergames.bukkit.towny.event.actions.TownyDestroyEvent;
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

public class HammerListener implements Listener {

    private final ForgeHammers instance;

    public HammerListener(ForgeHammers instance) {
        this.instance = instance;
    }

    @EventHandler
    private void breakEven(final BlockBreakEvent event) {
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();
        ItemStack itemInUse = this.getItemInUse(inventory.getItemInMainHand(), inventory.getItemInOffHand());
        if (itemInUse.getType() == Material.AIR) {
            return;
        }
        ItemMeta itemMeta = itemInUse.getItemMeta();

        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(instance, "hammer");
        if (pdc.has(key, PersistentDataType.STRING)) {
            String json = pdc.get(key, PersistentDataType.STRING);
            IHammer hammer = new Gson().fromJson(json, IHammer.class);
            hammer.accept(event.getBlock().getLocation(), player);
        }

    }

    private ItemStack getItemInUse(ItemStack main, ItemStack off) {
        return main.getType() == Material.AIR ? off : main;
    }
}
