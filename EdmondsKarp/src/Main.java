import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        for (String fileName : args) {
            System.out.println("----------");
            System.out.println("Edmonds-Karp:\n");
        int[][] links = scanFileToGraphArr(fileName);
        Graph graph = new Graph(links);
        printMatrix(graph.edKarp());
        System.out.println("Maximum flow: " + graph.getMaxFlow() + "\n");
        }
    }

    private static int[][] scanFileToGraphArr(String filename) {
        ArrayList<int[]> scannedArray = new ArrayList<>();

        try {
            File file = new File(System.getProperty("user.dir") + "/" + filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String[] stringArr = Arrays.stream(scanner.nextLine().split("\\s+"))
                                    .filter(e -> e.trim().length() > 0).toArray(String[]::new);

                int[] intArr = new int[stringArr.length];
                for (int i = 0; i < stringArr.length; i++) {
                    intArr[i] = Integer.parseInt(stringArr[i]);
                }

                scannedArray.add(intArr);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return scannedArray.toArray(new int[0][0]);
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] line : matrix) {
            if (line.length == 2) {
                System.out.println("Nodes: " + line[0] + ", Edges: " + line[1]);
                continue;
            }
            System.out.println(line[0] + " --> " + line[1] + ", Weight: " + line[2]);
        }
    }

    private static void printMatrix(Object[][] matrix) {
        for (Object[] path : matrix) {
            for (Object link : path) {
                System.out.print(link + " ");
            }
            System.out.println("\n");
        }
    }
}
