package mintychochip.forgehammers;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public interface Grasper {

    void toss(ItemStack itemStack, Hammer hammer);

    Hammer grab(ItemStack itemStack);
}
