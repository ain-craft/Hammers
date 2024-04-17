package mintychochip.forgehammers.strategies;

import java.util.function.Consumer;
import mintychochip.forgehammers.container.Hammer;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public final class PatternedHammerStrategy implements HammerStrategy {


    @Override
    public void accept(Location origin, Player player, Hammer hammer,
        Consumer<Block> blockConsumer) {

    }
}
