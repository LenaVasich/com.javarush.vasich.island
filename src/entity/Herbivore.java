package entity;

import application.Cell;

public class Herbivore extends Animal {

    public Herbivore(String name, double weight, int maxSpeed, double maxSatiety, int maxQuantityOnOneCell, Cell cell) {
        super(name, weight, maxSpeed, maxSatiety, maxQuantityOnOneCell, cell);
    }

    @Override
    public void eat() {
        super.eat();
        System.out.println("а теперь поем травы....");
    }

}
