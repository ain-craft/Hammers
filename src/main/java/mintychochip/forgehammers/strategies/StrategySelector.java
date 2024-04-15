package mintychochip.forgehammers.strategies;

import mintychochip.forgehammers.HammerType;

public interface StrategySelector {

    default HammerStrategy selectStrategy(HammerType type) {
        return switch(type) {
            case TRAD -> new TraditionalHammerStrategy();
            case PATTERNED -> new PatternedHammerStrategy();
        };
    }
}
