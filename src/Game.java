import java.util.Scanner;

class Game {

    private Board board = new Board();
    private int turn = 0;

    private Scanner sc = new Scanner(System.in);

    private boolean gameEnded;

    void startGame(boolean singleplayer) {

        if(!singleplayer) {
            System.out.println(
                    "You chose multiplayer. Have fun\n" +
                            "Choose name for player one"
            );
            board.getPlayerOne().setName(sc.nextLine());
            System.out.println("Choose name for player two");
            board.getPlayerTwo().setName(sc.nextLine());
        } else {
            System.out.println(
                    "You chose singleplayer. Have fun\n" +
                            "Choose name for player one"
            );
            board.getPlayerOne().setName("Minimax");
            board.getPlayerTwo().setName(sc.nextLine());
        }

        board.printBoard();

        Player currentPlayer;

        while(!gameEnded) {

            currentPlayer = nextPlayer(turn);

            System.out.println(
                    currentPlayer.getName() + " it's your turn\n" +
                    "Place your piece with a number between 1-9"
            );
            if(singleplayer && turn % 2 == 0) {
                board.miniMaxMove();
                turn++;
            } else {
                // Take space method with the player piece and a pos 1-9 as parameters.
                // If space available, take space and turn to next player.
                int pos = sc.nextInt();
                sc.nextLine();
                if (board.takeSpace(currentPlayer.getPiece(), pos)) {
                    turn++;
                }
            }
            board.printBoard();

            // Checks all possible winning combinations on current board and return int based on player.
            if(
                    board.hasWon(board.getBoard()) == 10 ||
                    board.hasWon(board.getBoard()) == -10
            ) {
                System.out.println(currentPlayer.getName() + " YOU HAVE WON!\nWant to play again? y/n");
                gameEnded = true;
                // If no one has won, check if nine pieces have been placed, if so it is a draw.
            } else if(board.getPiecesPlaced() == 9) {
                System.out.println("GAME ENDED DRAW!\nWant to play again? y/n");
                gameEnded = true;
            }
        }

        // Runs game again on a new board.
        if(sc.nextLine().equalsIgnoreCase("y")) {
           restart(singleplayer);
        }
    }

    // Method to switch between players.
    private Player nextPlayer(int turn) {
        if(turn % 2 == 0) return board.getPlayerOne();
        else return board.getPlayerTwo();
    }

    private void restart(boolean singleplayer) {
        gameEnded = false;
        board = new Board();
        turn = 0;
        startGame(singleplayer);
    }
}
