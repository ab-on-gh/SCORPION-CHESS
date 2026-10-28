import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;

import java.util.Scanner;
import java.util.List;
import java.lang.Math;

public class Main {
    static Board board = new Board();

    // generate engine moves
    static Move moveGenerator() {
        List<Move> legal = board.legalMoves();
        int r = (int) (Math.random() * legal.size());
        Move enginemove = legal.get(r);
        return enginemove;
    }

    // validate user move
    static Move validateMove(String usermove) {
        List<Move> legal = board.legalMoves();
        for (Move m : legal) {

            String l = m.toString();

            if (l.equals(usermove)) {
                return m;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        gui chessGUI = new gui(board);
        chessGUI.setBoard();

        while (!board.isMated() && !board.isDraw()) { // loop only runs when the game is not over (duh)
            System.out.println("Enter a move (eg e2e4):");

            String usermove = input.next();
            Move validmove = validateMove(usermove);

            if (validmove == null) {
                input.nextLine();
                System.out.println("Invalid move! Press Enter to continue.");
                input.nextLine();
                continue;
            }

            try {
                board.doMove(validmove);
                Move enginemove = moveGenerator();
                board.doMove(enginemove);
                chessGUI.updateBoard();
            }

            catch (Exception e) {
                System.out.println("Invalid move! Press Enter to continue.");
                input.nextLine();
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
