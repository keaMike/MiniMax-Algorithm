import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new Menu().menu();
    }
    public static class Menu {

        private Game game = new Game();
        private Scanner sc = new Scanner(System.in);
        private boolean singleplayer;

        public void menu() {
            do {
                System.out.println(
                        "Welcome to Tic Tac Toe!\n" +
                                "(1) Play multiplayer\n" +
                                "(2) Play against Minimax\n" +
                                "(3) Quit"
                );
                String choice = sc.nextLine();
                switch (choice) {
                    case "1":
                        game.startGame(singleplayer);
                        break;
                    case "2":
                        singleplayer = true;
                        game.startGame(singleplayer);
                        break;
                    case "3":
                        System.exit(1);
                        break;
                    default:
                        System.out.println("Invalid choice.. Try again");
                        break;
                }
            } while (true);
        }
    }
}
