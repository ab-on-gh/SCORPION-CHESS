import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;

import java.util.Scanner;
import java.util.List;
import java.lang.Math;

public class Main {
    static Board board = new Board();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        scorpionEngine engine = new scorpionEngine(board);
        gui chessGUI = new gui(board);
        moveHandler mover = new moveHandler(board, chessGUI, engine);

        chessGUI.setBoard();

        boolean userWhite = true;
        if (!userWhite) {
            mover.makeEngineMove();
        }

        while (!board.isMated() && !board.isDraw()) { // loop only runs when the game is not over (duh)

            System.out.println("Enter a move (eg e2e4):");

            String usermove = input.next();

            boolean legalMade = mover.handleTurns(usermove);

            if (!legalMade) {
                input.nextLine();
                System.out.println("Invalid move! Press Enter to continue.");
                input.nextLine();
                continue;
            }

        }

        if (board.isMated()) {
            System.out.println(board.getSideToMove() + " is mated!");
            chessGUI.closeBoard();
        } else {
            System.out.println("Draw");
            chessGUI.closeBoard();
        }

    }
}
