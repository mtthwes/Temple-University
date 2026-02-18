import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NQueen {
    private int size;

    public NQueen(int n) {
        this.size = n;
    }

    public List<List<String>> solveNQueens() {
        char[][] board = new char[size][];
        for (int i = 0; i < size; i++) {
            board[i] = new char[size];
            Arrays.fill(board[i], '.');
        }
        List<List<String>> solutions = new ArrayList<>();
        backtrack(board, 0, solutions);
        return solutions;
    }

    private void backtrack(char[][] board, int row, List<List<String>> solutions) {
        if (row == board.length) {
            solutions.add(constructSolution(board));
            return;
        }
        for (int col = 0; col < board.length; col++) {
            if (isSafe(board, row, col)) {
                board[row][col] = 'Q';
                backtrack(board, row + 1, solutions);
                board[row][col] = '.';
            }
        }
    }

    private boolean isSafe(char[][] board, int row, int col) {
        int n = board.length;
        // Check vertical column
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q') return false;
        }
        // Check upper-left diagonal
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') return false;
        }
        // Check upper-right diagonal
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 'Q') return false;
        }
        return true;
    }

    private List<String> constructSolution(char[][] board) {
        List<String> solution = new ArrayList<>();
        for (char[] row : board) {
            solution.add(new String(row));
        }
        return solution;
    }

    public static void main(String[] args) {
        int n = 8;
        NQueen solver = new NQueen(n);
        List<List<String>> solutions = solver.solveNQueens();

        System.out.println("Total Solutions: " + solutions.size());
        for (List<String> solution : solutions) {
            for (String row : solution) {
                System.out.println(row);
            }
            System.out.println();
        }
    }
}