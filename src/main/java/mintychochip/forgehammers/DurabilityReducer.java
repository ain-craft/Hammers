package mintychochip.forgehammers;

import java.util.Random;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public interface DurabilityReducer {
  default void deductDurability(ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    if(itemMeta instanceof Damageable damageable) {
      damageable.setDamage(damageable.getDamage() + this.getDamageByUnbreaking(itemStack));
      itemStack.setItemMeta(itemMeta);
    }
  }

  private int getDamageByUnbreaking(ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    int enchantLevel = itemMeta.getEnchantLevel(Enchantment.DURABILITY);
    if(enchantLevel == 0) {
      return 1;
    }
    return new Random().nextDouble() <= (double) 1 / (1 + enchantLevel) ? 1 : 0;
  }
}
