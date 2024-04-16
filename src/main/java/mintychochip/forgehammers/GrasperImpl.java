package mintychochip.forgehammers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import mintychochip.forgehammers.typeadapter.RuntimeTypeAdapterFactory;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class GrasperImpl implements Grasper {

  private final Gson gson;
  private final NamespacedKey hammerKey;

  public GrasperImpl(RuntimeTypeAdapterFactory<Hammer> hammerFactory, NamespacedKey hammerKey) {
    gson = new GsonBuilder().registerTypeAdapterFactory(hammerFactory).create();
    this.hammerKey = hammerKey;
  }

  @Override
  public boolean toss(ItemStack itemStack, Hammer hammer) {
    String json = gson.toJson(hammer);
    if (json == null) {
      return false;
    }
    ItemMeta itemMeta = itemStack.getItemMeta();
    PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
    pdc.set(hammerKey, PersistentDataType.STRING, json);
    return itemStack.setItemMeta(itemMeta);
  }

  @Override
  public Hammer grab(ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
    if (!pdc.has(hammerKey, PersistentDataType.STRING)) {
      return null;
    }
    String json = pdc.get(hammerKey, PersistentDataType.STRING);
    if (json == null) {
      return null;
    }
    String type = JsonParser.parseString(json).getAsJsonObject().get(Constants.DESERIALIZATION_TYPE)
        .getAsString();
    if (type == null) {
      return null;
    }
    Hammer hammer = gson.fromJson(json, Hammer.class);
    hammer.setDeserializationType(type);
    return hammer;
  }

  @Override
  public ItemStack getItemInUse(ItemStack main, ItemStack off) {
    return main.getType() == Material.AIR ? off : main;
  }
}
