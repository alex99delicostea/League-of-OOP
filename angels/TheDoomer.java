package angels;

import characters.Pair;
import characters.Knight;
import characters.MoldCharacter;
import characters.Pyromancer;
import characters.Wizard;
import characters.Rogue;

public class TheDoomer extends Angel {
    public TheDoomer(final Pair<Integer, Integer> angelCoordonates, final String name) {
        super(angelCoordonates, name);
    }
    /**mesajul care apare cand TheDoomer ataca un jucator. */
    public StringBuilder createMessage(final MoldCharacter character) {
        StringBuilder message = new StringBuilder();
        message.append(this.getAngelName());
        message.append(" hit ");
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
          character.setHp(0);
      }
    }
    /** */
    @Override
    public void visitBYAngels(final Knight character) {
        if (character.alive()) {
            super.notifyAllObservers(createMessage(character));
            character.setHp(0);
        }
    }
    /** */
    @Override
    public void visitBYAngels(final Wizard character) {
        if (character.alive()) {
            super.notifyAllObservers(createMessage(character));
            character.setHp(0);
        }

    }
    /** */
    @Override
    public void visitBYAngels(final Rogue character) {
        if (character.alive()) {
            super.notifyAllObservers(createMessage(character));
            character.setHp(0);
        }

    }
}
