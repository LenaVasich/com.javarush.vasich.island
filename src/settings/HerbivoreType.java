package settings;

public enum HerbivoreType {
    HORSE("🐎"),
    DEER("🦌"),
    BOAR("🐗"),
    SHEEP("🐑"),
    GOAT("🐐"),
    BUFFALO("🐃"),
    RABBIT("🐇"),
    DUCK("🦆"),
    MOUSE("🐁"),
    CATERPILLAR("🐛");

    private final String picture;

    HerbivoreType(String picture) {
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

    public static boolean isHerbivore(String name) {
        try {
            valueOf(name.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}


