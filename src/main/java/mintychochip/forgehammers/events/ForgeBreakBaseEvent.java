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

package mintychochip.forgehammers.events;

import mintychochip.forgehammers.strategies.TraditionalHammerStrategy.Cardinal;
import mintychochip.genesis.events.AbstractEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class ForgeBreakBaseEvent extends AbstractEvent {

    /**
     * Post event, is only called by 'ForgePreBlockBreakEvent'
     */


    protected final Player player;

    protected Block block;

    protected final Cardinal cardinal;

    public Cardinal getCardinal() {
        return cardinal;
    }

    protected ForgeBreakBaseEvent(Cardinal cardinal, Player player) {
        this.cardinal = cardinal;
        this.player = player;
    }
    public Player getPlayer() {
        return player;
    }

}
