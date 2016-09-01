import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class Main {

    private static boolean useUniformCost;

    public static void main(String[] args) throws IOException {
        switch (args[0]){
            case "uniform-cost":
                useUniformCost = true;
                break;
            default:
                useUniformCost = false;
                break;
        }
        Vector<String> textFromFile = openFile();
        World world = new World(textFromFile);
    }

    private static Vector<String> openFile() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Vector<String> data = new Vector<>();

        String line = reader.readLine();

        while(line != null) {
            data.addElement(line);
            line = reader.readLine();
        }
        reader.close();
        return data;
    }
}
