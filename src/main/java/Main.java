import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;
import java.util.Scanner;

// v1.0.0

public class Main {
    static Board board = new Board();
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        // board.loadFromFen("7k/5K2/8/8/8/8/6R1/4b w - - 0 1");
        // board.loadFromFen("4k3/P7/8/8/8/8/8/4K3 w - - 0 1");
        System.out.println("Play as white - 1 or black - 0");
        int choice;

        while (true) {
            try {
                choice = input.nextInt();
                if (choice != 0 && choice != 1) {
                    System.out.println("Invalid input. Play as white - 1 or black - 0.");
                    continue;
                } else {
                    break;
                }

            } catch (Exception e) {
                System.out.println("Invalid input! Play as white - 1 or black - 0.");
                input.nextLine();
            }

        }

        boolean userWhite = (choice == 1);
        scorpionEngine engine;

        if (userWhite) {
            engine = new scorpionEngine(board, Side.BLACK);
        } else {
            engine = new scorpionEngine(board, Side.WHITE);
        }

        moveHandler mover = new moveHandler(board, engine);
        gui chessGUI = new gui(board, mover);

        chessGUI.setBoard(userWhite);

        if (!userWhite) {
            mover.makeEngineMove();
        }

    }
}
