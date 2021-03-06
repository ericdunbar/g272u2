import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
 /**
  * Nothing to do with COMP272.
  * @author Eric Dunbar
  * @date Aug 16, 2016
  * @title
  * @assignment 2
  *
  */
@SuppressWarnings("serial")
public class Mandelbrot extends JFrame {
 
    private final int MAX_ITER = 570;
    private final double ZOOM = 9000;
    private BufferedImage I;
    private double zx, zy, cX, cY, tmp;
 
    public Mandelbrot() {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                zx = zy = 0;
                cX = (x + 1800) / ZOOM;
                cY = (y - 100) / ZOOM;
                int iter = MAX_ITER;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                I.setRGB(x, y, iter | (iter << 16));
            }
        }
    }
 
    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }
 
    public static void main(String[] args) {
        new Mandelbrot().setVisible(true);
    }
}