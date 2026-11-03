import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;
import javax.swing.*;
import java.awt.*;

// v1.0.0

public class gui extends JPanel {

    final Board board;
    final moveHandler mover;

    final JFrame f = new JFrame("🦂 SCORPION CHESS 🦂");
    final JPanel p = new JPanel(new GridLayout(8, 8, 0, 0));
    final JButton[][] squares = new JButton[8][8];

    public gui(Board board, moveHandler mover) {
        this.board = board;
        this.mover = mover;
    }

    public void closeBoard() {
        f.dispose();
    }

    private static JButton selected = null;
    private boolean[][] squareWhite = new boolean[8][8];

    public void setBoard(boolean userWhite) {

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                JButton b = new JButton();
                b.setOpaque(true);
                b.setBorderPainted(false);

                if ((c + r) % 2 == 0) {
                    b.setBackground(Color.WHITE);
                    squareWhite[r][c] = true;
                } else {
                    b.setBackground(Color.GRAY);
                    squareWhite[r][c] = false;
                }

                int num;
                char let;
                if (userWhite) {
                    num = 8 - r;
                    let = (char) ('A' + c);
                } else {
                    num = r + 1;
                    let = (char) ('A' + 7 - c);
                }
                String name = "" + let + num;

                b.setName(name);

                squares[r][c] = b;
                p.add(b);

                b.addActionListener(e -> {
                    if (selected == null) {
                        selected = b;
                    } else {
                        selected = null;
                    }
                    mover.handleClicks(name);
                    updateBoard();

                    checkGameOver();
                });
            }
        }

        updateBoard();

        f.add(p);
        f.setSize(600, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    private void checkGameOver() {
        if (board.isMated()) {
            updateBoard();
            System.out.println(board.getSideToMove() + " is mated!");
            closeBoard();
        } else if (board.isDraw()) {
            updateBoard();
            System.out.println("Draw");
            closeBoard();
        }
    }

    public void updateBoard() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                JButton square = squares[r][c];
                String name = square.getName();
                Square sq = Square.valueOf(name);
                Piece piece = board.getPiece(sq);
                String pieceIcon = "/images/" + piece.toString() + ".png";

                if (piece != Piece.NONE) {
                    ImageIcon unscaled = new ImageIcon(getClass().getResource(pieceIcon));
                    Image scaled = unscaled.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                    // scales the images cause they're too big otherwise
                    ImageIcon icon = new ImageIcon(scaled);

                    square.setIcon(icon);
                } else {
                    square.setIcon(null);
                }
                if (square == selected) {
                    square.setBackground(Color.ORANGE);
                } else if (squareWhite[r][c]) {
                    square.setBackground(Color.WHITE);
                } else {
                    square.setBackground(Color.GRAY);
                }
            }
        }
    }
}
