package entity;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class Predator extends Animal{


    public Predator(String name, double weight, int maxSpeed, double maxSatiety, int maxQuantityOnOneCell) {
        this.name = name;
        this.weight = weight;
        this.maxSpeed = maxSpeed;
        this.maxSatiety = maxSatiety;
        this.maxQuantityOnOneCell = maxQuantityOnOneCell;
    }

    @Override
    public String toString() {
        return "Predator{" +
                "название='" + name + '\'' +
                ", вес=" + weight +
                ", максСкорость=" + maxSpeed +
                ", максНасыщение=" + maxSatiety +
                ", максКоличествоНаКлетке=" + maxQuantityOnOneCell +
                '}';
    }
}
