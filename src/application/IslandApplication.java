package application;

import entity.Animal;
import util.AnimalFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class IslandApplication {

    private static final int NUM_THREADS = 4;
    static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(NUM_THREADS);

    public static void main(String[] args) throws InterruptedException {
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

        CountDownLatch latch = new CountDownLatch(width * height);
        List<Callable<Void>> tasks = new ArrayList<>();

        for (int day = 1; day <= days; day++) {
            tasks.clear();
            for (int i = 0; i < island.getWidth(); i++) {
                for (int j = 0; j < island.getHeight(); j++) {
                    Cell cell = island.getCells()[i][j];
                    tasks.add(() -> {
                        taskForCell(cell, island);
                        latch.countDown();
                        return null;
                    });
                    tasks.add(() -> {
                        cell.reproduce();
                        latch.countDown();
                        return null;
                    });
                }
            }
            executorService.invokeAll(tasks);
            latch.await();

            //island.printCells();
            island.printStatistic(day);
        }
        shutdownExecutor();

    }

    private static void taskForCell(Cell cell, Island island) {
        for (Animal animal : cell.getAllAnimals()) {
            animal.eat(cell);
            animal.move(cell, island);
        }
    }

    private static void shutdownExecutor() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
