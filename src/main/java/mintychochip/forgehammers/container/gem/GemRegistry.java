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
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import mintychochip.forgehammers.container.gem.sub.AutoSmelt;
import mintychochip.forgehammers.container.gem.sub.Compactor;
import mintychochip.forgehammers.container.gem.sub.GoldDigger;
import mintychochip.forgehammers.container.gem.sub.Magnetic;
import mintychochip.forgehammers.container.gem.sub.VeinMiner;
import mintychochip.genesis.util.Rarity;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GemRegistry implements Registry<Gem> {

  public final static GemRegistry INSTANCE = new GemRegistry();

  private GemRegistry() {
    this.register(
        new AutoSmelt(GemEnum.AUTO_SMELT, "Auto Smelt", "SMELT", 0, 3, Rarity.COMMON));
    this.register(
        new Magnetic(GemEnum.MAGNETIC, "Magnetic", "telekenis", 0, 1, Rarity.COMMON));
    this.register(
        new GoldDigger(GemEnum.GOLD_DIGGER, "Gold Digger", "asd", 1, 3, Rarity.COMMON));
    this.register(
        new VeinMiner(GemEnum.VEIN_MINER, "Vein", "asd", 1, 3, Rarity.COMMON));
    this.register(
        new Compactor(GemEnum.COMPACTOR, "compactor", "asd", 1, 5, Rarity.COMMON)
    );
  }

  private final List<Gem> gems = new ArrayList<>();

  public boolean register(Gem gem) {
    return gems.add(gem);
  }

  @Override
  public @Nullable Gem get(@NotNull NamespacedKey namespacedKey) {
    return gems.stream().filter(gem -> gem.getKey().equals(namespacedKey)).findFirst().orElse(null);
  }

  @Override
  public @NotNull Stream<Gem> stream() {
    return gems.stream();
  }

  @NotNull
  @Override
  public Iterator<Gem> iterator() {
    return gems.iterator();
  }
}
