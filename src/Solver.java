import java.io.FileReader;
import java.io.File;
import java.io.IOException;

public class Solver
{
        Stack<Point> stack;
        Queue<Point> queue;
        Point start;
        char[][]mazeArray;
        boolean goalFound;

        public Solver() {
                goalFound = false;
                mazeArray = new char[10][12];
                for(int i=0;i < 10; i++)
                {
                        for(int j=0;j<12;j++)
                        {
                                mazeArray[i][j] = '#';
                        }
                }
        }

        public Solver(String fileName)
        {
                goalFound = false;
                stack = new Stack<Point>();
                queue = new Queue<Point>();
                mazeArray = new char[10][12];
                readMazeFile(fileName);
                start = findStart();
                stack.push(start);
                queue.enqueue(start);
        }

        public void stepStack() {
                Point v = stack.pop();
                if(mazeArray[v.getY()][v.getX()] != 'v') {
                                //Label v as discovered
                                mazeArray[v.getY()][v.getX()] = 'v';
                                //for all edges push to stack
                                if(down(v) == '.') {
                                        stack.push(v.down());
                                }
                                if(up(v) == '.') {
                                        stack.push(v.up());
                                }
                                if(right(v) == '.') {
                                        stack.push(v.right());
                                }
                                if(left(v) == '.') {
                                        stack.push(v.left());
                                }
                        }

                        //IF Goal found pop everything off the stack
                        if(right(v) == '*' || up(v) == '*' || left(v) == '*' || down(v) == '*') {
                                System.out.println("Goal Found!");
                                goalFound = true;
                                while(!stack.isEmpty()) {
                                        stack.pop();
                                }
                        }

                        System.out.println(mapString());
        }

        public void stepQueue() {
                Point v = queue.dequeue();
                if(mazeArray[v.getY()][v.getX()] != 'v') {
                                //Label v as discovered
                                mazeArray[v.getY()][v.getX()] = 'v';
                                //for all edges push to stack
                                if(down(v) == '.') {
                                        queue.enqueue(v.down());
                                }
                                if(up(v) == '.') {
                                        queue.enqueue(v.up());
                                }
                                if(right(v) == '.') {
                                        queue.enqueue(v.right());
                                }
                                if(left(v) == '.') {
                                        queue.enqueue(v.left());
                                }
                        }

                        //IF Goal found pop everything off the stack
                        if(right(v) == '*' || up(v) == '*' || left(v) == '*' || down(v) == '*') {
                                System.out.println("Goal Found!");
                                goalFound = true;
                                while(!queue.isEmpty()) {
                                        queue.dequeue();
                                }
                        }

                        System.out.println(mapString());
        }

        public void solveMaze() {
                stack = new Stack<Point>();
                System.out.println("Start found at x: " + start.getX() + " y: " + start.getY());
                System.out.println("Char at start: " + charAtPoint(start));
                stack.push(start);
                while(!stack.isEmpty()) {
                        Point v = stack.pop();
                        if(mazeArray[v.getY()][v.getX()] != 'v') {
                                //Label v as discovered
                                mazeArray[v.getY()][v.getX()] = 'v';
                                //for all edges push to stack
                                if(down(v) == '.') {
                                        stack.push(v.down());
                                }
                                if(up(v) == '.') {
                                        stack.push(v.up());
                                }
                                if(right(v) == '.') {
                                        stack.push(v.right());
                                }
                                if(left(v) == '.') {
                                        stack.push(v.left());
                                }
                        }

                        //IF Goal found pop everything off the stack
                        if(right(v) == '*' || up(v) == '*' || left(v) == '*' || down(v) == '*') {
                                System.out.println("Goal Found!");
                                while(!stack.isEmpty()) {
                                        stack.pop();
                                }
                        }

                        System.out.println(mapString());
                }

        }

        public char left(Point p) {
                return mazeArray[p.getY()][p.getX() - 1];
        }

        public char up(Point p) {
                return mazeArray[p.getY() - 1][p.getX()];
        }

        public char right(Point p) {
                return mazeArray[p.getY()][p.getX() + 1];
        }

        public char down(Point p) {
                return mazeArray[p.getY() + 1][p.getX()];
        }

        public Point findStart() {
                Point start = new Point(0,0);
                for(int i=0;i < 10; i++)
                {
                        for(int j=0;j<12;j++)
                        {
                                if(mazeArray[i][j] == 'o')
                                {
                                        start.setPoint(j, i);
                                        return start;
                                }
                        }
                }
                return null;
        }

        public char charAtPoint(Point point) {
                return mazeArray[point.getY()][point.getX()];
        }

        public char charAtPoint(int x, int y) {
                return mazeArray[y][x];
        }

        public void readMazeFile(String fileName){
                try
                {
                        FileReader fileReader = new FileReader (new File(fileName));
                        for(int i=0;i < 10; i++)
                        {
                                for(int j=0;j<12;j++)
                                {
                                        mazeArray[i][j] = (char)fileReader.read();
                                }
                                fileReader.read();
                        }
                        fileReader.close();
                }
                catch(IOException e){
                        System.out.println("Could not open file: " + fileName);
                }
        }

        public void test(String file){
                readMazeFile(file);
                System.out.println(mapString());
                solveMaze();
        }

        public String mapString()
        {
                String string = new String("");
                for(int i=0;i<mazeArray.length;i++)
                {
                        for(int j=0;j<mazeArray[0].length;j++)
                        {
                                string += mazeArray[i][j];
                        }
                        string += '\n';
                }
                return string;
        }

        private class Point {
                private int x, y;

                public Point(int x, int y) {
                        this.x = x;
                        this.y = y;
                }

                public void setPoint(int x, int y) {
                        this.x = x;
                        this.y =y;
                }

                public int[] getPoint() {
                        int p[] = new int[2];
                        p[0] = x;
                        p[1] = y;
                        return p;
                }

                public int getX() {
                        return x;
                }

                public int getY() {
                        return y;
                }

                public Point left() {
                        return new Point(x - 1, y);
                }

                public Point up() {
                        return new Point(x, y - 1);
                }

                public Point right() {
                        return new Point(x + 1, y);
                }

                public Point down() {
                        return new Point(x, y + 1);
                }
        }

}
