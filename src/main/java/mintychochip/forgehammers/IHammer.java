package mintychochip.forgehammers;

import org.bukkit.Material;

public interface IHammer {
    void setDeserializationType(String deserializationType);
    String getDeserializationType();
    boolean materialBlacklisted(Material material);
    float getHardness();
}
