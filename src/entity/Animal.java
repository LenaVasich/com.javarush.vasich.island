package entity;

public abstract class Animal { //геттеры и сеттеры, мб переделать в аннотации?
        //🐃, 🐻, 🐎, 🦌, 🐗, 🐑, 🐐, 🐺, 🐍, 🦊, 🦅, 🐇, 🦆, 🐁, 🐛
    public String name;
    public double weight;
    public int maxQuantityOnOneCell;
    public int maxSpeed;
    public double maxSatiety;
    public String animalType;

    public double actualSatiety; //(Фактическая сытость) - в течение ЖЦ животного, значение этого поля должно уменьшаться (или увеличиваться когда поел)

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getMaxQuantityOnOneCell() {
        return maxQuantityOnOneCell;
    }

    public void setMaxQuantityOnOneCell(int maxQuantityOnOneCell) {
        this.maxQuantityOnOneCell = maxQuantityOnOneCell;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getMaxSatiety() {
        return maxSatiety;
    }

    public void setMaxSatiety(double maxSatiety) {
        this.maxSatiety = maxSatiety;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public void eat() { //есть растения и/или других животных (если в их локации есть подходящая еда)
        // Должен оттолкнуться от вероятности съедания
        // Должен проверить свою текущую сытость
    }

    public void move() { //передвигаться (в соседние клетки)
        //а есть ли место для этого вида в новой локации?
    }

    private void chooseDirection() { //определить в какую локацию МОЖНО идти

    }

    public void reproduce() { //размножаться (при наличии пары в их локации)

    }

    public void die() { //умирать, от голода или съедения

    }

    private void worker() { //нечто, что уменьшает значение поля текущей сытости

    }

}

