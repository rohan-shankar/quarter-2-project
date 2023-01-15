import java.io.Serializable;
public class Location implements Serializable {
    int xpos;
    int ypos;

    public Location(int x, int y) {
        this.xpos = x;
        this.ypos = y;
    }
    public int getx() {
        return xpos;
    }
    public int gety() {
        return ypos;
    }

    //@Override
    public boolean equals(Location temp) {
        if (temp.getx() == getx()) {
            if (temp.gety() == gety()) {
                return true;
            }
        }
        return false;
    }  

    @Override
    public int hashCode() {
        return (getx() * 100 + gety());
    }
    
    //gggggggggggggggggggg
}
