package angels;

import characters.Pair;
import characters.Knight;
import characters.MoldCharacter;
import characters.Pyromancer;
import characters.Wizard;
import characters.Rogue;

public class Spawner extends Angel {
    private final  int pyroModifier = 150;
    private final  int knightModifier = 200;
    private final  int rogueModifier = 180;
    private final  int wizardModifier = 120;
    public Spawner(final Pair<Integer, Integer> angelCoordonates, final String name) {
        super(angelCoordonates, name);
    }
    /**mesajul care apare cand Spawner reinvie un jucator.*/
    public StringBuilder createMessage(final MoldCharacter character) {
        StringBuilder message = new StringBuilder();
        message.append(this.getAngelName());
        message.append(" helped ");
        message.append(character.getClass().getSimpleName());
        message.append(" ").append(character.getId());
        message.append("\n").append("Player ");
        message.append(character.getClass().getSimpleName());
        message.append(" ");
        message.append(character.getId());
        message.append(" was brought to life by an angel");
        return message;
    }
    /** */
    @Override
    public void visitBYAngels(final Pyromancer character) {
        if (!character.alive()) {
            character.setHp(pyroModifier);
            super.notifyAllObservers(createMessage(character));
        }
    }
    /** */
    @Override
    public void visitBYAngels(final Knight character) {
        if (!character.alive()) {
            character.setHp(knightModifier);
            super.notifyAllObservers(createMessage(character));
        }

    }
    /** */
    @Override
    public void visitBYAngels(final Wizard character) {
        if (!character.alive()) {
            character.setHp(wizardModifier);
            super.notifyAllObservers(createMessage(character));
        }

    }
    /** */
    @Override
    public void visitBYAngels(final Rogue character) {
        if (!character.alive()) {
            character.setHp(rogueModifier);
            super.notifyAllObservers(createMessage(character));
        }

    }
}
