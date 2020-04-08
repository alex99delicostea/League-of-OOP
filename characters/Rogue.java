package characters;

import greatmagician.Observable;
import angels.Angel;
import charactersstrategies.Context;
import charactersstrategies.AttackStrategy;
import charactersstrategies.DefenceStrategy;

import java.util.ArrayList;

public class Rogue extends MoldCharacter implements Visitable {
    private int backstabIndex;
    private int roundSOfLongTermDamage = Constants.ROUNDS_ROGUE;
    private float rogueModifierBackstab = Constants.ROGUE_MODIFIER_BACKSTAB;
    private float knightModifierBackstab = Constants.KNIGHT_MODIFIER_BACKSTAB;
    private float pyromancerModifierBackstab = Constants.PYROMANCER_MODIFIER_BACKSTAB;
    private float wizardModifierBackstab = Constants.WIZARD_MODIFIER_BACKSTAB;
    private float rogueModifierParalysis = Constants.ROGUE_MODIFIER_PARALYSIS;
    private float knightModifierParalysis = Constants.KNIGHT_MODIFIER_PARALYSIS;
    private float pyromancerModifierParalysis = Constants.PYROMANCER_MODIFIER_PARALYSIS;
    private float wizardModifierParalysis = Constants.WIZARD_MODIFIER_PARALYSIS;
    private ArrayList<Float> modifiers;
    public Rogue(final char land, final Pair<Integer, Integer> coordonates, final char name) {
        super(land, coordonates, name);
        this.setHp(Constants.BASIC_HP_ROGUE);
        this.setStartHP(Constants.BASIC_HP_ROGUE);
        this.setLevelModifier(Constants.ROGUE_LEVEL_MODIFIER);
        this.backstabIndex = 0;
        modifiers = new ArrayList<>();
        modifiers.add(rogueModifierBackstab);
        modifiers.add(knightModifierBackstab);
        modifiers.add(pyromancerModifierBackstab);
        modifiers.add(wizardModifierBackstab);
        modifiers.add(rogueModifierParalysis);
        modifiers.add(knightModifierParalysis);
        modifiers.add(pyromancerModifierParalysis);
        modifiers.add(wizardModifierParalysis);
    }
    /**setarea modificatorilor de catre ingeri. */
    public void setModifiers(final float value) {
        for (int i = 0; i < modifiers.size(); i++) {
            modifiers.set(i, modifiers.get(i) + value);
        }
    }
    /** */
    public void increseBackstabIndex() {
        this.backstabIndex++;
    }
    /** */
    public void decreseBackstabIndex() {
        this.backstabIndex--;
    }

    @Override
    /** */
    public void useSpells(final MoldCharacter opponent) {
             ArrayList<Float> paralysis = paralysis(opponent);
             float paralysisRightNow = paralysis.get(0);
             float paralysisLongTerm = paralysis.get(1);
             float backstab = backstab(opponent);
             if (opponent.getName() == 'R') {
                  backstab *= modifiers.get(0);
                  paralysisRightNow *= modifiers.get(Constants.FOUR);
                  paralysisLongTerm *= modifiers.get(Constants.FOUR);

             }
             if (opponent.getName() == 'K') {
                 backstab *= modifiers.get(1);
                 paralysisRightNow *= modifiers.get(Constants.FIVE);
                 paralysisLongTerm *= modifiers.get(Constants.FIVE);

             }
             if (opponent.getName() == 'P') {
                 backstab *= modifiers.get(2);
                 paralysisRightNow *= modifiers.get(Constants.SIX);
                 paralysisLongTerm *= modifiers.get(Constants.SIX);
             }
              if (opponent.getName() == 'W') {
                  backstab *= modifiers.get(Constants.THREE);
                  paralysisRightNow *= modifiers.get(Constants.SEVEN);
                  paralysisLongTerm *= modifiers.get(Constants.SEVEN);

              }

             int rightNowDamage = Math.round(backstab) + Math.round(paralysisRightNow);
             opponent.receiveDamage(rightNowDamage);
             opponent.setImobilized(roundSOfLongTermDamage);
             opponent.setRoundOfLongTermDamage(roundSOfLongTermDamage);
             opponent.setDamageOverTime(Math.round(paralysisLongTerm));

    }
    /**damage al atacului backstab ce poate fi marit in cazul in care
     * locatia bataliei este pe teren ul favorabil caracterului Rogue si
     * lovitura data este "critical hit"(backstabIndex % 3 == 0).*/
    public float backstab(final MoldCharacter opponent) {
         float backStabDamage = 0;
         backStabDamage = Constants.START_DAMAGE_BACKSTAB
                 + Constants.DAMAGE_PER_LEVEL_BACKSTAB * this.getLevel();
         final float updateDamage = 1.5f;
         if (this.getLand() == 'W') {
             if (backstabIndex % Constants.ROUNDS_ROGUE == 0) {
                 backStabDamage *= updateDamage;
             }
         }
         backstabIndex++;
        final float rogueLandInd = 1.15f;
        if (this.getLand() == 'W') {
            backStabDamage *= rogueLandInd;

        }
         return backStabDamage;
    }
    /**pe langa damage ul dat,este indusa o imobilizare de 3 runde sau de 6
     * in cazul in care terenul pe care are loc batalia este cel favorabil lui Rogue.*/
    public ArrayList<Float> paralysis(final MoldCharacter opponent) {
        ArrayList<Float> res = new ArrayList<>();
        float damageRightNow = 0;
        float longTermDamage = 0;

        damageRightNow = Constants.START_DAMAGE_PARALYSIS
                + Constants.DAMAGE_PER_LEVEL_PARALYSIS * this.getLevel();
        longTermDamage = damageRightNow;
        if (this.getLand() == 'W') {
            roundSOfLongTermDamage = Constants.ROUND_ROGUE_ON_WOODS;
        } else {
            roundSOfLongTermDamage = Constants.ROUNDS_ROGUE;
        }
        final float rogueLandInd = 1.15f;
        if (this.getLand() == 'W') {
            damageRightNow *= rogueLandInd;
            longTermDamage *= rogueLandInd;

        }

        res.add(damageRightNow);
        res.add(longTermDamage);
        return res;
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
        int maxHp = Constants.BASIC_HP_ROGUE + this.getLevelModifier() * this.getLevel();
        if ((maxHp / Constants.SEVEN) < this.getHp() && this.getHp() < (maxHp / Constants.FIVE)) {
            Context c = new Context(new AttackStrategy());
            c.executeStrategy(this);
        }
        if ((maxHp / Constants.FIVE) > this.getHp()) {
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
        message.append(" ");
        message.append(this.getId());
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
    public void accept(final Visitable opponent) {
        opponent.visit(this);
    }

}
