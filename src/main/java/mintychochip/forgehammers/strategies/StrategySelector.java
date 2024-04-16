package mintychochip.forgehammers.strategies;

import mintychochip.forgehammers.Hammer;
import mintychochip.forgehammers.Hammer.Traditional;

public interface StrategySelector {

    default HammerStrategy selectStrategy(Hammer hammer) {
        if (hammer instanceof Traditional) {
            return new TraditionalHammerStrategy();
        }
        return null;
    }
}
