package application;

import java.util.Scanner;

public class IslandApplication {
    public static void main(String[] args) {
        System.out.println("Давайте создадим остров! Введите количество дней и размеры для запуска эмуляции");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Количество дней: ");
        int days = scanner.nextInt();
        System.out.print("Ширина(х): ");
        int width = scanner.nextInt();
        System.out.print("Высота(у): ");
        int height = scanner.nextInt();

        Island island = Island.getIsland(width, height);
        island.printStatistic(0);

        System.out.println("Начинаем симуляцию...");

        for (int day = 1; day <= days; day++) {

            for (int i = 0; i < island.getWidth(); i++) {
                for (int j = 0; j < island.getHeight(); j++) {
                    Cell cell = island.getCells()[i][j];
                    cell.getAllAnimals().forEach(animal -> animal.move(cell, island));
                    cell.getAllAnimals().forEach(animal -> animal.eat(cell));
                    cell.reproduce(); // тут же и рост травы
                }
            }

            //island.printCells();
            island.printStatistic(day);
        }
    }
}