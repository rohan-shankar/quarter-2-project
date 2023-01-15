import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Scanner;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class Screen extends JPanel implements KeyListener, ActionListener {
    
    Animate a = new Animate(this);
    Thread animateThread = new Thread(a);
    Font font = new Font("Arial", Font.PLAIN, 20);
    DLList<String> griffinDialogues;
    DLList<String> lucasDialogues;
    int keyX;
    int keyY;
    boolean canWin;
    boolean drawWin;
    int whichquest;

    Fish fish = new Fish(10, 50, Color.white, 150, 150, true);
    Thread fishThread = new Thread(fish);

    Fish fish2 = new Fish(10, 100, Color.pink, 10, 400, false);
    Thread fishThread2 = new Thread(fish2);

    Fish fish3 = new Fish(300, 50, Color.green, 400, 50, true);
    Thread fishThread3 = new Thread(fish3);

    


    MyHashTable<Location, Integer> map;

    Player player;
    
    int coinCounter;
    //Lucas things
    NPC lucas;
    
    boolean foundLucas;
    boolean hasKey;
    boolean drawKey;
    
    

    
    //griffin things
    NPC griffin;
    
    boolean foundgriffin;
    boolean hasGems;
    boolean drawGems;
    boolean drawgriffin;
    int temporaryCounter;
    int temporaryCounter2;
    boolean drawInitial;
    boolean playsoundonce;
    


    public Screen() {

        
        this.setLayout(null); 
        addKeyListener(this); 
        animateThread.start();
        fishThread.start();
        fishThread2.start();
        fishThread3.start();
        keyY = 200;
        keyX = 200;
        canWin = false;
        drawWin = false;
        whichquest = 1;
        

        

        lucasDialogues = new DLList<String>();
        //TODO: Add lucas pfp :D
        lucasDialogues.add("foo");
        lucasDialogues.add("Lucas: Hello there");
        lucasDialogues.add("Lucas: My name is Lucas :)");
        lucasDialogues.add("Lucas: Collect a key and then come back to me for a prize!");
        lucasDialogues.add("Lucas: Great job! You found the key! As a reward, I'll increase your score by 10!");
        lucasDialogues.add("Goodbye!");
        lucasDialogues.add("disappear");
        griffinDialogues = new DLList<String>();
        //griffinDialogues.add("foo");
        //TODO: MAKE GRIFFIN LOOK DIFFERENT
        griffinDialogues.add("Griffin: Hello! My name is Griffin :D");
        griffinDialogues.add("Griffin: Hope that you've enjoyed the game!");
        griffinDialogues.add("Griffin: As a reward for talking to me, I'll increase your score by 20!");
        griffinDialogues.add("Griffin: Goodbye!");
        griffinDialogues.add("disappear");

        playsoundonce = true;
        
        drawInitial = true;
        
        map = new MyHashTable<Location, Integer>();
        player = new Player(300, 300, "https://static.wikia.nocookie.net/simpsons/images/b/bd/Homer_Simpson.png/revision/latest?cb=20201222215437");
        coinCounter = 0;
        hasKey = false;
        drawKey = false;
        
        lucas = new NPC(400, 400, lucasDialogues, 0, Color.red);
        foundLucas = false;
        griffin = new NPC(200, 130, griffinDialogues, 0, Color.magenta);

        //SETTING UP THE MAP!*****************************************************
        int counter = 0;
        try {
            Scanner read = new Scanner(new FileReader("mapread.txt"));
            String next = ""; 
            int x = 0;
            int y = 0;
            while (read.hasNext()) {
                next = read.next();
                map.put(new Location(x,y), Integer.parseInt(next));
                x++;
                if (x >= 100) {
                    x = 0;
                    y++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //END SETTING UP THE MAP!*****************************************************
        this.setFocusable(true);
        playSound("Sound/start.wav");
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500,500);
    }

    @Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);

        

        if (!drawWin) {
            if (lucas.getCurrentIndex() == 6) {
                drawgriffin = true;
                if (playsoundonce) {
                    playSound("Sound/quest1finished.wav");
                    playsoundonce = false;
                    
                }
            }

            if (griffin.getCurrentIndex() >= 4) {
                whichquest = 3;
            }
    
            
            
            
            g.setFont(font);
            g.setColor(Color.lightGray);       
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {
                    int temp = map.get(new Location(i, j)).get(0);
                    if (temp == 3) {
                        g.setColor(new Color(153,102,0));
                    }
                    else if (temp == 4) {
                        g.setColor(Color.blue);
                    }
                    else if (temp == 2) {
                        g.setColor(Color.yellow);
                    }
                    else if (temp == 1) {
                        g.setColor(Color.green);
                    }
                    /*else if (temp = 6) {
                        g.setColor(Color.pink);
                    }*/
                    Location o = new Location(i, j);
                    //System.out.println(o.getx() + " " + o.gety());
                    g.fillRect(o.getx()*5, o.gety()*5, 5, 5); 
                }
            }
    
            if (drawgriffin) {
                System.out.println("drawing griffin");
                griffin.drawMe(g);
            }
    
            if (drawKey && hasKey == false) {   
                g.setColor(Color.pink);
                //map.put(new Location(200, 200))
                g.fillRect(200, 200, 5, 5);
            }

            //draw instructions
            if (whichquest == 1) {
                g.setColor(Color.white);
                g.setFont(new Font("serif", Font.PLAIN, 10));
                g.drawString("You're on quest 1! Talk to Lucas, and collect his key!", 20, 40);
                g.setFont(font);
            } else if (whichquest == 2) {
                g.setColor(Color.white);
                g.setFont(new Font("serif", Font.PLAIN, 10));
                g.drawString("You're on quest 2! Talk to Griffin in the upper left corner.", 20, 40);
                g.setFont(font);
            } else if (whichquest == 3 || canWin) {
                g.setColor(Color.white);
                g.setFont(new Font("serif", Font.PLAIN, 10));
                g.drawString("You've completed both quests! Press e to end the game!", 20, 40);
                g.setFont(font);
            }
    
    
            if (drawInitial) {
                g.setFont(new Font("serif", Font.PLAIN, 10));
                //System.out.println("DRAWING DIALOGUE");
                g.setColor(Color.white);
                g.drawString("Welcome! Talk to the NPC in the bottom right to start a quest! Collect coins to get points!", 20, 470);
                g.setFont(font);
            }
    
            if (foundLucas) {
                drawInitial = false;
                //TODO: Change Font size!
                g.setFont(new Font("serif", Font.PLAIN, 10));
                //System.out.println("DRAWING DIALOGUE");
                g.setColor(Color.white);
                if (!lucas.dialogues.get(lucas.getCurrentIndex()).equals("disappear")) {
                    g.drawString(lucas.getNow(), 20, 470);
                }
                g.setFont(font);
            }
            if (hasKey) {
                g.setColor(Color.white);
                g.setFont(new Font("serif", Font.PLAIN, 10));
                g.drawString("Key Has been Collected!", 20, 450);
                g.setFont(font);
            }
    
            if (coinCounter >= 39) {
                foundgriffin = true;
            }
    
            if (foundgriffin == true) {
                drawInitial = false;
                g.setFont(new Font("serif", Font.PLAIN, 10));
                //System.out.println("DRAWING DIALOGUE");
                g.setColor(Color.white);
                if (!griffin.dialogues.get(griffin.getCurrentIndex()).equals("disappear")) {
                    g.drawString(griffin.getNow(), 20, 470);
                }
                g.setFont(font);
            }
    
            if (griffin.getCurrentIndex() >= 4) {
                /*g.setColor(Color.white);
                g.setFont(new Font("serif", Font.PLAIN, 10));
                g.drawString("You've completed both quests! Press e to end the game!", 20, 40);
                g.setFont(font);*/
                canWin = true;
            }
    
            player.drawMe(g);
            lucas.drawMe(g);
            g.setColor(Color.red);
            g.drawString(String.valueOf(coinCounter), 20, 20);

            g.setColor(Color.white);
            fish.drawMe(g);
            fish2.drawMe(g);
            fish3.drawMe(g);
            
        }

        else {
            g.setColor(Color.pink);
            g.fillRect(0, 0, 500, 500);
            g.setColor(Color.black);
            g.drawString("YOU WON!!!!!", 250, 250);
            g.drawString("Score: " + coinCounter, 250, 300);
            fishThread.interrupt();
            fishThread2.interrupt();
            fishThread3.interrupt();
        }
        

        
    }

    @Override
    public void keyTyped(KeyEvent e) {        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        boolean lucasvar = false;
        boolean griffinvar = false;

        if (e.getKeyCode()  == 40) {
            player.ypos(player.y() + 5);
            if (checkBad().equals("water") || checkBad().equals("lucas") || checkBad().equals("griffin")) {
                if (checkBad().equals("lucas")) {lucasvar = true;}
                if (checkBad().equals("griffin")) {griffinvar = true;}
                player.ypos(player.y() - 5);
            }
            
            else if (player.x() == keyX) {
                if (player.y() == keyY) {
                    hasKey = true;
                }
            }
        }
        else if (e.getKeyCode() == 38) {
            player.ypos(player.y() - 5);
            if (checkBad().equals("water") || checkBad().equals("lucas") || checkBad().equals("griffin")) {
                if (checkBad().equals("lucas")) {lucasvar = true;}
                if (checkBad().equals("griffin")) {griffinvar = true;}
                player.ypos(player.y() + 5);
            }
            else if (player.x() == keyX) {
                if (player.y() == keyY) {
                    hasKey = true;
                }
            }
            //OUTPUTALL();
        }

        else if (e.getKeyCode()  == 39) {
            player.xpos(player.x() + 5);
            if (checkBad().equals("water") || checkBad().equals("lucas") || checkBad().equals("griffin")) {
                if (checkBad().equals("lucas")) {lucasvar = true;}
                if (checkBad().equals("griffin")) {griffinvar = true;}
                player.xpos(player.x() - 5);
            }
            else if (player.x() == keyX) {
                if (player.y() == keyY) {
                    hasKey = true;
                }
            }
        }
        else if (e.getKeyCode() == 37) {
            player.xpos(player.x() - 5);
            if (checkBad().equals("water") || checkBad().equals("lucas") || checkBad().equals("griffin")) {
                if (checkBad().equals("lucas")) {lucasvar = true;}
                if (checkBad().equals("griffin")) {griffinvar = true;}
                player.xpos(player.x() + 5);
            }
            else if (player.x() == keyX) {
                if (player.y() == keyY) {
                    hasKey = true;
                }
            }
        } 

        if (griffinvar || lucasvar) {
            playSound("Sound/npcspeaking.wav");
        } 

        else if (e.getKeyCode() == 67) {
            System.out.println("CHEATING THE GAME");
            if (lucas.getCurrentIndex() != 6) {
                hasKey = true;
                lucas.goNext();
                lucas.goNext();
                lucas.goNext();
                lucas.goNext();
                lucas.goNext();
                lucas.goNext();
                lucas.goNext();
                lucas.goNext();
                lucas.goNext();
                lucas.goNext();
                temporaryCounter ++;
                if (temporaryCounter == 1) {
                    coinCounter += 10;
                    
                
                }
                drawgriffin = true;
                whichquest = 2;
            }else if (canWin) {
                drawWin = true;
                whichquest = 3;
            }
            else {
                griffin.goNext();
                griffin.goNext();
                griffin.goNext();
                griffin.goNext();
                griffin.goNext();
                griffin.goNext();
                temporaryCounter2 ++;
                if (temporaryCounter2 == 1) {
                    coinCounter += 20;

                }
                whichquest = 3;

            }
            
            
            
            playSound("Sound/quest1finished.wav");
            
        }
        

        if (checkBad().equals("coin")) {
            map.remove(new Location(player.x()/5, player.y()/5), 2);
            map.put(new Location(player.x()/5, player.y()/5), 3);
            coinCounter ++;

        }

        if (e.getKeyCode() == 69) {
            if (canWin) {
                drawWin = true;
            }
        }

        if (lucasvar) {
            lucas.goNext();
            
            if (lucas.getCurrentIndex() == 3 && !hasKey) {
                lucas.changeContinue(false);
                drawKey = true;
            }
            else if (lucas.getCurrentIndex() == 3 && hasKey) {
                lucas.changeContinue(true);
                lucas.goNext();
                hasKey = false;
                drawKey = false;
                
            }
            else if (lucas.getCurrentIndex() == 5) {
                coinCounter += 10;
                //drawgriffin = true;
                drawKey = false;
                whichquest = 2;
            }
        }
        if (griffinvar) {
            griffin.goNext();

            if (griffin.getCurrentIndex() == 2) {
                coinCounter += 20;
            }

            
        }
        repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {        
    }

    public String checkBad() {

        DLList<Integer> temporary = new DLList<>();
        //temporary.add(4);
        for (int i = 0; i < map.keySet().size(); i ++) {
            temporary = map.get(new Location(player.x()/5, player.y()/5));
            if (temporary!= null) {
                if (temporary.get(0) == 4 || temporary.get(0) == 1) {
                    System.out.println("Found water!");
                    return "water";
                }
                else if (temporary.get(0) == 2) {
                    System.out.println("Found coin");
                    playSound("Sound/coinsound.wav");
                    return "coin";
                }
                else if (player.x() == lucas.getX() && player.y() == lucas.getY()) {
                    System.out.println("Found lucas");
                    
                    foundLucas = true;
                    
                    return "lucas";
                }
                else if (player.x() == griffin.getX() && player.y() == griffin.getY()) {
                    System.out.println("Found griffin");
                    
                    foundgriffin = true;
                    
                    return "griffin";
                }
                

            }
        }
        return "none";
    }

    public void playSound(String path) {

        try {
            URL url = this.getClass().getClassLoader().getResource(path);
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }

    public void OUTPUTALL() {
        //make sure to update this for EVERY new variable!!!
        try {
            FileOutputStream fos = new FileOutputStream("saved.dat");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            //out.writeObject(griffinDialogues);
            //out.writeObject(lucasDialogues);
            out.writeObject(map);
            out.writeObject(player);
            out.writeObject(coinCounter);
            //out.writeObject(lucas);
            out.writeObject(foundLucas);
            out.writeObject(hasKey);
            out.writeObject(drawKey);
            //out.writeObject(griffin);
            out.writeObject(foundgriffin);
            out.writeObject(hasGems);
            out.writeObject(drawGems);
            out.writeObject(drawgriffin);
            out.writeObject(temporaryCounter);
            out.writeObject(drawInitial);
            out.writeObject(playsoundonce);
            
            out.close();

        } catch (Exception e) {
            System.out.println("Error in outputting :(");
            e.printStackTrace();
        }

    }
    
    /*public void INPUTALL() {

        
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream("saved.dat");
            in = new ObjectInputStream(fis);

            //lucasDialogues = (DLList<String>)in.readObject();
            //griffinDialogues = (DLList<String>)in.readObject();
            map = (MyHashTable<Location, Integer>)in.readObject();
            player = (Player)in.readObject();
            coinCounter = (int)in.readObject();
            //lucas = (NPC)in.readObject();
            foundLucas = (boolean)in.readObject();
            hasKey = (boolean)in.readObject();
            drawKey = (boolean)in.readObject();
            //griffin = (NPC)in.readObject();
            foundgriffin = (boolean)in.readObject();
            hasGems = (boolean)in.readObject();
            drawGems = (boolean)in.readObject();
            drawgriffin = (boolean)in.readObject();
            temporaryCounter = (int)in.readObject();
            drawInitial = (boolean)in.readObject();
            playsoundonce = (boolean)in.readObject();
            
            in.close();

            

        } catch (Exception e) {
            System.out.println("Error in inputting >:(");
            e.printStackTrace();
            playsoundonce = true;
        
            drawInitial = true;
            
            map = new MyHashTable<Location, Integer>();
            player = new Player(300, 300, "https://static.wikia.nocookie.net/simpsons/images/b/bd/Homer_Simpson.png/revision/latest?cb=20201222215437");
            coinCounter = 0;
            hasKey = false;
            drawKey = false;
            
            lucas = new NPC(400, 400, lucasDialogues, 0);
            foundLucas = false;

            lucasDialogues = new DLList<String>();
            //TODO: Add lucas pfp :D
            lucasDialogues.add("foo");
            lucasDialogues.add("Lucas: Hello there");
            lucasDialogues.add("Lucas: My name is Lucas :)");
            lucasDialogues.add("Lucas: Collect a key and then come back to me for a prize!");
            lucasDialogues.add("Lucas: Great job! You found the key! As a reward, I'll increase your score by 10!");
            lucasDialogues.add("Goodbye!");
            lucasDialogues.add("disappear");
            griffinDialogues = new DLList<String>();
            //griffinDialogues.add("foo");
            griffinDialogues.add("Griffin: Hello! My name is Griffin :D");
            griffinDialogues.add("Griffin: Looks like you've collected enough coins for me to give you a quest!");
            griffinDialogues.add("Griffin: Collect 10 gems, and come back to me for a reward!");
            griffinDialogues.add("Griffin: Wow, you got all 10 gems!");
            griffinDialogues.add("Griffin: Congrats!");
            griffinDialogues.add("Griffin: As a reward, I'll increase your score by 20!");
            griffinDialogues.add("Griffin: Goodbye!");
            griffinDialogues.add("disappear");
        }
        
    }*/
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        
    }
}

