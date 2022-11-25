import java.text.DecimalFormat;
import java.util.*;

public class Graph{
    private final Map<Vertex, List<Vertex>> adjList;
    private int numberOfVertices;
    private int numberOfEdges;

    public Graph(){
        numberOfVertices = 0;
        numberOfEdges = 0;
        adjList = new HashMap<>();
    }

    public void addVertex(String[] data){
        //                             ID       fName    lName    cName    dName    email    friendCount
        adjList.putIfAbsent(new Vertex(data[0], data[1], data[2], data[3], data[4], data[5], data[6]), new ArrayList<>());
        numberOfVertices++;
    }
    public void addEdge(String sourceID, String friendID){
        Vertex source = find(sourceID);
        Vertex friend = find(friendID);

        adjList.get(source).add(friend);
        numberOfEdges++;

    }

    public void removeFriendship(String friend1, String friend2) {
        Vertex v1 = find(friend1.toLowerCase());
        Vertex v2 = find(friend2.toLowerCase());
        if (v1 == null || v2 == null){
            if (v1 == null && v2 != null){
                System.out.println("Sorry..\n" + friend1 + " not found!");
                return;
            }
            if (v2 == null && v1 != null){
                System.out.println("Sorry..\n" + friend2 + " not found!");
                return;
            }
            System.out.println("Sorry.. \n" + friend1 + " and " + friend2 + " not found!");
            return;
        }

        if(adjList.get(v1).contains(v2)){
            // Remove Edges
            adjList.get(v1).remove(v2);
            adjList.get(v2).remove(v1);
            // Adjust friend count
            v1.friendCount--;
            v2.friendCount--;

            numberOfEdges--;

            System.out.println("\nThe edge between the students " + v1.firstName + " and " + v2.firstName +
                    " has been successfully removed..");
            System.out.println("Total number of students in the graph: " + numberOfVertices);
            System.out.println("Total number of edges in the graph: " + numberOfEdges);
            return;
        }
        System.out.println("\nSorry.. there is no edge between the vertices " + v1.firstName + " and " + v2.firstName + ".");
    }
    public void deleteAccount(String student){
        Vertex toDelete = find(student);
        if (toDelete == null){
            System.out.println("\nSorry..\n" + student + " not found!");
            return;
        }

        // Remove vertex
        adjList.remove(toDelete);
        numberOfVertices--;

        for(Vertex i : adjList.keySet()){
            // Remove all edges
            if(adjList.get(i).contains(toDelete)){
                adjList.get(i).remove(toDelete);
                numberOfEdges--;
                i.friendCount--;
            }
        }
        System.out.println("The student " + toDelete.firstName + " has been successfully removed..");
        System.out.println("Total number of vertices in the graph: " + numberOfVertices);
        System.out.println("Total number of edges in the graph: " + numberOfEdges);

    }
    public void countFriends(String name){
        Vertex v = find(name);
        if(v==null){
            System.out.println("\nSorry..\n" + name + " not found!");
            return;
        }

        System.out.println("\nFriend count for " + v.firstName + ": " + v.friendCount);
        if(v.friendCount > 0){
            System.out.println("Friends of " + v.firstName + " are:");
            for(Vertex i : adjList.get(v)){
                System.out.println(i.firstName);
            }
        }
    }
    public void friendCircle(String college){
        HashMap<Vertex, Boolean> visited = new HashMap<>();
        ArrayList<Vertex> output = new ArrayList<>();
        for(Vertex key : adjList.keySet()){
            if (visited.get(key)==null){
                output.addAll(arrayListBFS(key, college, visited));
            }
        }
        if(output.isEmpty()){
            System.out.println("College could not be found.");
            return;
        }
        System.out.println("Following are the friend circles in the " + output.get(0).collegeName + ":");

        for(Vertex i : output){
            System.out.println(i.firstName);
            int h = 0;
            System.out.print("|\t");
            for(Vertex j : adjList.get(i)){
                System.out.print(j.firstName);
                if(h < adjList.get(i).size() - 1){
                    System.out.print(" - ");
                }
                h++;
            }
            System.out.println();
        }
        System.out.println();
    }
    public void normalizedCentrality(String name, double sum){
        Vertex v = find(name);
        sum = sum / (numberOfVertices - 1);
        assert v != null;
        DecimalFormat d = new DecimalFormat("####0.00");
        System.out.println("The Normalized Closeness Centrality for " + v.firstName + ": " + d.format(sum));
    }
    public double closenessCentrality(String name){
        // pre condition
        Vertex source = find(name);
        if (source == null){
            return -1;
        }
        double sum = 0;
        /* * Need to keep track of distance of each vertex; subject to change the
             distance based on shortest path
           * Need to keep track of every visited vertex - visited is defined
             by every vertex at the top of the queue: pq
         */
        HashMap<Vertex, Float> distance = new HashMap<>();
        HashMap<Vertex, Boolean> visited = new HashMap<>();
        Queue<Vertex> pq = new LinkedList<>();

        // Add the first source value - 0
        distance.put(source, 0F);
        pq.add(source);

        // start Dijkstra's algorithm
        while(!pq.isEmpty()){
            Vertex v = pq.poll();
            // top of queue
            visited.put(v, true);
            // BFS search
            for(Vertex i : adjList.get(v)){
                // A vertex must not be visited again if it has been visited / deque()'ed
                if(visited.get(i) == null){
                    // Only put it if it's absent with infinity as its value
                    distance.putIfAbsent(i, Float.MAX_VALUE);
                    // Since it is an unweighted graph and Dijkstra's algo is based on weight,
                    // weight is 1 for every edge
                    float weight = 1;
                    // If the distance of the edge is greater than its vertex's distance + weight,
                    // replace the edge's distance with the vertex's distance + 1 (weight)
                    if (distance.get(i) > distance.get(v) + weight){
                        distance.replace(i, distance.get(v) + weight);
                        // Add it to the queue
                        pq.add(i);
                    }
                }
            }
        }

        // Get the closeness centrality
        for(float i : distance.values()){
            if(i != 0){
                // defined by 1/dij for every distance of all Vertexes
                sum += 1/i;
            }
        }
        System.out.print("\nThe Closeness Centrality for " + source.firstName + ": ");
        return sum;
    }

