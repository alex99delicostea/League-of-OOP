package characters;

import greatmagician.Observable;
import angels.Angel;

import java.util.ArrayList;

public  abstract class MoldCharacter implements Visitable {
    private int id;
    private int hp;
    private int xp;
    private char name;
    private char land;
    private int startHP;
    private int levelModifier;
    private Pair<Integer, Integer> coordonates;
    private int damageOverTime;
    private int imobilized;
    private int level;
    private int roundOfLongTermDamage;
    protected ArrayList<Observable> observators;

    public MoldCharacter(final char land, final Pair<Integer, Integer>
            coordonates, final char name) {
        this.land = land;
        this.coordonates = coordonates;
        this.name = name;
        hp = 0;
        xp = 0;
        imobilized = 0;
        level = 0;
        observators = new ArrayList<>();
    }
    /** */
    public void setId(final int id) {
        this.id = id;
    }
    /** */
    public int getId() {
        return id;
    }
    /**setarea Hp-ului unui jucator,in cazul in care aceasta devine mai mica sau egala cu 0
     * acesta este omorat si daca este mai mare decat Hp-ul maxim posibil la nivelul curent
     * se truncheaza la acea valoare.*/
    public void setHp(final int hp) {
        this.hp = hp;
        if (this instanceof Knight) {
            int maxHp = Constants.BASIC_HP_KNIGHT + this.getLevelModifier() * this.getLevel();
            if (this.hp > maxHp) {
                this.hp = maxHp;
            }
        }
        if (this instanceof Pyromancer) {
            int maxHp = Constants.BASIC_HP_PYROMANCER + this.getLevelModifier() * this.getLevel();
            if (this.hp > maxHp) {
                this.hp = maxHp;
            }
        }
        if (this instanceof Wizard) {
            int maxHp = Constants.BASIC_HP_WIZARD + this.getLevelModifier() * this.getLevel();
            if (this.hp > maxHp) {
                this.hp = maxHp;
            }
        }
        if (this instanceof Rogue) {
            int maxHp = Constants.BASIC_HP_ROGUE + this.getLevelModifier() * this.getLevel();
            if (this.hp > maxHp) {
                this.hp = maxHp;
            }
        }
        if (this.hp <= 0) {
            this.notifyAllObservers(createMessage(this));
        }
    }
    /**mesaj ce apare cand un jucator este omorat de un inger.*/
    public StringBuilder createMessage(final MoldCharacter character) {
        StringBuilder message = new StringBuilder();
        message.append("Player ");
        message.append(character.getClass().getSimpleName());
        message.append(" ");
        message.append(character.getId());
        message.append(" was killed by an angel");
        return message;
    }
    /**mesaj ce apare cand un jucator trece la nivelul urmator. */
    public StringBuilder createMessageForLevelUp(final MoldCharacter character) {
        StringBuilder message = new StringBuilder();
        message.append(character.getClass().getSimpleName());
        message.append(" ");
        message.append(character.getId());
        message.append(" reached level ").append(character.getLevel());
        return message;
    }

