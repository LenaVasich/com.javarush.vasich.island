package entity;

import application.Cell;
import application.Island;
import settings.Direction;
import settings.HerbivoreType;
import settings.PredatorType;

import java.util.LinkedList;
import java.util.Random;

public abstract class Animal {

    private final String name;
    private double weight; //а он меняется или нет?
    private final int maxSpeed;
    private final double maxSatiety;
    private final int maxQuantityOnOneCell;

    private double actualSatiety; //(Фактическая сытость) - в течение ЖЦ животного, значение этого поля должно уменьшаться (или увеличиваться когда поел)

    private Cell cell;

    protected Animal(String name, double weight, int maxSpeed, double maxSatiety, int maxQuantityOnOneCell, Cell cell) {
        this.name = name;
        this.weight = weight;
        this.maxSpeed = maxSpeed;
        this.maxSatiety = maxSatiety;
        this.maxQuantityOnOneCell = maxQuantityOnOneCell;
        this.actualSatiety = maxSatiety; // начальная сытость на максимум
        this.cell = cell;
    }

    public void eat() { //есть растения и/или других животных (если в их локации есть подходящая еда)
        // если хищник: получаем список кого можно в принципе сожрать
        // проверяем есть ли такой тип в этой клетке
        // нашли - пытаемся съесть,
        // если успешно, то + насыщение, смерть объекта
        // если не ушпешно, пытаемя съесть кого-то следующего подходящего пока не закончилось время
        //  если травоядное: если есть трава в этой клетке
        // едим траву: траву убить, насыщение прибавить
        // если есть что еще можно съесть - попробовать съесть, как у хищника

    }

    public void move(Cell currentCell, Island island) {
        Cell targetCell = currentCell;
        int attempts = 0;

        while (targetCell == currentCell && attempts < 10) { // переделать под многопоточность и интеррапт
            targetCell = chooseDirection(this, currentCell, island);

            if (targetCell != currentCell) {
                if (this instanceof Herbivore) {
                    currentCell.getHerbivores().get(HerbivoreType.valueOf(this.getName().toUpperCase())).remove(this);
                    targetCell.getHerbivores().get(HerbivoreType.valueOf(this.getName().toUpperCase())).add((Herbivore) this);
                    System.out.println(this.getName() + " moved from " + currentCell.getX() + "," + currentCell.getY() + " to " + targetCell.getX() + "," + targetCell.getY());
                } else if (this instanceof Predator) {
                    currentCell.getPredators().get(PredatorType.valueOf(this.getName().toUpperCase())).remove(this);
                    targetCell.getPredators().get(PredatorType.valueOf(this.getName().toUpperCase())).add((Predator) this);
                    System.out.println(this.getName() + " moved from " + currentCell.getX() + "," + currentCell.getY() + " to " + targetCell.getX() + "," + targetCell.getY());
                }
            }

            attempts++;
        }
    }

    private Cell chooseDirection(Animal animal, Cell cell, Island island) {
        Direction[] directions = Direction.values();
        Random random = new Random();
        int cellsNumber = random.nextInt(animal.maxSpeed + 1);

        for (int i = 0; i < directions.length; i++) {
            int index = random.nextInt(directions.length);
            Direction direction = directions[index];
            int x = cell.getX();
            int y = cell.getY();

            switch (direction) {
                case LEFT -> x -= cellsNumber;
                case RIGHT -> x += cellsNumber;
                case UP -> y -= cellsNumber;
                case DOWN -> y += cellsNumber;
            }
            if (x >= 0 && x < island.getWidth() &&
                    y >= 0 && y < island.getHeight() &&
                    checkFreeSpaceOnCellForAType(animal, island.getCells()[x][y])) {

                return island.getCells()[x][y];
            }

        }

        return cell;
    }

    private boolean checkFreeSpaceOnCellForAType(Animal animal, Cell cell) {
        return cell.getAnimalCountByType(animal.getName()) < animal.maxQuantityOnOneCell;
    }

    public <T extends Animal> void die(LinkedList<T> animals, T animal) {
        animals.remove(animal);
    }

    private void worker() { //нечто, что уменьшает значение поля текущей сытости

    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
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

    public double getActualSatiety() {
        return actualSatiety;
    }

    public void setActualSatiety(double actualSatiety) {
        this.actualSatiety = actualSatiety;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

}

