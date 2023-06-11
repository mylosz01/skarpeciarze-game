package com.skarpeta.skarpeciarzegame.worldmap;

import com.skarpeta.skarpeciarzegame.app.Catana;
import com.skarpeta.skarpeciarzegame.tools.InvalidMoveException;
import com.skarpeta.skarpeciarzegame.tools.Point;
import com.skarpeta.skarpeciarzegame.tools.ResourceType;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.Consumer;

/** Mapa gry, definiowana przez tablicę pól Field */
public class WorldMap extends Group {
    /** Zbiór pól mapy */
    private final List<Field> fields;
    private final List<Island> islands;
    private final int mapSize;
    private final int seed;

    /** Tworzenie mapy o wymiarach size * size, generowana poprzez losowo wybrany plik noise */
    public WorldMap(int size, int seed) {
        this.seed = seed;
        WorldGenerator worldGenerator = new WorldGenerator(seed);
        fields = new ArrayList();
        mapSize = size;
        for(int y = 0; y< mapSize; y++) {
            for (int x = 0; x < mapSize; x++) {
                Field newField = worldGenerator.generateField(this,new Point(x,y));
                newField.setOnMouseClicked((e)->click(e,newField));
                fields.add(newField);
            }
        }
        islands = Island.findIslands(this);
    }


    void click(MouseEvent mouseEvent, Field field) {
        if(mouseEvent.getButton() == MouseButton.PRIMARY) {
            selectField(field);
        }
    }

    /** Wykonywanie akcji dla każdego z pola */
    public void forEach(Consumer<Field> action) {
        fields.forEach(action);
    }

    public Field getField(Point position) {
        if(position.isNegative())
            throw new NoSuchElementException("pola nie moga byc na pozycji ujemnej");
        return fields.get(position.convertToOneDimention(mapSize));
    }

    /** Wybieranie pola przez gracza,
     *  poruszanie się na poprawnie wybrane pole
     */
    public void selectField(Field field) {
        try {
            Catana.getClientThread().getPlayer().sendMove(field);
        } catch (InvalidMoveException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(" "+e.getMessage());
        }
    }

    /** wylosowanie pola dla gracza z gwarancja drewna na wyspie*/
    public Point placePlayer() {
        List<Island> treeIslands =  islands.stream().filter(island -> island.countTrees() != 0).toList();
        if(treeIslands.size() == 0)
            return getRandomPosition();
        Random random = new Random();
        Island randomIsland = treeIslands.get(random.nextInt(treeIslands.size()));
        return randomIsland.getFields().get(random.nextInt(randomIsland.getFields().size())).getPosition();
    }

    private boolean hasTrees() {
        return fields.stream().anyMatch(field -> field.hasResource() && field.getResourceType() == ResourceType.FOREST);
    }

    private Point getRandomPosition() {
        List<Field> fieldList = fields.stream().filter(field -> field.getTerrain() != TerrainType.WATER).toList();
        return fieldList.get(new Random().nextInt(fieldList.size())).getPosition();
    }

    public int getSeed(){
        return seed;
    }

    public void generateResources() {
        WorldGenerator worldGenerator = new WorldGenerator(seed);
        this.forEach(worldGenerator::generateResource);
    }

    public int getMapSize() {
        return mapSize;
    }
}
