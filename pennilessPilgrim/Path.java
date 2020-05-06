/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pennilessPilgrim;

/**
 *
 * @author Mac
 */
public class Path
{
    public static final int STEP_PRIORITY = 1;
    public static final int TAX_PRIORITY = 2;
    public static final int NO_PRIORITY = 3;
    
    private final int[][] MAP;
    private final int STEP;
    private final double TAX;
    
    public Path(int[][] map, int step, double tax) {
        this.STEP = step;
        this.TAX = tax;
        this.MAP = new int[map.length][];
        for(int i = 0; i < map.length; i++) {
            MAP[i] = map[i].clone();
        }
    }

    public double getTax() {
        return TAX;
    }

    public int getStep() {
        return STEP;
    }
    
    public double getPriority(int priority) {
        if(priority == STEP_PRIORITY)
            return STEP;
        if(priority == TAX_PRIORITY)
            return TAX;
        return 1/0;
    }
    
    @Override
    public String toString() {
        String s = "";
        for(int[] row : MAP) {
            for(int val : row) {
                switch(val) {
                    case 0:
                        s += "   ";
                        break;
                    case -1:
                        s += " * ";
                        break;
                    default:
                        s += String.format("%2d ", val);
                }
            }
            s += "\n";
        }
        return s;
    }
}
