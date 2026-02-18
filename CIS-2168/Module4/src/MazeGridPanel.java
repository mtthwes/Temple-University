import javax.swing.*;
import java.awt.*;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
import java.awt.Color;

public class MazeGridPanel extends JPanel {
    private int rows;
    private int cols;
    private Cell[][] maze;

    public void generateMaze() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                if (row == 0 && col == 0) {
                    continue;
                } else if (row == 0) {
                    maze[row][col].westWall = false;
                    maze[row][col - 1].eastWall = false;
                } else if (col == 0) {
                    maze[row][col].northWall = false;
                    maze[row - 1][col].southWall = false;
                } else {
                    boolean north = Math.random() < 0.5;
                    if (north) {
                        maze[row][col].northWall = false;
                        maze[row - 1][col].southWall = false;
                    } else {
                        maze[row][col].westWall = false;
                        maze[row][col - 1].eastWall = false;
                    }
                    maze[row][col].repaint();
                }
            }
        }

        this.repaint();
    }

    public void generateMazeByDFS() {
        boolean[][] visited = new boolean[rows][cols];
        Stack<Cell> stack = new Stack<>();

        Cell start = maze[0][0];
        visited[0][0] = true;
        stack.push(start);

        while (!stack.isEmpty()) {
            Cell current = stack.peek();
            int row = current.row;
            int col = current.col;

            java.util.List<Cell> neighbors = new java.util.ArrayList<>();

            if (row > 0 && !visited[row - 1][col]) {
                neighbors.add(maze[row - 1][col]);
            }
            if (row < rows - 1 && !visited[row + 1][col]) {
                neighbors.add(maze[row + 1][col]);
            }
            if (col > 0 && !visited[row][col - 1]) {
                neighbors.add(maze[row][col - 1]);
            }
            if (col < cols - 1 && !visited[row][col + 1]) {
                neighbors.add(maze[row][col + 1]);
            }

            if (!neighbors.isEmpty()) {
                int randomIndex = (int) (Math.random() * neighbors.size());
                Cell neighbor = neighbors.get(randomIndex);

                if (neighbor.row == row - 1 && neighbor.col == col) {
                    // Neighbor is to the north
                    current.northWall = false;
                    neighbor.southWall = false;
                } else if (neighbor.row == row + 1 && neighbor.col == col) {
                    // Neighbor is to the south
                    current.southWall = false;
                    neighbor.northWall = false;
                } else if (neighbor.row == row && neighbor.col == col - 1) {
                    // Neighbor is to the west
                    current.westWall = false;
                    neighbor.eastWall = false;
                } else if (neighbor.row == row && neighbor.col == col + 1) {
                    // Neighbor is to the east
                    current.eastWall = false;
                    neighbor.westWall = false;
                }

                visited[neighbor.row][neighbor.col] = true;
                stack.push(neighbor);
            } else {
                stack.pop();
            }

        }
        this.repaint();
    }

    public boolean visited(int row, int col) {
        Cell c = maze[row][col];
        Color status = c.getBackground();
        if (status.equals(Color.WHITE) || status.equals(Color.RED)) {
            return false;
        }

        return true;
    }

    public void solveMaze() {
        Stack<Cell> stack = new Stack<>();
        Cell start = maze[0][0];
        Cell finish = maze[rows - 1][cols - 1];

        start.setBackground(Color.YELLOW);
        finish.setBackground(Color.RED);

        stack.push(start);

        while (!stack.isEmpty()) {
            Cell current = stack.peek();

            if (current == finish) {
                break;
            }

            int row = current.row;
            int col = current.col;
            boolean moved = false;

            // Try to go north.
            if (row > 0 && !current.northWall && !visited(row - 1, col)) {
                Cell neighbor = maze[row - 1][col];
                neighbor.setBackground(Color.GREEN); // mark neighbor as visited
                stack.push(neighbor);
                moved = true;
            }
            // Else try to go south.
            else if (row < rows - 1 && !current.southWall && !visited(row + 1, col)) {
                Cell neighbor = maze[row + 1][col];
                neighbor.setBackground(Color.GREEN);
                stack.push(neighbor);
                moved = true;
            }
            // Else try to go east.
            else if (col < cols - 1 && !current.eastWall && !visited(row, col + 1)) {
                Cell neighbor = maze[row][col + 1];
                neighbor.setBackground(Color.GREEN);
                stack.push(neighbor);
                moved = true;
            }
            // Else try to go west.
            else if (col > 0 && !current.westWall && !visited(row, col - 1)) {
                Cell neighbor = maze[row][col - 1];
                neighbor.setBackground(Color.GREEN);
                stack.push(neighbor);
                moved = true;
            }

            if (!moved) {
                current.setBackground(Color.LIGHT_GRAY); // mark current as dead-end
                stack.pop();
            }

            start.setBackground(Color.YELLOW);
            finish.setBackground(Color.RED);
        }

        repaint();
    }


    public void solveMazeBFS() {
        Queue<Cell> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
        Cell[][] predecessor = new Cell[rows][cols];

        Cell start = maze[0][0];
        Cell finish = maze[rows - 1][cols - 1];

        visited[0][0] = true;
        start.setBackground(Color.GREEN);
        queue.offer(start);

        int[] dRow = {-1, 1, 0, 0};
        int[] dCol = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            int row = current.row;
            int col = current.col;

            if (current == finish) {
                break;
            }

            for (int i = 0; i < 4; i++) {
                int newRow = row + dRow[i];
                int newCol = col + dCol[i];
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && !visited[newRow][newCol]) {
                    if (i == 0 && current.northWall) continue;
                    if (i == 1 && current.southWall) continue;
                    if (i == 2 && current.westWall)  continue;
                    if (i == 3 && current.eastWall)  continue;

                    visited[newRow][newCol] = true;
                    predecessor[newRow][newCol] = current;
                    maze[newRow][newCol].setBackground(Color.LIGHT_GRAY);
                    queue.offer(maze[newRow][newCol]);
                }
            }
        }

        Cell curr = finish;
        while (curr != null) {
            curr.setBackground(Color.GREEN);
            if (curr == start)
                break;
            curr = predecessor[curr.row][curr.col];
        }

        start.setBackground(Color.YELLOW);
        finish.setBackground(Color.RED);

        repaint();
    }

    public MazeGridPanel(int rows, int cols) {
        this.setPreferredSize(new Dimension(800, 800));
        this.rows = rows;
        this.cols = cols;
        this.setLayout(new GridLayout(rows, cols));
        this.maze = new Cell[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                maze[row][col] = new Cell(row, col);
                this.add(maze[row][col]);
            }
        }

        //this.generateMaze();
        this.generateMazeByDFS();

        this.solveMaze();
        //this.solveMazeBFS();

    }
}
