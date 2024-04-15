package mintychochip.forgehammers.commands;

import mintychochip.forgehammers.Grasper;
import mintychochip.genesis.builder.ItemBuilder;
import mintychochip.genesis.commands.abstraction.GenericCommandObject;
import mintychochip.genesis.commands.abstraction.SubCommand;
import mintychochip.genesis.container.AbstractItem;
import org.bukkit.entity.Player;

public class ForgeHammerCreation extends GenericCommandObject implements SubCommand {
    private final Grasper grasper;
    public ForgeHammerCreation(String executor, String description, Grasper grasper) {
        super(executor, description);
        this.grasper = grasper;
    }

    @Override
    public boolean execute(String[] strings, Player player) {
    }
}
