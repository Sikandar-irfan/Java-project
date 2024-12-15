package com.example;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TicTacToeSwing extends JFrame {
    private JButton[][] buttons = new JButton[3][3];
    private boolean playerXTurn = true;
    private int scoreX = 0;
    private int scoreO = 0;
    private JLabel scoreLabel;
    private JLabel turnLabel; 
    private String playerXName;
    private String playerOName;
    private boolean isPlayerVsComputer;

    public TicTacToeSwing() {
        setTitle("Tic-Tac-Toe");
        setSize(500, 500); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initial game mode selection
        selectGameMode();

        // Get player names
        getPlayerNames();

        scoreLabel = new JLabel("Score - " + playerXName + ": " + scoreX + ", " + playerOName + ": " + scoreO);
        add(scoreLabel, BorderLayout.NORTH);

        turnLabel = new JLabel(playerXName + "'s turn", JLabel.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 10));
        add(turnLabel, BorderLayout.WEST); 

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[i][j].addActionListener(new ButtonClickListener(i, j, this));
                panel.add(buttons[i][j]);
            }
        }
        add(panel, BorderLayout.CENTER);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetGame());
        add(resetButton, BorderLayout.SOUTH);

        JButton changeModeButton = new JButton("Change Mode");
        changeModeButton.addActionListener(e -> changeGameMode());
        add(changeModeButton, BorderLayout.EAST);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> exitGame());
        add(exitButton, BorderLayout.SOUTH); // Add exit button to the south
    }

    private void selectGameMode() {
        String[] options = {"Player vs Player", "Player vs Computer"};
        int choice = JOptionPane.showOptionDialog(this, "Select Game Mode", "Game Mode",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == JOptionPane.CLOSED_OPTION) {
            System.exit(0); // Exit if the dialog is closed
        }

        isPlayerVsComputer = (choice == 1); // true if Player vs Computer
    }

    private void getPlayerNames() {
        playerXName = getPlayerName("Enter name for Player X:");
        playerOName = isPlayerVsComputer ? "Computer" : getPlayerName("Enter name for Player O:");
    }
    
    private void getplayerName() {
        playerXName = getPlayerName("Enter name for Player X:");
    }

    private void changeGameMode() {
        // Reset the game state
        resetBoard();
        scoreX = 0;
        scoreO = 0;
        updateScore();

        // Select new game mode
        selectGameMode();

        // Get player names again if switching to Player vs Player
        if (!isPlayerVsComputer) {
            playerXName = getPlayerName("Enter name for Player X:");
            playerOName = getPlayerName("Enter name for Player O:");
        } else {
            getplayerName();
            playerOName = "Computer"; // Reset computer name
        }

        turnLabel.setText(playerXName + "'s turn"); // Reset turn label
        updateScore(); // Update the score display with new names
    }

    private String getPlayerName(String message) {
        String name;
        do {
            name = JOptionPane.showInputDialog(message);
            if (name == null) {
                System.exit(0); // Exit if the dialog is closed
            }
            if (name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty. Please enter a valid name.");
            }
        } while (name == null || name.trim().isEmpty());
        return name;
    }

    private class ButtonClickListener implements ActionListener {
        private int row, col;
        private TicTacToeSwing outer;

        public ButtonClickListener(int row, int col, TicTacToeSwing outer) {
            this.row = row;
            this.col = col;
            this.outer = outer;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (buttons[row][col].getText().isEmpty()) {
                buttons[row][col].setText(outer.playerXTurn ? "X" : "O");
                if (outer.checkForWin()) {
                    String winner = outer.playerXTurn ? outer.playerXName : outer.playerOName;
                    JOptionPane.showMessageDialog(null, "Congratulations " + winner + "! You have won!");
                    if (outer.playerXTurn) {
                        outer.scoreX++;
                    } else {
                        outer.scoreO++;
                    }
                    outer.updateScore();
                    outer.resetBoard();
                } else if (outer.checkForDraw()) {
                    JOptionPane.showMessageDialog(null, "It's a draw! The game will reset.");
                    outer.resetBoardWithoutScore();
                } else {
                    outer.turnLabel.setText((outer.playerXTurn ? outer.playerOName : outer.playerXName) + "'s turn");
                    outer.playerXTurn = !outer.playerXTurn;

                    // If playing against computer, make the computer's move
                    if (outer.isPlayerVsComputer && !outer.playerXTurn) {
                        outer.computerMove();
                    }
                }
            }
        }
    }

    private void computerMove() {
        int[] bestMove = minimax(0, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (bestMove[1] != -1 && bestMove[2] != -1) { // Check if valid move is found
            buttons[bestMove[1]][bestMove[2]].setText("O");
        } else {
            // Handle the case where no valid move is found
            JOptionPane.showMessageDialog(null, "No valid moves available.");
            return;
        }

        if (checkForWin()) {
            JOptionPane.showMessageDialog(null, "Computer wins!");
            scoreO++;
            updateScore();
            resetBoard();
        } else if (checkForDraw()) {
            JOptionPane.showMessageDialog(null, "It's a draw! The game will reset.");
            resetBoardWithoutScore();
        } else {
            turnLabel.setText(playerXName + "'s turn");
            playerXTurn = true;
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
                    if (buttons[i][j].getText().isEmpty()) {
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
                    if (buttons[i][j].getText().isEmpty()) {
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
            if (buttons[i][0].getText().equals(buttons[i][1].getText()) && 
                buttons[i][0].getText().equals(buttons[i][2].getText())) {
                if (buttons[i][0].getText().equals("O")) return 10;
                if (buttons[i][0].getText().equals("X")) return -10;
            }
        }
        // Check columns
        for (int j = 0; j < 3; j++) {
            if (buttons[0][j].getText().equals(buttons[1][j].getText()) && 
                buttons[0][j].getText().equals(buttons[2][j].getText())) {
                if (buttons[0][j].getText().equals("O")) return 10;
                if (buttons[0][j].getText().equals("X")) return -10;
            }
        }
        // Check diagonals
        if (buttons[0][0].getText().equals(buttons[1][1].getText()) && 
            buttons[0][0].getText().equals(buttons[2][2].getText())) {
            if (buttons[0][0].getText().equals("O")) return 10;
            if (buttons[0][0].getText().equals("X")) return -10;
        }
        if (buttons[0][2].getText().equals(buttons[1][1].getText()) && 
            buttons[0][2].getText().equals(buttons[2][0].getText())) {
            if (buttons[0][2].getText().equals("O")) return 10;
            if (buttons[0][2].getText().equals("X")) return -10;
        }
        return 0; // No winner
    }

    private void exitGame() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private boolean checkForDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    return false; // If any button is empty, it's not a draw
                }
            }
        }
        return true; // All buttons are filled, it's a draw
    }

    private boolean checkForWin() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(buttons[i][1].getText()) && 
                buttons[i][0].getText().equals(buttons[i][2].getText()) && 
                !buttons[i][0].getText().isEmpty()) {
                return true;
            }
        }
        // Check columns
        for (int j = 0; j < 3; j++) {
            if (buttons[0][j].getText().equals(buttons[1][j].getText()) && 
                buttons[0][j].getText().equals(buttons[2][j].getText()) && 
                !buttons[0][j].getText().isEmpty()) {
                return true;
            }
        }
        // Check diagonals
        if (buttons[0][0].getText().equals(buttons[1][1].getText()) && 
            buttons[0][0].getText().equals(buttons[2][2].getText()) && 
            !buttons[0][0].getText().isEmpty()) {
            return true;
        }
        if (buttons[0][2].getText().equals(buttons[1][1].getText()) && 
            buttons[0][2].getText().equals(buttons[2][0].getText()) && 
            !buttons[0][2].getText().isEmpty()) {
            return true;
        }
        return false; // No winner
    }

    private void updateScore() {
        scoreLabel.setText("Score - " + playerXName + ": " + scoreX + ", " + playerOName + ": " + scoreO);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        playerXTurn = true;
        turnLabel.setText(playerXName + "'s turn"); // Reset turn label
    }

    private void resetBoardWithoutScore() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        playerXTurn = true;
        turnLabel.setText(playerXName + "'s turn"); // Reset turn label
    }

    private void resetGame() {
        scoreX = 0;
        scoreO = 0;
        updateScore();
        resetBoard();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToeSwing game = new TicTacToeSwing();
            game.setVisible(true);
        });
    }
}