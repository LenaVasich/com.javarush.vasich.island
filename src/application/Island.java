package application;

import entity.Animal;
import entity.Plant;
import settings.HerbivoreType;
import settings.PredatorType;

import java.util.TreeMap;

public class Island {

    private int width; //x
    private int height; //y
    private Cell[][] cells; //массив клеток

    public Island(int width, int height) {
        this.width = width;
        this.height = height;

        cells = new Cell[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new Cell(width, height);
                //System.out.println(cells[x][y]);
            }
        }

        System.out.println("Остров создан! Размер: " + width + "x" + height);

    }

    public static void printStatistic(Island island, int day) {
        System.out.println("День " + day + ", общее число сущностей:");
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
