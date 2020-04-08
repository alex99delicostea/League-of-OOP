package angels;

import characters.Pair;
import characters.Knight;
import characters.MoldCharacter;
import characters.Pyromancer;
import characters.Wizard;
import characters.Rogue;

public class LifeGiver extends Angel {
    private final  int pyroModifier = 80;
    private final  int knightModifier = 100;
    private final  int rogueModifier = 90;
    private final  int wizardModifier = 120;
    public LifeGiver(final Pair<Integer, Integer> angelCoordonates, final String name) {
        super(angelCoordonates, name);
    }
    /**mesajul care apare cand LiveGiver ajuta un jucator. */
    public StringBuilder createMessage(final MoldCharacter character) {
        StringBuilder message = new StringBuilder();
        message.append(this.getAngelName());
        message.append(" helped ");
        message.append(character.getClass().getSimpleName());
        message.append(" ");
        message.append(character.getId());
        return message;
    }
    /** */
    @Override
    public void visitBYAngels(final Pyromancer character) {
        if (character.alive()) {
            super.notifyAllObservers(createMessage(character));
            character.setHp(character.getHp() + pyroModifier);
        }
    }
    /** */
    @Override
    public void visitBYAngels(final Knight character) {
        if (character.alive()) {
            super.notifyAllObservers(createMessage(character));
            character.setHp(character.getHp() + knightModifier);
        }
    }
    /** */
    @Override
    public void visitBYAngels(final Wizard character) {
        if (character.alive()) {
            super.notifyAllObservers(createMessage(character));
            character.setHp(character.getHp() + wizardModifier);
        }
    }
    /** */
    @Override
    public void visitBYAngels(final Rogue character) {
        if (character.alive()) {
            super.notifyAllObservers(createMessage(character));
            character.setHp(character.getHp() + rogueModifier);
        }
    }
}
