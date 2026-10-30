import java.util.ArrayList;
import java.util.List;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;

public class scorpionEngine {

    final Board board;
    final Side side;

    public scorpionEngine(Board board, Side side) {
        this.board = board;
        this.side = side;
    }

    public Move moveGenerator() {
        List<Move> legal = board.legalMoves();
        if (legal.size() == 0) {
            return null;
        }

        Move enginemove = getBest(legal);

        return enginemove;
    }

    private Move getBest(List<Move> legal) {
        int best = Integer.MIN_VALUE;
        Move e = null;
        for (Move m : legal) {
            board.doMove(m);
            int score = minimax(4, true);
            board.undoMove();

            if (score > best) { // loops thru all the scores given some legal move has been made, store the best
                                // move into e
                best = score;
                e = m;
            }
        }
        return e; // return the best move
    }

    private int minimax(int depth, boolean minimizing) {
        if (board.isMated() || depth == 0) {
            return evalmove(depth);
        }

        List<Move> legal = board.legalMoves();
        int best = Integer.MIN_VALUE;
        int worst = Integer.MAX_VALUE;

        if (minimizing) {
            for (Move m : legal) {

                board.doMove(m);
                int score = minimax(depth - 1, false); // flips to maximizing
                board.undoMove();

                if (score < worst) { // loops thru all the scores given some legal move has been made, store the
                                     // worst outcome for the engine
                    worst = score;
                }
            }
            return worst;

        } else { // if maximizing
            for (Move m : legal) {

                board.doMove(m);
                int score = minimax(depth - 1, true); // flips to minimizing
                board.undoMove();

                if (score > best) { // loops thru all the scores given some legal move has been made, store the
                                    // best outcome for the engine
                    best = score;
                }
            }
            return best;
        }
    }

    private int evalmove(int depth) {
        if (board.isMated() && board.getSideToMove() == side) {
            return (depth - 10000);
        } else if (board.isMated()) {
            return (depth + 10000);
        } else {
            return 0;
        }

    }

    private int randomMove(List<Move> legal) {
        return (int) (Math.random() * legal.size());
    }
}
