package mintychochip.forgehammers;

import mintychochip.forgehammers.strategies.StrategySelector;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

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
        ItemStack itemInUse = grasper.getItemInUse(inventory.getItemInMainHand(),
            inventory.getItemInOffHand());
        if (itemInUse.getType() == Material.AIR) {
            return;
        }
        Hammer grab = grasper.grab(itemInUse);
        if(grab == null) {
            return;
        }
        this.selectStrategy(grab).accept(event.getBlock().getLocation(), player, grab);
    }

}
