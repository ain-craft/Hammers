package mintychochip.forgehammers.strategies;

import mintychochip.forgehammers.Hammer;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public final class TraditionalHammerStrategy implements HammerStrategy {
    enum Cardinal {
        UP_DOWN, EAST_WEST, NORTH_SOUTH
    }

    @Override
    public void accept(Location location, Player player, Hammer hammer) {
        if (!(hammer instanceof Hammer.Traditional traditional)) {
            return;
        }
        final Cardinal cardinal = this.getCardinal(player);
        final int radius = traditional.getRadius();
        for (int offset = -radius; offset < radius; offset++) {
            for (int j = -radius; j < radius; j++) {
                Vector v = switch(cardinal) {
                    case NORTH_SOUTH -> new Vector(offset,j,0);
                    case EAST_WEST ->  new Vector(0, offset, j);
                    case UP_DOWN -> new Vector(offset,0,j);
                };
                Block block = location.clone().add(v).getBlock();
                player.breakBlock(block);
            }
        }
    }

    private Cardinal getCardinal(Player player) {
        final Location playerLocation = player.getLocation();
        final float pitch = playerLocation.getPitch();
        final float yaw = (playerLocation.getYaw() + 360) % 360;

        if (Math.abs(pitch) > 45) {
            return Cardinal.UP_DOWN;
        }
        if (yaw >= 45 && yaw < 225) {
            return Cardinal.NORTH_SOUTH;
        }
        return Cardinal.EAST_WEST;
    }
}
