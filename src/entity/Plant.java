package entity;

import application.Cell;

public class Plant {

    private static final String picture = "🌱";
    private static final double  weight = 1;
    private static final int maxQuantityOnOneCell = 200;

    private Cell cell;

    public Plant(Cell cell) {
        this.cell = cell;
    }

    public static String getPicture() {
        return picture;
    }

    public static double getWeight(){
        return weight;
    }

    public static int getMaxQuantityOnOneCell(){
        return maxQuantityOnOneCell;
    }

    public Cell getCell() {
        return cell;
    }
}
