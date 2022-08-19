import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NQueens {

    int p;
    int N;
    int[] queens;
    int[] rows;
    int[] mainDiagonal;
    int[] secondDiagonal;
    String[][] resultBoard;
    long start;

    public NQueens(int N) {
        this.p = generateRandomInt(N);
        this.N = N;
        this.queens = new int[N];
        this.resultBoard = new String[N][N];
        start = System.currentTimeMillis();
    }

    private int generateRandomInt(int end) {
        Random rn = new Random();
        return rn.nextInt(end);
    }

    private void generateInitNQueens() {
        int row = p;
        int column = 0;
        int save = row + 1;

        if (row % 2 == 0) {
            while (column < this.N) {
                if (row + 2 < this.N) {
                    queens[column] = row + 2;
                    row +=2;
                } else {
                    if (save == 2) {
                        row = save;
                        save++;
                    } else {
                        row = 1;
                        save = 2;
                    }
                    queens[column] = row;
                }
                column++;
            }
            if (queens[0] == queens[this.N - 1]) {
                queens[this.N - 1] = 0;
            }
        } else {
            while (column < this.N) {
                if (row + 2 < this.N) {
                    queens[column] = row + 2;
                    row+=2;
                } else {
                    if (save == 1) {
                        row = save;
                        save++;
                    } else {
                        row = 0;
                        save = 1;
                    }
                    queens[column] = row;
                }
                column++;
            }
        }
        getCollisions();
    }

    private void fillResultBoard() {
        for (int i = 0; i < this.N; i++) {
            int Q = queens[i];
            for (int j = 0; j < this.N; j++) {
                if (j == Q) {
                    resultBoard[j][i] = "Q ";
                } else {
                    resultBoard[j][i] = "_ ";
                }
            }
        }
    }

    private void printSolution() {
        System.out.println(Arrays.toString(queens));
        fillResultBoard();
        for (int i = 0; i < this.N; i++) {
            for (int j = 0; j < this.N; j++) {
                System.out.print(resultBoard[i][j]);
            }
            System.out.println();
        }
    }

    private void getCollisions() {
        this.rows = new int[N];
        this.mainDiagonal = new int[2 * N - 1];
        this.secondDiagonal = new int[2 * N - 1];
        for (int i = 0; i < this.N; i++) {
            int row = queens[i];
            rows[row]++;
            mainDiagonal[i - row + this.N - 1]++;
            secondDiagonal[i + row]++;
        }
    }

    private boolean hasConflicts() {
        int result = 0;
        int x, y, z;

        for (int i = 0; i < 2 * N - 1; i++) {
            x = 0;
            if (i < N) {
                x = rows[i];
            }
            y = mainDiagonal[i];
            z = secondDiagonal[i];

            result += (x * (x - 1)) / 2;
            result += (y * (y - 1)) / 2;
            result += (z * (z - 1)) / 2;
        }
        return result > 0;
    }

    private int getColWithQueenWithMaxConflicts() {

        List<Integer> indexes = new ArrayList<>();
        int maxConflicts = 0;
        int maxIndex = -1;

        for (int i = 0; i < this.N; i++) {
            int value = queens[i];
            int conflicts = rows[value] - 1 + mainDiagonal[i - value + N - 1] - 1 + secondDiagonal[i + value] - 1;
            if (conflicts == maxConflicts) {
                indexes.add(i);
            } else if (conflicts > maxConflicts) {
                maxConflicts = conflicts;
                maxIndex = i;
                indexes.clear();
                indexes.add(maxIndex);
            }
        }
        int end = indexes.size();
        return maxConflicts == 0 ? -1 : indexes.get(generateRandomInt(end));
    }

    private int getRowWithMinConflicts(int col) {

        int oldRow = queens[col];
        int minConflicts = this.N + 1;
        int minIndex = -1;
        int conflicts;
        List<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < this.N; i++) {
            if (i != oldRow) {
                conflicts = rows[i] + mainDiagonal[col - i + this.N - 1] + secondDiagonal[col + i];
            } else {
                conflicts = rows[i] - 1 + mainDiagonal[col - i + this.N - 1] - 1 + secondDiagonal[col + i] - 1;
            }
            if (conflicts == minConflicts) {
                if (i != oldRow) {
                    indexes.add(i);
                }
            } else if (conflicts < minConflicts) {
                minConflicts = conflicts;
                minIndex = i;
                indexes.clear();
                indexes.add(minIndex);
            }
        }
        int end = indexes.size();
        return indexes.get(generateRandomInt(end));
    }

    public void solve() {
        generateInitNQueens();
        //initializeQueens();
        int k = 2;
        int iterations = 0;
        int col, row;

        while (iterations++ <= k * N) {
            col = getColWithQueenWithMaxConflicts();
            /*if (fightAll()) {
                System.out.println("Every queen is fighting with all of the others");
                return;
            }*/
            if (col == -1) {
                long duration = System.currentTimeMillis() - start;
                System.out.println("It took " + (double)duration/1000 + " seconds!");
                printSolution();
                return;
            }

            int oldRow = queens[col];
            row = getRowWithMinConflicts(col);
            queens[col] = row;
            mainDiagonal[col - row + this.N - 1]++;
            mainDiagonal[col - oldRow + this.N - 1]--;
            secondDiagonal[col + row]++;
            secondDiagonal[col + oldRow]--;
            rows[row]++;
            rows[oldRow]--;
        }
        if (hasConflicts()) {
            solve();
        }
    }

    private void initializeQueens() {
        for (int i = 0; i < this.N; i++) { // main diagonal
            this.queens[i] = i;
        }

        for (int i = 0; i < this.N; i++) { // secondary diagonal
            this.queens[i] = this.N - 1 - i;
        }

        getCollisions();
    }

    private boolean fightAll() {
        return mainDiagonal[this.N - 1] == this.N || secondDiagonal[this.N - 1] == this.N;
    }

    public void solve1() {
        initializeQueens();
        boolean fighting = fightAll();

        if (fighting) {
            System.out.println("Every queen is fighting with all of the others");
            //printSolution();
        } else {
            System.out.println("There are queens with no enemies");
        }
    }

    public static void main(String[] args) {

        int N = 10;
        NQueens nq = new NQueens(N);
        nq.solve1();

    }

}
