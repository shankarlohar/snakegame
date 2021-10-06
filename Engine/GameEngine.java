// Declaring this as a package to be used in the main Snake.Java as a backend.
package Engine;

// The required imports are here
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import javax.sound.sampled.*;
import java.io.IOException;

// Declareing the claass for game engine
public class GameEngine extends JPanel implements ActionListener {

    // Initializing some variable to be used.

    private final int WINDOW_WIDTH = 800; // This is the width of the game pannel.
    private final int WINDOW_HEIGHT = 500; // This is the height of the game pannel.
    private final int GRID_SIZE = 50; // This is the grid size of the pannel to display things.
    private final int MAX_SNAKE_LENGTH = 900; // This is the maximum snake length.
    private final int DELAY = 200; // This is the Snake's speed... the less the value the faster it goes.

    private final int x[] = new int[MAX_SNAKE_LENGTH]; // Snake length in x-axis,
    private final int y[] = new int[MAX_SNAKE_LENGTH]; // Snake length in y-axis.

    private int SNAKE_BODY_COUNT; // This is the snake body count.
    private int apple_x; // X co-ordinate of Apple.
    private int apple_y; // Y co-ordinate of Apple.
    private int SCORE = 1; // The SCORE counting of the game.

    private boolean leftDirection = false; // Left direction control variable.
    private boolean rightDirection = true; // Right direction control variable.
    private boolean upDirection = false; // Up direction control variable.
    private boolean downDirection = false; // Down direction control variable.

    private boolean GAME_RUNNING_STATUS = true; // This is the game running status.

    private Timer timer; // This works to delay the snake speed or game.

    private Image SNAKE_BODY_IMAGE; // Snake body image variable.
    private Image APPLE_IMAGE; // Apple image variavle.
    private Image SNAKE_HEAD_IMAGE; // Snake head image variable.
    private Image GAME_OVER_IMAGE; // Game over image variable.

    private Clip BACKGROUND_MUSIC; // This is the song sound variable
    private Clip APPLE_EATEN_SOUND; // This is the eaten sound variable
    private Clip GAME_OVER_SOUND; // This is the game over sound variable
    private Clip POWER_UP_SOUND; // This is the power up sound variable variable

    // This is the GameEngin Constructor.
    public GameEngine() {
        playGame(); // calling the Start Game function.
    }

    // This function loads the image in the variables declared.
    private void loadImages() {

        ImageIcon iih = new ImageIcon("GameData/Snake/SnakeHeadUp.png");
        SNAKE_HEAD_IMAGE = iih.getImage();

        ImageIcon iid = new ImageIcon("GameData/Snake/SnakeTailUp.png");
        SNAKE_BODY_IMAGE = iid.getImage();

        ImageIcon iia = new ImageIcon("GameData/Food/SnakeFood.png");
        APPLE_IMAGE = iia.getImage();

        ImageIcon iio = new ImageIcon("GameData/Game/GameOver.png");
        GAME_OVER_IMAGE = iio.getImage();

    }

    // This function loads the sound clips in the variavles declared.
    private void loadSound() {

        File GameMusic = new File("GameData/Music/GameMusic.wav");
        AudioInputStream audioStream1;
        try {
            audioStream1 = AudioSystem.getAudioInputStream(GameMusic);
            BACKGROUND_MUSIC = AudioSystem.getClip();
            BACKGROUND_MUSIC.open(audioStream1);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
            e1.printStackTrace();
        }

        File AppleEaten = new File("GameData/Music/AppleEaten.wav");
        AudioInputStream audioStream2;
        try {
            audioStream2 = AudioSystem.getAudioInputStream(AppleEaten);
            APPLE_EATEN_SOUND = AudioSystem.getClip();
            APPLE_EATEN_SOUND.open(audioStream2);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
            e1.printStackTrace();
        }

        File GameOver = new File("GameData/Music/GameOver.wav");
        AudioInputStream audioStream3;
        try {
            audioStream3 = AudioSystem.getAudioInputStream(GameOver);
            GAME_OVER_SOUND = AudioSystem.getClip();
            GAME_OVER_SOUND.open(audioStream3);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
            e1.printStackTrace();
        }

        File PowerUp = new File("GameData/Music/PowerUp.wav");
        AudioInputStream audioStream4;
        try {
            audioStream4 = AudioSystem.getAudioInputStream(PowerUp);
            POWER_UP_SOUND = AudioSystem.getClip();
            POWER_UP_SOUND.open(audioStream4);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
            e1.printStackTrace();
        }

    }

