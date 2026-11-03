import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.lang.Math;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;

// v1.0.0

public class scorpionEngine {

    final Board board;
    final Side side;

    public scorpionEngine(Board board, Side side) {
        this.board = board;
        this.side = side;
    }

    public Random random = new Random();

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
            boolean coinflip = random.nextBoolean();

            board.doMove(m);
            int score = minimax(4, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
            board.undoMove();

            if (score > best) { // loops thru all the scores given some legal move has been made, store the best
                                // move into e
                best = score;
                e = m;
            } else if (score == best && coinflip) { // if two moves are equal, flip a coin
                e = m;
            }
        }
        return e; // return the best move
    }

    private int minimax(int depth, int alpha, int beta, boolean minimizing) {
        if (board.isMated() || depth == 0) {
            return evalmove(depth);
        }

        List<Move> legal = board.legalMoves();
        int best = Integer.MIN_VALUE;
        int worst = Integer.MAX_VALUE;

        if (minimizing) {
            for (Move m : legal) {

                board.doMove(m);
                int score = minimax(depth - 1, alpha, beta, false); // flips to maximizing
                board.undoMove();

                worst = Math.min(worst, score); // loops thru all the scores given some legal move has been made, store
                                                // the worst outcome for the engine. (improved from previous if
                                                // statement--less writing)

                beta = Math.min(beta, worst);

                if (alpha >= beta) {
                    break;
                }

            }
            return worst;

        } else { // if maximizing
            for (Move m : legal) {

                board.doMove(m);
                int score = minimax(depth - 1, alpha, beta, true); // flips to minimizing
                board.undoMove();

                best = Math.max(best, score); // store best outcome for engine

                alpha = Math.max(alpha, best);

                if (alpha >= beta) {
                    break;
                }

            }
            return best;
        }
    }

    private int evalmove(int depth) {
        if (board.isMated() && board.getSideToMove() == side) {
            return (-10000 - depth);
        } else if (board.isMated()) {
            return (10000 + depth);
        } else {
            int adv = pieceAdv() + positionalAdv();
            return adv;
        }

    }

    private int positionalAdv() {

        int adv = 0;
        int moveCount = board.getMoveCounter();
        if (moveCount < 15) {
            // logic for opening (generally opening lasts 15 moves)
            for (Square s : Square.values()) {
                Piece p = board.getPiece(s);
                if (p == Piece.NONE) {
                    continue;
                } else {
                    adv += centerBonus(s, p);
                }
            }
        }

        return adv;
    }

    private int centerBonus(Square s, Piece p) {
        // engine pieces get a bonus for being in the center, opponent pieces reduce
        // engine's bonus
        int adv = 0;
        if (pieceValues.get(p) != null && pieceValues.get(p) >= 1 && pieceValues.get(p) < 5) {
            int file = s.getFile().ordinal();
            int rank = s.getRank().ordinal();
            if (p.getPieceSide() == side) {
                adv = centerBonus[file][rank];
            } else {
                adv -= centerBonus[file][rank];
            }
        }
        return adv;
    }

    private int pieceAdv() {
        int adv = 0;

        for (Piece p : pieceValues.keySet()) {
            if (p.getPieceSide() == side) {
                adv += pieceValues.get(p) * Long.bitCount(board.getBitboard(p));
                // Long.bitCount(board.getBitboard(p)) returns the number of pieces of type p.
                // eg. 3 pawns and a knight for the engine will add (1+1+1+3)=6 to its advantage
            } else {
                adv -= pieceValues.get(p) * Long.bitCount(board.getBitboard(p));
                // eg. 3 pawns and a knight for the opponent will subtract 6 from engine's
                // advantage.
            }
        }

        return adv;
    }

    private static int[][] centerBonus = {
            { 0, 1, 2, 3, 3, 2, 1, 0 },
            { 1, 2, 3, 4, 4, 3, 2, 1 },
            { 2, 3, 4, 5, 5, 4, 3, 2 },
            { 2, 6, 8, 10, 10, 8, 6, 2 },
            { 2, 6, 8, 10, 10, 8, 6, 2 },
            { 2, 3, 4, 5, 5, 4, 3, 2 },
            { 1, 2, 3, 4, 4, 3, 2, 1 },
            { 0, 1, 2, 3, 3, 2, 1, 0 } };

    private static Map<Piece, Integer> pieceValues = Map.of(
            Piece.WHITE_PAWN, 10,
            Piece.BLACK_PAWN, 10,

            Piece.WHITE_KNIGHT, 30,
            Piece.BLACK_KNIGHT, 30,

            // bishops are slightly more valuable than knights
            Piece.WHITE_BISHOP, 35,
            Piece.BLACK_BISHOP, 35,

            Piece.WHITE_ROOK, 50,
            Piece.BLACK_ROOK, 50,

            Piece.WHITE_QUEEN, 90,
            Piece.BLACK_QUEEN, 90);

    private int randomMove(List<Move> legal) {
        return (int) (Math.random() * legal.size());
    }
}
