import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GraphDriver {
    private static Graph theGraph;
    public static void main(String[] args) {
        int size = -1;
        try{
            BufferedReader theFile = new BufferedReader(new FileReader("src/Assignment05_inputFile.txt"));
            while(theFile.readLine()!=null){
                size++;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

        theGraph = new Graph(size);
    }
}
