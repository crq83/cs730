import javafx.util.Pair;

import java.util.HashSet;
import java.util.Vector;

/**
 * Created by chasrickarby on 8/31/16.
 */
public class World {
    public int row, col;
    public char[][] map;

    public HashSet<Location> walls;
    public HashSet<Location> dirt;
    public Location startingLocation;

    public World(Vector<String> data){
        col = Integer.parseInt(data.elementAt(0));
        row = Integer.parseInt(data.elementAt(1));

        walls = new HashSet<>();
        dirt = new HashSet<>();

        map = new char[row][col];

        for (int r = 0; r < row; r++){
            map[r] = data.elementAt(2+r).toCharArray();
            for (int c = 0; c < col; c++){
                switch (map[r][c]){
                    case '@':
                        // Initial position
                        startingLocation = new Location(r, c);
                        break;
                    case '#':
                        // Wall
                        walls.add(new Location(r,c));
                        break;
                    case '*':
                        // Dirt
                        dirt.add(new Location(r,c));
                        break;
                }
            }
        }
    }
}
