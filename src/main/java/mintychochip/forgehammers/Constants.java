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

package mintychochip.forgehammers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import mintychochip.forgehammers.container.ForgeHammers;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public class Constants {

  public static final Constants INSTANCE = new Constants();
  public static final String DESERIALIZATION_TYPE = "deserialization-type";
  public static final NamespacedKey HAMMER_KEY = new NamespacedKey(ForgeHammers.getInstance(),
      "hammer");
  public static final List<Material> ORE_MATERIALS = Arrays.stream(Material.values()).filter(
          material -> material.toString().contains("_ORE"))
      .toList();

}
