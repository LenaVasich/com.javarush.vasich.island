package application;

import java.util.Arrays;

public class Island {

    int length;
    int width;

    public Island(int length, int width) {
        this.length = length;
        this.width = width;
        Cell[][] cells = new Cell[length][width]; //создаем массив клеток
        System.out.println("Остров создан! Размер: " + length + "x" + width);
        //заполнить каждую клетку живностью
    }
}
