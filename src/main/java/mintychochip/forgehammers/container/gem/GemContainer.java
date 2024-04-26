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

import it.unimi.dsi.fastutil.Hash;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import mintychochip.forgehammers.container.ForgeHammers;
import mintychochip.forgehammers.container.GemEntry;
import mintychochip.genesis.items.interfaces.Embeddable;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

public class GemContainer implements Embeddable {

  private final List<GemEntry> entries = new ArrayList<>();

  public void add(Gem gem) {
    this.add(gem.getGemEnum(), gem.createValue());
  }

  public void add(GemEnum gemEnum, int level) {
    entries.add(new GemEntry(gemEnum, level));
  }

  public void remove(GemEnum gemEnum) {
    GemEntry entry = this.find(gemEnum);
    if (entry != null) {
      entries.remove(entry);
    }
  }

  public GemEntry find(GemEnum gemEnum) {
    return entries.stream().filter(gemEntry -> gemEntry.gemEnum() == gemEnum)
        .findFirst().orElse(null);
  }

  public int getLevel(GemEnum gemEnum) {
    GemEntry entry = this.find(gemEnum);
    return entry.level();
  }
  public Collection<GemEnum> keySet() {
    return entries.stream().map(GemEntry::gemEnum).toList();
  }
  public NamespacedKey getKey() {
    return new NamespacedKey(ForgeHammers.getInstance(), this.getSimpleKey());
  }

  @Override
  public String getSimpleKey() {
    return "gems";
  }
}
