import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Gameplay extends JPanel implements KeyListener, ActionListener{

    private int[] snakeXlength = new int[750];
    private int[] snakeYlenght = new int[750];

    String player_name = "";

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    private ImageIcon rightmouth;
    private ImageIcon upmouth;
    private ImageIcon downmouth;
    private ImageIcon leftmouth;

    private int lenghtofsnake = 3;

    private Timer timer;
    private int delay = 150;
    private ImageIcon snakeimage;
    // позиции для рандома
    private int [] potionXpose = {25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400,
    425, 450, 475, 500, 525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775, 800, 825, 850};
    private int [] potionYpose = {75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400,
            425, 450, 475, 500, 525, 550, 575, 600, 625};

    private ImageIcon potionimage;
    private Random random = new Random();
    private int xpos = random.nextInt(34);
    private int ypos = random.nextInt(23);

    private int score = 0;

    private int moves = 0;

    private ImageIcon titleImage;
    private ImageIcon BackgraundImage;

    public Gameplay()
    {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        // Диалоговое окно
        player_name = JOptionPane.showInputDialog("Enter your nickname.");

        timer = new Timer(delay, this);
        timer.start();


    }

    public void paint(Graphics g)
    {

        if(moves == 0) //В начале игры рисуем позицию змейки. Если игра уже идёт это не проверяется.
        {
            snakeXlength[2] = 50;
            snakeXlength[1] = 75;
            snakeXlength[0] = 100;

            snakeYlenght[2] = 100;
            snakeYlenght[1] = 100;
            snakeYlenght[0] = 100;
        }

        //Рисует границы заглавной картинки
        g.setColor(Color.WHITE);
        g.drawRect(24, 10, 851 , 55);

        //Рисует картинку
        titleImage = new ImageIcon("unnamed.png");
        titleImage.paintIcon(this, g, 25 , 11);

        //Рисует границы для самой игры
        g.setColor(Color.WHITE);
        g.drawRect(24, 74, 855, 577);

        //Рисуем фон для игры
        BackgraundImage =new ImageIcon("agu1.png");
        BackgraundImage.paintIcon(this,g,25,75);

        //Рисуем счёт
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Lenght: "+lenghtofsnake, 750, 50);

        //Ввод имени игрока
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Player name: "+player_name, 50, 40);

        //Рисуем длину змеи(score)
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Scores: "+score, 750, 30);

        rightmouth = new ImageIcon("headUltraR.png");
        rightmouth.paintIcon(this, g, snakeXlength[0], snakeYlenght[0]);

        for(int a = 0; a < lenghtofsnake; a++){ // Проверяем направление гадюки

            if (a == 0 && right) //ультра правые
            {
                rightmouth = new ImageIcon("headUltraR.png");
                rightmouth.paintIcon(this, g, snakeXlength[a], snakeYlenght[a]);
            }

            if (a == 0 && left) //ультра левые
            {
                leftmouth = new ImageIcon("headUltraL.png");
                leftmouth.paintIcon(this, g, snakeXlength[a], snakeYlenght[a]);
            }

            if (a == 0 && up) //Камунисты
            {
                upmouth = new ImageIcon("headKamunism.png");
                upmouth.paintIcon(this, g, snakeXlength[a], snakeYlenght[a]);
            }

            if (a == 0 && down) //Либерасты
            {
                downmouth = new ImageIcon("headLiberian.png");
                downmouth.paintIcon(this, g, snakeXlength[a], snakeYlenght[a]);
            }

            if (a!=0) // Рисуем колбаску.
            {
                snakeimage = new ImageIcon("snakeimage.png");
                snakeimage.paintIcon(this, g, snakeXlength[a], snakeYlenght[a]);
            }
        }

        potionimage = new ImageIcon("potion.png");

        // Увелечение длинны змейки и счета
        if((potionXpose[xpos] == snakeXlength[0]) && (potionYpose[ypos] == snakeYlenght[0]))
        {
            score++;
            lenghtofsnake++;
            xpos = random.nextInt(34);
            ypos = random.nextInt(23);
        }

        potionimage.paintIcon(this, g, potionXpose[xpos], potionYpose[ypos]);
        //Проверка на проигрыш
        for(int b = 1; b < lenghtofsnake; b++)
        {
            if(snakeXlength[b] == snakeXlength[0] && snakeYlenght[b] == snakeYlenght[0])
            {
                right = false;
                left = false;
                up = false;
                down = false;

                repaint();

                g.setColor(Color.RED);
                g.setFont(new Font("Times New Roman", Font.BOLD, 100));
                g.drawString("Game over", 200, 300);

                g.setFont(new Font("Times New Roman", Font.BOLD, 20));
                g.drawString("Press 'SPACE' to restart", 350, 340);

            }
        }

        g.dispose();
    }

    @Override
    public void actionPerformed (ActionEvent e){
        timer .start();
        if (right)
        {
            for(int r = lenghtofsnake-1; r >= 0; r--)
            {
                snakeYlenght[r+1] = snakeYlenght[r];
            }

            for(int r = lenghtofsnake; r>=0; r--) //Логика игры (Направление движений и прочее..)
            {
                if (r==0){
                    snakeXlength[r] = snakeXlength[r] + 25;
                }
                else
                {
                    snakeXlength[r] = snakeXlength[r-1];
                }
                if( snakeXlength [r] > 850)
                {
                    snakeXlength[r] = 25;
                }

            }
            repaint();
        }

        if (left)
        {
            for(int r = lenghtofsnake-1; r >= 0; r--)
            {
                snakeYlenght[r+1] = snakeYlenght[r];
            }

            for(int r = lenghtofsnake; r>=0; r--)
            {
                if (r==0){
                    snakeXlength[r] = snakeXlength[r] - 25;
                }
                else
                {
                    snakeXlength[r] = snakeXlength[r-1];
                }
                if( snakeXlength [r] < 25)
                {
                    snakeXlength[r] = 850;
                }

            }
            repaint();
        }
        if (up)
        {
            for(int r = lenghtofsnake-1; r >= 0; r--)
            {
                snakeXlength[r+1] = snakeXlength[r];
            }

            for(int r = lenghtofsnake; r>=0; r--)
            {
                if (r==0){
                    snakeYlenght[r] = snakeYlenght[r] - 25;
                }
                else
                {
                    snakeYlenght[r] = snakeYlenght[r-1];
                }
                if( snakeYlenght [r] < 75)
                {
                    snakeYlenght[r] = 625;
                }

            }
            repaint();
        }
        if (down)
        {
            for(int r = lenghtofsnake-1; r >= 0; r--)
            {
                snakeXlength[r+1] = snakeXlength[r];
            }

            for(int r = lenghtofsnake; r>=0; r--)
            {
                if (r==0){
                    snakeYlenght[r] = snakeYlenght[r] + 25;
                }
                else
                {
                    snakeYlenght[r] = snakeYlenght[r-1];
                }
                if( snakeYlenght [r] > 625)
                {
                    snakeYlenght[r] = 75;
                }

            }
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    //Кей капы
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            moves = 0;
            score = 100;
            lenghtofsnake = 3;
            repaint();

        }
        if (e.getKeyCode() == KeyEvent.VK_D) //Допрашиваем какая кнопка нажата
        {
            moves++;
            right = true;
            if (!left) //check
            {
                right = true;
            }
            else
            {
                right = false;
                left = true;
            }
            up = false;
            down = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_A)
        {
            moves++;
            left = true;
            if (!right) //check
            {
                left = true;
            }
            else
            {
                left = false;
                right = true;
            }
            up = false;
            down = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_W)
        {
            moves++;
            up = true;
            if (!down) //check
            {
                up = true;
            }
            else
            {
                up = false;
                down = true;
            }
            right = false;
            left = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_S)
        {
            moves++;
            down = true;
            if (!up) //check
            {
                down = true;
            }
            else
            {
                down = false;
                up = true;
            }
            right = false;
            left = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_F)
        {
            JFrame frame = new JFrame();
            moves = 0;
            score = 100;
            repaint();
            JOptionPane.showMessageDialog(frame, "Работа зачтена. Спасибо.");
            System.exit(0);


        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
