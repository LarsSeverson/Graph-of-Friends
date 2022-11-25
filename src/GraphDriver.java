import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class GraphDriver {
    private static Graph theGraph;
    public static void main(String[] args) {
        theGraph = new Graph();
        System.out.print("Enter file name: ");
        // Assignment05_inputFile.txt
        Scanner input = new Scanner(System.in);
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
            // Run switch inputs
            run();
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
                for(int j = 7; j < data.length; j++){
                    theGraph.addEdge(data[0], data[j]);
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
    private static void run(){
        Scanner input = new Scanner(System.in);
        while(true) {
            System.out.println("\n1. Remove friendship\n" +
                    "2. Delete Account\n" +
                    "3. Count friends\n" +
                    "4. Friends Circle\n" +
                    "5. Closeness centrality\n" +
                    "6. Exit\n");
            System.out.print("Enter an option: ");
            switch(input.nextInt()){
                case 1:
                    System.out.print("Enter the first name of student 1: ");
                    String friend1 = input.next();
                    System.out.print("Enter the first name of student 2: ");
                    String friend2 = input.next();

                    theGraph.removeFriendship(friend1, friend2);
                    break;
                case 2:
                    System.out.print("Please enter the first name of the student to delete: ");
                    String student = input.next();

                    theGraph.deleteAccount(student);
                    break;
                case 3:
                    System.out.print("Please enter the first name of the student to check friend count: ");
                    student = input.next();
                    theGraph.countFriends(student);
                    break;
                case 4:
                    System.out.print("Please enter the college to see the friend circles: ");
                    Scanner in = new Scanner(System.in);
                    theGraph.friendCircle(in.nextLine());
                    break;
                case 5:
                    String s;
                    System.out.print("Please enter the student name to check closeness centrality: ");
                    double sum = theGraph.closenessCentrality(s = input.next());
                    if (sum == -1){
                        System.out.println("Sorry..\n" + s + " not found!");
                        break;
                    }

                    DecimalFormat res = new DecimalFormat("####0.00");
                    System.out.println(res.format(sum));

                    theGraph.normalizedCentrality(s, sum);
                    break;
                case 6:
                    System.exit(1);

                default:
                    System.out.println("Invalid input.");
                    break;
            }
        }
    }
}
