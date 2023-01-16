# Adventure Game

This Adventure Game involves skills and concepts I’d learned over my first high school semester, including Hash Tables, Multithreading, File Importing and Exporting, Java Swing Graphics, and more. It has rudimentary mechanics and is easy to understand and grasp.

In this game, the player explores an island, and tries to increase their score (through collecting coins) before the game ends. The game has easy to understand mechanics, as well as in-built instructions on how to move around. The player can additionally interact with two NPCs (Non-Player Characters) placed around the island, who provide rudimentary quests for the player to complete. For example, one of the NPCs has the player collect a key.

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
