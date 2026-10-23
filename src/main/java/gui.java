import com.github.bhlangonijr.chesslib.Board;
import javax.swing.*;
import java.awt.*;

public class gui extends JPanel {

    final Board board;
    final JFrame f = new JFrame("GRID");
    final JPanel p = new JPanel(new GridLayout(8, 8, 0, 0));
    final JButton[][] squares = new JButton[8][8];

    public gui(board){
        this.board = board;
    }

    public void set_board() {
        boolean white = true;

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                JButton b = new JButton();
                if ((c + r) % 2 == 0) {
                    b.setBackground(Color.WHITE);
                } else {
                    b.setBackground(Color.GRAY);
                }
                b.setOpaque(true);
                b.setBorderPainted(false);
                squares[r][c] = b;

                p.add(b);
                int num;
                char let;
                if (white) {
                    num = 8 - r;
                    let = (char) ('a' + c);
                } else {
                    num = r + 1;
                    let = (char) ('a' + 7 - c);
                }
                String imgName = getPieceImage(let, num);

                b.setIcon(loadPiece(imgName));

                b.addActionListener(e -> {
                    System.out.println("" + let + num);
                });
            }
        }

        f.add(p);

        f.setSize(400, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

}