    /** */
    public int getHp() {
        return this.hp;
    }
    /** */
    public void setXp(final int xp) {
        this.xp = xp;
    }
    /** */
    public int getXp() {
        return this.xp;
    }
    /** */
    public int getLevel() {
        return this.level;
    }
    /** */
    public char getName() {
        return this.name;
    }
    /** */
    public void setName(final char name) {
        this.name = name;
    }
    /** */
    public char getLand() {
        return this.land;
    }
    /** */
    public void setLand(final char land) {
        this.land = land;
    }
    /** */
    public void setStartHP(final int startHP) {
        this.startHP = startHP;
    }
    /** */
    public int getStartHP() {
        return this.startHP;
    }
    /** */
    public boolean alive() {
        return this.hp > 0;
    }
    /** */
    public void setRoundOfLongTermDamage(final int rounds) {
        roundOfLongTermDamage = rounds;
    }
    /** */
    public void setDamageOverTime(final int damage) {
        damageOverTime = damage;
    }
    /** */
    public int getDamageOverTime() {
        return this.damageOverTime;
    }
    /** */
    public int getLevelModifier() {
        return this.levelModifier;
    }
    /** */
    public void setLevelModifier(final int levelModifier) {
        this.levelModifier = levelModifier;
    }
    /** */
    public float getRoundOfLongTermDamage() {
        return this.roundOfLongTermDamage;
    }
    /** */
    public void setImobilized(final int number) {
        this.imobilized = number;
    }
    /** */
    public int getImobilized() {
        return this.imobilized;
    }
    /** */
    public void setCoordonates(final Pair<Integer, Integer> coordonates) {
        this.coordonates = coordonates;
    }
    /** */
    public Pair<Integer, Integer> getCoordonates() {
        return this.coordonates;
    }
    /**Este calculat xp-ul,noul hp si level ul castigatorului in urma
     * unei batalii.Daca castigatorul depaseste 250 xp acesta se afla la nivelul 1,
     * orice alta crestere de inca 50 xp garantand o noua crestere si in nivel.*/
    public void  xpAndLevelModifier(final MoldCharacter opponent, final int lev) {
          this.xp = this.xp + Math.max(0, Constants.MAX_XP_TO_GET
                 - (this.getLevel() - lev) * Constants.FORTY);
          float copyXp = this.xp;
          int levelPossible = 0;
          while (copyXp >= Constants.LIMIT_FOR_UPDATE_LEVEL) {
              if (levelPossible == 0) {
                  if (copyXp >= Constants.LIMIT_FOR_UPDATE_TO_FIRST_LEVEL) {
                      levelPossible++;
                      copyXp -= Constants.LIMIT_FOR_UPDATE_TO_FIRST_LEVEL;
                  } else {
                      break;
                  }
              } else {
                      levelPossible++;
                      copyXp -= Constants.LIMIT_FOR_UPDATE_LEVEL;
              }
          }
      if (levelPossible > this.level) {
          if (this.getName() == 'P' && this.alive()) {
              this.hp = Constants.BASIC_HP_PYROMANCER
                      + Constants.PYROMANCER_LEVEL_MODIFIER * levelPossible;
          }
          if (this.getName() == 'K' && this.alive()) {
              this.hp = Constants.BASIC_HP_KNIGHT
                      + Constants.KNIGHT_LEVEL_MODIFIER * levelPossible;
          }
          if (this.getName() == 'W' && this.alive()) {
              this.hp = Constants.BASIC_HP_WIZARD
                      + Constants.WIZARD_LEVEL_MODIFIER * levelPossible;
          }
          if (this.getName() == 'R' && this.alive()) {
              this.hp = Constants.BASIC_HP_ROGUE
                      + Constants.ROGUE_LEVEL_MODIFIER * levelPossible;
          }
          for (int i = this.level; i < levelPossible; i++) {
              this.level++;
              this.notifyAllObservers(createMessageForLevelUp(this));

          }
      }
    }
    /**jucatorul primeste Xp de la XPAngel si in cazul in care este destul de
     * mult pentru a trece la nivelul urmator primeste si Hp-ul corespunzator. */
    public void setXpByAngel(final int xpGot) {
        int lev = this.level;
        int initXp = this.xp;
        this.xp += xpGot;
        int dif = (this.xp / Constants.LIMIT_FOR_UPDATE_LEVEL)
                - (initXp / Constants.LIMIT_FOR_UPDATE_LEVEL);
      if (this.xp >= Constants.LIMIT_FOR_UPDATE_TO_FIRST_LEVEL) {
          for (int i = 0; i < dif; i++) {
              this.level++;
              this.notifyAllObservers(createMessageForLevelUp(this));
          }
      }
      if (this.level > lev) {
          if (this.getName() == 'P') {
              this.hp = Constants.BASIC_HP_PYROMANCER
                      + Constants.PYROMANCER_LEVEL_MODIFIER * this.level;
          }
          if (this.getName() == 'K') {
              this.hp = Constants.BASIC_HP_KNIGHT
                      + Constants.KNIGHT_LEVEL_MODIFIER * this.level;
          }
          if (this.getName() == 'W') {
              this.hp = Constants.BASIC_HP_WIZARD
                      + Constants.WIZARD_LEVEL_MODIFIER * this.level;
          }
          if (this.getName() == 'R') {
              this.hp = Constants.BASIC_HP_ROGUE
                      + Constants.ROGUE_LEVEL_MODIFIER * this.level;
          }
      }

    }
    /**Modificarea Xp-ului si Hp-ului dupa vizita lui LevelUpAngel.*/
    public void getLevelUpAngel() {
        int lev = this.level;
        if (this.getXp() < Constants.LIMIT_FOR_UPDATE_TO_FIRST_LEVEL) {
            this.setXp(Constants.LIMIT_FOR_UPDATE_TO_FIRST_LEVEL);
            this.level++;
            this.notifyAllObservers(createMessageForLevelUp(this));
        } else if (this.getXp() >= Constants.LIMIT_FOR_UPDATE_TO_FIRST_LEVEL) {
            this.setXp(this.getXp() + Constants.LIMIT_FOR_UPDATE_LEVEL
                    - (this.getXp() % Constants.LIMIT_FOR_UPDATE_LEVEL));
            this.level++;
            this.notifyAllObservers(createMessageForLevelUp(this));
        }
      if (this.level > lev) {
          if (this.getName() == 'P') {
              this.hp = Constants.BASIC_HP_PYROMANCER
                      + Constants.PYROMANCER_LEVEL_MODIFIER * this.level;
          }
          if (this.getName() == 'K') {
              this.hp = Constants.BASIC_HP_KNIGHT
                      + Constants.KNIGHT_LEVEL_MODIFIER * this.level;
          }
          if (this.getName() == 'W') {
              this.hp = Constants.BASIC_HP_WIZARD
                      + Constants.WIZARD_LEVEL_MODIFIER * this.level;
          }
          if (this.getName() == 'R') {
              this.hp = Constants.BASIC_HP_ROGUE
                      + Constants.ROGUE_LEVEL_MODIFIER * this.level;
          }
      }
    }
    /** schimbarea coordonatelor pe harta in cazul in care caracterul nu
     * este imobilizat. */
    public void move(final Pair<Integer, Integer> newCoordonates) {
      if (this.alive() && imobilized == 0) {
          this.coordonates = newCoordonates;
      } else if (this.alive() && imobilized > 0) {
              this.imobilized--;
      }

    }
    /**incasare damage direct. */
    public void receiveDamage(final int damage) {
      if (this.alive()) {
          this.hp -= damage;
      }
    }
    /**incasare damage pe termen lung. */
    public void receiveLongTermDamage() {
        float damage = this.getDamageOverTime();
        if (this.alive() && this.roundOfLongTermDamage > 0) {
            this.hp -= damage;
        }
        roundOfLongTermDamage--;
    }
    public abstract void useSpells(MoldCharacter opponent);
    public abstract void accept(Visitable opponent);
    public abstract void notifyAllObservers(StringBuilder message);
    public abstract void acceptVisitByAngel(Angel angel);
    public abstract void addobserver(Observable observer);
    public abstract void decideStrategy();
    public abstract StringBuilder createMessageforKill(MoldCharacter opponent);

}
