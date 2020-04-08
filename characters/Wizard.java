package characters;

import greatmagician.Observable;
import angels.Angel;
import charactersstrategies.Context;
import charactersstrategies.AttackStrategy;
import charactersstrategies.DefenceStrategy;

import java.util.ArrayList;

public class Wizard extends MoldCharacter implements Visitable {
   private float rogueModifierDrain = Constants.ROGUE_MODFIER_DRAIN;
   private float knightModifierDrain = Constants.KNIGHT_MODIFIER_DRAIN;
   private float pyromancerModifierDrain = Constants.PYROMANCER_MODIFIER_DRAIN;
   private float wizardModifierDrain = Constants.WIZARD_MODIFIER_DRAIN;
   private float rogueModifierDeflect = Constants.ROGUE_MODIFIER_DEFLECT;
   private float knightModifierDeflect = Constants.KNIGHT_MODIFIER_DEFLECT;
   private float pyromancerModifierDeflect = Constants.PYROMANCER_MODIFIER_DEFLECT;
   private ArrayList<Float> modifiers;
    public Wizard(final char land, final Pair<Integer, Integer> coordonates, final char name) {
        super(land, coordonates, name);
        this.setHp(Constants.BASIC_HP_WIZARD);
        this.setStartHP(Constants.BASIC_HP_WIZARD);
        this.setLevelModifier(Constants.WIZARD_LEVEL_MODIFIER);
        modifiers = new ArrayList<>();
        modifiers.add(rogueModifierDrain);
        modifiers.add(knightModifierDrain);
        modifiers.add(pyromancerModifierDrain);
        modifiers.add(wizardModifierDrain);
        modifiers.add(rogueModifierDeflect);
        modifiers.add(knightModifierDeflect);
        modifiers.add(pyromancerModifierDeflect);
    }
    /**setarea modificatorilor de catre ingeri. */
    public void setModifiers(final float value) {
        for (int i = 0; i < modifiers.size(); i++) {
            modifiers.set(i, modifiers.get(i) + value);
        }
    }

    @Override
    /** utilizarea atacurilor din drain si deflect. */
    public void useSpells(final MoldCharacter opponent) {
           int drainDamage = Math.round(drain(opponent));
           int deflectDamage = Math.round(deflect(opponent));
           int total = deflectDamage + drainDamage;
           opponent.receiveDamage(total);

    }
    /**calcularea damage drain dupa formula data si dupa utilizarea
     * modificatorilor de rasa. */
    public float drain(final MoldCharacter opponent) {
         float drainProcent = Constants.BASE_DRAIN_PERCENT
                 + Constants.DRAIN_PERCENT_PER_LEVEL * this.getLevel();
         float basicHp = Math.min(Constants.WIZARD_PERCENT
                 * (opponent.getStartHP() + opponent.getLevelModifier()
                 * opponent.getLevel()), opponent.getHp());

         if (opponent.getName() == 'R') {
             drainProcent *= modifiers.get(0);
         }
         if (opponent.getName() == 'K') {
             drainProcent *= modifiers.get(1);
         }
         if (opponent.getName() == 'P') {
             drainProcent *= modifiers.get(2);
         }
         if (opponent.getName() == 'W') {
             drainProcent *= modifiers.get(Constants.THREE);
         }
        float damage = drainProcent * basicHp;
        final float wizardLandInd = 1.1f;
        if (this.getLand() == 'D') {
            damage *= wizardLandInd;
        }
         return damage;
    }
    /**damage ul deflet este realizat prin inmultirea procentului(deflectpercent)
     * cu damage ul direct generat de adversar caracterului wizard. */
    public float deflect(final MoldCharacter opponent) {
        float deflectpercent = Constants.BASE_DEFLECT_PERCENT
                + Constants.DEFLECT_PERCENT_PER_LEVEL * this.getLevel();
        float damageFromAbility = 0;
        if (opponent.getName() == 'P') {
            ArrayList<Float> res = ((Pyromancer) opponent).ignite(this);
            damageFromAbility = Math.round(((Pyromancer) opponent)
                    .fireblast(this)) + Math.round(res.get(0));

        }
        if (opponent.getName() == 'K') {

            damageFromAbility = ((Knight) opponent).execute(this).get(0);
            if (((Knight) opponent).execute(this).get(1) == 0) {
                damageFromAbility += ((Knight) opponent).slam(this).get(0);
            }

        }
        if (opponent.getName() == 'R') {
            ((Rogue) opponent).decreseBackstabIndex();
            ArrayList<Float> res1 = ((Rogue) opponent).paralysis(this);
            damageFromAbility = ((Rogue) opponent).backstab(this) + res1.get(0);
            ((Rogue) opponent).increseBackstabIndex();
        }
        float totalDamage = damageFromAbility * deflectpercent;
        final float wizardLandInd = 1.1f;
        if (this.getLand() == 'D') {
            totalDamage *= wizardLandInd;
        }

        if (opponent.getName() == 'R') {
            totalDamage *= modifiers.get(Constants.FOUR);
        }
        if (opponent.getName() == 'K') {
            totalDamage *= modifiers.get(Constants.FIVE);
        }
        if (opponent.getName() == 'P') {
            totalDamage *= modifiers.get(Constants.SIX);
        }

       return totalDamage;
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
    /**adaugare observator. */
    @Override
    public void addobserver(final Observable observer) {
        observators.add(observer);
    }
    /**se alege strategia ofensiva sau defensiva in functie de Hp. */
    @Override
    public void decideStrategy() {
        int maxHp = Constants.BASIC_HP_WIZARD + this.getLevelModifier() * this.getLevel();
        if ((maxHp / Constants.FOUR) < this.getHp() && this.getHp() < (maxHp / 2)) {
            Context c = new Context(new AttackStrategy());
            c.executeStrategy(this);
        }
        if ((maxHp / Constants.FOUR) > this.getHp()) {
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

}
