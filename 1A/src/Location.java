/**
 * Created by chasrickarby on 9/1/16.
 */
public class Location {
    public Integer row;
    public Integer col;
    public char direction;

    public Location(int r, int c){
        row = r;
        col = c;
    }

    public Location(int r, int c, char d){
        row = r;
        col = c;
        direction = d;
    }

    public String print(){
        return "" + row + ", " + col;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Location rect2 = (Location) obj;
        return (this.row == rect2.row && this.col == rect2.col);
    }

    public int hashCode(){
        return (((row * 7) + (col * 13))*31);
    }
}
