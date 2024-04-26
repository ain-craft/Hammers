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

package mintychochip.forgehammers.container.gem.sub.triggers;

import java.lang.reflect.Method;
import mintychochip.forgehammers.container.gem.GemAnno;
import mintychochip.forgehammers.container.gem.GemAnno.ExecutionPriority;
import mintychochip.forgehammers.events.DropEvent;
import org.bukkit.event.entity.EntityCombustEvent;

public interface ITriggerOnCombust {
  void execute(EntityCombustEvent event, int level);

  default ExecutionPriority getPrio() {
    try {
      Method method = this.getClass().getMethod("execute", EntityCombustEvent.class, int.class);
      return this.getPriority(method);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }
  private ExecutionPriority getPriority(Method method) {
    if (method.isAnnotationPresent(GemAnno.class)) {
      GemAnno annotation = method.getAnnotation(GemAnno.class);
      return annotation.priority();
    }
    // Default priority if the method is not annotated with GemAnno
    return ExecutionPriority.LOW;
  }
}
