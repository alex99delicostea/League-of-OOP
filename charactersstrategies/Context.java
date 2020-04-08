package charactersstrategies;

import characters.MoldCharacter;

public class Context {
    private Strategy strategy;
    /** */
    public Context(final Strategy strategy) {
        this.strategy = strategy;
    }
    /** */
    public void executeStrategy(final MoldCharacter character) {
        strategy.implementStrategy(character);
    }
}
