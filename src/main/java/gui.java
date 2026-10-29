import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Side;

import javax.swing.*;
import java.awt.*;

public class gui extends JPanel {

    final Board board;

    final JFrame f = new JFrame("🦂 SCORPION CHESS 🦂");
    final JPanel p = new JPanel(new GridLayout(8, 8, 0, 0));
    final JButton[][] squares = new JButton[8][8];

    public gui(Board board) {
        this.board = board;
    }

    public void closeBoard() {
        f.dispose();
    }

    public void setBoard() {
        boolean userWhite = true;

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                boolean squarecolor;
                JButton b = new JButton();
                b.setOpaque(true);
                b.setBorderPainted(false);

                if ((c + r) % 2 == 0) {
                    b.setBackground(Color.WHITE);
                    squarecolor = true;
                } else {
                    b.setBackground(Color.GRAY);
                    squarecolor = false;
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
                    
                });
            }
        }

        updateBoard();

        f.add(p);
        f.setSize(600, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void updateBoard() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                JButton square = squares[r][c];
                String name = square.getName();
                Square sq = Square.valueOf(name);
                Piece piece = board.getPiece(sq);

                if (piece == Piece.NONE) {
                    square.setText("");
                } else {
                    square.setText(iconHandler(piece));
                }
                square.setFont(new Font("Arial", Font.PLAIN, 40));
            }
        }
    }

    public String iconHandler(Piece piece) {
        switch (piece) {
            case WHITE_KING:
                return "♔";
            case WHITE_QUEEN:
                return "♕";
            case WHITE_ROOK:
                return "♖";
            case WHITE_BISHOP:
                return "♗";
            case WHITE_KNIGHT:
                return "♘";
            case WHITE_PAWN:
                return "♙";

            case BLACK_KING:
                return "♚";
            case BLACK_QUEEN:
                return "♛";
            case BLACK_ROOK:
                return "♜";
            case BLACK_BISHOP:
                return "♝";
            case BLACK_KNIGHT:
                return "♞";
            case BLACK_PAWN:
                return "♟";

            default:
                return "";
        }
    }

}
