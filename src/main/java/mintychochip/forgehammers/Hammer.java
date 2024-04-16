package mintychochip.forgehammers;

import com.google.gson.annotations.SerializedName;
import mintychochip.genesis.items.interfaces.Embeddable;
import org.bukkit.NamespacedKey;

public abstract class Hammer implements IHammer, Embeddable {

  @SerializedName("deserialization-type")
  protected String deserializationType;

  private Hammer(String deserializationType) {
    this.deserializationType = deserializationType;
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
      //add some deserializationtype validation
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
