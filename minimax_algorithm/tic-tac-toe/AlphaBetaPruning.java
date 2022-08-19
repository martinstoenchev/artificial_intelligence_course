import java.util.Scanner;

public class AlphaBetaPruning {

    private final int MAX_DEPTH = 10;
    private Board board;

    public AlphaBetaPruning() {
        this.board = new Board();
    }

    public void run(int playersTurn) {
        int row;
        int column;
        if (playersTurn == 2) {
            this.board.move(0);
            this.board.printBoard();
            playersTurn = 1;
        }

        while (!this.board.isGameOver()) {
            if (playersTurn == 1) {
                boolean successfullyMoved;
                do {
                    System.out.println("Select your row - a number between 1 and 3:");
                    Scanner sc = new Scanner(System.in);
                    row = sc.nextInt();
                    System.out.println("Select your column - a number between 1 and 3:");
                    column = sc.nextInt();
                    successfullyMoved = this.board.move(row - 1, column - 1);
                    playersTurn = 2;
                } while (!successfullyMoved);

            } else { // it's computer's turn
                alphaBetaPruning(this.board.getPlayersTurn(), this.board, 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
                playersTurn = 1;
                this.board.printBoard();
            }
        }

        System.out.println("THE GAME IS OVER");
        if (this.board.getWinner() == '_') {
            System.out.println("THERE IS NOT A WINNER");
        } else {
            System.out.println("THE WINNER IS ***" + this.board.getWinner() + "***");
        }
    }

    private int alphaBetaPruning(char playersTurn, Board board, int depth, double alpha, double beta) {
        if (depth++ == this.MAX_DEPTH || board.isGameOver()) {
            return score(playersTurn, board, depth);
        }

        if (board.getPlayersTurn() == playersTurn) {
            return getMax(playersTurn, board, depth, alpha, beta);
        } else {
            return getMin(playersTurn, board, depth, alpha, beta);
        }
    }

    private int getMax(char playersTurn, Board board, int depth, double alpha, double beta) {
        int indexOfBestMove = -1;
        for (Integer move : board.getAvailableMoves()) {
            Board newBoard = board.getCopy();
            newBoard.move(move);
            int score = alphaBetaPruning(playersTurn, newBoard, depth, alpha, beta);
            if (score > alpha) {
                alpha = score;
                indexOfBestMove = move;
            }
            if (alpha >= beta) {
                break;
            }
        }
        if (indexOfBestMove != -1) {
            board.move(indexOfBestMove);
        }
        return (int)alpha;
    }

    private int getMin(char playersTurn, Board board, int depth, double alpha, double beta) {
        int indexOfBestMove = -1;
        for (Integer move : board.getAvailableMoves()) {
            Board newBoard = board.getCopy();
            newBoard.move(move);
            int score = alphaBetaPruning(playersTurn,newBoard, depth, alpha, beta);
            if (score < beta) {
                beta = score;
                indexOfBestMove = move;
            }
            if (alpha >= beta) {
                break;
            }
        }

        if (indexOfBestMove != -1) {
            board.move(indexOfBestMove);
        }
        return (int)beta;
    }

    private int score(char player, Board board, int depth) {

        char opponent = (player == 'X') ? 'O' : 'X';

        if (board.isGameOver() && board.getWinner() == player) {
            return 10 - depth;
        } else if (board.isGameOver() && board.getWinner() == opponent) {
            return depth - 10;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Who is first?");
        System.out.println("You (1) or the Computer(2):");
        int firstPlayer = sc.nextInt();
        AlphaBetaPruning solve = new AlphaBetaPruning();
        solve.run(firstPlayer);

    }

}
