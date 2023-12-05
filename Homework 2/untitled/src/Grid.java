import java.util.Arrays;

/**
 * Grid.java
 * <p>
 * This class is responsible for creating and managing the grid
 * </p>
 * @author Christopher Mata
 */
public class Grid {

    /**
     * Global Variable containing the grid structure
     */
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
            grid.setCellValue(0, col, 30.0);
        }

        // Sets the bottom values of the grid
        for (int col = 1; col < gridSize - 1; col++) {
            grid.setCellValue(gridSize - 1, col, 75.0);
        }

        // Sets the left values of the grid
        for (int row = 1; row < gridSize - 1; row++) {
            grid.setCellValue(row, 0, 15.0);
        }

        // Sets the right values of the grid
        for (int row = 1; row < gridSize - 1; row++) {
            grid.setCellValue(row, gridSize - 1, 72.0);
        }
    }

    /**
     * Prints the grid
     */
    public void printGrid() {
        // Print the grid to see the partially filled numbers
        for (Double[] row : grid) {
            for (Double cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public Double[] getRow(int index){
        Double[] row = new Double[grid[index].length];
        System.arraycopy(grid[index], 0, row, 0, grid[index].length);

        return row;
    }

    public Double[] getCol(int index){
        Double[] column = new Double[grid.length];
        for (int i = 0; i < grid.length; i++) {
            column[i] = grid[i][index];
        }

        return column;
    }
    public Double[][] getGrid() {
        return grid;
    }
}