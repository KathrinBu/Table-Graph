import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        String line1= scanner.nextLine();
        String[] nm= line1.split(" ");
        int n = Integer.parseInt(nm[0]);
        int m = Integer.parseInt(nm[1]);
        int[][] table = new int[n][m];
        for (int i = 0; i < n; i++) {
            String line= scanner.nextLine();
            String[] num=line.split(" ");
            for (int j = 0; j < m; j++) {
                table[i][j] = Integer.parseInt(num[j]);
            }
        }
        Queue<int[]> queue = new LinkedList<>();
        int[][] distanceTable = new int[n][m];
        findTable(distanceTable,n,m,table,queue);
        minFind(queue,distanceTable,n,m);
    }

    public static int[][] findTable(int[][] distanceTable, int n, int m, int[][] table, Queue<int[]> queue) {
        for (int[] row : distanceTable) {
            Arrays.fill(row, Integer.MAX_VALUE); //изначально не знаем, есть ли путь, присвоение максимального значения
                                                 // позволит нам затем обновлять значения только тех клеток,
                                                 // для которых мы нашли путь меньшей длины.
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (table[i][j] == 1) {
                    distanceTable[i][j] = 0;
                    queue.offer(new int[]{i, j});
                }
            }
        }
        return distanceTable;
    }

    public static void minFind(Queue<int[]> queue, int[][] distanceTable, int n, int m) {
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0];
            int col = cell[1];

            int distance = distanceTable[row][col];

            int[] dx = {-1, 1, 0, 0}; //двигаемся в четырех возможных направлений (вверх, вниз, влево, вправо)
            int[] dy = {0, 0, -1, 1}; //двигаемся в четырех возможных направлений (вверх, вниз, влево, вправо)

            for (int i = 0; i < 4; i++) {
                int newRow = row + dx[i];
                int newCol = col + dy[i];

                if (isValidCell(newRow, newCol, n, m) && distance + 1 < distanceTable[newRow][newCol]) {
                    distanceTable[newRow][newCol] = distance + 1;
                    queue.offer(new int[]{newRow, newCol});
                }
            }
        }
        System.out.println("Таблица минимальных расстояний:");
        for (int[] row : distanceTable) {
            for (int cellDistance : row) {
                System.out.print(cellDistance + " ");
            }
            System.out.println();
        }
    }
    //проверяем, помещаемся ли мы в таблицу
    private static boolean isValidCell(int row, int col, int numRows, int numCols) {
        return row >= 0 && col >= 0 && row < numRows && col < numCols;
    }
}

