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

package mintychochip.forgehammers.container.gem;

import java.util.ArrayList;
import java.util.List;
import mintychochip.forgehammers.container.ForgeHammers;
import mintychochip.forgehammers.container.Grasper;
import mintychochip.forgehammers.container.gem.strategies.GemStrategySelector;
import mintychochip.genesis.util.Rarity;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public abstract class Gem implements Keyed, GemStrategySelector {
  public static final Gem AUTO_SMELT = getGem(GemEnum.AUTO_SMELT.getNamespace());
  public static final Gem MAGNETIC = getGem(GemEnum.MAGNETIC.getNamespace());
  public static final Gem GOLD_DIGGER = getGem(GemEnum.GOLD_DIGGER.getNamespace());
  private final Rarity rarity;
  private final String description;
  private final String name;
  private final int min;
  private final int max;
  private final GemEnum gemEnum;
  private final List<NamespacedKey> conflicts = new ArrayList<>();
  @Override
  public @NotNull NamespacedKey getKey() {
    return gemEnum.getKey();
  }

  public static Gem getGem(GemEnum gemEnum) {
    return GemRegistry.INSTANCE.get(gemEnum.getKey());
  }
  public static Gem getGem(String namespace) {
    NamespacedKey namespacedKey = new NamespacedKey(ForgeHammers.getInstance(), namespace);
    return GemRegistry.INSTANCE.get(namespacedKey);
  }
  public static void slot(ItemStack itemStack) {
    ItemMeta itemMeta = itemStack.getItemMeta();
    Grasper<ItemMeta,GemContainer> gemGrasper = new Grasper<>() {};
  }
  public int createValue() {
    return this.selectStrategy(gemEnum).create(this);
  }

  public Gem(GemEnum gemEnum, String name, String description, int min, int max, Rarity rarity) {
    this.gemEnum = gemEnum;
    this.name = name;
    this.description = description;
    this.min = min;
    this.max = max;
    this.rarity = rarity;
  }

  public GemEnum getGemEnum() {
    return gemEnum;
  }

  public String getDescription() {
    return description;
  }

  public String getName() {
    return name;
  }

  public int getMin() {
    return min;
  }

  public int getMax() {
    return max;
  }
}
