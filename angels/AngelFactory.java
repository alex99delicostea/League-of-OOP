package angels;

import characters.Pair;

public class AngelFactory {
      protected AngelFactory() {
      }
      public static Angel getAngel(final Pair<Integer, Integer> coordonates, final String name) {
          if (name.equals("DamageAngel")) {
              return new DamageAngel(coordonates, name);
          }
          if (name.equals("DarkAngel")) {
              return new DarkAngel(coordonates, name);
          }
          if (name.equals("Dracula")) {
              return new Dracula(coordonates, name);
          }
          if (name.equals("GoodBoy")) {
              return new GoodBoy(coordonates, name);
          }
          if (name.equals("LevelUpAngel")) {
              return new LevelUpAngel(coordonates, name);
          }
          if (name.equals("LifeGiver")) {
              return new LifeGiver(coordonates, name);
          }
          if (name.equals("SmallAngel")) {
              return new SmallAngel(coordonates, name);
          }
          if (name.equals("TheDoomer")) {
              return new TheDoomer(coordonates, name);
          }
          if (name.equals("XPAngel")) {
              return new XPAngel(coordonates, name);
          }
          if (name.equals("Spawner")) {
              return new Spawner(coordonates, name);
          }
          return null;
      }
}
