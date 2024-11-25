package settings;

public enum HerbivoreTypes {
    HORSE,
    DEER,
    RABBIT,
    MOUSE,
    GOAT,
    SHEEP,
    BOAR,
    BUFFALO,
    DUCK,
    CATERPILLAR;

    public static boolean isHerbivore(String name) {
        for (HerbivoreTypes type : HerbivoreTypes.values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
