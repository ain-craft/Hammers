package mintychochip.forgehammers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface IHammer extends BiConsumer<Location,Player> {
    Material getMaterial();
    HammerType getType();
}
