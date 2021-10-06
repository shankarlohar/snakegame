import Engine.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Snake extends JFrame {

    public Snake() {
        initUI();
    }

    private void initUI() {

        add(new GameEngine());

        setResizable(false);
        pack();

        setTitle("Shankar's Snake");
        ImageIcon logo = new ImageIcon("GameData/Game/GameLogo.png");
        setIconImage(logo.getImage());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        JFrame ex;

        ex = new Snake();
        ex.setVisible(true);

    }
}