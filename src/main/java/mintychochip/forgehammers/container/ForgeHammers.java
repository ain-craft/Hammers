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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import mintychochip.forgehammers.commands.SetGem;
import mintychochip.forgehammers.container.ToolPerks.Perk;
import mintychochip.forgehammers.container.gem.GemEnum;
import mintychochip.forgehammers.container.gem.GemGrasper;
import mintychochip.forgehammers.container.gem.sub.listeners.BlockDropListener;
import mintychochip.forgehammers.listeners.BreakListener;
import mintychochip.forgehammers.Constants;
import mintychochip.forgehammers.GrasperImpl;
import mintychochip.forgehammers.listeners.HammerListener;
import mintychochip.forgehammers.listeners.DropListener;
import mintychochip.forgehammers.listeners.PreBreakListener;
import mintychochip.forgehammers.commands.ForgeHammerCreation;
import mintychochip.forgehammers.commands.ForgeHammerRadius;
import mintychochip.forgehammers.config.HammerConfig;
import mintychochip.forgehammers.typeadapter.RuntimeTypeAdapterFactory;
import mintychochip.genesis.commands.abstraction.GenericMainCommandManager;
import mintychochip.genesis.commands.abstraction.GenericSubCommandManager;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ForgeHammers extends JavaPlugin {

  private static ForgeHammers instance;

  public static ForgeHammers getInstance() {
    return instance;
  }

  @Override
  public void onEnable() {
    // Plugin startup logic
    instance = this;
    RuntimeTypeAdapterFactory<HammerLike> hammerFactory = RuntimeTypeAdapterFactory.of(HammerLike.class,
            Constants.DESERIALIZATION_TYPE)
        .registerSubtype(HammerLike.Traditional.class, "traditional")
        .registerSubtype(HammerLike.Patterned.class, "patterned");
    NamespacedKey hammerKey = new NamespacedKey(this, "hammer");
    GrasperImpl grasper = new GrasperImpl(hammerFactory, hammerKey);
    List<Listener> listeners = Arrays.asList(new BreakListener(this), new PreBreakListener(this),
        new HammerListener(this, grasper, new GemGrasper()), new DropListener(this), new BlockDropListener(this));
    HammerConfig hammerConfig = new HammerConfig("hammer.yml", this);
    GenericMainCommandManager genericMainCommandManager1 = new GenericMainCommandManager("gems",
        "asd");
    genericMainCommandManager1.addSubCommand(new SetGem("set","asd", Arrays.stream(GemEnum.values()).map(Enum::toString).collect(
        Collectors.toSet()), new GemGrasper()));
    GenericMainCommandManager genericMainCommandManager = new GenericMainCommandManager("forge",
        "asd");
    genericMainCommandManager.addSubCommand(
        new ForgeHammerCreation("hammer", "asd", hammerConfig, grasper));
    genericMainCommandManager.addSubCommand(
        new ForgeHammerRadius("radius", "sets radius", hammerConfig, grasper));
    getCommand("forge").setExecutor(genericMainCommandManager);
    getCommand("gems").setExecutor(genericMainCommandManager1);
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}
