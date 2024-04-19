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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Material;

public enum Tool {
  HAMMER(new Whitelist.Builder().addStringSet(
          Set.of("STONE", "DEEPSLATE", "GRANITE", "ANDESITE", "DIORITE", "TUFF", "CALCITE",
              "NETHERRACK", "NYLIUM", "TERRACOTTA", "GLASS", "ICE", "BRICK", "PRISMARINE", "BASALT",
              "PURPUR", "MAGMA", "CORAL", "COPPER", "IRON", "GOLD", "DIAMOND", "EMERALD",
              "NETHERITE", "ORE",
              "AMETHYST", "CONCRETE", "OBSIDIAN")).removeStringSet(Set.of("CONCRETE_POWDER")).build()
      .getWhitelist()),
  SHOVEL(
      new Whitelist.Builder().addStringSet(
              Set.of("DIRT", "GRASS_BLOCK", "SAND", "GRAVEL", "CLAY", "SNOW"))
          .removeStringSet(Set.of("STONE", "SUSPICIOUS")).remove(Material.SNOW).build()
          .getWhitelist());
  private final Set<Material> whitelist;

  Tool(Set<Material> whitelist) {
    this.whitelist = whitelist;
  }

  public Set<Material> getWhitelist() {
    return whitelist;
  }

  private static class Whitelist {

    private final Set<Material> whitelist;

    public Whitelist(Builder builder) {
      this.whitelist = builder.whitelist;
    }

    public Set<Material> getWhitelist() {
      return whitelist;
    }

    private static class Builder {

      private final Set<Material> whitelist = new HashSet<>();

      public Builder add(Material material) {
        whitelist.add(material);
        return this;
      }

      public Builder addAll(Collection<Material> materials) {
        whitelist.addAll(materials);
        return this;
      }

      public Builder remove(Material material) {
        whitelist.remove(material);
        return this;
      }

      public Builder removeAll(Collection<Material> materials) {
        whitelist.removeAll(materials);
        return this;
      }

      public Builder addStringSet(Set<String> stringSet) {
        this.addAll(this.getMaterialsFromStringList(stringSet));
        return this;
      }

      public Builder removeStringSet(Set<String> stringSet) {
        this.removeAll(this.getMaterialsFromStringList(stringSet));
        return this;
      }

      private Set<Material> getMaterialsFromStringList(Set<String> filters) {
        Set<Material> materials = new HashSet<>();
        for (String filter : filters) {
          for (Material material : Material.values()) {
            if (material.name().contains(filter) && material.isBlock()) {
              materials.add(material);
            }
          }
        }
        return materials;
      }

      public Whitelist build() {
        return new Whitelist(this);
      }
    }
  }
}
