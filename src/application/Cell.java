package application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Animal;
import entity.Herbivore;
import entity.Plant;
import entity.Predator;
import settings.HerbivoreTypes;
import settings.PredatorTypes;

import java.io.File;
import java.util.LinkedList;

public class Cell {

    File entitySettings = new File("C:\\Users\\Elena\\IdeaProjects\\com.javarush.vasich.island\\src\\settings\\entitySettings.json");
    ObjectMapper objectMapper = new ObjectMapper();

    public Cell() {
        //наполняем клетку массивами существ.......

        LinkedList<? extends Animal> wolfs = createEntityList(PredatorTypes.WOLF.name());
        LinkedList<? extends Animal> anacondas = createEntityList(PredatorTypes.ANACONDA.name());
        LinkedList<? extends Animal> foxes = createEntityList(PredatorTypes.FOX.name());
        LinkedList<? extends Animal> bears = createEntityList(PredatorTypes.BEAR.name());
        LinkedList<? extends Animal> eagles = createEntityList(PredatorTypes.EAGLE.name());

        LinkedList<? extends Animal> horses = createEntityList(HerbivoreTypes.HORSE.name());
        LinkedList<? extends Animal> deers = createEntityList(HerbivoreTypes.DEER.name());
        LinkedList<? extends Animal> rabbits = createEntityList(HerbivoreTypes.RABBIT.name());
        LinkedList<? extends Animal> mice = createEntityList(HerbivoreTypes.MOUSE.name());
        LinkedList<? extends Animal> goats = createEntityList(HerbivoreTypes.GOAT.name());
        LinkedList<? extends Animal> sheeps = createEntityList(HerbivoreTypes.SHEEP.name());
        LinkedList<? extends Animal> boars = createEntityList(HerbivoreTypes.BOAR.name());
        LinkedList<? extends Animal> buffalos = createEntityList(HerbivoreTypes.BUFFALO.name());
        LinkedList<? extends Animal> ducks = createEntityList(HerbivoreTypes.DUCK.name());
        LinkedList<? extends Animal> caterpillars = createEntityList(HerbivoreTypes.CATERPILLAR.name());

        LinkedList<Plant> plants = new LinkedList<>(); //травы надо тоже создать!

    }

    private LinkedList<? extends Animal> createEntityList(String name){
        try {
            JsonNode rootNode = objectMapper.readTree(entitySettings);
            JsonNode typeNode = rootNode.get(name.toLowerCase());
            if (typeNode != null) {
                double weight = typeNode.get("weight").asDouble();
                int maxSpeed = typeNode.get("maxSpeed").asInt();
                double maxSatiety = typeNode.get("maxSatiety").asDouble();
                int maxQuantityOnOneCell = typeNode.get("maxQuantityOnOneCell").asInt();
                int randomQuantity = (int) (Math.random() * maxQuantityOnOneCell) + 1;

                if (PredatorTypes.isPredator(name)){
                    LinkedList<Predator> result = new LinkedList<>();
                    for (int i = 0; i < randomQuantity; i++) {
                        result.add(new Predator(name.toLowerCase(), weight, maxSpeed, maxSatiety, maxQuantityOnOneCell));
                    }
                    System.out.println("Создано " + randomQuantity + " " + name);
                    System.out.println(result.get(0));
                    return result;
                } else if (HerbivoreTypes.isHerbivore(name)) {
                    LinkedList<Herbivore> result = new LinkedList<>();
                    for (int i = 0; i < randomQuantity; i++) {
                        result.add(new Herbivore(name.toLowerCase(), weight, maxSpeed, maxSatiety, maxQuantityOnOneCell));
                    }
                    System.out.println("Создано " + randomQuantity + " " + name);
                    System.out.println(result.get(0));
                    return result;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Что-то пошло не так....");
        }
        return null;
    }
}
