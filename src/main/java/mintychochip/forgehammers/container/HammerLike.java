/*
 *
 *  Copyright (C) 2024 mintychochip
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package mintychochip.forgehammers.container;

import com.google.gson.annotations.SerializedName;
import java.util.Set;
import mintychochip.forgehammers.Constants;
import mintychochip.forgehammers.IHammer;
import mintychochip.genesis.items.interfaces.Embeddable;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;

public abstract class HammerLike implements IHammer, Embeddable {

  @SerializedName("deserialization-type")
  protected String deserializationType;

  @SerializedName("whitelist")
  protected final Set<Material> whitelist;
  @SerializedName("perks")
  protected final HammerPerks perks = new HammerPerks();
  @SerializedName("strength")
  protected float strength;

  public Set<Material> getWhitelist() {
    return whitelist;
  }

  private HammerLike(String deserializationType, Set<Material> whitelist) {
    this.deserializationType = deserializationType;
    this.whitelist = whitelist;
  }

  public void setStrength(float strength) {
    this.strength = strength;
  }

  public HammerPerks getPerks() {
    return perks;
  }

  @Override
  public boolean materialWhitelisted(Material material) {
    return whitelist.stream().anyMatch(entry -> entry == material);
  }

  public boolean blockWhitelisted(Block block) {
    return this.materialWhitelisted(block.getType());
  }

  @Override
  public NamespacedKey getKey() {
    return Constants.HAMMER_KEY;
  }

  public float getStrength() {
    return strength;
  }

  @Override
  public String getSimpleKey() {
    return "hammer";
  }

  @Override
  public String getDeserializationType() {
    return deserializationType;
  }

  @Override
  public void setDeserializationType(String deserializationType) {
    this.deserializationType = deserializationType;
  }

  public final static class Traditional extends HammerLike {

    @SerializedName("radius")
    private int radius;

    private Traditional(String deserializationType,
        Set<Material> whitelist) {
      super(deserializationType, whitelist);
    }

    public static Traditional create(HammerType type, Tool whitelist) {
      return new Traditional(type.getDeserializationType(), whitelist.getWhitelist());
    }

    public int getRadius() {
      return radius;
    }

    public void setRadius(int radius) {
      this.radius = radius;
    }
  }

  public static final class Patterned extends HammerLike {

    @SerializedName("pattern")
    private String[][] pattern;

    private Patterned(String deserializationType,
        Set<Material> whitelist) {
      super(deserializationType, whitelist);
    }

    public void setPattern(String[][] pattern) {
      this.pattern = pattern;
    }

    public String[][] getPattern() {
      return pattern;
    }

    public static Patterned create(HammerType type, Tool whitelist) {
      return new Patterned(type.getDeserializationType(), whitelist.getWhitelist());
    }
  }
}
