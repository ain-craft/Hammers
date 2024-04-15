package mintychochip.forgehammers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mintychochip.forgehammers.typeadapter.RuntimeTypeAdapterFactory;
import org.bukkit.Nameable;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
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
    public void toss(ItemStack itemStack, Hammer hammer) {
        String json = gson.toJson(hammer);
        if(json == null) {
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(hammerKey, PersistentDataType.STRING,json);
        itemStack.setItemMeta(itemMeta);
    }

    @Override
    public Hammer grab(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        if(pdc.has(hammerKey,PersistentDataType.STRING)) {
            String json = pdc.get(hammerKey, PersistentDataType.STRING);
            return gson.fromJson(json,Hammer.class);
        }
        return null;
    }
}
