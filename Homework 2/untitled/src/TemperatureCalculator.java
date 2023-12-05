import java.util.concurrent.*;

public class TemperatureCalculator {
    private static final double CONVERGENCE_THRESHOLD = 5.0;
    private static final int NUM_THREADS = 8;
    private static final Grid gridMaker = new Grid(20);
    private static final Double[][] grid = gridMaker.getGrid();

    private static final int GRID_SIZE = grid.length;


    public static void main(String[] args){
        int iterations = 0;
        double totalError = 0.0;
        do {
            totalError = calculateTotalError();
            iterations++;
            System.out.println("Total Grid Error: " + totalError);
            System.out.println("Grid: ");
            for (Double[] row : grid) {
                for (double cell : row) {
                    System.out.print(cell + " ");
                }
                System.out.println();
            }
            ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
            for (int i = GRID_SIZE-1; i > 0; i--) {
                executor.submit(new TemperatureCalculatorThread(i, 1));
                executor.submit(new TemperatureCalculatorThread(1, i));
            }
            // when no more to submit, call shutdown, submitted jobs will continue to run
            executor.shutdown();
            // now wait for the jobs to finish
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while((totalError > CONVERGENCE_THRESHOLD) || (Double.isNaN(totalError)));

        System.out.println("Converged!");
        System.out.println("Total Iterations: " + iterations);
    }

    private static double calculateTotalError() {
        double totalError = 0;
        for (int i = 1; i < GRID_SIZE-2; i++) {
            for (int j = 1; j < GRID_SIZE-2; j++) {
                double currentError = Math.abs(grid[i][j] - neighborAverage(i, j));
                totalError += currentError;
            }
        }
        return totalError;
    }
    private static double neighborAverage(int row, int col) {
        double sum = 0;
        int count = 0;
        if (row > 0 && grid[row - 1][col] != 0.0){
            sum += grid[row - 1][col];
            count++;
        }
        if (row < GRID_SIZE - 1 && grid[row + 1][col] != 0.0){
            sum += grid[row + 1][col];
            count++;
        }
        if (col > 0 && grid[row][col - 1] != 0.0){
            sum += grid[row][col - 1];
            count++;
        }
        if (col < GRID_SIZE - 1 && grid[row][col + 1] != 0.0) {
            sum += grid[row][col + 1];
            count++;
        }
        return sum/count;
    }

    static class TemperatureCalculatorThread implements Runnable {
        private int row;
        private int column;

        public TemperatureCalculatorThread(int row, int col) {
            this.row = row;
            this.column = col;
        }

        @Override
        public void run() {
            //Start from the row/column given, then run diagonally across the grid
            while(row<GRID_SIZE-1 && column<GRID_SIZE-1) {
                if(grid[row-1][column] != 0.0 || grid[row+1][column] != 0.0 || grid[row][column-1] != 0.0 || grid[row][column+1] != 0.0) {
                    grid[row][column] = neighborAverage(row, column);
                }
                this.row++;
                this.column++;
            }
        }
    }
}