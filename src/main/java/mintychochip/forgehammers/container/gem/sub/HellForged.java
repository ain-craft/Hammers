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

import mintychochip.forgehammers.container.gem.Gem;
import mintychochip.forgehammers.container.gem.GemAnno;
import mintychochip.forgehammers.container.gem.GemAnno.ExecutionPriority;
import mintychochip.forgehammers.container.gem.GemEnum;
import mintychochip.forgehammers.container.gem.strategies.GemStrategy;
import mintychochip.forgehammers.container.gem.sub.triggers.ITriggerOnCombust;
import mintychochip.forgehammers.container.gem.sub.triggers.ITriggerOnDamageEvent;
import mintychochip.genesis.util.Rarity;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.StonecuttingRecipe;

public class HellForged extends Gem implements ITriggerOnCombust, ITriggerOnDamageEvent {
  public HellForged(GemEnum gemEnum, String name, String description, int min, int max,
      Rarity rarity) {
    super(gemEnum, name, description, min, max, rarity);
  }
  @GemAnno(priority = ExecutionPriority.NORMAL)
  @Override
  public void execute(EntityCombustEvent event, int level) {
    event.setCancelled(true);
  }
  @Override
  public ExecutionPriority getPrio() {
    return ITriggerOnCombust.super.getPrio();
  }

  @Override
  public void execute(EntityDamageEvent event, int level) {
    DamageCause cause = event.getCause();
    if(cause == DamageCause.LAVA || cause == DamageCause.FIRE || cause == DamageCause.FIRE_TICK) {
      event.setCancelled(true);
    }
  }

  @Override
  public GemStrategy selectStrategy(GemEnum gemEnum) {
    return super.selectStrategy(gemEnum);
  }
}
