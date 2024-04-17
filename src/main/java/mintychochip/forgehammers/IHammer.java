package mintychochip.forgehammers;

import javafx.scene.paint.Material;
import org.bukkit.block.Block;

import java.util.List;

public interface IHammer {

    String getDeserializationType();

    float getHardness();

    List<String> getBlacklist();

    boolean materialBlacklisted(Material material);

    boolean blockBlacklisted(Block block); //proxy
}

