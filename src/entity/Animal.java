package entity;

public abstract class Animal { //геттеры и сеттеры, мб переделать в аннотации?

    private double weight;
    private int maxQuantityOnOneCell;
    private int maxSpeed;
    private double maxSatiety;

    private double actualSatiety; //(Фактическая сытость) - в течение ЖЦ животного, значение этого поля должно уменьшаться (или увеличиваться когда поел)

    //НУЖЕН УНИВЕРСАЛЬНЫЙ КОНСТРУКТОР, В КОТОРОМ БУДУТ СОЗДАВАТЬСЯ ОБЪЕКТЫ РАЗНЫХ ЖИВОТНЫХ ТИПА ХИЩНИК/ТРАВОЯДНЫЙ
    //В КОНСТРУКТОР БУДЕТ ПЕРЕДАВАТЬСЯ ИМЯ ИЗ ЭНУМА И ПАРАМЕТРЫ ИЗ ФАЙЛА ЭНТИТИСЕТТИНГС

    public void eat() { //есть растения и/или других животных (если в их локации есть подходящая еда)
        //        Должен оттолкнуться от вероятности съедания
        //        Должен проверить свою текущую сытость
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

