
import java.awt.*;

import java.awt.image.BufferedImage;


public class Fish implements Runnable {
    private int x;
    private int y;
    private Color url;
    private BufferedImage img;
    private int endx;
    private int endy;
    private boolean movex;
    private int modifier;
    private int startx;
    private int starty;
    public Fish(int startx, int starty, Color url, int endx, int endy, boolean movex) {
        this.x = startx;
        this.startx = startx;
        this.y = starty;
        this.starty = starty;
        this.url = url;
        this.endx = endx;
        this.endy = endy;
        this.movex = movex;
        this.modifier = 2;
    }

    
    public void drawMe(Graphics g) {
        /*try {
			Image image = ImageIO.read(new File(url));
			Image finalImg = image.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
			g.drawImage(finalImg, x, y, null);
		} catch (IOException e) {
			System.out.println(e);
		}*/
        
        //g.drawImage(image, x, y, null);
        g.setColor(url);
        g.fillOval(x, y, 20, 20);
        //spent WAY too long trying to get these fish to look at least somewhat realistic lol
        g.drawPolygon(new int[] {x-5, x-5, x+5}, new int[] {y-5, y+25, y+10}, 3);
        //System.out.println("DRAWING!");
    }
    
    
    
    public void run() {
        while (true) {
            
            if (movex) {
                x += modifier;
                if (x > endx) {
                    modifier = -2;
                }
                if (x < startx) {
                    modifier = 2;
                }

            } else {
                y += modifier;
                if (y > endy) {
                    modifier = -2;
                }
                if (y < starty) {
                    modifier = 2;
                }
            }try{
                Thread.sleep(100); //millisecond
            }catch(InterruptedException ex){
                Thread.currentThread().interrupt();
            }
            System.out.println("X: " + x + " Y: " + y);
        
            
        }
    }
}
