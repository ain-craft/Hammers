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
  protected final List<String> whitelist;

  @SerializedName("strength")
  protected float strength;

  private HammerLike(String deserializationType, List<String> whitelist) {
    this.deserializationType = deserializationType;
    this.whitelist = whitelist;
  }

  public boolean blockWhitelisted(Block block) {
    return this.materialWhitelisted(block.getType());
  }

  @Override
  public boolean materialWhitelisted(Material material) {
    return whitelist.stream().anyMatch(entry -> entry.equalsIgnoreCase(material.toString()));
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
  public void setStrength(float strength) {
    this.strength = strength;
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
  @Override
  public void addToWhitelist(Material material) {
    if(whitelist != null) {
      whitelist.add(material.toString());
    }
  }
  @Override
  public void addMaterialsToWhitelist(Set<Material> materials) {
    if(whitelist != null) {
      materials.forEach(this::addToWhitelist);
    }
  }
  public final static class Traditional extends HammerLike {

    @SerializedName("radius")
    private int radius;

    private Traditional(int radius, String deserializationType,
        List<String> whitelist) {
      super(deserializationType, whitelist);
      this.radius = radius;
    }

    public static Traditional create(int radius,
        List<String> whitelist) {
      if (radius < 0) {
        radius = 1;
      }
      return new Traditional(radius, HammerType.TRADITIONAL.getDeserializationType(),
          whitelist);
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
    private final String[][] pattern;

    private Patterned(String[][] pattern, String deserializationType,
        List<String> whitelist) {
      super(deserializationType, whitelist);
      this.pattern = pattern;
    }

    public String[][] getPattern() {
      return pattern;
    }

    public static Patterned create(String[][] pattern, List<String> whitelist) {
      if (pattern == null) {
        return null;
      }
      return new HammerLike.Patterned(pattern, HammerType.PATTERNED.getDeserializationType(),
          whitelist);
    }
  }
}
