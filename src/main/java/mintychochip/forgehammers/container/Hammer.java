package mintychochip.forgehammers.container;

import com.google.gson.annotations.SerializedName;
import mintychochip.forgehammers.Constants;
import mintychochip.forgehammers.IHammer;
import mintychochip.genesis.items.interfaces.Embeddable;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;

import java.util.List;

public abstract class Hammer implements IHammer, Embeddable {

  @SerializedName("deserialization-type")
  protected String deserializationType;

  @SerializedName("blacklist")
  protected final List<String> blacklist;

  @SerializedName("hardness")
  protected final float hardness;

  private Hammer(String deserializationType, float hardness, List<String> blacklist) {
    this.deserializationType = deserializationType;
    this.hardness = hardness;
    this.blacklist = blacklist;
  }

  @Override
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
  public float getHardness() {
    return hardness;
  }

  @Override
  public String getDeserializationType() {
    return deserializationType;
  }

  public void setDeserializationType(String deserializationType) {
    this.deserializationType = deserializationType;
  }

  public static class Traditional extends Hammer {

    @SerializedName("radius")
    private int radius;

    private Traditional(int radius, String deserializationType) {
      super(deserializationType);
      this.radius = radius;
    }

    public static Traditional create(int radius, HammerType hammerType) {
      if(radius < 0) {
        radius = 1;
      }
      return new Hammer.Traditional(radius, hammerType.getDeserializationType());
    }

    public int getRadius() {
      return radius;
    }

    public void setRadius(int radius) {
      this.radius = radius;
    }
  }
}
