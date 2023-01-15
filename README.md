# Simple Adventure Game

This Simple Adventure Game involves skills and concepts I’d learned over my first high school semester, including Hash Tables, Multithreading, File Importing and Exporting, Java Swing Graphics, and more. It has rudimentary mechanics and is easy to understand and grasp. 

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
