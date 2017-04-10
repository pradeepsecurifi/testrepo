import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CloudTravel {
    public int airportNodes;

    CloudTravel(int V) {
        this.airportNodes = V;
    }

    int minDistance(double dist[], Boolean sptSet[]) throws Exception {
        // Initialize min value
        double min = Double.MAX_VALUE;
        int min_index = -1;

        for (int v = 0; v < airportNodes; v++) {
            System.out.println(" dist[v] here in minDistance is " + dist[v] + " v " + v);
            if (sptSet[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
        }
        return min_index;
    }

    // A utility function to print the constructed distance array
    void printSolution(double dist[], int n, int destination) {
        System.out.println("Vertex   Distance from Source");
        for (int i = 0; i < airportNodes; i++)
            System.out.println(i + " \t\t " + dist[i]);
        if (dist[destination] == Double.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(" The shortest path is " + dist[destination]);
        }

    }

    // Function that implements Dijkstra's single source shortest path
    // algorithm for a graph represented using adjacency matrix
    // representation
    void dijkstra(double graph[][], int src, int destination) throws Exception {
        double dist[] = new double[airportNodes]; // The output array. dist[i]
                                                    // will hold
        // the shortest distance from src to i

        // sptSet[i] will true if vertex i is included in shortest
        // path tree or shortest distance from src to i is finalized
        Boolean sptSet[] = new Boolean[airportNodes];

        // Initialize all distances as INFINITE and stpSet[] as false
        for (int i = 0; i < airportNodes; i++) {
            dist[i] = Double.MAX_VALUE;
            sptSet[i] = false;
        }
        System.out.println(" The Max value is " + Double.MAX_VALUE);
        System.out.println(" The Values are ");
        for (int i = 0; i < airportNodes; i++) {
            System.out.println(dist[i]);
        }

        // Distance of source vertex from itself is always 0
        dist[src] = 0;

        // Find shortest path for all vertices
        for (int count = 0; count < airportNodes - 1; count++) {
            // Pick the minimum distance vertex from the set of vertices
            // not yet processed. u is always equal to src in first
            // iteration.
            int u = minDistance(dist, sptSet);

            // Mark the picked vertex as processed
            sptSet[u] = true;

            // Update dist value of the adjacent vertices of the
            // picked vertex.
            for (int v = 0; v < airportNodes; v++) {

                // Update dist[v] only if is not in sptSet, there is an
                // edge from u to v, and total weight of path from src to
                // v through u is smaller than current value of dist[v]
                System.out.println(" u is " + u);
                System.out.println("dist[v] " + dist[u]);
                if (!sptSet[v] && graph[u][v] != 0.0 && dist[u] != Double.MAX_VALUE && dist[u] + graph[u][v] < dist[v])
                    dist[v] = dist[u] + graph[u][v];
            }
        }

        // print the constructed distance array
        printSolution(dist, airportNodes, destination);
    }

    void validNumber(int num, int nValue) throws Exception {

        if (num < 0 || num > nValue - 1) {
            throw new Exception("Number should be between 0 and " + (nValue - 1));
        }

    }

    // Driver method
    public static void main(String[] args) {

        BufferedReader br = null;

        try {

            br = new BufferedReader(new InputStreamReader(System.in));

            int source = -1, destination = -1;

            double latValue = 0, longValue = 0;
            boolean isLatValueContinue = false;
            boolean isLonValueContinue = false;
            boolean isCanTravelValueContinue = false;
            boolean isSourceValidContinue = false;
            boolean isDestinationValidContinue = false;

            Double lats[] = null;
            Double lons[] = null;
            String[] paths = null;

            do {

                if (!isLatValueContinue)
                    System.out.println(" Enter the latitutudes values one in each line:  ");
                else {
                    System.out.println(" Invalid latitude value.");
                    System.out.println(" Re-enter the latitude values one in each line ");
                }

                isLatValueContinue = false;
                String latTuple = br.readLine();
                String pattern = "\\{([ ,0-9]+)\\}";

                Pattern p = Pattern.compile(pattern);
                Matcher matcher = p.matcher(latTuple);

                String latValues[] = null;

                if (matcher.find()) {

                    String latitudeValues = matcher.group(1);
                    System.out.println(latitudeValues);

                    latValues = latitudeValues.split(",");

                    if (latValues.length == 0) {
                        System.out.println("Latitude tuple contains invalid input format.");
                        isLatValueContinue = true;
                        continue;
                    }

                    lats = new Double[latValues.length];
                } else {
                    System.out.println("Latitude tuple need be entered in the {val1,val2,..} format");
                    isLatValueContinue = true;
                    continue;
                }

                for (int i = 0; i < latValues.length; i++) {

                    isLatValueContinue = false;
                    try {

                        latValue = Double.parseDouble(latValues[i].trim());

                        if (latValue < -89 || latValue > 89) {

                            throw new Exception();
                        }

                        lats[i] = latValue * (Math.PI / 180);

                    } catch (NumberFormatException ne) {

                        isLatValueContinue = true;
                        break;
                    } catch (InputMismatchException ime) {

                        isLatValueContinue = true;
                        break;
                    } catch (Exception e) {

                        isLatValueContinue = true;
                        break;
                    }

                }

            } while (isLatValueContinue);

            Map<Double, Integer> longitudeValues = new HashMap<Double, Integer>();
            String exceptionMsg = null;

            do {

                if (!isLonValueContinue)
                    System.out.println(" Enter the longitudes values one in each line ");
                else {
                    System.out.println("Invalid longitude value. " + (exceptionMsg != null ? exceptionMsg : ""));
                    System.out.println(" Re-enter the longitude values one in each line ");
                }

                isLonValueContinue = false;

                String lonTuple = br.readLine();
                String pattern = "\\{([ ,0-9]+)\\}";

                Pattern p = Pattern.compile(pattern);
                Matcher matcher = p.matcher(lonTuple);

                String lonValues[] = null;

                if (matcher.find()) {

                    String lonTupleValue = matcher.group(1);
                    System.out.println(lonTupleValue);

                    lonValues = lonTupleValue.split(",");

                    if (lats.length != lonValues.length) {
                        System.out.println("Latitude and Longitude number of values should be equal.");
                        isLonValueContinue = true;
                        continue;
                    }

                    lons = new Double[lonValues.length];
                } else {
                    System.out.println("Longitude tuple need be entered in the {val1,val2,..} format");
                    isLonValueContinue = true;
                    continue;
                }

                for (int i = 0; i < lonValues.length; i++) {

                    try {

                        longValue = Double.parseDouble(lonValues[i]);

                        if (longitudeValues.containsKey(longValue)) {
                            int index = longitudeValues.get(longValue);
                            if (lats[index].equals(lats[i])) {
                                longitudeValues.clear();
                                throw new Exception("No two airports will reside at the same latitude and longitude.");
                            }

                        } else {
                            longitudeValues.put(longValue, i);
                        }

                        if (longValue < -179 || longValue > 179) {

                            throw new Exception();
                        }
                        lons[i] = longValue * (Math.PI / 180);

                    }

                    catch (NumberFormatException ne) {

                        isLonValueContinue = true;
                        break;
                    } catch (InputMismatchException ime) {

                        isLonValueContinue = true;
                        break;
                    } catch (Exception e) {
                        exceptionMsg = e.getMessage();
                        isLonValueContinue = true;
                        break;
                    }

                }

            } while (isLonValueContinue);

            int airportCount = lats.length;

            if (airportCount > 20 || airportCount < 1) {
                throw new Exception(
                        " \n Invalid number of airports. \n Airport number should be exist between 1 and 20");
            }

            double graph[][] = new double[airportCount][airportCount];

            for (double[] row : graph)
                Arrays.fill(row, 0.0);
            for (int i = 0; i < airportCount; i++) {
                for (int j = 0; j < airportCount; j++) {
                    System.out.print(graph[i][j] + " ");
                }
                System.out.println("");
            }

            CloudTravel t = new CloudTravel(airportCount);

            do {

                if (!isCanTravelValueContinue)
                    System.out.println("Enter the canTravel for particular airport one in each line ");
                else {

                    System.out.println("Invalid canTravel value. " + (exceptionMsg != null ? exceptionMsg : ""));
                    System.out.println("Re-enter the canTravel for particular airport one in each line ");
                }

                isCanTravelValueContinue = false;

                String canTravelTuple = br.readLine();
                String pattern = "\\{([\" ,0-9]+)\\}";

                Pattern p = Pattern.compile(pattern);
                Matcher matcher = p.matcher(canTravelTuple);

                
                paths=new String[airportCount];
                String canTravelValues[] = null;

                if (matcher.find()) {
                    
                    canTravelValues = matcher.group(1).split(",");
                    
                    if (airportCount != canTravelValues.length) {
                        System.out.println("Latitude and Longitude number of values should be equal with the canTravel value.");
                        isCanTravelValueContinue = true;
                        continue;
                    }
                    
                } else {
                    System.out.println("Can Travel tuple need be entered in the {\"val1\",\"val2\",..} format");
                    isCanTravelValueContinue = true;
                    continue;
                }

                for (int i = 0; i < canTravelValues.length; i++) {

                    try {

                        paths[i] = canTravelValues[i].replace("\"", "").trim();

                        if (paths[i].matches("(\\d+)")) {
                            System.out.println(paths[i]);

                            int num = Integer.parseInt(paths[i]);

                            t.validNumber(num, airportCount);

                        } else if (paths[i].replace(" ", "").matches("(\\d+)")) {
                            System.out.println(paths[i]);

                            for (String number : paths[i].split(" ")) {

                                int num = Integer.parseInt(number);

                                t.validNumber(num, airportCount);

                            }

                        } else {
                            throw new Exception(
                                    "CanTravel value will be a space delimeted list of integers or one integer value");
                        }

                    } catch (NumberFormatException ne) {

                        isCanTravelValueContinue = true;
                        break;
                    } catch (InputMismatchException ime) {

                        isCanTravelValueContinue = true;
                        break;
                    } catch (Exception e) {
                        exceptionMsg = e.getMessage();
                        isCanTravelValueContinue = true;
                        break;
                    }
                }
            } while (isCanTravelValueContinue);

            do {

                try {

                    if (!isSourceValidContinue)
                        System.out.println("Enter the source ");
                    else {
                        System.out.println("Invalid source value. " + (exceptionMsg != null ? exceptionMsg : ""));
                        System.out.println("Re-enter the source value: ");

                    }
                    isSourceValidContinue = false;
                    source = Integer.parseInt(br.readLine());
                    t.validNumber(source, airportCount);
                } catch (Exception e) {
                    exceptionMsg = e.getMessage();
                    isSourceValidContinue = true;
                }

            } while (isSourceValidContinue);

            do {

                try {

                    if (!isDestinationValidContinue)
                        System.out.println("Enter the destination ");
                    else {
                        System.out.println("Invalid destination value. " + (exceptionMsg != null ? exceptionMsg : ""));
                        System.out.println("Re-enter the destination value: ");

                    }
                    isDestinationValidContinue = false;
                    destination = Integer.parseInt(br.readLine());
                    ;
                    t.validNumber(destination, airportCount);
                } catch (Exception e) {
                    exceptionMsg = e.getMessage();
                    isDestinationValidContinue = true;
                }

            } while (isDestinationValidContinue);

            System.out.println(lats[2]);

            String[] temp;
            for (int i = 0; i < paths.length; i++) {
                System.out.println(paths[i]);
                temp = paths[i].split(" ");
                for (int j = 0; j < temp.length; j++) {
                    int value = Integer.parseInt(temp[j]);
                    double lat1 = lats[i];
                    double lon1 = lons[i];
                    double lat2 = lats[value];
                    double lon2 = lons[value];

                    // radius * acos(sin(lat1) * sin(lat2) + cos(lat1) *
                    // cos(lat2) *
                    // cos(lon1 - lon2))
                    double doubleDistance = 4000 * Math.acos(
                            Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
                    System.out.println(" Paths ... " + i + " " + value);
                    System.out.println(" Lats Lons ... " + lat1 + " " + lon1 + " " + lat2 + " " + lon2);
                    System.out.println(" Distance is " + doubleDistance);
                    System.out.println("i " + i + " value " + value);
                    graph[i][value] = doubleDistance;
                }
            }
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.print(graph[i][j] + " ");
                }
                System.out.println("");
            }

            t.dijkstra(graph, source, destination);
        } catch (

        Exception exception) {
            System.out.println("Unable to find the valid route between source and destination. \n Exception reason: "
                    + ((exception.getMessage() != null) ? exception.getMessage() : ""));
        } finally {

            try {
                if (br != null)
                    br.close();

            } catch (IOException e) {
                System.out.println("Exception in Buffered Reader: " + e.getMessage());
            }
        }

    }
}