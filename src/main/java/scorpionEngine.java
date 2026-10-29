import java.util.List;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;

public class scorpionEngine {
    final Board board;

    public scorpionEngine(Board board) {
        this.board = board;
    }

    public Move moveGenerator() {
        List<Move> legal = board.legalMoves();
        if (legal.size() == 0) {
            return null;
        }
        int r = (int) (Math.random() * legal.size());
        Move enginemove = legal.get(r);
        return enginemove;
    }
}
