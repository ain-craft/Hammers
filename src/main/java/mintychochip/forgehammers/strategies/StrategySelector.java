package mintychochip.forgehammers.strategies;

import mintychochip.forgehammers.container.Hammer;
import mintychochip.forgehammers.container.Hammer.Traditional;

public interface StrategySelector {

    default HammerStrategy selectStrategy(Hammer hammer) {
        if (hammer instanceof Traditional) {
            return TraditionalHammerStrategy.INSTANCE;
        }
        return null;
    }
}
