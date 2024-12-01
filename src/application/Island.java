package application;

import entity.Plant;
import settings.HerbivoreType;
import settings.PredatorType;

import java.util.TreeMap;

public class Island {

    private static Island island;
    private int width; // x
    private int height; // y
    private Cell[][] cells; // массив клеток

    private Island(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }

        System.out.println("Остров создан! Размер: " + width + "x" + height);
        //printCells();
    }

    public static Island getIsland(int width, int height) {
        if (island == null) {
            island = new Island(width, height); // Создание острова, если его нет
        }
        return island;
    }

    public void printCells() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                System.out.println(cells[i][j]);
            }
        }
    }

    public void printStatistic(int day) {
        System.out.print("День " + day + " итого:  ");
        TreeMap<String, Integer> totalCount = new TreeMap<>();

        for (HerbivoreType herbivore : HerbivoreType.values()) {
            totalCount.put(herbivore.getPicture(), 0);
        }
        for (PredatorType predator : PredatorType.values()) {
            totalCount.put(predator.getPicture(), 0);
        }
        totalCount.put(Plant.getPicture(), 0);

        for (int i = 0; i < island.cells.length; i++) {
            for (int j = 0; j < island.cells[i].length; j++) {
                Cell cell = island.cells[i][j];

                cell.getHerbivores().forEach((type, list) ->
                        totalCount.put(type.getPicture(), totalCount.get(type.getPicture()) + list.size())
                );

                cell.getPredators().forEach((type, list) ->
                        totalCount.put(type.getPicture(), totalCount.get(type.getPicture()) + list.size())
                );

                totalCount.put(Plant.getPicture(), totalCount.get(Plant.getPicture()) + cell.getPlants().size());
            }
        }

        totalCount.forEach((picture, count) -> System.out.print(picture + "=" + count + " "));
        System.out.println();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell[][] getCells() {
        return cells;
    }
}

