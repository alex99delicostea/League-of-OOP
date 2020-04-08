package charactersstrategies;

import characters.Rogue;
import characters.Wizard;
import characters.Pyromancer;
import characters.Knight;
import characters.MoldCharacter;

public class AttackStrategy implements Strategy {
    private final  int pyroModifierHp = 4;
    private final  int knightModifierHp = 5;
    private final  int rogueModifierHp = 7;
    private final  int wizardModifierHp = 10;
    private final  float pyroModifier = 0.7f;
    private final  float knightModifier = 0.5f;
    private final  float rogueModifier = 0.4f;
    private final  float wizardModifier = 0.6f;
    /**modificarile aduse Hp-ului si modifiactorilor in cazul amplicari
     * strategiei ofensive. */
    @Override
    public void implementStrategy(final MoldCharacter character) {
        if (character instanceof Knight && character.alive()) {
            character.setHp(character.getHp() - (character.getHp() / knightModifierHp));
            ((Knight) character).setModifiers(knightModifier);
        }
        if (character instanceof Pyromancer && character.alive()) {
            character.setHp(character.getHp() - (character.getHp() / pyroModifierHp));
            ((Pyromancer) character).setModifiers(pyroModifier);
        }
        if (character instanceof Rogue && character.alive()) {
            character.setHp(character.getHp() - (character.getHp() / rogueModifierHp));
            ((Rogue) character).setModifiers(rogueModifier);
        }
        if (character instanceof Wizard && character.alive()) {
            character.setHp(character.getHp() - (character.getHp() / wizardModifierHp));
            ((Wizard) character).setModifiers(wizardModifier);
        }
    }
}
