package main;

import greatmagician.GreatMagician;
import angels.Angel;
import angels.AngelFactory;
import characters.CharacterFactory;
import characters.MoldCharacter;
import characters.Pair;


import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

final class Main {
    private Main() {
    }
    public static void main(final String[] args) throws IOException {
          String fileIn = args[0];
          fileio.implementations.FileReader reader =
                  new fileio.implementations.FileReader(fileIn);
        String fileOut = args[1];
        PrintStream writer = new PrintStream(fileOut);
        System.setOut(writer);
        GreatMagician magician = GreatMagician.getInstance();
        Map map = Map.getInstance();
          int length = reader.nextInt();
          int width = reader.nextInt();
          char[][] keeper = new char[length][width];
          /** crearea terenului de bataie*/
          for (int i = 0; i < length; i++) {
              String word = reader.nextWord();
              char[] buffer = word.toCharArray();
              if (width >= 0) {
                  System.arraycopy(buffer, 0, keeper[i], 0, width);
              }
          }
          map.asignLands(length, width, keeper);
          int nrCharacters = reader.nextInt();
          ArrayList<MoldCharacter> characters = new ArrayList<>(nrCharacters);
          /** fiecarului caracter i se asciaza numele,coordonatele initiale si
          si tipul terenului pe care se afla.*/
          for (int i = 0; i < nrCharacters; i++) {
              String a = reader.nextWord();
              char name = a.charAt(0);
              Pair<Integer, Integer> coordonates = new Pair<>(0, 0);
              coordonates.setFirst(reader.nextInt());
              coordonates.setSecond(reader.nextInt());
              char land  = map.getPosition(coordonates.getFirst(), coordonates.getSecond());
              CharacterFactory factory = new CharacterFactory();
              characters.add(factory.getCharacter(name, coordonates, land));
          }
          for (int i = 0; i < nrCharacters; i++) {
              characters.get(i).setId(i);
              characters.get(i).addobserver(magician);
          }
          /** este citit numarul de runde */
          int nrRounds = reader.nextInt();
          char[][] moveMatrix = new char[nrRounds][nrCharacters];
          for (int i = 0; i < nrRounds; i++) {
              String word = reader.nextWord();
              char[] buffer = word.toCharArray();
              if (nrCharacters >= 0) {
                  System.arraycopy(buffer, 0, moveMatrix[i], 0, nrCharacters);
              }
          }

          for (int i = 0; i < nrRounds; i++) {
              /** sunt citite miscarile ce vor fi efectutate
               * de fieacre caracter daca se afla inca in viata
               * si nu sunt paralizati.
               */
              System.out.println("~~ Round" + " " + (i + 1) + " " + "~~");
              if (i > 0) {
                  for (int j = 0; j < nrCharacters; j++) {
                      characters.get(j).receiveLongTermDamage();
                  }

              }
              for (int j = 0; j < nrCharacters; j++) {

                  if (characters.get(j).alive()) {
                      /** daca nu este prima runda de joc
                       * caracterele primesc damage ul pe
                       * termen lung.
                       */

                      if (moveMatrix[i][j] == 'U') {
                          if (characters.get(j).getImobilized() == 0) {
                              characters.get(j).decideStrategy();
                              characters.get(j).getCoordonates()
                                      .setFirst(characters.get(j).getCoordonates().getFirst() - 1);
                          }
                       if (characters.get(j).getCoordonates().getFirst() >= 0
                               && characters.get(j).getCoordonates().getSecond() >= 0) {
                           characters.get(j).move(characters.get(j).getCoordonates());
                           characters.get(j).setLand(map.getPosition(characters.get(j)
                                   .getCoordonates().getFirst(), characters.get(j)
                                   .getCoordonates().getSecond()));
                       } else {
                           characters.get(j).move(characters.get(j).getCoordonates());
                       }
                      }
                      if (moveMatrix[i][j] == 'D') {
                          if (characters.get(j).getImobilized() == 0) {
                              characters.get(j).decideStrategy();
                              characters.get(j).getCoordonates()
                                      .setFirst(characters.get(j).getCoordonates().getFirst() + 1);
                          }
                          if (characters.get(j).getCoordonates().getFirst() >= 0
                                  && characters.get(j).getCoordonates().getSecond() >= 0) {
                              characters.get(j).move(characters.get(j).getCoordonates());
                              characters.get(j).setLand(map.getPosition(characters.get(j)
                                      .getCoordonates().getFirst(), characters.get(j)
                                      .getCoordonates().getSecond()));
                          } else {
                              characters.get(j).move(characters.get(j).getCoordonates());
                          }
                      }
                      if (moveMatrix[i][j] == 'L') {
                          if (characters.get(j).getImobilized() == 0) {
                              characters.get(j).decideStrategy();
                              characters.get(j).getCoordonates().setSecond(characters
                                      .get(j).getCoordonates().getSecond() - 1);
                          }
                          if (characters.get(j).getCoordonates().getFirst() >= 0
                                  && characters.get(j).getCoordonates().getSecond() >= 0) {
                              characters.get(j).move(characters.get(j).getCoordonates());
                              characters.get(j).setLand(map.getPosition(characters.get(j)
                                      .getCoordonates().getFirst(), characters.get(j)
                                      .getCoordonates().getSecond()));
                          } else {
                              characters.get(j).move(characters.get(j).getCoordonates());
                          }
                      }
                      if (moveMatrix[i][j] == 'R') {
                          if (characters.get(j).getImobilized() == 0) {
                              characters.get(j).decideStrategy();
                              characters.get(j).getCoordonates().setSecond(characters.get(j)
                                      .getCoordonates().getSecond() + 1);
                          }
                          if (characters.get(j).getCoordonates().getFirst() >= 0
                                  && characters.get(j).getCoordonates().getSecond() >= 0) {
                              characters.get(j).move(characters.get(j).getCoordonates());
                              characters.get(j).setLand(map.getPosition(characters.get(j)
                                      .getCoordonates().getFirst(), characters.get(j)
                                      .getCoordonates().getSecond()));
                          } else {
                              characters.get(j).move(characters.get(j).getCoordonates());
                          }
                      }
                      if (moveMatrix[i][j] == '_') {
                          if (characters.get(j).getImobilized() == 0) {
                              characters.get(j).decideStrategy();
                          }
                      }

                  }
              }
              /** pentru batalie vor fi luate toate perechile posibile de caractere si
               * daca sunt in viata si coordonatele lor corespund,acestia se vor lupta.
               */
              for (int d = 0; d < nrCharacters - 1; d++) {
                  for (int e = d + 1; e < nrCharacters; e++) {
                      if (characters.get(d).alive() && characters.get(e).alive()
                              && characters.get(d).getCoordonates().equals(characters
                              .get(e).getCoordonates())) {
                          /** Rogue trebuie sa atace primul deoarece in cazul unei lupte
                           * cu un Wizard,daca cel din urma ar face primul atac damage ul pentru
                           * abilitatea backstab nu ar fi corect calculat.*/
                          if (characters.get(e).getName() == 'R'
                                  && characters.get(d).getName() == 'W') {
                              characters.get(d).accept(characters.get(e));
                              characters.get(e).accept(characters.get(d));
                          } else {
                              characters.get(e).accept(characters.get(d));
                              characters.get(d).accept(characters.get(e));
                          }
                          if (characters.get(d).alive() && !characters.get(e).alive()) {
                              StringBuilder information = characters.get(d)
                                      .createMessageforKill(characters.get(e));
                              characters.get(d).notifyAllObservers(information);
                              characters.get(d).xpAndLevelModifier(characters.get(e),
                                       characters.get(e).getLevel());
                          } else {
                              if (!characters.get(d).alive() && characters.get(e).alive()) {
                                  StringBuilder information = characters.get(e)
                                          .createMessageforKill(characters.get(d));
                                  characters.get(e).notifyAllObservers(information);
                                  characters.get(e).xpAndLevelModifier(characters.get(d),
                                           characters.get(d).getLevel());
                              } else if (!characters.get(d).alive() && !characters.get(e).alive()) {
                                  StringBuilder information = characters.get(d)
                                          .createMessageforKill(characters.get(e));
                                  characters.get(d).notifyAllObservers(information);
                                  StringBuilder anotherInformation = characters.get(e)
                                          .createMessageforKill(characters.get(d));
                                  characters.get(e).notifyAllObservers(anotherInformation);
                                  int levAnt = characters.get(d).getLevel();
                                  characters.get(d).xpAndLevelModifier(characters.get(e),
                                           characters.get(e).getLevel());
                                  characters.get(e).xpAndLevelModifier(characters.get(d), levAnt);

                              }
                          }
                      }
                  }
              }
              /** aparitia ingerilor si notifcarea magicianului dupa fiecare actiune */
              int nrAngelsinCurrentRound = reader.nextInt();
              ArrayList<Angel> angels = new ArrayList<>(nrAngelsinCurrentRound);
              if (nrAngelsinCurrentRound != 0) {
                  for (int contor = 0; contor < nrAngelsinCurrentRound; contor++) {
                      String line = reader.nextWord();
                      String[] pieces = line.split(",");
                      String name = pieces[0];
                      Pair<Integer, Integer> angelCoordonates = new Pair<>(0, 0);
                      angelCoordonates.setFirst(Integer.parseInt(pieces[1]));
                      angelCoordonates.setSecond(Integer.parseInt(pieces[2]));
                      angels.add(AngelFactory.getAngel(angelCoordonates, name));
                  }
                  for (int u = 0; u < nrAngelsinCurrentRound; u++) {
                      StringBuilder information = angels.get(u).apperanceMessage();
                      angels.get(u).addobserver(magician);
                      angels.get(u).notifyAllObservers(information);
                      for (int b = 0; b < nrCharacters; b++) {
                          if (angels.get(u).getAngelCoordonates()
                                  .equals(characters.get(b).getCoordonates())) {
                              characters.get(b).acceptVisitByAngel(angels.get(u));
                          }
                      }
                  }
              }
              angels.clear();
              System.out.println(" ");
          }
          reader.close();
          /** afisarea rezulatelor finale */
          System.out.println("~~" + " " + "Results" + " " + "~~");
          for (int i = 0; i < nrCharacters; i++) {
              if (characters.get(i).alive()) {
                  System.out.println(characters.get(i).getName() + " "
                          + characters.get(i).getLevel() + " " + characters.get(i).getXp() + " "
                 + characters.get(i).getHp() + " " + characters.get(i).getCoordonates().getFirst()
                          + " " + characters.get(i).getCoordonates().getSecond());
              } else {
                  System.out.println(characters.get(i).getName() + " dead");
              }
          }
          writer.close();

    }
}
