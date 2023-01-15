import java.awt.Graphics;
import java.net.URL;
import java.awt.*;
import java.io.IOException;
import javax.imageio.*;
import java.io.Serializable;
public class NPC implements Serializable {
    private int x;
    private int y;
    public DLList<String> dialogues;
    private int currentIndex;
    private boolean canContinue;
    private Color color;
    public NPC(int x, int y, DLList<String> dialogues, int startingIndex, Color color) {
        this.x = x;
        this.y = y;
        this.dialogues = dialogues;
        this.currentIndex = startingIndex;
        canContinue = true;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //TODO: check if it is dialogues.size, or dialogues.size - 1
    public boolean goNext() {
        if (currentIndex < dialogues.size()-1 && canContinue) {
            currentIndex ++;
            return true;
        }
        return false;
    }

    public void drawMe(Graphics g) {
        if (!dialogues.get(currentIndex).equals("disappear")) {
            g.setColor(color);
            g.fillRect(x, y, 5, 5);
            //g.drawImage(img, x, y, null);
        }
        else {
            g.setColor(Color.lightGray);
            g.fillRect(x, y, 5, 5);
        }
        
    }

    public String getNow() {
        return dialogues.get(currentIndex);
    }
    public int getCurrentIndex() {
        return currentIndex;
    }

    public void changeContinue(boolean temp) {
        canContinue = temp;
    }




}
