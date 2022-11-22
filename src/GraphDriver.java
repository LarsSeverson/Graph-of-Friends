import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class GraphDriver {
    private static Graph theGraph;
    private static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        theGraph = new Graph();
        System.out.print("Enter file name: ");
        // Assignment05_inputFile.txt
        try{
            String in;
            new BufferedReader(new FileReader(in = input.nextLine()));
            System.out.println("\nInput file is read successfully..");

            // Vertex
            setVertex(new BufferedReader(new FileReader(in)));
            // Edges
            setEdge(new BufferedReader(new FileReader(in)));

            System.out.println("Total number of vertices in the Graph: " + theGraph.getNumberOfVertices());
            System.out.println("Total number of edges in the Graph: " + theGraph.getNumberOfEdges());

            //while(true){
            System.out.println("\n1. Remove friendship\n" +
                    "2. Delete Account\n" +
                    "3. Count friends\n" +
                    "4. Friends Circle\n" +
                    "5. Closeness centrality\n" +
                    "6. Exit\n");


        }
        catch(IOException e){
            System.out.println("File not found");
        }
    }
    private static void setEdge(BufferedReader theFile) throws IOException{
        String line;
        int i = 0;

        // Add edge
        while((line = theFile.readLine()) != null){
            if (i > 0){
                String[] data = line.split("\t");
                if (data.length > 7){
                    int index = 7;
                    while(index < data.length){
                        // Add edge
                        theGraph.addEdge(data[0], data[index]);
                        index++;
                    }
                }
            }
            i++;
        }
    }
    private static void setVertex(BufferedReader theFile) throws IOException {
        String line;
        int i = 0;

        // Add vertices
        while((line = theFile.readLine()) != null){
            if (i > 0){
                String[] data = line.split("\t");
                theGraph.addVertex(data);
            }
            i++;
        }
    }
}
