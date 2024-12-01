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


    public void reproduce() {
        predators.values().forEach(this::reproduceForAnimalType);
        herbivores.values().forEach(this::reproduceForAnimalType);
        growPlants();
    }

    private <T extends Animal> void reproduceForAnimalType(LinkedList<T> animals) {
        int size = animals.size();
        if (size == 0) return;

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
            T newAnimal = (T) AnimalFactory.createAnimal(animal.getName(), this, animal.getClass());
            animals.add(newAnimal);
        }
    }

    private void growPlants() {
        int currentPlantCount = plants.size();
        int maxPlants = Plant.getMaxQuantityOnOneCell();
        if (currentPlantCount < maxPlants) {
            int newPlants = new Random().nextInt(maxPlants - currentPlantCount) + 1;
            for (int i = 0; i < newPlants; i++) {
                plants.add(new Plant(this));
            }
        }
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

    public LinkedList<? extends Animal> getAnimalsByType(String type) {
        String upperCaseType = type.toUpperCase();

        if (HerbivoreType.isHerbivore(upperCaseType)) {
            HerbivoreType herbivoreType = HerbivoreType.valueOf(upperCaseType);
            return herbivores.getOrDefault(herbivoreType, new LinkedList<>());
        }

        if (PredatorType.isPredator(upperCaseType)) {
            PredatorType predatorType = PredatorType.valueOf(upperCaseType);
            return predators.getOrDefault(predatorType, new LinkedList<>());
        }

        throw new IllegalArgumentException("Нет такого животного: " + type);
    }

    public LinkedList<Animal> getAllAnimals() {
        LinkedList<Animal> allAnimals = new LinkedList<>();

        for (LinkedList<Predator> predatorList : predators.values()) {
            allAnimals.addAll(predatorList);
        }

        for (LinkedList<Herbivore> herbivoreList : herbivores.values()) {
            allAnimals.addAll(herbivoreList);
        }

        return allAnimals;
    }



    @Override
    public String toString() {
        StringBuilder cellInfo = new StringBuilder();
        cellInfo.append("Cell[").append(x).append("][").append(y).append("]: ");

        cellInfo.append("Predators: ");
        predators.forEach((type, list) -> {
            if (!list.isEmpty()) {
                cellInfo.append(type.getPicture()).append("=").append(list.size()).append(" ");
            }
        });
        //cellInfo.append("\n");

        cellInfo.append("Herbivores: ");
        herbivores.forEach((type, list) -> {
            if (!list.isEmpty()) {
                cellInfo.append(type.getPicture()).append("=").append(list.size()).append(" ");
            }
        });
        //cellInfo.append("\n");

        cellInfo.append("Plants: ");
        if (!plants.isEmpty()) {
            cellInfo.append(Plant.getPicture()).append("=").append(plants.size());
        } else {
            cellInfo.append("none");
        }


        return cellInfo.toString().trim();
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

}
