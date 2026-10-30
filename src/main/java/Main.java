import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;

public class Main {
    static Board board = new Board();

    public static void main(String[] args) {
        board.loadFromFen("7k/5K2/8/8/8/8/6R1/4b w - - 0 1");
        boolean userWhite = false;
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
