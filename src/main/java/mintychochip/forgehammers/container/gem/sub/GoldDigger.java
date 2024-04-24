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

import mintychochip.forgehammers.container.Fortune;
import mintychochip.forgehammers.container.gem.Gem;
import mintychochip.forgehammers.container.gem.GemAnno;
import mintychochip.forgehammers.container.gem.GemAnno.ExecutionPriority;
import mintychochip.forgehammers.container.gem.GemEnum;
import mintychochip.forgehammers.container.gem.sub.triggers.TriggerOnBlockDrop;
import mintychochip.forgehammers.events.FakeBlockDropItemEvent;
import mintychochip.genesis.util.Rarity;
import org.bukkit.Material;

public class GoldDigger extends Gem implements TriggerOnBlockDrop, Fortune {

  public GoldDigger(GemEnum gemEnum, String name,
      String description, int min, int max, Rarity rarity) {
    super(gemEnum, name, description, min, max, rarity);
  }

  @Override
  @GemAnno(priority = ExecutionPriority.HIGH)
  public void execute(FakeBlockDropItemEvent event, int level) {
    event.setDrops(event.getDrops().stream().peek(drop -> {
      if (drop.getType() == Material.GOLD_NUGGET) {
        double amount = (double) drop.getAmount() * this.multiplier(level);
        drop.setAmount((int) amount);
      }
    }).toList());
  }
  private double multiplier(int level) {
    return Math.sqrt(level + 1);
  }

}
