package characters;

public class CharacterFactory {
    /** */
    public MoldCharacter
    getCharacter(final char name, final Pair<Integer, Integer> coordonates, final char land) {
        if (name == 'P') {
            return new Pyromancer(land, coordonates, name);
        }
        if (name == 'K') {
            return  new Knight(land, coordonates, name);
        }
        if (name == 'W') {
            return new Wizard(land, coordonates, name);
        }
        if (name == 'R') {
            return new Rogue(land, coordonates, name);
        }
        return null;
    }
}
