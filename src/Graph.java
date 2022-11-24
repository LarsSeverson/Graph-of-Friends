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
    public void closenessCentrality(String name){
        Vertex source = find(name);
        double sum = sumBFS(source);
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
    private double sumBFS(Vertex source){
        double res = 0;
        HashMap<Vertex, Float> distance = new HashMap<>();
        PriorityQueue<Vertex> pq = new PriorityQueue<>();

        distance.put(source, 0F);
        pq.add(source);

        float d = 1;
        while(!pq.isEmpty()){
            Vertex v = pq.poll();
            for(Vertex i : adjList.get(v)){
                if(!distance.containsKey(i)){
                    distance.put(i, 1/d);
                    pq.add(i);
                }
            }
            d++;
        }
        return res;
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
