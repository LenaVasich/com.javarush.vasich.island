package application;

import java.util.Arrays;

public class Island {

    int length;
    int width;

    public Island(int length, int width) {
        this.length = length;
        this.width = width;
        Cell[][] cells = new Cell[length][width]; //создаем массив клеток
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell();
            }
        }
        System.out.println("Остров создан! Размер: " + length + "x" + width);
    }
}
