package com.skarpeta.skarpeciarzegame.worldmap;

import com.skarpeta.skarpeciarzegame.tools.Point;
import com.skarpeta.skarpeciarzegame.tools.ResourceType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Island {
    private final List<Field> fields;
    public Island() {
        this.fields = new ArrayList<>();
    }

    public static List<Island> findIslands(WorldMap worldMap) {
        int mapSize = worldMap.getMapSize();
        boolean[][] visited = new boolean[mapSize][mapSize];
        List<Island> islands = new ArrayList<>();

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if (worldMap.getField(new Point(i,j)).getTerrain() != TerrainType.WATER && !visited[i][j]) {
                    Island island = new Island();
                    exploreIsland(worldMap, visited, new Point(i,j), island);
                    islands.add(island);
                }
            }
        }
        return islands;
    }

    private static void exploreIsland(WorldMap worldMap, boolean[][] visited, Point point, Island island) {

        if (point.isNegative() || point.x >= worldMap.getMapSize() || point.y >= worldMap.getMapSize())
            return;
        if (visited[point.x][point.y] || worldMap.getField(new Point(point.x,point.y)).getTerrain() == TerrainType.WATER)
            return;

        visited[point.x][point.y] = true;
        island.add(worldMap.getField(new Point(point.x,point.y)));

        exploreIsland(worldMap, visited, point.add(new Point(-1,0)), island); // Up
        exploreIsland(worldMap, visited, point.add(new Point(+1,0)), island); // Down
        exploreIsland(worldMap, visited, point.add(new Point(0,-1)), island); // Left
        exploreIsland(worldMap, visited, point.add(new Point(0,1)), island); // Right
    }

    public int countTrees(){
        int treeAmount =0;
        for (Field field : fields) {
            if (field.getResourceType() == ResourceType.FOREST)
                treeAmount++;
        }
        return treeAmount;
    }
    private void add(Field field){
        fields.add(field);
    }
    public void forEach(Consumer<Field> action) {
        fields.forEach(action);
    }
    public List<Field> getFields() {
        return fields;
    }
}
