package charactersstrategies;

import characters.Rogue;
import characters.Wizard;
import characters.Pyromancer;
import characters.Knight;
import characters.MoldCharacter;

public class DefenceStrategy implements Strategy {
    private final  int pyroModifierHp = 3;
    private final  int knightModifierHp = 4;
    private final  int rogueModifierHp = 2;
    private final  int wizardModifierHp = 5;
    private final  float pyroModifier = -0.3f;
    private final  float knightModifier = -0.2f;
    private final  float rogueModifier = -0.1f;
    private final  float wizardModifier = -0.2f;
    /**modificarile aduse Hp-ului si modifiactorilor in cazul amplicari
     * strategiei defensive.*/
    @Override
    public void implementStrategy(final MoldCharacter character) {
        if (character instanceof Knight) {
            character.setHp(character.getHp() + (character.getHp() / knightModifierHp));
            ((Knight) character).setModifiers(knightModifier);
        }
        if (character instanceof Pyromancer) {
            character.setHp(character.getHp() + (character.getHp() / pyroModifierHp));
            ((Pyromancer) character).setModifiers(pyroModifier);
        }
        if (character instanceof Rogue) {
            character.setHp(character.getHp() + (character.getHp() / rogueModifierHp));
            ((Rogue) character).setModifiers(rogueModifier);
        }
        if (character instanceof Wizard) {
            character.setHp(character.getHp() + (character.getHp() / wizardModifierHp));
            ((Wizard) character).setModifiers(wizardModifier);
        }
    }
}
