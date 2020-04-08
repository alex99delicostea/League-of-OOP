package characters;

import greatmagician.Observable;
import angels.Angel;
import charactersstrategies.Context;
import charactersstrategies.AttackStrategy;
import charactersstrategies.DefenceStrategy;

import java.util.ArrayList;

public class Knight extends MoldCharacter implements Visitable {
    private float rogueModifierExecute = Constants.ROGUE_MODFIER_EXECUTE;
    private float knightModifierExecute = Constants.KNIGHT_MODIFIER_EXECUTE;
    private float pyromancerModifierExecute = Constants.PYROMANCER_MODIFIER_EXECUTE;
    private float wizardModifierExecute = Constants.WIZARD_MODIFIER_EXECUTE;
    private float rogueModifierSlam = Constants.ROGUE_MODIFIER_SLAM;
    private float knightModifierSlam = Constants.KNIGHT_MODIFIER_SLAM;
    private float pyromancerModifierSlam = Constants.PYROMANCER_MODIFIER_SLAM;
    private float wizardModifierSlam = Constants.WIZARD_MODIFIER_SLAM;
    private ArrayList<Float> modifiers;
    public Knight(final char land, final Pair<Integer, Integer> coordonates, final char name) {
        super(land, coordonates, name);
        this.setHp(Constants.BASIC_HP_KNIGHT);
        this.setStartHP(Constants.BASIC_HP_KNIGHT);
        this.setLevelModifier(Constants.KNIGHT_LEVEL_MODIFIER);
        modifiers = new ArrayList<>();
        modifiers.add(rogueModifierExecute);
        modifiers.add(pyromancerModifierExecute);
        modifiers.add(wizardModifierExecute);
        modifiers.add(rogueModifierSlam);
        modifiers.add(knightModifierSlam);
        modifiers.add(pyromancerModifierSlam);
        modifiers.add(wizardModifierSlam);

    }
    /**setarea modificatorilor de catre ingeri.*/
    public void setModifiers(final float value) {
         for (int i = 0; i < modifiers.size(); i++) {
             modifiers.set(i, modifiers.get(i) + value);
        }
    }

    @Override
    /**folosirea atacurilor execute si slam fiind apelati si modoficatorii
     * de rasa. */
    public void useSpells(final MoldCharacter opponent) {

            ArrayList<Float> damageExecute = this.execute(opponent);
            ArrayList<Float> damageSlam = this.slam(opponent);
            float realDamageExecute = damageExecute.get(0);
            float realDamageSlam = damageSlam.get(0);
            if (opponent.getName() == 'R' && damageExecute.get(1) == 0.0) {
                realDamageExecute *= modifiers.get(0);
                realDamageSlam *= modifiers.get(Constants.THREE);

            }
            if (opponent.getName() == 'K' && damageExecute.get(1) == 0.0) {
                realDamageSlam *= modifiers.get(Constants.FOUR);

            }
             if (opponent.getName() == 'P' && damageExecute.get(1) == 0.0) {
                 realDamageExecute *= modifiers.get(1);
                 realDamageSlam *= modifiers.get(Constants.FIVE);

             }
             if (opponent.getName() == 'W' && damageExecute.get(1) == 0.0) {
                  realDamageExecute *= modifiers.get(2);
                  realDamageSlam *= modifiers.get(Constants.SIX);
             }
            int totalDamage = Math.round(realDamageExecute);
          if (realDamageSlam > 0) {
            opponent.setImobilized(1);
            totalDamage += Math.round(realDamageSlam);
          }
            opponent.receiveDamage(totalDamage);
    }
    /**Functia de execute in care se calculeaza limita minima pentru care
     * atacul nu este fatal,in acest caz putand fi efectuat si atacul slam. */
    public ArrayList<Float> execute(final MoldCharacter opponent) {
        float ok = 0;
        ArrayList<Float> res = new ArrayList<>();
        float limitHp;
        final float lifePercent = 0.2f;
        final float maxPercent = 0.4f;
        limitHp = lifePercent * (opponent.getStartHP() + opponent.getLevel()
                * opponent.getLevelModifier());
        if (this.getLevel() < Constants.MAX_LEVEL_RAISE_FOR_EXECUTE) {
            limitHp += ((float) (this.getLevel()) / (float) Constants.PERCENT)
                    * (opponent.getStartHP() + opponent.getLevel()
                    * opponent.getLevelModifier());
        } else {
            limitHp += maxPercent * (opponent.getStartHP() + opponent.getLevel()
                    * opponent.getLevelModifier());
        }
        float damageRightNow;
        if (limitHp > (float) opponent.getHp() && opponent.alive()) {
            ok = 1;
            damageRightNow = (float) opponent.getHp();
        } else {
            damageRightNow = Constants.START_DAMAGE_EXECUTE
                    + Constants.DAMAGE_PER_LEVEL_EXECUTE * this.getLevel();
        }

        final float knightLandInd = 1.15f;
        if (this.getLand() == 'L' && ok == 0) {
            damageRightNow *= knightLandInd;
        }
        int pos = Math.round(damageRightNow);
        pos *= 1.0;
        damageRightNow = pos;

        res.add(damageRightNow);
        res.add(ok);
        return res;
    }
    /**se verifica daca prin execute adeversarul a fost omorat,in caz contrar
     * aplicandu se atacul. */
    public ArrayList<Float> slam(final MoldCharacter opponent) {
        float ok1 = this.execute(opponent).get(1);
        //System.out.println(ok1);
        ArrayList<Float> res1 = new ArrayList<>();
        float damageRightNow = 0;
        if (ok1 == 0) {
            damageRightNow = Constants.START_DAMAGE_SLAM
                    + Constants.DAMAGE_PER_LEVEL_SLAM * this.getLevel();
           final  float knightLandInd = 1.15f;
            if (this.getLand() == 'L') {
                damageRightNow *= knightLandInd;
            }
        }
        int pos = Math.round(damageRightNow);
        pos *= 1.0;
        damageRightNow = pos;
        res1.add(damageRightNow);
        res1.add(ok1);
        return res1;
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
    /**notificarea observatorilor in cazul unei actinui esentiale in joc.*/
    @Override
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
    /**adugare observatori.*/
    @Override
    public void addobserver(final Observable observer) {
        observators.add(observer);
    }
    /**se alege strategia ofensiva sau defensiva in functie de Hp.*/
    @Override
    public void decideStrategy() {
        int maxHp = Constants.BASIC_HP_KNIGHT + this.getLevelModifier() * this.getLevel();
        if ((maxHp / Constants.THREE) < this.getHp() && this.getHp() < (maxHp / 2)) {
            Context c = new Context(new AttackStrategy());
            c.executeStrategy(this);

     } else if ((maxHp / Constants.THREE) > this.getHp()) {
            Context c1 = new Context(new DefenceStrategy());
            c1.executeStrategy(this);
        }
    }
    /**mesaj in cazul in care jucatorul omoara alt jucator.*/
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



