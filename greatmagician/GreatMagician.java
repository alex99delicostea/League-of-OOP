package greatmagician;

public final class GreatMagician implements Observable {
    private static GreatMagician magician = null;
    public static GreatMagician getInstance() {
        if (magician == null) {
            return new GreatMagician();
        }
        return magician;
    }
    @Override
    public void update(final StringBuilder information) {
        System.out.println(information);
    }
}
