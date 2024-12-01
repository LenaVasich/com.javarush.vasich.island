package application;

import entity.Animal;
import entity.Herbivore;
import entity.Plant;
import entity.Predator;
import settings.HerbivoreType;
import settings.PredatorType;
import util.AnimalFactory;
import util.PlantFactory;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Random;

public class Cell {

    private int x, y;

    private EnumMap<PredatorType, LinkedList<Predator>> predators = new EnumMap<>(PredatorType.class);
    private EnumMap<HerbivoreType, LinkedList<Herbivore>> herbivores = new EnumMap<>(HerbivoreType.class);
    private LinkedList<Plant> plants = new LinkedList<>();

    public Cell(int x, int y) {

        this.x = x;
        this.y = y;

        for (PredatorType type : PredatorType.values()) {
            predators.put(type, AnimalFactory.createAnimalList(type.name(), this, Predator.class));
        }

        for (HerbivoreType type : HerbivoreType.values()) {
            herbivores.put(type, AnimalFactory.createAnimalList(type.name(), this, Herbivore.class));
        }

        plants = PlantFactory.createPlantsList(this);
    }

    public static <T extends Animal> LinkedList<T> reproduce(LinkedList<T> animals, Cell cell) {
        int size = animals.size();
        if (size == 0) return animals;

        T animal = animals.get(0);
        int totalNewAnimals = 0;
        int maxAnimals = animal.getMaxQuantityOnOneCell();
        if (size > 1 && size < maxAnimals) {
            int parents = size / 2;
            int possibleNewAnimals = new Random().nextInt(parents * 3 - parents) + 1;
            totalNewAnimals = Math.min(possibleNewAnimals, maxAnimals - size);
        }

        for (int i = 0; i < totalNewAnimals; i++) {
            @SuppressWarnings("unchecked")
            T newAnimal = (T) AnimalFactory.createAnimal(animal.getName(), cell, animal.getClass());
            animals.add(newAnimal);
        }
        return animals;
    }

    public static LinkedList<Plant> growPlants(LinkedList<Plant> plants, Cell cell) {
        if (plants.size() < Plant.getMaxQuantityOnOneCell()) {
            int newPlants = new Random().nextInt(Plant.getMaxQuantityOnOneCell() - plants.size()) + 1;
            for (int i = 0; i < newPlants; i++) {
                plants.add(new Plant(cell));
            }
        }
        return plants;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cell[").append(x).append("][").append(y).append("]:\n");

        sb.append("Predators: ");
        predators.forEach((type, list) -> {
            if (!list.isEmpty()) {
                sb.append(type.getPicture()).append("=").append(list.size()).append(" ");
            }
        });
        sb.append("\n");

        sb.append("Herbivores: ");
        herbivores.forEach((type, list) -> {
            if (!list.isEmpty()) {
                sb.append(type.getPicture()).append("=").append(list.size()).append(" ");
            }
        });
        sb.append("\n");

        sb.append("Plants: ");
        if (!plants.isEmpty()) {
            sb.append(plants.get(0).getPicture()).append("=").append(plants.size());
        } else {
            sb.append("none");
        }

        return sb.toString().trim();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public LinkedList<Plant> getPlants() {
        return plants;
    }

    public EnumMap<PredatorType, LinkedList<Predator>> getPredators() {
        return predators;
    }

    public EnumMap<HerbivoreType, LinkedList<Herbivore>> getHerbivores() {
        return herbivores;
    }

    public int getAnimalCountByType(String typeName) {
        String upperCaseType = typeName.toUpperCase();

        if (HerbivoreType.isHerbivore(upperCaseType)) {
            HerbivoreType herbivoreType = HerbivoreType.valueOf(upperCaseType);
            return herbivores.getOrDefault(herbivoreType, new LinkedList<>()).size();
        }

        if (PredatorType.isPredator(upperCaseType)) {
            PredatorType predatorType = PredatorType.valueOf(upperCaseType);
            return predators.getOrDefault(predatorType, new LinkedList<>()).size();
        }

        throw new IllegalArgumentException("Нет такого животного: " + typeName);
    }


}
