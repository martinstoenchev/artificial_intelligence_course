import java.util.HashSet;

public class Board {

    private final int BOARD_SIZE = 3;

    private int numberOfMoves;
    private final char[][] board;
    private char playersTurn;
    private char winner;
    private boolean gameOver;
    private HashSet<Integer> availableMoves;

    public Board() {
        this.board = new char[BOARD_SIZE][BOARD_SIZE];
        this.numberOfMoves = 0;
        this.availableMoves = new HashSet<>();
        this.playersTurn = 'X';
        this.gameOver = false;
        initialize();
    }

    public void printBoard() {
        for (int i = 0; i < this.BOARD_SIZE; i++) {
            System.out.println("+-----+-----+-----+");
            System.out.print("|");
            for (int j = 0; j < this.BOARD_SIZE; j++) {
                System.out.print("  " + this.board[i][j] + "  |");
            }
            System.out.println();
        }
        System.out.println("+-----+-----+-----+");
    }

    public void move(int index) {
        move(index / this.BOARD_SIZE, index % this.BOARD_SIZE);
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public boolean move(int x, int y) {

        if (this.board[x][y] == '_') {
            board[x][y] = playersTurn;
        } else {
            System.out.println("THIS CELL IS NOT EMPTY");
            System.out.println("CHOOSE ANOTHER ONE, PLEASE");
            return false;
        }

        numberOfMoves++;
        availableMoves.remove(x * this.BOARD_SIZE + y);

        if (!hasEmptyCells()) {
            this.winner = '_';
            this.gameOver = true;
        }

        checkRow(x);
        checkColumn(y);
        checkMainDiagonal(x, y);
        checkSecondaryDiagonal(x, y);

        playersTurn = (playersTurn == 'X') ? 'O' : 'X';
        return true;
    }

    public Board getCopy() {
        Board newBoard = new Board();
        for (int i = 0; i < this.BOARD_SIZE; i++) {
            for (int j = 0; j < this.BOARD_SIZE; j++) {
                newBoard.setAtIndex(i, j, this.board[i][j]);
            }
        }
        newBoard.playersTurn = this.playersTurn;
        newBoard.winner = this.winner;
        newBoard.availableMoves = new HashSet<>();
        newBoard.availableMoves.addAll(this.availableMoves);
        newBoard.numberOfMoves = this.numberOfMoves;
        newBoard.gameOver = this.gameOver;

        return newBoard;
    }

    public char getPlayersTurn() {
        return this.playersTurn;
    }

    public HashSet<Integer> getAvailableMoves() {
        return this.availableMoves;
    }

    public char getWinner() {
        if (!this.gameOver) {
            throw new IllegalStateException("THE GAME IS NOT OVER YET!");
        }
        return this.winner;
    }

    private void setAtIndex(int x, int y, char playersTurn) {
        this.board[x][y] = playersTurn;
    }

    private void initialize() {
        for (int i = 0; i < this.BOARD_SIZE; i++) {
            for (int j = 0; j < this.BOARD_SIZE; j++) {
                this.board[i][j] = '_';
            }
        }

        this.availableMoves.clear();

        for (int i = 0; i < this.BOARD_SIZE * this.BOARD_SIZE; i++) {
            this.availableMoves.add(i);
        }
    }

    private boolean hasEmptyCells() {
        return this.numberOfMoves < this.BOARD_SIZE * this.BOARD_SIZE;
    }

    private void checkRow(int row) {
        int i;
        for (i = 1; i < this.BOARD_SIZE; i++) {
            if (this.board[row][i] != this.board[row][i - 1]) {
                break;
            }
        }
        if (i == this.BOARD_SIZE) {
            this.winner = this.playersTurn;
            this.gameOver = true;
        }
    }

    private void checkColumn(int column) {
        int i;
        for (i = 1; i < this.BOARD_SIZE; i++) {
            if (this.board[i][column] != this.board[i - 1][column]) {
                break;
            }
        }
        if (i == this.BOARD_SIZE) {
            this.winner = this.playersTurn;
            this.gameOver = true;
        }
    }

    private void checkMainDiagonal(int row, int column) {
        if (row == column) {
            int i;
            for (i = 1; i < this.BOARD_SIZE; i++) {
                if (this.board[i][i] != this.board[i - 1][i - 1]) {
                    break;
                }
            }
            if (i == this.BOARD_SIZE) {
                this.winner = this.playersTurn;
                this.gameOver = true;
            }
        }
    }

    private void checkSecondaryDiagonal(int row, int column) {
        if (row == this.BOARD_SIZE - 1 - column) {
            int i;
            for (i = 1; i < this.BOARD_SIZE; i++) {
                if (this.board[i][this.BOARD_SIZE - 1 - i] != this.board[i - 1][this.BOARD_SIZE - i]) {
                    break;
                }
            }
            if (i == this.BOARD_SIZE) {
                this.winner = this.playersTurn;
                this.gameOver = true;
            }
        }
    }

}
