import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Paint extends JPanel implements MouseListener, ActionListener, MouseMotionListener  {

    public static int stroke, eraser = 0;
    private int xX1, yY1, xX2, yY2, choice = 1;
    private int lungime = 15, latime = 15, grosime = 3;
    private static final Color BACKGROUND_COLOR = Color.BLUE;
    private int eraserW = 50;                                       //marimi pentru radiera
    private int eraserH = 50;
    private JFrame frame = new JFrame("Paint app");
    BufferedImage grid;
    Graphics2D gc;
    String numele = "imagine.jpg";
    String saveLocation = null;
    String picPath = null;

    Paint()                            // constructor which is MainFile
    {
        //construim aplicatia pe tot ecranul
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width, screenSize.height-30);


        frame.setBackground(BACKGROUND_COLOR);
        frame.getContentPane().add(this);

        //panoul cu configuratiile de sus
        JPanel upPanel = new JPanel();
        upPanel.setLayout(new GridLayout(1,6));
        JButton color = new JButton("Color");
        color.addActionListener(this);
        JButton erase = new JButton("Erase");                // this button is set as Eraser
        erase.addActionListener(this);
        JButton rect = new JButton("Empty Rect");            // this button is set as Empty Rect
        rect.addActionListener(this);                    // button 2 passed to every ActionListener object that registered using addActionListener
        JButton b3 = new JButton("Empty oval");            // this button is set as Empty Oval
        b3.addActionListener(this);                    // button 3 passed to every ActionListener object that registered using addActionListener
        JButton b4 = new JButton("Line");                // this button is set as Line
        b4.addActionListener(this);

        JTextField lungimeFigura = new JTextField("15",3); //maxim 3 cifre pot fi inserate, 15 este dimensiunea by default
        lungimeFigura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = lungimeFigura.getText();
                lungime = Integer.parseInt(input);
            }
        });

        JTextField latimeFigura = new JTextField("15",3); //maxim 3 cifre pot fi inserate, 15 este dimensiunea by default
        latimeFigura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = latimeFigura.getText();
                latime = Integer.parseInt(input);
            }
        });

        JTextField grosimeContur = new JTextField("3",2); //maxim 2 cifre pot fi inserate, 3 este dimensiunea by default
        grosimeContur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = grosimeContur.getText();
                grosime = Integer.parseInt(input);
                gc.setStroke(new BasicStroke(grosime));
            }
        });

        JLabel text1 = new JLabel();
        JLabel text2 = new JLabel();
        JLabel text3 = new JLabel();
        text3.setText("Contur + Enter");
        text1.setText("Lungime Figura + Enter");
        text2.setText("Latime Figura + Enter");


        upPanel.add(color);
        upPanel.add(erase);
        upPanel.add(rect);
        upPanel.add(b3);
        upPanel.add(b4);
        upPanel.add(text1);
        upPanel.add(lungimeFigura);
        upPanel.add(text2);
        upPanel.add(latimeFigura);
        upPanel.add(text3);
        upPanel.add(grosimeContur);

        //Meniul de jos

        JPanel lowerPanel = new JPanel();

        JButton b1 = new JButton("Clear Drawing");
        b1.addActionListener(this);

        JButton b2 = new JButton("Load");
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new java.io.File("C:/Users/"));
                fc.setDialogTitle("Alege folderul unde se afla imaginea: ");
                if(fc.showOpenDialog(b2) == JFileChooser.APPROVE_OPTION)
                {
                    //
                }
                picPath  = fc.getSelectedFile().getAbsolutePath();

                JLabel picLabel = new JLabel(new ImageIcon(picPath));
                JPanel jPanel = new JPanel();
                jPanel.add(picLabel);
                frame.add(jPanel);
                frame.setVisible(true);
            }
        });

        //imaginile se salveaza local, unde se afla si proiectul CU NUMELE PE CARE IL INTRODUCETI IN PARTEA DE JOS A PLANSEI
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (saveLocation != null)
                {String pathComplet = saveLocation + '\\'+ numele;
                File file = new File(pathComplet);
                try {
                    ImageIO.write(grid,"jpg",file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }}
                else
                { //salvare locala unde este si proiectul daca nu e mentionat directorul
                    File file = new File(numele);
                    try {
                        ImageIO.write(grid,"jpg",file);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //File chooser option
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new java.io.File("C:/Users/")); // nu stiu ce e cu pathul asta dar o sa aflu
                fc.setDialogTitle("Alege folderul unde vei salva capodopera: ");
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //pot salva doar in directoare
                if(fc.showOpenDialog(selectButton) == JFileChooser.APPROVE_OPTION)
                {
                    //
                }
                saveLocation = fc.getSelectedFile().getAbsolutePath();
            }
        });

        JLabel textSaveName = new JLabel();
        textSaveName.setText("Name + Extensia (.jpg / .png) + Enter");
        JLabel selectFolder = new JLabel();
        selectFolder.setText("Selectati folderul");

        JTextField numePictura = new JTextField("imagine.jpg",15);
        numePictura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numele = numePictura.getText();
            }
        });


        lowerPanel.add(b1);
        lowerPanel.add(b2);
        lowerPanel.add(textSaveName);
        lowerPanel.add(numePictura);
        lowerPanel.add(selectFolder);
        lowerPanel.add(selectButton);
        lowerPanel.add(saveButton);

        frame.getContentPane().add(upPanel, BorderLayout.NORTH);
        frame.getContentPane().add(lowerPanel, BorderLayout.SOUTH);
        if(picPath != null)
        {
            JLabel picLabel = new JLabel(new ImageIcon(picPath));
            JPanel jPanel = new JPanel();
            jPanel.add(picLabel);
            frame.add(jPanel);
        }

        addMouseListener(this);                    // receiving an event from the mouse
        frame.setVisible(true);                        // this frame is set visible to user
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        // the operation will automatically off when user clicked exit button

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (grid == null) {
            int w = this.getWidth();
            int h = this.getHeight();
            grid = (BufferedImage) (this.createImage(w, h)); //marimea plansei de desen
            gc = grid.createGraphics();
            gc.setColor(Color.black);
        }

        g2.drawImage(grid, null, 0, 0);
        check();
    }

    public void draw() {
        Graphics2D g = (Graphics2D) getGraphics();
        int w = latime;
        if (w < 0)
            w = w * (-1);

        int h = lungime;
        if (h < 0)
            h = h * (-1);

        if (choice == 1) {
            check();
            gc.drawRect(xX1, yY1, w, h);
            repaint();
        } else if (choice == 2) {
            check();
            gc.drawOval(xX1, yY1, w, h);
            repaint();
        } else if (choice == 3) {
                gc.setStroke(new BasicStroke(grosime));
            gc.drawLine(xX1, yY1, xX2, yY2);
            repaint();
        } else if (choice == 4) { //Rezetez canvasul
            repaint();
            Color temp = gc.getColor();
            gc.setColor(BACKGROUND_COLOR);
            gc.fillRect(0, 0, getWidth(), getHeight());
            gc.setColor(temp);
            repaint();
        } else {
            if (eraser == 1)
                gc.clearRect(xX1, yY1, w, h);
        }
    }

    public void check() {
        if (xX1 > xX2) {
            int z = 0;
            z = xX1;
            xX1 = xX2;
            xX2 = z;
        }
        if (yY1 > yY2) {
            int z = 0;
            z = yY1;
            yY1 = yY2;
            yY2 = z;
        }
    }

    public void actionPerformed(ActionEvent e) {

        super.removeMouseMotionListener(this);

        if (e.getActionCommand().equals("Color"))                // action when user choose Color button
        {
            Color bgColor = JColorChooser.showDialog(this, "Choose Background Color", getBackground());
            if (bgColor != null)
                gc.setColor(bgColor);
        }

        if (e.getActionCommand().equals("About"))                // action when user choose About button
        {
            System.out.println("About Has Been Pressed");
            JFrame about = new JFrame("About");
            about.setSize(300, 300);
            JButton picture = new JButton(new ImageIcon("C:/Users/TehRobot/Desktop/Logo.png"));
            about.add(picture);
            about.setVisible(true);
        }

        if (e.getActionCommand().equals("Empty Rect"))                // action when user choose Empt Rect
        {
            choice = 1;
        }

        if (e.getActionCommand().equals("Empty oval"))                // action when user choose Empt Oval
        {
            choice = 2;
        }

        if (e.getActionCommand().equals("Line"))                // action when user choose Line button
        {
            choice = 3;
        }

        if (e.getActionCommand().equals("Medium Line"))            // action when user choose Medium Line
        {
            stroke = 0;
        }

        if (e.getActionCommand().equals("Thick Line"))                // action when user choose Thick Line
        {

            stroke = 1;
        }

        if (e.getActionCommand().equals("Erase"))                // action when user choose Erase button
        {
            eraser = 1;
            choice = 5;
            super.addMouseMotionListener(this);
        }

        if (e.getActionCommand().equals("Clear Drawing")) {
            choice = 4;
            draw();
        }

    }

    public void mousePressed(MouseEvent e)                        // action when mouse is pressed
    {
        xX1 = e.getX();
        yY1 = e.getY();
    }

    public void mouseReleased(MouseEvent e) {
        xX2 = e.getX();
        yY2 = e.getY();
        draw();
        eraser = 0;
    }


    public void mouseDragged(MouseEvent re) {
        Color c = gc.getColor();
        gc.setColor(BACKGROUND_COLOR);
        gc.drawRect(re.getX(), re.getY(), eraserW, eraserH);
        gc.setColor(c);
        repaint();
    }

    public void mouseMoved(MouseEvent arg0) {
    }

    public void mouseExited(MouseEvent e)                        // action when mouse is exited
    {
    }

    public void mouseEntered(MouseEvent e)                        // action when mouse is entered
    {
    }

    public void mouseClicked(MouseEvent e)                        // action when mouse is clicked
    {
    }
}
