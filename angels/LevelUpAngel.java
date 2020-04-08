package angels;

import characters.Pair;
import characters.Knight;
import characters.MoldCharacter;
import characters.Pyromancer;
import characters.Wizard;
import characters.Rogue;

public class LevelUpAngel extends Angel {
    private final  float pyroModifier = 0.2f;
    private final  float knightModifier = 0.1f;
    private final  float rogueModifier = 0.15f;
    private final  float wizardModifier = 0.25f;
    public LevelUpAngel(final Pair<Integer, Integer> angelCoordonates, final String name) {
        super(angelCoordonates, name);
    }
    /** */
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
           character.getLevelUpAngel();
           character.setModifiers(pyroModifier);
       }
    }
    /** */
    @Override
    public void visitBYAngels(final Knight character) {
        if (character.alive()) {
            super.notifyAllObservers(createMessage(character));
            character.setModifiers(knightModifier);
            character.getLevelUpAngel();

        }
    }
    /**mesajul care apare cand LevelUpAngel ataca un jucator. */
    @Override
    public void visitBYAngels(final Wizard character) {
        if (character.alive()) {
            super.notifyAllObservers(createMessage(character));
            character.setModifiers(wizardModifier);
            character.getLevelUpAngel();

        }
    }
    /** */
    @Override
    public void visitBYAngels(final Rogue character) {
        if (character.alive()) {
            super.notifyAllObservers(createMessage(character));
            character.setModifiers(rogueModifier);
            character.getLevelUpAngel();

        }
    }
}
