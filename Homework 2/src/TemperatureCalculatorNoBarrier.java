import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This class is responsible for calculating the temperature of the grid using the Jacobi method.
 * @author Brandon Murry
 */
public class TemperatureCalculatorNoBarrier {
    // Global Variables for the class and the grid
    private static final double CONVERGENCE_THRESHOLD = 5.0;
    private static final int NUM_THREADS = 1;
    private static final Grid gridMaker = new Grid(100);
    private static final Double[][] grid = gridMaker.getGrid();
    private static final int GRID_SIZE = grid.length;

    /**
     * main method for the class. Calculates the temperature of the grid using the Jacobi method.
     * @param args Does not take args.
     */
    public static void main(String[] args){
        int iterations = 0;
        double totalError;
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        do {
            totalError = calculateTotalError();
            iterations++;
            for (int i = GRID_SIZE-1; i > 0; i--) {
                executor.submit(new TemperatureCalculatorThread(i, 1));
                executor.submit(new TemperatureCalculatorThread(1, i));
            }
        } while((totalError > CONVERGENCE_THRESHOLD) || (Double.isNaN(totalError)));
            // when no more to submit, call shutdown, submitted jobs will continue to run
            executor.shutdownNow();
            // now wait for the jobs to finish
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        long endTime = System.currentTimeMillis();
        System.out.println("Grid: ");
        for (Double[] row : grid) {
            for (double cell : row) {
                System.out.print((double)Math.round(cell * 10d) / 10d + " ");
            }
            System.out.println();
        }
        System.out.println("Converged!");
        System.out.println("Total Iterations: " + iterations);
        if(NUM_THREADS == 1){
            System.out.println("Type: Single Thread; Size = " + GRID_SIZE + "; Average Grid Value = " + gridMaker.getAverageValue() + "; Total Error= " + totalError + ";");
        } else {
            System.out.println("Type: "+ NUM_THREADS +" Threads; Size = " + GRID_SIZE + "; Average Grid Value = " + gridMaker.getAverageValue() + "; Total Error= " + totalError + ";");
        }
        System.out.println("Time: " + (endTime - startTime) + "ms");
    }

    /**
     * Calculates the total error of the grid
     * @return The total error of the grid
     */
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

    /**
     * Calculates the average of the neighbors of the given cell
     * @param row The row of the cell
     * @param col The column of the cell
     * @return The average of the neighbors of the given cell
     */
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

    /**
     * This class makes the threads that calculate the temperature of the grid using the Jacobi method.
     * The threads start at the given row/column and then run diagonally across the grid.
     */
    static class TemperatureCalculatorThread implements Runnable {
        private int row;
        private int column;

        /**
         * Constructor for the class
         * @param row The row to start at
         * @param col The column to start at
         */
        public TemperatureCalculatorThread(int row, int col) {
            this.row = row;
            this.column = col;
        }

        /**
         * Runs the thread
         */
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