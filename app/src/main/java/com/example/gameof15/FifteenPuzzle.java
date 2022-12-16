package com.example.gameof15;

import android.graphics.Color;

import java.awt.event.*;
import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public class FifteenPuzzle implements MouseListener
{
    private int[][] grid; // the current positions of the tiles and space, denoted by 0..15
    private int xspace;   // xspace,yspace are the current coordinates of the space
    private int yspace;
    private SimpleCanvas sc; // the canvas for display

    private final int size     = 4;               // the number of tiles across and down
    private final int tilesize = 100;              // the size of a tile
    private final int borderspace = 6;
    private final int gridsize = size * tilesize;  // the size of the grid
    //Colors
    private Color bgColor = Color.WHITE;
    private Color tileColor = Color.gray;
    private Color freeSpaceColor = bgColor;
    private Color borderColor = Color.red;
    private Color tileNumColor = Color.white;
    private        int[][] goal    = {{1,5,9,13}, {2,6,10,14}, {3,7,11,15}, {4,8,12,0}};
    // these two are public so that they can be used in BlueJ
    public  static int[][] close   = {{1,5,9,13}, {2,6,10,14}, {3,7,11, 0}, {4,8,12,15}};
    public  static int[][] example = {{5,11,14,0}, {9,3,13,7}, {2,8,10,12}, {4,1,15,6}};

    // this constructor sets up the grid as initialGrid and displays it on the canvas
    // (plus it initialises the other instance variables)
    public FifteenPuzzle (int[][] initialGrid)
    {
        this.grid = initialGrid;
        for(int i = 0; i<grid.length; i++){
            for(int j = 0; j<grid[i].length; j++)
            {
                if(grid[i][j] == 0){xspace =i; yspace=j;}
            }
        }
        sc = new SimpleCanvas("Fifteen Puzzle",gridsize + 1,gridsize +1, bgColor);
        sc.addMouseListener(this);
        drawGrid();
    }

    // this constructor sets up the grid as goal,
    // then it makes random moves to set up the puzzle and displays it on the canvas
    // (plus it initialises the other instance variables)
    public FifteenPuzzle ()
    {
        this.grid = goal;
        for(int i = 0; i<grid.length; i++){
            for(int j = 0; j<grid[i].length; j++)
            {
                if(grid[i][j] == 0){xspace =i; yspace=j;}
            }
        }
        sc = new SimpleCanvas("Fifteen Puzzle",gridsize + 1,gridsize +1, bgColor);
        sc.addMouseListener(this);
        drawGrid();
        sc.wait(4000);
        for(int attempt = 0; attempt<=10000; attempt++)
        {
            int x = ThreadLocalRandom.current().nextInt(0,4);
            int y = ThreadLocalRandom.current().nextInt(0,4);
            moveTile(x,y);
        }
    }

    /*
     * This Method checks if the constructor argument is legal
     * i.e. a 4x4 grid containing the numbers 0 to 15 inclusive.
     */
    private boolean legalGrid(int[][] testGrid)
    {
        return false;
    }
    // returns true iff x,y is adjacent to the space
    private boolean legalClick(int x, int y)
    {
        return Math.abs(x - xspace) + Math.abs(y - yspace) == 1;
    }

    // returns true iff the puzzle is finished
    private boolean finished()
    {
        if(grid == goal){return true;}
        else {return false;}
    }

    // moves the tile at x,y into the space, if it is adjacent,
    // and re-draws the grid; o/w do nothing
    public void moveTile (int x, int y)
    {
        if(legalClick(x,y))
        {
            grid[xspace][yspace] = grid[x][y];
            grid[x][y] =0;
            xspace = x;
            yspace = y;
            drawGrid();
        }
    }

    // draws the current grid on the canvas
    private void drawGrid()
    {
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++)
            {
                if(grid[i][j] !=0)
                {
                    drawTile(i,j,tileColor);
                    drawTilenum(i,j,tileNumColor);
                }
                else //Draws the free space
                {
                    drawTile(xspace, yspace, this.freeSpaceColor);
                }
                sc.repaint();
            }
        }

    }

    // draws the tile at x,y in colour c at the appropriate spot on the canvas
    private void drawTile(int x, int y, Color c)
    {
        sc.setForegroundColour(c);
        sc.drawRectangle(x*tilesize +borderspace, y*tilesize+borderspace,
                (x+1)*tilesize - borderspace, (y+1)*tilesize-borderspace);
    }

    private void drawTilenum(int x, int y, Color c)
    {
        sc.setForegroundColour(c);
        sc.drawString(Integer.toString(grid[x][y]),
                x * tilesize + tilesize / 2 - (grid[x][y] / 10 * 4 + 2),
                y * tilesize + tilesize / 2 + tilesize / 10);
    }

    public void mouseClicked (MouseEvent e) {
        moveTile(e.getX() / (tilesize-borderspace), e.getY() / (tilesize-borderspace));

    }

    public void mousePressed (MouseEvent e) {


    }

    public void mouseReleased(MouseEvent e) {


    }

    public void mouseEntered (MouseEvent e) {


    }

    public void mouseExited (MouseEvent e) {

    }
    public static void main(String[] args) {
        System.out.println("Hello");
        FifteenPuzzle fifteenP1 = new FifteenPuzzle();
    }
}