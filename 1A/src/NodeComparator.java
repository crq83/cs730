import java.util.Comparator;

/**
 * Created by chasrickarby on 9/3/16.
 */
public class NodeComparator implements Comparator<Node> {

    @Override
    public int compare(Node x, Node y) {
        if (x.cost < y.cost)
        {
            return -1;
        }
        if (x.cost > y.cost)
        {
            return 1;
        }
        return 0;
    }

}
