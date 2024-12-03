package util;

import application.Cell;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Animal;
import entity.Herbivore;
import entity.Predator;
import settings.HerbivoreType;
import settings.PredatorType;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class AnimalFactory {
    private static final File ENTITY_SETTINGS = new File("src/settings/entitySettings.json");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T extends Animal> T createAnimal(String name, Cell cell, Class<T> clazz) {
        try {
            JsonNode rootNode = OBJECT_MAPPER.readTree(ENTITY_SETTINGS);
            JsonNode animalType = rootNode.get(name.toLowerCase());

            if (animalType == null) throw new IllegalArgumentException("Не найдено параметров для животного: " + name);

            double weight = animalType.get("weight").asDouble();
            int maxSpeed = animalType.get("maxSpeed").asInt();
            double maxSatiety = animalType.get("maxSatiety").asDouble();
            int maxQuantityOnOneCell = animalType.get("maxQuantityOnOneCell").asInt();

            if (PredatorType.isPredator(name)) {
                return clazz.cast(new Predator(name, weight, maxSpeed, maxSatiety, maxQuantityOnOneCell, cell));
            } else if (HerbivoreType.isHerbivore(name)) {
                return clazz.cast(new Herbivore(name, weight, maxSpeed, maxSatiety, maxQuantityOnOneCell, cell));
            } else throw new IllegalArgumentException("Неизвестный тип животного: " + name);

        } catch (Exception e) {
            throw new RuntimeException("Ошибка в createAnimal...", e);
        }
    }

    public static <T extends Animal> List<T> createAnimalList(String name, Cell cell, Class<T> clazz) {
        try {
            JsonNode rootNode = OBJECT_MAPPER.readTree(ENTITY_SETTINGS);
            JsonNode animalType = rootNode.get(name.toLowerCase());

            if (animalType == null) throw new IllegalArgumentException("Не найдено параметров для животного: " + name);

            int maxQuantityOnOneCell = animalType.get("maxQuantityOnOneCell").asInt();
            int randomQuantity = (int) (Math.random() * maxQuantityOnOneCell) + 1;

            List<T> animalList = Collections.synchronizedList(new ArrayList<>());

            for (int i = 0; i < randomQuantity; i++) {
                animalList.add(createAnimal(name, cell, clazz));
            }
            return animalList;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка в createAnimalList...", e);
        }
    }

    public static List<String> getAllAnimalTypes() {
        return Stream.concat(
                Arrays.stream(HerbivoreType.values()).map(h -> h.name().toLowerCase()),
                Arrays.stream(PredatorType.values()).map(p -> p.name().toLowerCase())
        ).collect(Collectors.toList());
    }
}
