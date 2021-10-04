import Engine.*;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.JFrame;

public class Snake extends JFrame {

    public Snake() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        initUI();
    }

    private void initUI() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        add(new GameEngine());

        setResizable(false);
        pack();

        setTitle("Snake Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        JFrame ex;
        try {
            ex = new Snake();
            ex.setVisible(true);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }
}