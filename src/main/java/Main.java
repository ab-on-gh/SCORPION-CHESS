import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;

import main.java.gui;

import java.util.Scanner;
import java.util.List;
import java.lang.Math;

public class Main {
    static Board board = new Board();

    // generate engine moves
    static Move moveGenerator() {
        List<Move> legal = board.legalMoves();
        int r = (int) (Math.random() * legal.size());
        System.out.println(r);
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
        chessGUI.set_board();

        // System.out.println("What side? 1- white 2-black");
        // int side = input.nextInt();

        while (true) {
            // Clear terminal screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println(board.toString());
            if (board.isMated() || board.isStaleMate() || board.isDraw()) {
                System.out.println("GAME OVER");
                break;
            }

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
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println(board.toString());
                Move enginemove = moveGenerator();
                board.doMove(enginemove);

            }

            catch (Exception e) {
                System.out.println("Invalid move! Press Enter to continue.");
                input.nextLine();
            }

        }
    }
}
