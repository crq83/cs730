/**
 * Created by chasrickarby on 9/1/16.
 */
public class Location {
    public Integer row;
    public Integer col;

    public Location(int r, int c){
        row = r;
        col = c;
    }

    public String print(){
        return "" + row + ", " + col;
    }
}
