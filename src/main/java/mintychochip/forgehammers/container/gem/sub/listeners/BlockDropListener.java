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

package mintychochip.forgehammers.container.gem.sub.listeners;

import java.util.Arrays;
import mintychochip.forgehammers.AbstractListener;
import mintychochip.forgehammers.container.ForgeHammers;
import mintychochip.forgehammers.container.gem.Gem;
import mintychochip.forgehammers.container.gem.GemAnno.ExecutionPriority;
import mintychochip.forgehammers.container.gem.sub.triggers.TriggerOnBlockDrop;
import mintychochip.forgehammers.events.PreBlockDropEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class BlockDropListener extends AbstractListener {

  public BlockDropListener(ForgeHammers instance) {
    super(instance);
  }

  @EventHandler(priority = EventPriority.HIGH)
  private void onBlockDropEvent(final PreBlockDropEvent event) {
    if (event.isCancelled()) {
      return;
    }
    if (event.getGemContainer() == null) {
      return;
    }
    for (ExecutionPriority value : ExecutionPriority.values()) {
      event.getGemContainer().keySet().stream().map(gem -> Gem.getGem(gem.getNamespace()))
          .filter(gem -> gem instanceof TriggerOnBlockDrop).map(gem -> (TriggerOnBlockDrop) gem)
          .filter(triggerOnBlockDrop -> triggerOnBlockDrop.getPrio() == value)
          .forEach(triggerOnBlockDrop -> triggerOnBlockDrop.execute(event));
    }
  }

}
