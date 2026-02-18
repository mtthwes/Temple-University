import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Person extends Creature {

    private int monsterRow = -1;
    private int monsterCol = -1;
    private Object[][] field;
    private boolean isHidden = false;
    private final Random randomGen = new Random();

    public Person(Model model, int row, int column) {
        super(model, row, column);
        loadField(model);
    }


    private void loadField(Model model) {
        try {
            Field fieldInstance = Model.class.getDeclaredField("field");
            fieldInstance.setAccessible(true);
            field = (Object[][]) fieldInstance.get(model);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    private void findMonster() {
        monsterRow = -1;
        monsterCol = -1;
        for (int r = 0; r < field.length; r++) {
            for (int c = 0; c < field[r].length; c++) {
                if (field[r][c] instanceof Monster) {
                    monsterRow = r;
                    monsterCol = c;
                    return;
                }
            }
        }
    }


    private boolean monsterIsVisible() {
        for (int d = Model.MIN_DIRECTION; d <= Model.MAX_DIRECTION; d++) {
            if (look(d) == Model.MONSTER) {
                return true;
            }
        }
        return false;
    }


    @Override
    int decideMove() {
        findMonster();

        if (monsterRow == -1 || monsterCol == -1) {
            return Model.STAY;
        }

        int[][] monsterDistances = computeDistances(monsterRow, monsterCol);

        int distanceToMonster = manhattanDistance(row, column, monsterRow, monsterCol);
        if (!monsterIsVisible() && distanceToMonster > 3) {
            if (randomGen.nextInt(10) != 0) {
                isHidden = true;
                return Model.STAY;
            }
        }
        isHidden = false;

        return pickEscapeMove(monsterDistances);
    }


    private int pickEscapeMove(int[][] monsterDistances) {
        List<Integer> goodDirs = new ArrayList<>();
        int bestDistance = -1;

        for (int d = Model.MIN_DIRECTION; d <= Model.MAX_DIRECTION; d++) {
            if (!canMove(d)) {
                continue;
            }

            int newRow = row + Model.rowChange(d);
            int newCol = column + Model.columnChange(d);
            if (!validSpot(newRow, newCol)) {
                continue;
            }

            boolean inLOS = monsterLineOfSight(monsterRow, monsterCol, newRow, newCol);
            int dist = monsterDistances[newRow][newCol];

            if (dist == -1 && !inLOS) {
                return d;
            }

            if (!inLOS) {
                if (dist > bestDistance) {
                    bestDistance = dist;
                    goodDirs.clear();
                    goodDirs.add(d);
                } else if (dist == bestDistance) {
                    goodDirs.add(d);
                }
            }
        }

        if (!goodDirs.isEmpty()) {
            return goodDirs.get(randomGen.nextInt(goodDirs.size()));
        }

        goodDirs.clear();
        bestDistance = -1;
        for (int d = Model.MIN_DIRECTION; d <= Model.MAX_DIRECTION; d++) {
            if (!canMove(d)) {
                continue;
            }

            int newRow = row + Model.rowChange(d);
            int newCol = column + Model.columnChange(d);
            if (!validSpot(newRow, newCol)) {
                continue;
            }

            int dist = monsterDistances[newRow][newCol];
            if (dist == -1) {
                return d;
            }

            if (dist > bestDistance) {
                bestDistance = dist;
                goodDirs.clear();
                goodDirs.add(d);
            } else if (dist == bestDistance) {
                goodDirs.add(d);
            }
        }

        if (goodDirs.isEmpty()) {
            return Model.STAY;
        }

        return goodDirs.get(randomGen.nextInt(goodDirs.size()));
    }


    private int[][] computeDistances(int startRow, int startCol) {
        int rows = field.length;
        int cols = field[0].length;
        int[][] dist = new int[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                dist[r][c] = -1;
            }
        }

        if (!validSpot(startRow, startCol) || (field[startRow][startCol] instanceof Bush)) {
            return dist;
        }

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] { startRow, startCol });
        dist[startRow][startCol] = 0;

        int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
        int[] dc = {0, 1, 1, 1, 0, -1, -1, -1};

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int r = cell[0];
            int c = cell[1];
            int currentDist = dist[r][c];

            for (int i = 0; i < 8; i++) {
                int rr = r + dr[i];
                int cc = c + dc[i];
                if (validSpot(rr, cc) && !(field[rr][cc] instanceof Bush) && dist[rr][cc] == -1) {
                    dist[rr][cc] = currentDist + 1;
                    queue.offer(new int[] { rr, cc });
                }
            }
        }
        return dist;
    }

    private boolean monsterLineOfSight(int mR, int mC, int pR, int pC) {
        if (!validSpot(mR, mC) || field[mR][mC] instanceof Bush) {
            return false;
        }
        for (int d = Model.MIN_DIRECTION; d <= Model.MAX_DIRECTION; d++) {
            int rr = mR;
            int cc = mC;
            while (validSpot(rr, cc)) {
                if (field[rr][cc] instanceof Bush) {
                    break;
                }
                if (rr == pR && cc == pC) {
                    return true;
                }
                rr += Model.rowChange(d);
                cc += Model.columnChange(d);
            }
        }
        return false;
    }


    private boolean validSpot(int r, int c) {
        return (r >= 0 && r < field.length && c >= 0 && c < field[0].length);
    }


    private int manhattanDistance(int r1, int c1, int r2, int c2) {
        return Math.abs(r1 - r2) + Math.abs(c1 - c2);
    }
}
