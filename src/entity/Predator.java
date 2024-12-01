package entity;

import application.Cell;

public class Predator extends Animal{

    public Predator(String name, double weight, int maxSpeed, double maxSatiety, int maxQuantityOnOneCell, Cell cell) {
        super(name, weight, maxSpeed, maxSatiety, maxQuantityOnOneCell, cell);
    }

}
