package entity;

public class Herbivore extends Animal {

    public Herbivore(String name, double weight, int maxSpeed, double maxSatiety, int maxQuantityOnOneCell) {
        this.name = name;
        this.weight = weight;
        this.maxSpeed = maxSpeed;
        this.maxSatiety = maxSatiety;
        this.maxQuantityOnOneCell = maxQuantityOnOneCell;
    }

    @Override
    public String toString() {
        return "Predator{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", maxSpeed=" + maxSpeed +
                ", maxSatiety=" + maxSatiety +
                ", максКоличествоНаКлетке=" + maxQuantityOnOneCell +
                '}';
    }

    @Override
    public void eat() { //надо ли его переписывать? или надо оба переписать, они разные.......???
        System.out.println("я ем только траву");
    }
}

