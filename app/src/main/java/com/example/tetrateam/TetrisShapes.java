package com.example.tetrateam;

//Class that define the Tetris shapes (colors and shapes) for the game
public class TetrisShapes {
    // store the location of current shape (0 means empty)
    private static int[][] currentShape;


    // Tetris shape's as matrix
    public static final int[][] SHAPE_I = {
            {1, 1, 1, 1}
    };

    public static final int[][] SHAPE_O = {
            {2, 2},
            {2, 2}
    };

    public static final int[][] SHAPE_T = {
            {0, 3, 0},
            {3, 3, 3}
    };

    public static final int[][] SHAPE_S = {
            {0, 4, 4},
            {4, 4, 0}
    };

    public static final int[][] SHAPE_Z = {
            {4, 4, 0},
            {0, 4, 4}
    };

    public static final int[][] SHAPE_J = {
            {5, 0, 0},
            {5, 5, 5}
    };

    public static final int[][] SHAPE_L = {
            {0, 0, 5},
            {5, 5, 5}
    };

    // function that return random shape
    public static int[][] getRandomShape() {
        int random = (int) (Math.random() * 6);
        if (random == 0) {
            currentShape = SHAPE_I;
        } else if (random == 1) {
            currentShape = SHAPE_O;
        } else if (random == 2) {
            currentShape = SHAPE_T;
        } else if (random == 3) {
            currentShape = SHAPE_S;
        } else if (random == 4) {
            currentShape = SHAPE_Z;
        } else if (random == 5) {
            currentShape = SHAPE_J;
        } else {
            currentShape = SHAPE_L;
        }
        return currentShape;
    }

    //function that return the current shape
    public static int[][] getCurrentShape() {
        return currentShape;
    }

    // function that set the current shape
    public static void setCurrentShape(int[][] shape) {
        currentShape = shape;
    }
}

