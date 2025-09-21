package com.example.tictactoe;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button[][] buttons = new Button[3][3];
    private boolean playerXTurn = true;
    private int scoreX = 0;
    private int scoreO = 0;
    private TextView scoreLabel;
    private TextView turnLabel;
    private String playerXName = "Player X";
    private String playerOName = "Player O";
    private boolean isPlayerVsComputer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupGameBoard();
        selectGameMode();
    }

    private void initializeViews() {
        scoreLabel = findViewById(R.id.scoreLabel);
        turnLabel = findViewById(R.id.turnLabel);
        updateScore();
        updateTurnLabel();
    }

    private void setupGameBoard() {
        GridLayout gameBoard = findViewById(R.id.gameBoard);
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new Button(this);
                buttons[i][j].setLayoutParams(new GridLayout.LayoutParams());
                buttons[i][j].setTextSize(48);
                buttons[i][j].setMinHeight(200);
                buttons[i][j].setMinWidth(200);
                
                final int row = i;
                final int col = j;
                buttons[i][j].setOnClickListener(v -> onButtonClick(row, col));
                
                gameBoard.addView(buttons[i][j]);
            }
        }
    }

    private void onButtonClick(int row, int col) {
        if (!buttons[row][col].getText().toString().isEmpty()) {
            return;
        }

        buttons[row][col].setText(playerXTurn ? "X" : "O");
        
        if (checkForWin()) {
            String winner = playerXTurn ? playerXName : playerOName;
            Toast.makeText(this, "Congratulations " + winner + "! You have won!", Toast.LENGTH_LONG).show();
            
            if (playerXTurn) {
                scoreX++;
            } else {
                scoreO++;
            }
            updateScore();
            resetBoard();
        } else if (checkForDraw()) {
            Toast.makeText(this, "It's a draw! The game will reset.", Toast.LENGTH_LONG).show();
            resetBoardWithoutScore();
        } else {
            playerXTurn = !playerXTurn;
            updateTurnLabel();
            
            // If playing against computer, make the computer's move
            if (isPlayerVsComputer && !playerXTurn) {
                computerMove();
            }
        }
    }

    private void selectGameMode() {
        String[] options = {"Player vs Player", "Player vs Computer"};
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Game Mode");
        builder.setItems(options, (dialog, which) -> {
            isPlayerVsComputer = (which == 1);
            if (isPlayerVsComputer) {
                playerOName = "Computer";
            } else {
                playerOName = "Player O";
            }
            updateScore();
            updateTurnLabel();
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void computerMove() {
        int[] bestMove = minimax(0, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (bestMove[1] != -1 && bestMove[2] != -1) {
            buttons[bestMove[1]][bestMove[2]].setText("O");
            
            if (checkForWin()) {
                Toast.makeText(this, "Computer wins!", Toast.LENGTH_LONG).show();
                scoreO++;
                updateScore();
                resetBoard();
            } else if (checkForDraw()) {
                Toast.makeText(this, "It's a draw! The game will reset.", Toast.LENGTH_LONG).show();
                resetBoardWithoutScore();
            } else {
                playerXTurn = true;
                updateTurnLabel();
            }
        }
    }

    private int[] minimax(int depth, boolean isMaximizing, int alpha, int beta) {
        int score = evaluateBoard();
        if (score == 10) return new int[]{10, -1, -1}; // O wins
        if (score == -10) return new int[]{-10, -1, -1}; // X wins
        if (checkForDraw()) return new int[]{0, -1, -1}; // Draw

        int bestRow = -1, bestCol = -1;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttons[i][j].getText().toString().isEmpty()) {
                        buttons[i][j].setText("O");
                        int currentScore = minimax(depth + 1, false, alpha, beta)[0];
                        buttons[i][j].setText("");
                        if (currentScore > bestScore) {
                            bestScore = currentScore;
                            bestRow = i;
                            bestCol = j;
                        }
                        alpha = Math.max(alpha, bestScore);
                        if (beta <= alpha) {
                            break; // Beta cut-off
                        }
                    }
                }
            }
            return new int[]{bestScore, bestRow, bestCol};
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttons[i][j].getText().toString().isEmpty()) {
                        buttons[i][j].setText("X");
                        int currentScore = minimax(depth + 1, true, alpha, beta)[0];
                        buttons[i][j].setText("");
                        if (currentScore < bestScore) {
                            bestScore = currentScore;
                            bestRow = i;
                            bestCol = j;
                        }
                        beta = Math.min(beta, bestScore);
                        if (beta <= alpha) {
                            break; // Alpha cut-off
                        }
                    }
                }
            }
            return new int[]{bestScore, bestRow, bestCol};
        }
    }

    private int evaluateBoard() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().toString().equals(buttons[i][1].getText().toString()) && 
                buttons[i][0].getText().toString().equals(buttons[i][2].getText().toString())) {
                if (buttons[i][0].getText().toString().equals("O")) return 10;
                if (buttons[i][0].getText().toString().equals("X")) return -10;
            }
        }
        // Check columns
        for (int j = 0; j < 3; j++) {
            if (buttons[0][j].getText().toString().equals(buttons[1][j].getText().toString()) && 
                buttons[0][j].getText().toString().equals(buttons[2][j].getText().toString())) {
                if (buttons[0][j].getText().toString().equals("O")) return 10;
                if (buttons[0][j].getText().toString().equals("X")) return -10;
            }
        }
        // Check diagonals
        if (buttons[0][0].getText().toString().equals(buttons[1][1].getText().toString()) && 
            buttons[0][0].getText().toString().equals(buttons[2][2].getText().toString())) {
            if (buttons[0][0].getText().toString().equals("O")) return 10;
            if (buttons[0][0].getText().toString().equals("X")) return -10;
        }
        if (buttons[0][2].getText().toString().equals(buttons[1][1].getText().toString()) && 
            buttons[0][2].getText().toString().equals(buttons[2][0].getText().toString())) {
            if (buttons[0][2].getText().toString().equals("O")) return 10;
            if (buttons[0][2].getText().toString().equals("X")) return -10;
        }
        return 0; // No winner
    }

    private boolean checkForWin() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().toString().equals(buttons[i][1].getText().toString()) && 
                buttons[i][0].getText().toString().equals(buttons[i][2].getText().toString()) && 
                !buttons[i][0].getText().toString().isEmpty()) {
                return true;
            }
        }
        // Check columns
        for (int j = 0; j < 3; j++) {
            if (buttons[0][j].getText().toString().equals(buttons[1][j].getText().toString()) && 
                buttons[0][j].getText().toString().equals(buttons[2][j].getText().toString()) && 
                !buttons[0][j].getText().toString().isEmpty()) {
                return true;
            }
        }
        // Check diagonals
        if (buttons[0][0].getText().toString().equals(buttons[1][1].getText().toString()) && 
            buttons[0][0].getText().toString().equals(buttons[2][2].getText().toString()) && 
            !buttons[0][0].getText().toString().isEmpty()) {
            return true;
        }
        if (buttons[0][2].getText().toString().equals(buttons[1][1].getText().toString()) && 
            buttons[0][2].getText().toString().equals(buttons[2][0].getText().toString()) && 
            !buttons[0][2].getText().toString().isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean checkForDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().toString().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void updateScore() {
        scoreLabel.setText("Score - " + playerXName + ": " + scoreX + ", " + playerOName + ": " + scoreO);
    }

    private void updateTurnLabel() {
        turnLabel.setText((playerXTurn ? playerXName : playerOName) + "'s turn");
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        playerXTurn = true;
        updateTurnLabel();
    }

    private void resetBoardWithoutScore() {
        resetBoard();
    }

    public void onResetClick(View view) {
        scoreX = 0;
        scoreO = 0;
        updateScore();
        resetBoard();
    }

    public void onChangeModeClick(View view) {
        resetBoard();
        scoreX = 0;
        scoreO = 0;
        updateScore();
        selectGameMode();
    }
}