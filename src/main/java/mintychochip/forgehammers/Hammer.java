package mintychochip.forgehammers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class Hammer implements IHammer, Activatable {
    protected final Material material;
    protected boolean active;
    private Hammer(Material material) {
        this.material = material;
    }
    @Override
    public boolean isActive() {
        return active;
    }
    @Override
    public void setActive(boolean b) {
        this.active = b;
    }
    @Override
    public Material getMaterial() {
        return material;
    }
    public static class Traditional extends Hammer {

        private int radius;
        public Traditional(int radius, Material material) {
            super(material);
            this.radius = radius;
        }
        public Traditional create(int radius, Material material) {
           if(material == Material.AIR) {
               return null;
           }
           return new Hammer.Traditional(radius,material);
        }

        public int getRadius() {
            return radius;
        }

        @Override
        public HammerType getType() {
            return HammerType.TRAD;
        }

        @Override
        public void accept(Location location, Player player) {

        }
    }
}
