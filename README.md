# Adventure Game

I coded this for my Advanced Computer Science class, which I am currently taking. In this game, the player explores an island, and tries to increase their score (through collecting coins) before the game ends. The game has easy to understand mechanics, as well as in-built instructions on how to move around. The player can additionally interact with two NPCs (Non-Player Characters) placed around the island, who provide rudimentary quests for the player to complete. For example, one of the NPCs has the player collect a key.

In terms of implementation, I coded the project using Java. I used a Hashtable for the game’s pixelated map, for maximum efficiency in retrieving items. I then used File Importing to load the map from a preset text file since creating the map using built-in code would be extremely inefficient. I used Multithreading for some of the game’s animated objects. I also used Java Swing Graphics to display the entire game on its own separate window, instead of a text-based game, which would have had severe limitations with functionality. 

## Example Code Snippet

```java
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
```
