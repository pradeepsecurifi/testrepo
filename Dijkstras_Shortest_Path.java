import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
 
public class Dijkstras_Shortest_Path
{
    private double          distances[];
    private Set<Integer> settled;
    private Set<Integer> unsettled;
    private int          number_of_nodes;
    private double         adjacencyMatrix[][];
 
    public Dijkstras_Shortest_Path(int number_of_nodes)
    {
        this.number_of_nodes = number_of_nodes;
        distances = new double[number_of_nodes + 1];
        settled = new HashSet<Integer>();
        unsettled = new HashSet<Integer>();
        adjacencyMatrix = new double[number_of_nodes + 1][number_of_nodes + 1];
    }
 
    public void dijkstra_algorithm(double adjacency_matrix[][], int source)
    {
        int evaluationNode;
        for (int i = 1; i <= number_of_nodes; i++)
            for (int j = 1; j <= number_of_nodes; j++)
                adjacencyMatrix[i][j] = adjacency_matrix[i][j];
 
        for (int i = 1; i <= number_of_nodes; i++)
        {
            distances[i] = Double.MAX_VALUE;
        }
 
        unsettled.add(source);
        distances[source] = 0;
        while (!unsettled.isEmpty())
        {
            evaluationNode = getNodeWithMinimumDistanceFromUnsettled();
            unsettled.remove(evaluationNode);
            settled.add(evaluationNode);
            evaluateNeighbours(evaluationNode);
        }
    }
 
    private int getNodeWithMinimumDistanceFromUnsettled()
    {
        double min;
        int node = 0;
 
        Iterator<Integer> iterator = unsettled.iterator();
        node = iterator.next();
        min = distances[node];
        for (int i = 1; i <= distances.length; i++)
        {
            if (unsettled.contains(i))
            {
                if (distances[i] <= min)
                {
                    min = distances[i];
                    node = i;
                }
            }
        }
        return node;
    }
 
    private void evaluateNeighbours(int evaluationNode)
    {
        double edgeDistance = -1;
        double newDistance = -1;
 
        for (int destinationNode = 1; destinationNode <= number_of_nodes; destinationNode++)
        {
            if (!settled.contains(destinationNode))
            {
                if (adjacencyMatrix[evaluationNode][destinationNode] != Integer.MAX_VALUE)
                {
                    edgeDistance = adjacencyMatrix[evaluationNode][destinationNode];
                    newDistance = distances[evaluationNode] + edgeDistance;
                    if (newDistance < distances[destinationNode])
                    {
                        distances[destinationNode] = newDistance;
                    }
                    unsettled.add(destinationNode);
                }
            }
        }
    }
 
    public static void main(String... arg)
    {
        double adjacency_matrix[][];
        int number_of_vertices;
        int source = 0, destination = 0;
        Scanner scan = new Scanner(System.in);
        try
        {
            System.out.println("Enter the number of vertices");
            number_of_vertices = scan.nextInt();
            adjacency_matrix = new double[number_of_vertices + 1][number_of_vertices + 1];
 
            System.out.println("Enter the Weighted Matrix for the graph");
            // for (int i = 1; i <= number_of_vertices; i++)
            // {
            //     for (int j = 1; j <= number_of_vertices; j++)
            //     {
            //         adjacency_matrix[i][j] = scan.nextInt();
            //         if (i == j)
            //         {
            //             adjacency_matrix[i][j] = 0;
            //             continue;
            //         }
            //         if (adjacency_matrix[i][j] == 0)
            //         {
            //             adjacency_matrix[i][j] = Integer.MAX_VALUE;
            //         }
            //     }
            // }
 
            int lats[] = {0, 0, 70};
            int lons[] = {90, 0, 45};
            String[] paths = {"2", "0 2", "0 1"};
            double dd;
            String[] temp;
            for(int i=0; i<paths.length; i++){
                 System.out.println(paths[i]);
                 temp = paths[i].split(" ");
                 for(int j=0; j<temp.length; j++){
                    int value = Integer.parseInt(temp[j]);
                    int lat1 = lats[i];
                    int lon1 = lons[i];
                    int lat2 = lats[value];
                    int lon2 = lons[value];
                    // radius * acos(sin(lat1) * sin(lat2) + cos(lat1) * cos(lat2) * cos(lon1 - lon2))
                    dd = 4000 * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
                    System.out.println(" Paths ... "+i+" "+value);
                    System.out.println(" Lats Lons ... "+lat1+" "+lon1+" "+lat2+" "+lon2);
                    System.out.println(" Distance is "+dd);
                    adjacency_matrix[i][value] = dd;
                    if (i == value){
                        adjacency_matrix[i][value] = 0;
                    }
                    if (adjacency_matrix[i][value] == 0){
                        adjacency_matrix[i][value] = Double.MAX_VALUE;
                    }
                 }
            }

            for(int i=0; i<number_of_vertices; i++){
                for(int j=0; j<number_of_vertices; j++){
                    System.out.print(adjacency_matrix[i][j]+" ");
                }
                System.out.println("");
            }

            System.out.println("Enter the source ");
            source = scan.nextInt();
 
            System.out.println("Enter the destination ");
            destination = scan.nextInt();
 
            Dijkstras_Shortest_Path dijkstrasAlgorithm = new Dijkstras_Shortest_Path(
                    number_of_vertices);
            dijkstrasAlgorithm.dijkstra_algorithm(adjacency_matrix, source);
 
            System.out.println("The Shorted Path from " + source + " to " + destination + " is: ");
            for (int i = 1; i <= dijkstrasAlgorithm.distances.length - 1; i++)
            {
                if (i == destination)
                    System.out.println(source + " to " + i + " is "
                            + dijkstrasAlgorithm.distances[i]);
            }

            for(int i=0; i<dijkstrasAlgorithm.distances.length; i++){
                System.out.println(dijkstrasAlgorithm.distances[i]);
            }

        } catch (InputMismatchException inputMismatch)
        {
            System.out.println("Wrong Input Format");
        }
        scan.close();
    }
}