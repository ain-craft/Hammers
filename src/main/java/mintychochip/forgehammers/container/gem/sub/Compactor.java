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

package mintychochip.forgehammers.container.gem.sub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mintychochip.forgehammers.container.gem.Gem;
import mintychochip.forgehammers.container.gem.GemAnno;
import mintychochip.forgehammers.container.gem.GemAnno.ExecutionPriority;
import mintychochip.forgehammers.container.gem.GemEnum;
import mintychochip.forgehammers.container.gem.sub.triggers.ITriggerOnDrop;
import mintychochip.forgehammers.events.DropEvent;
import mintychochip.forgehammers.events.MergeEvent;
import mintychochip.genesis.util.Rarity;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Compactor extends Gem implements ITriggerOnDrop {

  private final Map<Material, CompactionEntry> compactionMap = new HashMap<>();
  public Compactor(GemEnum gemEnum, String name,
      String description, int min, int max, Rarity rarity) {
    super(gemEnum, name, description, min, max, rarity);
    compactionMap.put(Material.GOLD_NUGGET, new CompactionEntry(9, Material.GOLD_INGOT));
    compactionMap.put(Material.GOLD_INGOT, new CompactionEntry(9, Material.GOLD_BLOCK));
  }
  @GemAnno(priority = ExecutionPriority.NORMAL)
  @Override
  public void execute(DropEvent event, int level) {
    List<ItemStack> newDrops = new ArrayList<>();
    for (ItemStack drop : event.getDrops()) {
      if(compactionMap.containsKey(drop.getType())) {
        CompactionEntry compactionEntry = compactionMap.get(drop.getType());
        int amount = compactionEntry.amount(drop.getAmount());
        if(amount > 0) {
          newDrops.add(new ItemStack(compactionEntry.getTo(),amount));
        }
        int remainder = compactionEntry.remainder(drop.getAmount());
        if(remainder > 0) {
          newDrops.add(new ItemStack(drop.getType(),remainder));
        }
      } else {
        newDrops.add(drop);
      }
    }
    event.setDrops(newDrops);
  }
  static class CompactionEntry {
    private final int amount;

    private final Material to;

    public CompactionEntry(int count, Material to) {
      this.amount = count;
      this.to = to;
    }

    public int getAmount() {
      return amount;
    }

    public Material getTo() {
      return to;
    }

    public int remainder(int amount) {
      return amount % this.amount;
    }
    public int amount(int amount) {
      return amount / this.amount;
    }
  }
}
