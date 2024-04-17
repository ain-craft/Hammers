package mintychochip.forgehammers.container;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import mintychochip.forgehammers.Constants;
import mintychochip.forgehammers.IHammer;
import mintychochip.genesis.items.interfaces.Embeddable;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;

public abstract class Hammer implements IHammer, Embeddable {

  @SerializedName("deserialization-type")
  protected String deserializationType;

  @SerializedName("blacklist")
  protected final List<String> blacklist;

  @SerializedName("strength")
  protected final float strength;

  private Hammer(String deserializationType, float strength, List<String> blacklist) {
    this.deserializationType = deserializationType;
    this.strength = strength;
    this.blacklist = blacklist;
  }

  public boolean blockBlacklisted(Block block) {
    return this.materialBlacklisted(block.getType());
  }

  @Override
  public boolean materialBlacklisted(Material material) {
    return blacklist.stream().anyMatch(entry -> entry.equalsIgnoreCase(material.toString()));
  }

  @Override
  public NamespacedKey getKey() {
    return Constants.HAMMER_KEY;
  }

  @Override
  public String getSimpleKey() {
    return "hammer";
  }

  @Override
  public float getStrength() {
    return strength;
  }

  @Override
  public String getDeserializationType() {
    return deserializationType;
  }

  @Override
  public void setDeserializationType(String deserializationType) {
    this.deserializationType = deserializationType;
  }

  public final static class Traditional extends Hammer {

    @SerializedName("radius")
    private int radius;

    private Traditional(int radius, String deserializationType, float hardness,
        List<String> blacklist) {
      super(deserializationType, hardness, blacklist);
      this.radius = radius;
    }

    public static Traditional create(int radius, float strength,
        List<String> blacklist) {
      if (radius < 0) {
        radius = 1;
      }
      return new Traditional(radius, HammerType.TRADITIONAL.getDeserializationType(), strength,
          blacklist);
    }

    public int getRadius() {
      return radius;
    }

    public void setRadius(int radius) {
      this.radius = radius;
    }
  }

  public static final class Patterned extends Hammer {

    @SerializedName("pattern")
    private final String[][] pattern;

    private Patterned(String[][] pattern, String deserializationType, float strength,
        List<String> blacklist) {
      super(deserializationType, strength, blacklist);
      this.pattern = pattern;
    }

    public String[][] getPattern() {
      return pattern;
    }

    public static Patterned create(String[][] pattern, float hardness, List<String> blacklist) {
      if (pattern == null) {
        return null;
      }
      return new Hammer.Patterned(pattern, HammerType.PATTERNED.getDeserializationType(), hardness,
          blacklist);
    }
  }
}
