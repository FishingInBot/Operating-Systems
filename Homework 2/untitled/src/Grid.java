import java.util.Arrays;

/**
 * This class is responsible for creating and managing the grid.
 * @author Brandon Murry, based on Christopher Mata's Grid class.
 */
public class Grid {

    /**
     * Global Variables containing the values for the grid and the grid itself
     */
    private final double TOP_VALUE = 30.0;
    private final double BOTTOM_VALUE = 75.0;
    private final double LEFT_VALUE = 15.0;
    private final double RIGHT_VALUE = 72.0;
    private Double[][] grid;

    /**
     * Constructor for the Grid class
     * @param n The size of the grid
     */
    public Grid(int n) {
        grid = new Double[n][n];
        for (Double[] row : grid) {
            Arrays.fill(row, 0.0);
        }
        buildGrid(this, n);
    }

    /**
     * Sets the value at a specific position in the grid
     * @param row The row of the grid
     * @param col The column of the grid
     * @param value The value to be set
     */
    public void setCellValue(int row, int col, Double value) {
        grid[row][col] = value;
    }

    /**
     * Gets the value at a specific position in the grid
     * @param row The row of the grid
     * @param col The column of the grid
     * @return The value at a specific position in the grid
     */
    public Double getCellValue(int row, int col) {
        return grid[row][col];
    }

    /**
     * Gets the size of the grid
     * @return The size of the grid
     */
    public int getSize() {
        return grid.length;
    }

    /**
     * Gets the size of a specific row in the grid
     * @param row The row of the grid
     * @return The size of a specific row in the grid
     */
    public int getSize(int row) {
        return grid[row].length;
    }

    /**
     * Builds the grid
     * @param grid The grid to be built
     * @param gridSize The size of the grid
     */
    public void buildGrid(Grid grid, int gridSize){
        // Sets the top values of the grid
        for (int col = 1; col < gridSize - 1; col++) {
            grid.setCellValue(0, col, TOP_VALUE);
        }

        // Sets the bottom values of the grid
        for (int col = 1; col < gridSize - 1; col++) {
            grid.setCellValue(gridSize - 1, col, BOTTOM_VALUE);
        }

        // Sets the left values of the grid
        for (int row = 1; row < gridSize - 1; row++) {
            grid.setCellValue(row, 0, LEFT_VALUE);
        }

        // Sets the right values of the grid
        for (int row = 1; row < gridSize - 1; row++) {
            grid.setCellValue(row, gridSize - 1, RIGHT_VALUE);
        }
    }

    /**
     * Gets the average value of the grid
     * @return The average value of the grid
     */
    public Double getAverageValue(){
        Double sum = 0.0;
        for (Double[] row : grid) {
            for (Double cell : row) {
                sum += cell;
            }
        }
        return sum / (grid.length * grid[0].length);
    }

    /**
     * Gets the grid
     * @return The grid
     */
    public Double[][] getGrid() {
        return grid;
    }
}