    // This generates the apple in a random position.
    private void locateApple() {

        APPLE_EATEN_SOUND.setMicrosecondPosition(0);
        POWER_UP_SOUND.setMicrosecondPosition(0);
        int r = (int) (Math.random() * WINDOW_WIDTH / GRID_SIZE);
        apple_x = ((r * GRID_SIZE));

        r = (int) (Math.random() * WINDOW_HEIGHT / GRID_SIZE);
        apple_y = ((r * GRID_SIZE));
    }

    // This checks if the snake ate the apple or not.
    private void checkApple() {

        if ((x[0] == apple_x) && (y[0] == apple_y)) {

            APPLE_EATEN_SOUND.start();
            SCORE += 10; // Increasing the Scores.
            if (SCORE % 100 == 0) {
                POWER_UP_SOUND.start();
            }
            SNAKE_BODY_COUNT++; // Increasing snake length.
            locateApple(); // Calling a new apple.
        }
    }

    // This function initialize the game with Snake and apple.
    private void initGame() {

        SNAKE_BODY_COUNT = 3;

        for (int z = 0; z < SNAKE_BODY_COUNT; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

        BACKGROUND_MUSIC.start();
        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    // This class sets up the controls for the game.
    private class Controls extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }

    // This is how the snake moves in diffent directions.
    private void move() {

        for (int z = SNAKE_BODY_COUNT; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= GRID_SIZE;
        }

        if (rightDirection) {
            x[0] += GRID_SIZE;
        }

        if (upDirection) {
            y[0] -= GRID_SIZE;
        }

        if (downDirection) {
            y[0] += GRID_SIZE;
        }
    }

    // This function checks for collison if the snake clashes with itself or crosses
    // the boundaries of window.
    private void checkCollision() throws LineUnavailableException {

        for (int z = SNAKE_BODY_COUNT; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                GAME_RUNNING_STATUS = false;
            }
        }

        if (y[0] >= WINDOW_HEIGHT) {
            GAME_RUNNING_STATUS = false;
        }

        if (y[0] < 0) {
            GAME_RUNNING_STATUS = false;
        }

        if (x[0] >= WINDOW_WIDTH) {
            GAME_RUNNING_STATUS = false;
        }

        if (x[0] < 0) {
            GAME_RUNNING_STATUS = false;
        }

        if (!GAME_RUNNING_STATUS) {
            timer.stop();
            GAME_OVER_SOUND.start();
        }
    }

    // This is the game console with snake and apple.
    private void playGame() {

        addKeyListener(new Controls()); // Added controls to the keylisteners.
        setBackground(Color.black); // This makes the background of the pannel black
        setFocusable(true);

        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)); // This defines the Window size.
        loadImages(); // Calls the load image function.
        loadSound(); // Calls the load sound function.
        initGame(); // calls the initialize game function.
    }

    // This is an override method for the paint component for our graphics.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGraphics(g);
    }

    // This keeps a check of apple and collision and movement of the snake.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (GAME_RUNNING_STATUS) {
            try {
                checkApple();
                checkCollision();
                move();
                if (!BACKGROUND_MUSIC.isActive()) {
                    BACKGROUND_MUSIC.setMicrosecondPosition(0);
                }
            } catch (LineUnavailableException e1) {
                e1.printStackTrace();
            }
        }
        repaint();
    }

    // This is where we draw the Game components , snake and food.
    private void drawGraphics(Graphics g) {

        if (GAME_RUNNING_STATUS) {

            g.setColor(Color.white);
            g.setFont(new Font("Helvetica", Font.BOLD, 10));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("SCORE: " + SCORE, (WINDOW_WIDTH - metrics.stringWidth("SCORE: " + SCORE)) / 2,
                    g.getFont().getSize());

            g.drawImage(APPLE_IMAGE, apple_x, apple_y, this);

            for (int z = 0; z < SNAKE_BODY_COUNT; z++) {
                if (z == 0) {
                    g.drawImage(SNAKE_HEAD_IMAGE, x[z], y[z], this);
                } else {
                    g.drawImage(SNAKE_BODY_IMAGE, x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOverGraphics(g);
        }
    }

    private void gameOverGraphics(Graphics g) {

        Font small = new Font("Helvetica", Font.BOLD, 40);
        FontMetrics metr = getFontMetrics(small);
        String SCORE = String.valueOf(this.SCORE);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawImage(GAME_OVER_IMAGE, 0, 0, this);
        g.drawString("SCORE: " + SCORE, (WINDOW_WIDTH - metr.stringWidth("SCORE: " + SCORE)) / 2,
                g.getFont().getSize());
    }

}