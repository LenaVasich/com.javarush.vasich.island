package application;

import entity.Animal;
import entity.Herbivore;
import entity.Predator;
import util.AnimalFactory;

import java.util.LinkedList;
import java.util.Scanner;

public class IslandApplication {
    public static void main(String[] args) {
        System.out.println("Давайте создадим остров! Введите размеры для запуска эмуляции.");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ширина(х): ");
        int width = scanner.nextInt();
        System.out.print("Высота(у): ");
        int height = scanner.nextInt();
        Island island = new Island(width, height);

//        for (int i = 0; i < island.getCells().length; i++) {
//            for (int j = 0; j < island.getCells()[i].length; j++) {
//                System.out.println(island.getCells()[i][j].toString());
//            }
//        }

        Island.printStatistic(island, 0);
        System.out.println("Начинаем симуляцию...");

        //дальше нужно запустить эмуляцию по дням и вывод статистики..........

        Cell[][] cells = island.getCells();
        for (int day = 1; day <= 4; day++) {
            System.out.println("Day " + day);
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    Cell cell = cells[i][j];

                    //System.out.println("Before moving - Cell " + i + "," + j + ": " + cell);

                    cell.getHerbivores().forEach((type, animals) -> {
                        Cell.reproduce(animals, cell);
                        for (Herbivore animal : animals) {
                            animal.move(cell, island);
                        }
                    });

                    cell.getPredators().forEach((type, animals) -> {
                        Cell.reproduce(animals, cell);
                        for (Predator animal : animals) {
                            animal.move(cell, island);
                        }
                    });

                    Cell.growPlants(cell.getPlants(), cell);

                    //System.out.println("After moving - Cell " + i + "," + j + ": " + cell);
                }
            }
            Island.printStatistic(island, day);
        }



    }
}