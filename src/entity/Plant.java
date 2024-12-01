package entity;

import application.Cell;

import java.util.LinkedList;

public class Plant {

    private static final String picture = "🌱";
    private static final int  weight = 1;
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
