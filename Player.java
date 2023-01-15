import java.awt.Graphics;
import java.net.URL;
import java.awt.*;
import java.io.IOException;
import javax.imageio.*;
import java.io.Serializable;


public class Player implements Serializable {
    private int x;
    private int y;
    private String url;
    private Image img;
    private Image newImage;

    public Player(int x, int y, String url) {
        this.x = x;
        this.y = y;
        this.url = url;

        try {
            img = ImageIO.read(new URL(url));
        } catch (IOException e) {
            System.out.println(e);
        }


    }

    public void drawMe(Graphics g) {
        
        g.setColor(Color.black);
        g.fillRect(x, y, 5, 5);
        //g.drawImage(img, x, y, null);
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public void ypos(int newy) {
        this.y = newy;
    }
    public void xpos(int newx) {
        this.x = newx;
    }

    

    
}