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
