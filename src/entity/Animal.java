package entity;

import application.Cell;
import application.Island;
import java.util.LinkedList;

public abstract class Animal {

    private final String name;
    private double weight;
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

    public void move(Animal animal) { //передвигаться (в соседние клетки)
        // запускаем чуздирекшн, он вернет новую клетку
        // если они отличаются от текущих, то меняем координаты ???? - переместить объект в лист другой клетки???
        // иначе запускаем снова чуз дирекшн пока не закончится время

    }

    private Cell chooseDirection(Animal animal, Cell cell, Island island) { //определить в какую локацию МОЖНО идти
//        Direction[] directions = Direction.values();
//        int index = new Random().nextInt(Direction.values().length);
//        int cellsNumber = new Random().nextInt(animal.maxSpeed + 1);
//        int x, y;
        Cell newCell = cell;
//
//
//        switch (directions[index]) {
//            case LEFT -> {
//                x = cell.getX() - cellsNumber;
//                if (x >= 0) {
//                    newCell = island.cells[x][cell.getY()];
//                    animal.getMaxQuantityOnOneCell();
//                    //newCell.
//                    //получить максимум для типа, если текущее меньше максимума, то вернуть новую клетку
//                }
//            }
//            case RIGHT -> {
//                x = cell.getX() + cellsNumber;
//                if (x < island.x) {
//                    newCell = island.cells[x][cell.getY()];
//                    //получить максимум для типа, если текущее меньше максимума, то вернуть новую клетку
//                }
//            }
//            case UP -> {
//                y = cell.getY() - cellsNumber;
//                if (y >= 0) {
//                    newCell = island.cells[cell.getX()][y];
//                    //получить максимум для типа, если текущее меньше максимума, то вернуть новую клетку
//                    //перенести в другой массив
//                }
//            }
//            case DOWN -> {
//                y = cell.getY() + cellsNumber;
//                if (y < island.y) {
//                    newCell = island.cells[cell.getX()][y];
//
//                    //получить максимум для типа, если текущее меньше максимума, то вернуть новую клетку
//                }
//            }
//            default -> {
//                return newCell;
//            }
//        }
        return newCell;
    }

    private void checkFreeSpaceOnCellForAType(Animal animal, Cell cell){
         //if (animal.maxQuantityOnOneCell < ;

    }

    public <T extends Animal> void die(LinkedList<T> animals, T animal) {
        animals.remove(animal);
    }

    private void worker() { //нечто, что уменьшает значение поля текущей сытости

    }

}

