package util;

import application.Cell;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Plant;

import java.io.File;
import java.util.LinkedList;

public class PlantFactory {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final File settingsFile = new File("src/settings/entitySettings.json");

    public static LinkedList<Plant> createPlantsList(Cell cell) {
        LinkedList<Plant> plants = new LinkedList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(settingsFile);
            JsonNode plantNode = rootNode.get("plant");
            if (plantNode != null) {
                int randomQuantity = (int) (Math.random() * Plant.getMaxQuantityOnOneCell()) + 1;
                for (int i = 0; i < randomQuantity; i++) {
                    plants.add(new Plant(cell));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Не удалось создать растение...");
        }
        return plants;
    }


}
