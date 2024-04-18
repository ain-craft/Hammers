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

package mintychochip.forgehammers.container;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public interface ToolMaterialTypeConverter {

  default ToolMaterial getToolMaterial(ItemStack itemStack) {
    Material material = itemStack.getType();
    if (!material.isItem()) {
      return ToolMaterial.WOODEN;
    }
    ItemMeta itemMeta = itemStack.getItemMeta();
    if(itemMeta instanceof Damageable) {
      String typeString = material.toString().toLowerCase();
      if (typeString.contains("wooden")) {
        return ToolMaterial.WOODEN;
      }
      if (typeString.contains("gold")) {
        return ToolMaterial.GOLD;
      }
      if (typeString.contains("stone")) {
        return ToolMaterial.STONE;
      }
      if (typeString.contains("iron")) {
        return ToolMaterial.IRON;
      }
      if (typeString.contains("diamond")) {
        return ToolMaterial.DIAMOND;
      }
      if (typeString.contains("netherite")) {
        return ToolMaterial.NETHERITE;
      } else {
        if (typeString.equalsIgnoreCase("shears")) {
          return ToolMaterial.IRON;
        }
      }
    }
    return ToolMaterial.WOODEN;
  }
}
