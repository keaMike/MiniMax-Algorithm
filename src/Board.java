class Board {

    private char[][] board = new char[5][5];
    private Player playerOne = new Player("X", 'X');
    private Player playerTwo = new Player("O", 'O');
    private int piecesPlaced = 0;

    public Board() {
        // On restart set pieces placed to zero
        piecesPlaced = 0;
    }

    char[][] getBoard() {
        return board;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public int getPiecesPlaced() {
        return piecesPlaced;
    }

    void printBoard() {

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                if(j % 2 == 0 && i % 2 == 0 && board[i][j] == 0) System.out.print(' ');
                if(j % 2 != 0 && i % 2 != 0) System.out.print('+');
                if(j % 2 != 0 && i % 2 == 0) System.out.print('|');
                if(j % 2 == 0 && i % 2 != 0) System.out.print('-');
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    boolean takeSpace(char playerPiece, int pos) {
        switch(pos) {
            case 1:
                return placePiece(playerPiece ,0,0);
            case 2:
                return placePiece(playerPiece, 0, 2);
            case 3:
                return placePiece(playerPiece, 0, 4);
            case 4:
                return placePiece(playerPiece, 2, 0);
            case 5:
                return placePiece(playerPiece, 2, 2);
            case 6:
                return placePiece(playerPiece, 2, 4);
            case 7:
                return placePiece(playerPiece, 4, 0);
            case 8:
                return placePiece(playerPiece, 4, 2);
            case 9:
                return placePiece(playerPiece, 4, 4);
        }
        return false;
    }

    private boolean placePiece(char playerPiece, int row, int col) {
        if(board[row][col] == 0) {
            board[row][col] = playerPiece;
            piecesPlaced++;
            return true;
        }
        else {
            System.out.println("Space is taken.. Try again");
            return false;
        }
    }

    void miniMaxMove() {
        int bestScore = -1000;
        int[] bestMove = new int[]{-1,-1};
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                // Is the spot available
                if(i % 2 == 0 && j % 2 == 0 && board[i][j] == 0) {
                    // Place piece and check for future score
                    board[i][j] = playerOne.getPiece();
                    piecesPlaced++;
                    int score = miniMax(board, false);
                    board[i][j] = 0;
                    piecesPlaced--;
                    if(score > bestScore) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestScore = score;
                    }
                }
            }
        }
        placePiece(playerOne.getPiece(), bestMove[0], bestMove[1]);
    }

    private int miniMax(char[][] board, boolean isMaximizing) {
        int result = hasWon(board);
        // If a player has won return score
        if(result != 0) {
            return result;
        }
        // Return zero if tie
        if(piecesPlaced == 9) return 0;

        if(isMaximizing) {
            int bestScore = -1000;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    // Is the spot available
                    if(i % 2 == 0 && j % 2 == 0 && board[i][j] == 0) {
                        // Place piece and run possibles with best score
                        board[i][j] = playerOne.getPiece();
                        piecesPlaced++;
                        bestScore = Math.max(bestScore, miniMax(board, false));
                        // Remove piece again
                        board[i][j] = 0;
                        piecesPlaced--;
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = 1000;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    // Is the spot available
                    if(i % 2 == 0 && j % 2 == 0 && board[i][j] == 0) {
                        // Place piece and run possibles with best score
                        board[i][j] = playerTwo.getPiece();
                        piecesPlaced++;
                        bestScore = Math.min(bestScore, miniMax(board, true));
                        // Remove piece again
                        board[i][j] = 0;
                        piecesPlaced--;
                    }
                }
            }
            return bestScore;
        }
    }

    int hasWon(char[][] board) {

        //Vertical
        for(int i = 0; i < 6; i += 2) {
            if(board[0][i] == 'X' && board[2][i] == 'X' && board[4][i] == 'X') return 1;
            if(board[0][i] == 'O' && board[2][i] == 'O' && board[4][i] == 'O') return -1;
        }
        //Horizontal
        for(int i = 0; i < 6; i += 2) {
            if (board[i][0] == 'X' && board[i][2] == 'X' && board[i][4] == 'X') return 1;
            if (board[i][0] == 'O' && board[i][2] == 'O' && board[i][4] == 'O') return -1;
        }
        //Diagonal
        if(board[0][0] == 'X' && board[2][2] == 'X' && board[4][4] == 'X') return 1;
        if(board[0][4] == 'X' && board[2][2] == 'X' && board[4][0] == 'X') return 1;

        if(board[0][0] == 'O' && board[2][2] == 'O' && board[4][4] == 'O') return -1;
        if(board[0][4] == 'O' && board[2][2] == 'O' && board[4][0] == 'O') return -1;
        return 0;
    }
}
