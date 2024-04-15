package mintychochip.forgehammers;

import com.google.gson.annotations.SerializedName;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class Hammer implements IHammer, Activatable {
    @SerializedName("material")
    protected final Material material;
    @SerializedName("active")
    protected boolean active;
    @SerializedName("type")
    protected HammerType hammerType;

    private Hammer(Material material, HammerType hammerType) {
        this.material = material;
        this.hammerType = hammerType;
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

    @Override
    public HammerType getType() {
        return hammerType;
    }

    public static class Traditional extends Hammer {
        @SerializedName("radius")
        private int radius;

        public Traditional(int radius, Material material, HammerType hammerType) {
            super(material, hammerType);
            this.radius = radius;
        }

        public Traditional create(int radius, Material material, HammerType hammerType) {
            if (material == Material.AIR) {
                return null;
            }
            return new Hammer.Traditional(radius, material, hammerType);
        }

        public int getRadius() {
            return radius;
        }

        @Override
        public void accept(Location location, Player player) {

        }
    }
}
