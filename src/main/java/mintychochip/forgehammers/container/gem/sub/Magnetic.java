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
import mintychochip.forgehammers.container.gem.Gem;
import mintychochip.forgehammers.container.gem.GemAnno;
import mintychochip.forgehammers.container.gem.GemAnno.ExecutionPriority;
import mintychochip.forgehammers.container.gem.GemEnum;
import mintychochip.forgehammers.container.gem.sub.triggers.ITriggerOnDrop;
import mintychochip.forgehammers.container.gem.sub.triggers.TriggerOnDropCreation;
import mintychochip.forgehammers.events.CreateItemEvent;
import mintychochip.forgehammers.events.DropEvent;
import mintychochip.genesis.util.Rarity;
import org.bukkit.inventory.ItemStack;

public class Magnetic extends Gem implements ITriggerOnDrop {

  public Magnetic(GemEnum gemEnum, String name,
      String description, int min, int max, Rarity rarity) {
    super(gemEnum, name, description, min, max, rarity);
  }
  @GemAnno(priority = ExecutionPriority.MONITOR)

  @Override
  public void execute(DropEvent event, int level) {
    event.setDrop(false);
  }
}
