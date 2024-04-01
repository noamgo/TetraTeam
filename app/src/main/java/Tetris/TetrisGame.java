package Tetris;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tetrateam.BaseMenuActivity;
import com.example.tetrateam.FirebaseManager;
import com.example.tetrateam.GameMenuActivity;
import com.example.tetrateam.MusicService;
import com.example.tetrateam.R;

import java.util.Locale;

public class TetrisGame extends BaseMenuActivity {

    int[][] board;
    GridLayout gridLayout;
    ImageButton btnRotate;
    TextView tvScore, tvLevel;

    boolean isGameOver = false;


    // Define the number of rows and columns
    int numRows = 20;
    int numCols = 10;

    // store the location of current shape
    int currentShapeRow = 0;
    int currentShapeCol = 0;

    // keep player score
    int score = 0;
    // keep player level
    int level = 0;
    // keep player lines cleared
    int totalLinesCleared = 0;

    private final Handler handler = new Handler();
    private int DELAY_MS;
    private TextToSpeech tsp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tetris_game_screen);

        board = new int[numRows][numCols];

        tvLevel = findViewById(R.id.tvLevel);

        tvScore = findViewById(R.id.tvScore);

        // start the game
        startGame();

        btnRotate = findViewById(R.id.btnRotate);
        btnRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotateShape();
            }
        });

        gridLayout = findViewById(R.id.gridLayout);
        gridLayout.setOnTouchListener(new OnSwipeTouchListener(TetrisGame.this) {
            public void onSwipeRight() {
                moveRight();
            }

            public void onSwipeLeft() {
                moveLeft();
            }

            public void onSwipeBottom() {
                // Define a Runnable to handle smooth downward movement
                Runnable smoothMoveDown = new Runnable() {
                    @Override
                    public void run() {
                        if (!isCollisionDown(TetrisShapes.getCurrentShape(), currentShapeCol, currentShapeRow) && currentShapeRow != 0) {
                            moveDown();

                            // Schedule the next move after a short delay
                            handler.postDelayed(this, DELAY_MS / 10); // Adjust the delay as needed for smoother movement
                        } else {
                            // Collision detected, stop smooth movement
                            handler.removeCallbacks(this);
                        }
                    }
                };
                // Start smooth downward movement
                handler.post(smoothMoveDown);
            }
        });

        tsp = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    int lang = tsp.setLanguage(Locale.ENGLISH);
                }
            }
        });
    }

    public void startGame() {
        score = 0;
        tvScore.setText("score: " + score);
        level = 0;
        tvLevel.setText("level: " + level);
        initializeGameBoard();
        createShape(numRows, numCols);
        startAutoMoveDown();
        isGameOver = false;
        startMusic();
    }

    private void initializeGameBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                // Set initial values for the board (0 for empty)
                board[i][j] = 0;
            }
        }
        UpdateBoard();
    }

    private boolean isPlaceAvailable(int[][] shape, int row, int col) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != 0) {
                    // Check boundaries and set the Tetrimino on the board
                    if (board[row + i][col + j] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Method to spawn a new Shape on the board that randomize a shape
    private void createShape(int numRows, int numCols) {
        int[][] selectedShape = TetrisShapes.getRandomShape();

        int initialRow = 0;
        int initialCol = numCols / 2 - selectedShape[0].length / 2;

        if (!isPlaceAvailable(selectedShape, initialRow, initialCol)) {
            gameOver();
        }
        else
            putShapeOnBoard(selectedShape, initialRow, initialCol);
    }

    // Method to set a shape on the game board
    private boolean putShapeOnBoard(int[][] shape, int row, int col) {
        // Store the position of the current shape
        currentShapeRow = row;
        currentShapeCol = col;

        if (!isPlaceAvailable(shape, row, col)) {
            return false;
        }
        // Implement logic to set the Tetrimino on the board
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != 0) {
                    // Check boundaries and set the Tetrimino on the board
                    if (row + i < board.length && col + j < board[0].length) {
                        board[row + i][col + j] = shape[i][j];
                    }
                }
            }
        }


        // Update the board graphics
        UpdateBoard();
        return true;
    }

    private void UpdateBoard() {
        // Alternate between "hello_pic" and "background" for each column
        for (int row = 0; row < board.length; row++) {
            // Iterate through columns
            for (int col = 0; col < board[0].length; col++) {
                ImageChanger(row, col);
            }

        }
    }

    /*
     menu for colors:
     0 - background
     1 - red
     2 - yellow
     3 - blue
     4 - green
     5 - purple
     */
    private void ImageChanger(int r, int c) {
        // Get the ID of the ImageView based on row and column indices
        int imageViewId = getResources().getIdentifier("c" + c + "r" + r, "id", getPackageName());
        ImageView imageView = findViewById(imageViewId);

        if (imageView != null) {
            // Set the image resource based on the value in the board array
            switch (board[r][c]) {
                case 0:
                    imageView.setImageResource(R.drawable.tetris_background);
                    break;
                case 1:
                    imageView.setImageResource(R.drawable.red_block);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.yellow_block);
                    break;
                case 3:
                    imageView.setImageResource(R.drawable.blue_block);
                    break;
                case 4:
                    imageView.setImageResource(R.drawable.green_block);
                    break;
                case 5:
                    imageView.setImageResource(R.drawable.purple_block);
                    break;
            }
        } else {
            Log.e("ImageChanger", "ImageView not found for row " + r + " and column " + c);
        }
    }

    private boolean isCollisionDown(int[][] shape, int col, int row) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                // If the current cell of the shape is filled
                if ((shape[i][j] != 0 && i == shape.length - 1) || ((shape[i][j] != 0 && shape[i + 1][j] == 0))) {
                    // Check if the cell below is out of bounds or occupied
                    if (row + i + 1 >= board.length || board[row + i + 1][col + j] != 0) {
                        return true; // Collision downwards detected
                    }
                }
            }
        }
        return false;
    }

    private boolean isCollisionRight(int[][] shape, int col, int row) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                // If the current cell of the shape is filled
                if ((shape[i][j] != 0 && j == shape[0].length - 1) || ((shape[i][j] != 0 && shape[i][j + 1] == 0))) {
                    // Check if the cell below is out of bounds or occupied
                    if (col + j + 1 >= board[0].length || board[row + i][col + j + 1] != 0) {
                        return true; // Collision downwards detected
                    }
                }
            }
        }
        return false;
    }

    private boolean isCollisionLeft(int[][] shape, int col, int row) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                // If the current cell of the shape is filled
                if ((shape[i][j] != 0 && j == 0) || ((shape[i][j] != 0 && shape[i][j - 1] == 0))) {
                    // Check if the cell below is out of bounds or occupied
                    if (col + j - 1 < 0 || board[row + i][col + j - 1] != 0) {
                        return true; // Collision downwards detected
                    }
                }
            }
        }
        return false;
    }

    //check all sides at once
    private boolean isCollision(int[][] shape, int col, int row) {
        if (isCollisionDown(shape, col, row) || isCollisionRight(shape, col, row) || isCollisionLeft(shape, col, row)) {
            return true;
        }
        return false;
    }

    private void moveDown() {
        if (!isCollisionDown(TetrisShapes.getCurrentShape(), currentShapeCol, currentShapeRow)) {
            clearCurrentShape(); // Clear the current shape from its previous position

            // Iterate over the shape's cells and move each cell down if the cell below is empty
            for (int i = TetrisShapes.getCurrentShape().length - 1; i >= 0; i--) {
                for (int j = 0; j < TetrisShapes.getCurrentShape()[0].length; j++) {
                    if (TetrisShapes.getCurrentShape()[i][j] != 0) {
                        // Remove the condition for board bounds check if you're sure it won't go out of bounds
                        board[currentShapeRow + i + 1][currentShapeCol + j] = board[currentShapeRow + i][currentShapeCol + j];
                        board[currentShapeRow + i][currentShapeCol + j] = 0;
                    }
                }
            }

            // Update the current shape position
            currentShapeRow++;

            putShapeOnBoard(TetrisShapes.getCurrentShape(), currentShapeRow, currentShapeCol); // Put the shape in its new position
            UpdateBoard(); // Update the board graphics
        } else {
            // Current shape has stopped, create a new shape
            removeLines();
            createShape(board.length, board[0].length);
            currentShapeRow = 0;
            currentShapeCol = board[0].length / 2 - TetrisShapes.getCurrentShape()[0].length / 2;
            putShapeOnBoard(TetrisShapes.getCurrentShape(), currentShapeRow, currentShapeCol);
        }
    }

    private void moveLeft() {
        if (!isCollisionLeft(TetrisShapes.getCurrentShape(), currentShapeCol, currentShapeRow)) {
            clearCurrentShape(); // Clear the current shape from its previous position

            // Iterate over the shape's cells and move each cell to the left if the cell to the left is empty
            for (int i = 0; i < TetrisShapes.getCurrentShape().length; i++) {
                for (int j = 0; j < TetrisShapes.getCurrentShape()[0].length; j++) {
                    if (TetrisShapes.getCurrentShape()[i][j] != 0) {
                        board[currentShapeRow + i][currentShapeCol + j - 1] = board[currentShapeRow + i][currentShapeCol + j];
                        board[currentShapeRow + i][currentShapeCol + j] = 0;
                    }
                }
            }

            // Update the current shape position
            currentShapeCol--;

            putShapeOnBoard(TetrisShapes.getCurrentShape(), currentShapeRow, currentShapeCol); // Put the shape in its new position
            UpdateBoard(); // Update the board graphics
        }
    }

    private void moveRight() {
        if (!isCollisionRight(TetrisShapes.getCurrentShape(), currentShapeCol, currentShapeRow)) {
            clearCurrentShape(); // Clear the current shape from its previous position

            // Iterate over the shape's cells and move each cell to the right if the cell to the right is empty
            for (int i = 0; i < TetrisShapes.getCurrentShape().length; i++) {
                for (int j = TetrisShapes.getCurrentShape()[0].length - 1; j >= 0; j--) {
                    if (TetrisShapes.getCurrentShape()[i][j] != 0) {
                        board[currentShapeRow + i][currentShapeCol + j + 1] = board[currentShapeRow + i][currentShapeCol + j];
                        board[currentShapeRow + i][currentShapeCol + j] = 0;
                    }
                }
            }

            // Update the current shape position
            currentShapeCol++;

            putShapeOnBoard(TetrisShapes.getCurrentShape(), currentShapeRow, currentShapeCol); // Put the shape in its new position
            UpdateBoard(); // Update the board graphics
        }
    }

    private void clearCurrentShape() {
        // Clear the current shape from its current position on the board
        for (int i = 0; i < TetrisShapes.getCurrentShape().length; i++) {
            for (int j = 0; j < TetrisShapes.getCurrentShape()[0].length; j++) {
                if (TetrisShapes.getCurrentShape()[i][j] != 0) {
                    board[currentShapeRow + i][currentShapeCol + j] = 0;
                }
            }
        }
    }

    // Rotate the current shape clockwise
    private void rotateShape() {
        if (!isCollision(TetrisShapes.getCurrentShape(), currentShapeCol, currentShapeRow)) {
            // Clear the current shape from its previous position
            clearCurrentShape();

            // Get the current shape and its dimensions
            int[][] currentShape = TetrisShapes.getCurrentShape();
            int rows = currentShape.length;
            int cols = currentShape[0].length;

            // Create a new array for the rotated shape
            int[][] rotatedShape = new int[cols][rows];


            // Rotate the shape clockwise
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    rotatedShape[j][rows - 1 - i] = currentShape[i][j];
                }
            }


            // Update the current shape with the rotated shape
            TetrisShapes.setCurrentShape(rotatedShape);

            // Put the rotated shape on the board
            putShapeOnBoard(rotatedShape, currentShapeRow, currentShapeCol);

            // Update the board graphics
            UpdateBoard();
        }
    }

    public boolean isLineComplete(int row) {
        for (int i = 0; i < board[0].length; i++) {
            if (board[row][i] == 0) {
                return false;
            }
        }
        return true;
    }

    public void levelUp() {
        if (totalLinesCleared <= 50) {
            level = totalLinesCleared / 10;
            DELAY_MS = 700 - level * 150;
            tvLevel.setText("Level: " + level);
        }
    }

    public void removeLines() {
        int linesRemoved = 0;
        for (int i = 0; i < board.length; i++) {
            if (isLineComplete(i)) {
                linesRemoved++;
                for (int j = i; j > 0; j--) {
                    for (int k = 0; k < board[0].length; k++) {
                        board[j][k] = board[j - 1][k];
                    }
                }
            }
        }
        if (linesRemoved > 0) {
            score += linesRemoved * 100 + (linesRemoved - 1) * 25;
            tvScore.setText("Score: " + score);
            totalLinesCleared += linesRemoved;
            levelUp();
        }
    }

    public void gameOver() {
        Toast.makeText(TetrisGame.this, "Game Over", Toast.LENGTH_SHORT).show();
        showWinnerPopup();
        stopMusic();
        isGameOver = true;
        FirebaseManager.updateHighScore(score);
    }

    // Method to show the pop-up screen
    private void showWinnerPopup() {
        // Create an AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflate the winner_popup.xml layout
        View popupView = getLayoutInflater().inflate(R.layout.activity_end_game_pop_up, null);

        // Set the custom layout to the AlertDialog.Builder
        builder.setView(popupView);

        // Create the AlertDialog
        AlertDialog dialog = builder.create();

        // Find views from the layout
        TextView endGameTextView = popupView.findViewById(R.id.tvGameEnded);
        Button restartButton = popupView.findViewById(R.id.restartButton);
        Button menuButton = popupView.findViewById(R.id.menuButton);

        // Set the text of the TextView
        endGameTextView.setText("Game ended!!! \n" + "Your score is: \n" + score);

        // Set the text of the TextToSpeech
        tsp.speak("Game ended!!! Your score is: " + score, TextToSpeech.QUEUE_FLUSH, null, "");

        // Set a click listener for the close button
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog when the close button is clicked
                dialog.dismiss();
                startGame();
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog when the close button is clicked
                dialog.dismiss();
                Intent intent = new Intent(TetrisGame.this, GameMenuActivity.class);
                startActivity(intent);
            }
        });
        // Show the dialog
        dialog.show();
    }

    private void startMusic() {
        Intent musicIntent = new Intent(this, MusicService.class);
        startService(musicIntent);
    }

    private void stopMusic() {
        Intent musicIntent = new Intent(this, MusicService.class);
        stopService(musicIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAutoMoveDown();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAutoMoveDown();
    }

    private void startAutoMoveDown() {
        handler.postDelayed(autoMoveDownRunnable, DELAY_MS);
    }

    private void stopAutoMoveDown() {
        handler.removeCallbacks(autoMoveDownRunnable);
    }

    private Runnable autoMoveDownRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isGameOver) {
                moveDown();
                levelUp();
                handler.postDelayed(this, DELAY_MS);
            }
        }
    };
}
