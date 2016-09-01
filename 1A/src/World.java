import java.util.Vector;

/**
 * Created by chasrickarby on 8/31/16.
 */
public class World {
    public int row, col;
    public char[][] map;

    public World(Vector<String> data){
        col = Integer.parseInt(data.elementAt(0));
        row = Integer.parseInt(data.elementAt(1));
        map = new char[row][col];
        for (int r = 0; r < row; r++){
            map[r] = data.elementAt(2+r).toCharArray();
            System.out.println(data.elementAt(2+r));
        }
    }
}
