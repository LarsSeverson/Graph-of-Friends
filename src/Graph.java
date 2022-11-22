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
        adjList.get(friend).add(source);

        numberOfEdges++;
    }
    public int getNumberOfVertices() {
        return numberOfVertices;
    }
    public int getNumberOfEdges() {
        return numberOfEdges;
    }

    private Vertex find(String id){
        for(Vertex i : adjList.keySet()){
            if (i.studentId.equals(id)){
                return i;
            }
        }
        return null;
    }

    private static class Vertex {
        private final String studentId;
        private final String friendCount;
        private final String firstName;
        private final String lastName;
        private final String collegeName;
        private final String departmentName;
        private final String studentEmail;

        private Vertex(String studentId, String firstName, String lastName, String collegeName, String departmentName, String studentEmail, String friendCount){
            this.studentId = studentId; this.firstName = firstName; this.lastName = lastName; this.collegeName = collegeName;
            this.departmentName = departmentName; this.studentEmail = studentEmail; this.friendCount = friendCount;
        }
    }
}