    public int getNumberOfVertices() {
        return numberOfVertices;
    }
    public int getNumberOfEdges() {
        return numberOfEdges;
    }

    private ArrayList<Vertex> arrayListBFS(Vertex current, String college, HashMap<Vertex, Boolean> visited ){
        ArrayList<Vertex> result = new ArrayList<>();
        Queue<Vertex> q = new LinkedList<>();
        visited.put(current, true);
        q.add(current);

        // BFS loop
        if(current.collegeName.equalsIgnoreCase(college)){
            result.add(current);
        }
        while(!q.isEmpty()){
            Vertex v = q.poll();
            for(Vertex i : adjList.get(v)){
                if (visited.get(i) == null){
                    if (i.collegeName.equalsIgnoreCase(college)){
                        result.add(i);
                    }
                    q.add(i);
                    visited.put(i, true);
                }
            }
        }
        return result;
    }
    private Vertex find(String data){
        try{
            long l = Long.parseLong(data);
            for(Vertex i : adjList.keySet()){
                if (i.studentId == l){
                    return i;
                }
            }
        }
        catch(NumberFormatException e){
            for(Vertex i : adjList.keySet()){
                if (i.firstName.equalsIgnoreCase(data)){
                    return i;
                }
            }
        }
        return null;
    }

    private static class Vertex {
        private final long studentId;
        private int friendCount;
        private final String firstName;
        private final String lastName;
        private final String collegeName;
        private final String departmentName;
        private final String studentEmail;

        private Vertex(String studentId, String firstName, String lastName, String collegeName, String departmentName, String studentEmail, String friendCount){
            this.studentId = Long.parseLong(studentId); this.firstName = firstName; this.lastName = lastName; this.collegeName = collegeName.replaceAll("\"", "");
            this.departmentName = departmentName; this.studentEmail = studentEmail; this.friendCount = Integer.parseInt(friendCount);
        }
    }
}
