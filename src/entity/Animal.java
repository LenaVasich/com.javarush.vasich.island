package entity;

import application.Cell;
import application.Island;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import settings.Direction;
import settings.HerbivoreType;
import settings.PredatorType;

import java.io.File;
import java.util.*;

public abstract class Animal {

    private final String name;
    private final double weight;
    private final int maxSpeed;
    private final double maxSatiety;
    private final int maxQuantityOnOneCell;

    private double actualSatiety; //(Фактическая сытость) - в течение ЖЦ животного, значение этого поля должно уменьшаться (или увеличиваться когда поел)

    private Cell cell;

    private static final File CHANCE_OF_FEED = new File("src/settings/chanceOfFeed.json");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    protected Animal(String name, double weight, int maxSpeed, double maxSatiety, int maxQuantityOnOneCell, Cell cell) {
        this.name = name;
        this.weight = weight;
        this.maxSpeed = maxSpeed;
        this.maxSatiety = maxSatiety;
        this.maxQuantityOnOneCell = maxQuantityOnOneCell;
        this.actualSatiety = maxSatiety;
        this.cell = cell;
    }

    public void eat(Cell cell) {
        if (this.actualSatiety < this.maxSatiety) {
            boolean hasEaten = false;

            try {
                JsonNode rootNode = OBJECT_MAPPER.readTree(CHANCE_OF_FEED);
                JsonNode animalNode = rootNode.get(this.name.toLowerCase());
                if (animalNode == null) {
                    throw new IllegalArgumentException("Не найдены параметры для животного: " + name);
                }

                Map<String, Integer> whoCanBeEaten = new HashMap<>(); //союираем мапу кто в принципе может быть съеден этим типом
                animalNode.fieldNames().forEachRemaining(foodType -> {
                    int probability = animalNode.get(foodType).asInt();
                    if (probability > 0) {
                        whoCanBeEaten.put(foodType, probability);
                    }
                });

                // Проходим по возможной добыче
                for (Map.Entry<String, Integer> entry : whoCanBeEaten.entrySet()) {
                    String foodType = entry.getKey();
                    int eatProbability = entry.getValue();

                    if (foodType.equals("plant") && !cell.getPlants().isEmpty()) {
                        if (new Random().nextInt(100) < eatProbability) {
                            synchronized (cell.getPlants()) {
                                Iterator<Plant> plantIterator = cell.getPlants().iterator();
                                if (plantIterator.hasNext()) {
                                    plantIterator.next();
                                    plantIterator.remove();
                                    //System.out.println(name + " съел растение в клетке " + cell.getX() + "," + cell.getY());
                                    this.increaseSatiety(Plant.getWeight());
                                    hasEaten = true;
                                    break;
                                }
                            }
                        }
                    } else if (!foodType.equals("plant") && cell.getAnimalCountByType(foodType) > 0) {
                        List<? extends Animal> animalsToEat = cell.getAnimalsByType(foodType);
                        synchronized (animalsToEat) {
                            Iterator<? extends Animal> animalIterator = animalsToEat.iterator();
                            if (animalIterator.hasNext()) {
                                Animal prey = animalIterator.next();
                                animalIterator.remove();
                                prey.die(cell);
                                //System.out.println(name + " съел " + prey.getName() + " в клетке " + cell.getX() + "," + cell.getY());
                                this.increaseSatiety(prey.getWeight());
                                hasEaten = true;
                                break;
                            }
                        }
                    }
                }

                if (!hasEaten) {
                    this.decreaseSatiety(this.getMaxSatiety() * 0.1, cell);
                }

            } catch (Exception e) {
                throw new RuntimeException("Ошибка при обработке еды", e);
            }
        }
    }

    public void move(Cell currentCell, Island island) {
        int attempts = 0;
        while (attempts < 10) {
            Cell targetCell = chooseCellToMove(this, currentCell, island);

            if (targetCell != currentCell) {
                //System.out.println("[" + targetCell.getX()+ "][" + targetCell.getY()+"]" + animal.name);
                if (this instanceof Predator) {
                    PredatorType predatorType = PredatorType.valueOf(this.getName().toUpperCase());
                    currentCell.getPredators().get(predatorType).remove(this);
                    targetCell.getPredators().get(predatorType).add((Predator) this);
                } else if (this instanceof Herbivore) {
                    HerbivoreType herbivoreType = HerbivoreType.valueOf(this.getName().toUpperCase());
                    currentCell.getHerbivores().get(herbivoreType).remove(this);
                    targetCell.getHerbivores().get(herbivoreType).add((Herbivore) this);
                }
                this.decreaseSatiety(this.getMaxSatiety() * 0.25, targetCell);
                break;
            }
            attempts++;
        }
    }

    private Cell chooseCellToMove(Animal animal, Cell cell, Island island) {
        Direction[] directions = Direction.values();
        int cellsToMove = animal.getMaxSpeed() > 1 ? new Random().nextInt(animal.getMaxSpeed()) + 1 : 1;

        int index = new Random().nextInt(directions.length);
        Direction direction = directions[index];
        int x = cell.getX();
        int y = cell.getY();

        switch (direction) {
            case LEFT -> x -= cellsToMove;
            case RIGHT -> x += cellsToMove;
            case UP -> y -= cellsToMove;
            case DOWN -> y += cellsToMove;
        }
        if (x >= 0 && x < island.getWidth() &&
                y >= 0 && y < island.getHeight() &&
                checkFreeSpaceOnCellForAType(animal, island.getCells()[x][y])) {
            return island.getCells()[x][y];
        }

        return cell;
    }

    private boolean checkFreeSpaceOnCellForAType(Animal animal, Cell cell) {
        return cell.getAnimalCountByType(animal.getName()) < animal.maxQuantityOnOneCell;
    }

    public void die(Cell currentCell) {
        if (this.actualSatiety <= 0) {
            if (this instanceof Predator) {
                PredatorType predatorType = PredatorType.valueOf(this.getName().toUpperCase());
                currentCell.getPredators().get(predatorType).remove(this);
            } else if (this instanceof Herbivore) {
                HerbivoreType herbivoreType = HerbivoreType.valueOf(this.getName().toUpperCase());
                currentCell.getHerbivores().get(herbivoreType).remove(this);
            }
//          System.out.println(this.getName() + " умер в клетке [" + currentCell.getX() + "][" + currentCell.getY() + "] от " + reason);
        }
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public double getMaxSatiety() {
        return maxSatiety;
    }

    public int getMaxQuantityOnOneCell() {
        return maxQuantityOnOneCell;
    }

    public void increaseSatiety(double foodWeight) {
        this.actualSatiety += foodWeight;
    }

    public void decreaseSatiety(double amount, Cell cell) {
        this.actualSatiety -= amount;
        if (this.actualSatiety <= 0) {
            this.die(cell);
        }
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                "x='" + cell.getX() + '\'' +
                "y='" + cell.getY() + '\'' +
                '}';
    }
}

