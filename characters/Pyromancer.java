package characters;


import greatmagician.Observable;
import angels.Angel;
import charactersstrategies.Context;
import charactersstrategies.AttackStrategy;
import charactersstrategies.DefenceStrategy;

import java.util.ArrayList;

public class Pyromancer extends MoldCharacter implements Visitable {
    private float rogueModifier = Constants.ROGUE_MODIFIER;
    private float knightModifier = Constants.KNIGHT_MODIFIER;
    private float pyromancerModifier = Constants.PYROMANCER_MODIFIER;
    private float wizardModifier = Constants.WIZARD_MODIFIER;
    private ArrayList<Float> modifiers;
    public Pyromancer(final char land, final Pair<Integer, Integer> coordonates, final char name) {
        super(land, coordonates, name);
        this.setHp(Constants.BASIC_HP_PYROMANCER);
        this.setStartHP(Constants.BASIC_HP_PYROMANCER);
        this.setLevelModifier(Constants.PYROMANCER_LEVEL_MODIFIER);
        modifiers = new ArrayList<>();
        modifiers.add(rogueModifier);
        modifiers.add(knightModifier);
        modifiers.add(pyromancerModifier);
        modifiers.add(wizardModifier);
    }
    /**setarea modificatorilor de catre ingeri. */
    public void setModifiers(final float value) {
        for (int i = 0; i < modifiers.size(); i++) {
            modifiers.set(i, modifiers.get(i) + value);
        }
    }
    @Override
    /** Sunt folosite atacurile definite in functiile fireblast
     * si ignite si sunt aplicati si modificatorii de rasa pentru fiecare
     * caracter.*/
    public void useSpells(final MoldCharacter opponent) {
           ArrayList<Float> ignite = ignite(opponent);
           float fireblastDamage = fireblast(opponent);
           float rightNowDamage = ignite.get(0);
           float longTermDamage = ignite.get(1);
           if (opponent.getName() == 'R') {
               fireblastDamage *= modifiers.get(0);
               rightNowDamage *= modifiers.get(0);
               longTermDamage *= modifiers.get(0);
           }
           if (opponent.getName() == 'K') {
               fireblastDamage *= modifiers.get(1);
               rightNowDamage *= modifiers.get(1);
               longTermDamage *= modifiers.get(1);
           }
           if (opponent.getName() == 'P') {
               fireblastDamage *= modifiers.get(2);
               rightNowDamage *= modifiers.get(2);
               longTermDamage *= modifiers.get(2);
           }
           if (opponent.getName() == 'W') {
               fireblastDamage *= modifiers.get(Constants.THREE);
               rightNowDamage *= modifiers.get(Constants.THREE);
               longTermDamage *= modifiers.get(Constants.THREE);
           }
           int totalDamageRightNow = Math.round(fireblastDamage) + Math.round(rightNowDamage);
           opponent.receiveDamage(totalDamageRightNow);
           int roundSOfLongTermDamage = 2;
           opponent.setRoundOfLongTermDamage(roundSOfLongTermDamage);
           opponent.setDamageOverTime(Math.round(longTermDamage));

    }

    /** */
    public float fireblast(final MoldCharacter opponent) {
         float damageRightNow = 0;
         damageRightNow = Constants.START_DAMAGE_FIREBLAST
                 + Constants.DAMAGE_PER_LEVEL_FIREBLAST * this.getLevel();
         final float pyromancerLandInd = 1.25f;
         if (this.getLand() == 'V') {
             damageRightNow *= pyromancerLandInd;
         }
         int poz = Math.round(damageRightNow);
         poz *= 1.0;
         damageRightNow = poz;
         return damageRightNow;

    }
    /** */
    public ArrayList<Float> ignite(final MoldCharacter opponent) {
        float damageRightNow;
        float longTermDamage;
        ArrayList<Float> res = new ArrayList<>();
        damageRightNow = Constants.START_DAMAGE_IGNITE_RIGHT_NOW
                + Constants.DAMAGE_PER_LEVEL_IGINTE_RIGHT_NOW * this.getLevel();
        longTermDamage = Constants.START_DAMAGE_IGNITE_LONG_TERM
                + Constants.DAMAGE_PER_LEVEL_IGNITE_LONG_TERM * this.getLevel();
        final float pyromancerLandInd = 1.25f;
        if (this.getLand() == 'V') {
            damageRightNow *= pyromancerLandInd;
            longTermDamage *= pyromancerLandInd;
        }
        int poz = Math.round(damageRightNow);
        int poz1 = Math.round(longTermDamage);
        poz *= 1.0;
        poz1 *= 1.0;
        damageRightNow = poz;
        longTermDamage = poz1;

        res.add(damageRightNow);
        res.add(longTermDamage);

        return res;

    }

    @Override
    /** */
    public void visit(final Pyromancer character) {
        this.useSpells(character);
    }

    @Override
    /** */
    public void visit(final Knight character) {
        this.useSpells(character);
    }

    @Override
    /** */
    public void visit(final Wizard character) {
        this.useSpells(character);
    }

    @Override
    /** */
    public void visit(final Rogue character) {
        this.useSpells(character);
    }
    /** */
    public void accept(final Visitable character) {
        character.visit(this);
    }
    /**notificarea observatorilor in cazul unei actinui esentiale in joc. */
    public void notifyAllObservers(final StringBuilder message) {
        for (Observable observator : observators) {
            observator.update(message);
        }
    }
    /** */
    @Override
    public void acceptVisitByAngel(final Angel angel) {
        angel.visitBYAngels(this);
    }
    /**adugare observatori. */
    @Override
    public void addobserver(final Observable observer) {
        observators.add(observer);
    }
    /**se alege strategia ofensiva sau defensiva in functie de Hp. */
    @Override
    public void decideStrategy() {
        int maxHp = Constants.BASIC_HP_PYROMANCER + this.getLevelModifier() * this.getLevel();
        if ((maxHp / Constants.FOUR) < this.getHp() && this.getHp() < (maxHp / Constants.THREE)) {
            Context c = new Context(new AttackStrategy());
            c.executeStrategy(this);

        } else  if ((maxHp / Constants.FOUR) > this.getHp()) {
            Context c1 = new Context(new DefenceStrategy());
            c1.executeStrategy(this);
        }
    }
    /**mesaj in cazul in care jucatorul omoara alt jucator. */
    @Override
    public StringBuilder createMessageforKill(final MoldCharacter opponent) {
        StringBuilder message = new StringBuilder();
        message.append("Player ");
        message.append(opponent.getClass().getSimpleName());
        message.append(" ");
        message.append(opponent.getId());
        message.append(" was killed by ");
        message.append(this.getClass().getSimpleName());
        message.append(" ").append(this.getId());
        return message;
    }
}
