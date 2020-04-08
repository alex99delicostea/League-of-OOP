package main;

final class Map {
    private static Map map = null;
    private char[][] landsMap;
    public static Map getInstance() {
        if (map == null) {
            return new Map();
        }
        return map;
    }
    public void asignLands(final int length, final int width, final char[][] lands) {
        landsMap = new char[length][width];
        for (int i = 0; i < length; i++) {
            if (width >= 0) {
                System.arraycopy(lands[i], 0, landsMap[i], 0, width);
            }
        }
    }
    public char getPosition(final int latitude, final int longitude) {
        return landsMap[latitude][longitude];
    }

}
