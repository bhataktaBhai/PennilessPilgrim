package pennilessPilgrim;

import java.util.Scanner;

public abstract class Pilgrim
{
    static int rows, columns;
    static int[][] map;
    static int PRIORITY;
    
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter number of rows followed by columns.");
        System.out.println("Or just enter two zeroes for the TED-ED Riddle setting.");
        rows = 2 * scan.nextInt() - 1;
        columns = 2 * scan.nextInt() - 1;
        System.out.println("Find the shortest path or the path with the least tax (oooh, -ve)?");
        System.out.println("1. Prioritise steps\n2. Prioritise tax.\n3. Just give me one ASAP!");
        PRIORITY = scan.nextInt();
        
        //TED-ED
        if(rows == -1  ||  columns == -1) {
            rows = 9;
            columns = 9;
            map = new int[rows][columns]; 
            for(int i = 0; i < map.length; i += 2)
                for(int j = 0; j < map[0].length; j += 2)
                    map[i][j] = -1;
            map[0][1] = 1;
            map[0][3] = 2;
            Path bestPath = bestPath(0, 4, 3, 4);
            System.out.println(bestPath);
            System.out.println(bestPath.getTax() + " owed after " + (bestPath.getStep() - 1) + " steps.");
            return;
        }
        
        map = new int[rows][columns];
        
        for(int i = 0; i < map.length; i += 2)
            for(int j = 0; j < map[0].length; j += 2)
                map[i][j] = -1;
        
        Path bestPath = bestPath(0, 0, 1, 0);
        System.out.println(bestPath);
        System.out.println(bestPath.getTax() + " owed after " + (bestPath.getStep() - 1) + " steps.");
    }
    
    static Path bestPath(int row, int col, int step, double tax)
    {
        if(row == rows - 1  &&  col == columns - 1) {
            if(tax <= 0)
                return new Path(map, step, tax);
            else
                return null;
        }
        
        int[][] directions = {null, null, null, null};
        if(row + 1 < rows  &&  map[row + 1][col] == 0)
            directions[0] = new int[]{1, 0};
        if(row > 0  &&  map[row - 1][col] == 0)
            directions[1] = new int[]{-1, 0};
        if(col + 1 < columns  &&  map[row][col + 1] == 0)
            directions[2] = new int[]{0, 1};
        if(col > 0  &&  map[row][col - 1] == 0)
            directions[3] = new int[]{0, -1};
        
        double[] taxes = {tax * 2, tax / 2, tax + 2, tax - 2};
        Path best = null;
        double min = -1;
              
        for(int i = 0; i < 4; i++) {
            if(directions[i] == null)
                continue;
            int rJump = directions[i][0];
            int cJump = directions[i][1];
            map[row + rJump][col + cJump] = step;
            tax = taxes[i];
            Path nextPath = bestPath(row + 2*rJump,
                                     col + 2*cJump,
                                     step + 1,
                                     tax);
            if(nextPath != null) {
                if(PRIORITY == Path.NO_PRIORITY) {
                    return nextPath;
                }
                if(best == null  ||  nextPath.getPriority(PRIORITY) < min) {
                    best = nextPath;
                    min = nextPath.getPriority(PRIORITY);
                }
            }
            map[row + rJump][col + cJump] = 0;
        }
        
        return best;
    }
}