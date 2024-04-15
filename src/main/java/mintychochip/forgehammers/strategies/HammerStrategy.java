package mintychochip.forgehammers.strategies;

import java.util.function.BiConsumer;

import mintychochip.forgehammers.Hammer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface HammerStrategy {

    void accept(Location location, Player player, Hammer hammer);
}
