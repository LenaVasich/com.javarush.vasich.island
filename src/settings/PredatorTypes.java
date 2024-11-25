package settings;

import java.util.Arrays;

public enum PredatorTypes {
    WOLF,
    ANACONDA,
    FOX,
    BEAR,
    EAGLE;

    public static boolean isPredator(String name) {
        for (PredatorTypes type : PredatorTypes.values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}

