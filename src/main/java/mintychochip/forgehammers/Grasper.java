package mintychochip.forgehammers;

import org.bukkit.inventory.ItemStack;

public interface Grasper {

    boolean toss(ItemStack itemStack, Hammer hammer);

    Hammer grab(ItemStack itemStack);

    ItemStack getItemInUse(ItemStack main, ItemStack off);
}
