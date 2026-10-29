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
        moveHandler mover = new moveHandler(board, engine);
        gui chessGUI = new gui(board, mover);

        boolean userWhite = true;

        chessGUI.setBoard(userWhite);

        if (!userWhite) {
            mover.makeEngineMove();
        }

    }
}
