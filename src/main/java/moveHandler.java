import java.util.List;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;

public class moveHandler {
    final Board board;
    final scorpionEngine engine;
    String from = null;

    public moveHandler(Board board, scorpionEngine engine) {
        this.board = board;
        this.engine = engine;
    }

    public void handleClicks(String selected) {
        if (from == null) { // if we click a square for the first time remember it
            from = selected;
            return;
        }

        String usermove = from + selected; // the user move is from the first square to the second selected square
        System.out.println(usermove);

        handleTurns(usermove); // make the user's move
        from = null; // reset the selection
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
    }

    private boolean makeUserMove(String usermove) {
        Move validmove = validateMove(usermove);
        if (validmove == null) {
            System.out.println("INVALID MOVE REQUESTED");
            return false;
        } else {
            board.doMove(validmove);
            return true;
        }
    }

    private Move validateMove(String usermove) {
        List<Move> legal = board.legalMoves();
        for (Move m : legal) {

            String l = m.toString();

            if (l.equalsIgnoreCase(usermove)) { // this was bugging out earlier cause capital lowercase whatever so we
                                                // ignore the capitalization
                return m;
            }
        }
        return null;
    }

}
