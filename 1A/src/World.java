import javafx.util.Pair;
import java.util.Vector;

/**
 * Created by chasrickarby on 8/31/16.
 */
public class World {
    public int row, col;
    public char[][] map;

    public Vector<Pair<Integer, Integer>> walls;
    public Vector<Pair<Integer, Integer>> dirt;
    public Pair<Integer, Integer> startingLocation;

    public World(Vector<String> data){
        col = Integer.parseInt(data.elementAt(0));
        row = Integer.parseInt(data.elementAt(1));

        walls = new Vector<Pair<Integer, Integer>>();
        dirt = new Vector<Pair<Integer, Integer>>();

        map = new char[row][col];

        for (int r = 0; r < row; r++){
            map[r] = data.elementAt(2+r).toCharArray();
            for (int c = 0; c < col; c++){
                switch (map[r][c]){
                    case '@':
                        // Initial position
                        startingLocation = new Pair<>(r, c);
                        break;
                    case '#':
                        // Wall
                        walls.addElement(new Pair<>(r,c));
                        break;
                    case '*':
                        // Dirt
                        dirt.addElement(new Pair<>(r,c));
                        break;
                }
            }
        }
    }
}
