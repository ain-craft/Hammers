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

import com.google.gson.Gson;
import mintychochip.forgehammers.container.Grabber;
import mintychochip.forgehammers.container.Grasper;
import mintychochip.forgehammers.container.Tosser;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class GemGrasper implements Grasper<ItemMeta,GemContainer> {

  @Override
  public GemContainer grab(ItemMeta container, NamespacedKey key) {
    PersistentDataContainer pdc = container.getPersistentDataContainer();
    if(!pdc.has(key)) {
      return null;
    }
    return new Gson().fromJson(pdc.get(key, PersistentDataType.STRING),GemContainer.class);
  }

  @Override
  public void toss(ItemMeta container, GemContainer data, NamespacedKey key) {
    String json = new Gson().toJson(data);
    if(json == null) {
      return;
    }
    container.getPersistentDataContainer().set(key,PersistentDataType.STRING,new Gson().toJson(data));
  }
}
