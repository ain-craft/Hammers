package mintychochip.forgehammers;

import mintychochip.forgehammers.container.Hammer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public interface Grasper {

    boolean toss(ItemStack itemStack, Hammer hammer);

    Hammer grab(ItemStack itemStack);

    ItemStack getItemInUse(ItemStack main, ItemStack off);

    Hammer grab(PlayerInventory playerInventory);
}
