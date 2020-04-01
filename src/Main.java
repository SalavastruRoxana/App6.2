
public class Main
{
    public static void main(String[] args)
    {
        new Paint();
    }

}

/*
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        Frame frame = new Frame("Testing");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.add(new ImageCanvas());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public class ImageCanvas extends Canvas {

        private BufferedImage img;

        public ImageCanvas() {
            try {
                img = ImageIO.read(new File("C:/java_toDelete/img.jpg"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return img == null ? new Dimension(200, 200) : new Dimension(img.getWidth(), img.getHeight());
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (img != null) {
                int x = (getWidth() - img.getWidth()) / 2;
                int y = (getHeight() - img.getHeight()) / 2;
                g.drawImage(img, x, y, this);
            }
        }

    }

}
*/
