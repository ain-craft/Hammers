package mintychochip.forgehammers;

import mintychochip.forgehammers.container.ForgeHammers;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Constants {

    public static final String DESERIALIZATION_TYPE = "deserialization-type";
    public static final NamespacedKey HAMMER_KEY = new NamespacedKey(ForgeHammers.getInstance(),
            "hammer");
    public static final List<Material> ORE_MATERIALS = Arrays.stream(Material.values()).filter(material ->
            !material.toString().contains("LEGACY") && material.toString().contains("ORE_")).toList();
}
