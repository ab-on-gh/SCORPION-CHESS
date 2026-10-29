import java.util.List;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;

public class moveHandler {
    final Board board;
    final gui chessGUI;
    final scorpionEngine engine;

    public moveHandler(Board board, gui chessGUI, scorpionEngine engine) {
        this.board = board;
        this.chessGUI = chessGUI;
        this.engine = engine;
    }

    public boolean handleTurns(String usermove) {
        boolean legalMade = makeUserMove(usermove); // true if a legal move has been made, false if no legal move has
                                                    // been made/attempted
        if (!legalMade) {
            return legalMade;
        } else {
            makeEngineMove();
            return legalMade;
        }

    }

    public void makeEngineMove() {
        Move enginemove = engine.moveGenerator();
        if (enginemove == null) {
            return; // if there are no legal moves avaliable (ie engine has been mated or drawn) do
                    // nothing
        }
        board.doMove(enginemove);
        chessGUI.updateBoard();
    }

    private boolean makeUserMove(String usermove) {
        Move validmove = validateMove(usermove);
        if (validmove == null) {
            return false;
        } else {
            board.doMove(validmove);
            chessGUI.updateBoard();
            return true;
        }
    }

    private Move validateMove(String usermove) {
        List<Move> legal = board.legalMoves();
        for (Move m : legal) {

            String l = m.toString();

            if (l.equals(usermove)) {
                return m;
            }
        }
        return null;
    }

}
