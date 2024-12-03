public class Maze {

    // Beispiel-Labyrinthe
    private static final String[][] EXAMPLES = {
        {
            "  #  ",
            "# # #",
            "     ",
            "#### ",
            "     ",
        },
        {
            "     ",
            "#####",
            "#   #",
            "# # #",
            "     ",
        },
        // Weitere Labyrinthe können hier hinzugefügt werden
    };

    public static void main(String[] args) {
        // Test-Modus (z. B. d 0 1)
        String mode = args[0];
        if (mode.equals("d")) {
            int index = Integer.parseInt(args[1]);
            boolean debug = args.length > 2 && args[2].equals("1");
            runDemoMode(index, debug);
        }
    }

    public static void runDemoMode(int index, boolean debug) {
        String[] mazeStr = EXAMPLES[index];
        boolean[][] maze = loadMaze(mazeStr);
        int[][] path = new int[maze.length * maze[0].length][2]; // Dummy-Path
        updatePath(path, maze, debug);
    }

    // Aufgabe 1.1: Labyrinth laden
    public static boolean[][] loadMaze(String[] maze) {
        int rows = maze.length;
        int cols = maze[0].length();
        boolean[][] labyrinth = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                labyrinth[rows - 1 - i][j] = maze[i].charAt(j) == ' ';
            }
        }
        return labyrinth;
    }

    // Aufgabe 1.2: Prüfen, ob Koordinaten außerhalb des Labyrinths sind
    public static boolean isOut(int x, int y, boolean[][] maze) {
        return x < 0 || y < 0 || x >= maze[0].length || y >= maze.length;
    }

    // Aufgabe 1.3: Prüfen, ob eine Wand vorliegt
    public static boolean isWall(int x, int y, boolean[][] maze) {
        return isOut(x, y, maze) || !maze[y][x];
    }

    // Aufgabe 1.4: Pfad aktualisieren
    public static void updatePath(int[][] path, boolean[][] maze, boolean debug) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (debug) {
                    System.out.printf("(%d, %d) ", j, i);
                }
            }
            System.out.println();
        }
    }

    // Aufgabe 1.5: Gültigen Pfad prüfen
    public static boolean isValidPath(int[][] path, boolean[][] maze) {
        for (int i = 0; i < path.length - 1; i++) {
            int x1 = path[i][0], y1 = path[i][1];
            int x2 = path[i + 1][0], y2 = path[i + 1][1];
            if (isOut(x1, y1, maze) || isWall(x1, y1, maze) || Math.abs(x1 - x2) + Math.abs(y1 - y2) != 1) {
                return false;
            }
        }
        return true;
    }

    // Aufgabe 1.6: Zwei Pfade vergleichen
    public static boolean comparePath(int[][] path1, int[][] path2) {
        if (path1.length != path2.length) return false;
        for (int i = 0; i < path1.length; i++) {
            if (path1[i][0] != path2[i][0] || path1[i][1] != path2[i][1]) {
                return false;
            }
        }
        return true;
    }

    // Aufgabe 2.1: Kürzesten Pfad rekursiv berechnen
    public static int getShortestPathLengthRekursiv(int x, int y, int lastX, int lastY, boolean[][] maze) {
        if (isOut(x, y, maze) || isWall(x, y, maze)) {
            return Integer.MAX_VALUE;
        }
        if (x == maze[0].length - 1 && y == maze.length - 1) {
            return 0; // Ziel erreicht
        }
        int minSteps = Integer.MAX_VALUE;
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int[] dir : directions) {
            int nextX = x + dir[0];
            int nextY = y + dir[1];
            if (nextX == lastX && nextY == lastY) continue; // Vorherige Zelle vermeiden
            int steps = getShortestPathLengthRekursiv(nextX, nextY, x, y, maze);
            minSteps = Math.min(minSteps, steps);
        }
        return minSteps == Integer.MAX_VALUE ? Integer.MAX_VALUE : minSteps + 1;
    }

    // Aufgabe 2.3: Kürzesten Pfad rekursiv mit Cache berechnen
    public static int getShortestPathLengthRekursivCached(int x, int y, int lastX, int lastY, boolean[][] maze, int[][] cache) {
        if (isOut(x, y, maze) || isWall(x, y, maze)) {
            return Integer.MAX_VALUE;
        }
        if (x == maze[0].length - 1 && y == maze.length - 1) {
            return 0; // Ziel erreicht
        }
        if (cache[y][x] != -1) return cache[y][x];
        int minSteps = Integer.MAX_VALUE;
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int[] dir : directions) {
            int nextX = x + dir[0];
            int nextY = y + dir[1];
            if (nextX == lastX && nextY == lastY) continue; // Vorherige Zelle vermeiden
            int steps = getShortestPathLengthRekursivCached(nextX, nextY, x, y, maze, cache);
            minSteps = Math.min(minSteps, steps);
        }
        cache[y][x] = minSteps == Integer.MAX_VALUE ? Integer.MAX_VALUE : minSteps + 1;
        return cache[y][x];
    }

    // Aufgabe 2.5: Kürzesten Pfad iterativ berechnen
    public static int getShortestPathLengthIterative(int x, int y, boolean[][] maze) {
        // Implementieren Sie die iterative Logik mit Stack-ähnlichem Ansatz
        return 0; // Platzhalter
    }
}
