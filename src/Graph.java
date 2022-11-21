import java.util.*;

public class Graph{
    private final List<Node>[] Students;

    private int numberOfVertices;

    public Graph(int size){
        // initial number of vertex
        numberOfVertices = size;
        Students = new LinkedList[size];
        for(int i = 0; i < Students.length; i++){
            Students[i] = new LinkedList<>();
        }

    }

    private static class Edge{

    }
    private static class Node{
        private int studentId;
        private int friendCount;

        private String firstName;
        private String lastName;
        private String collegeName;
        private String departmentName;
        private String studentEmail;

        private ArrayList<Integer> friends;

        private Node(int studentId, String firstName, String lastName, String collegeName, String departmentName, String studentEmail, int friendCount){
            this.studentId = studentId; this.firstName = firstName; this.lastName = lastName; this.collegeName = collegeName;
            this.departmentName = departmentName; this.studentEmail = studentEmail; this.friendCount = friendCount;
        }

    }
}
