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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import mintychochip.forgehammers.container.ForgeHammers;
import mintychochip.genesis.items.interfaces.Embeddable;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

public class GemContainer implements Embeddable {
  private final Map<GemEnum, Integer> gemMap = new HashMap<>();

  public void add(Gem gem) {
    gemMap.put(gem.getGemEnum(), gem.createValue());
  }

  public void remove(GemEnum gemEnum) {
    gemMap.remove(gemEnum);
  }

  public int getLevel(GemEnum gemEnum) {
    return gemMap.get(gemEnum);
  }

  public boolean has(GemEnum gemEnum) {
    return gemMap.containsKey(gemEnum);
  }
  public Set<GemEnum> keySet() {
    return gemMap.keySet();
  }
  public NamespacedKey getKey() {
    return new NamespacedKey(ForgeHammers.getInstance(), this.getSimpleKey());
  }

  @Override
  public String getSimpleKey() {
    return "gems";
  }
}
