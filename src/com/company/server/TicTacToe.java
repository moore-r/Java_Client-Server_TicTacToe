package com.company.server;

import java.util.Scanner;

/**
 * This class will create the board, make moves and determine
 * if the game is over
 */
public class TicTacToe {
    public int Player1;
    public int Player2;
    public char board[] = new char[9];
    public boolean gameOver;

    /**
     * Constructor that creates the empty board slots, board will just be on command line
     */
    public TicTacToe() {
        // Set the players
        Player1 = 0;
        Player2 = 1;

        // The game is active
        gameOver = false;

        // Set the board
        char spot[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8'};

        for (int i = 0; i < 9; i += 1) {
            board[i] = spot[i];
        }
    }

    /**
     * Just prints the board
     */
    public String printBoard() {


        String theBoard = "\n\n\n"
                + "\t\t" + board[0] + "   | " + board[1] + "  | " + board[2] + "\n"
                + "\t\t ___|____|___ " + "\n"
                + "\t\t" + board[3] + "   | " + board[4] + "  | " + board[5] + "\n"
                + "\t\t ___|____|___ " + "\n"
                + "\t\t" + board[6] + "   | " + board[7] + "  | " + board[8] + "\n"
                + "\n\n\n";

        return theBoard;
    }

    /**
     * The function that actually makes a move and changes board
     *
     * @param player   the player making the move
     * @param position the spot on the board to be changed
     * @return whether or not the board was actually changed
     */
    public boolean makePlay(int player, int position) {
        // First check if the chosen space is available
        if (!checkSpace(position)) {
            return false;
        }

        // If it is, put the correct character in the space
        if (player == 0) {
            board[position] = 'X';
            return true;
        } else {
            board[position] = 'O';
            return true;
        }
    }

    /**
     * Simple function that checks if a spot in the board is already in use
     *
     * @param position The spot to check
     * @return true if spot is free, false if it is not.
     */
    public boolean checkSpace(int position) {
        if (board[position] == 'X' || board[position] == 'O') {
            return false;
        } else
            return true;
    }

    /**
     * This function calls the check over function
     *
     * @return if X won return 0, if y won return 1,
     * if a tie happened return 2, if the game is still going return 3
     */
    public int check() {
        if (checkOver('X')) {
            return 0;
        }

        if (checkOver('O')) {
            return 1;
        }

        // TODO: Before returning check for a tie
        if(checkTie()){
            // There was a tie
            return 2;
        }

        return 3;
    }

    /**
     * Check all the possible winning combos
     *
     * @param piece the character, X or O that we will be checking for
     * @return true if the gam has been won, false if not
     */
    public boolean checkOver(char piece) {
        if (board[0] == piece && board[1] == piece && board[2] == piece) return true;
        if (board[3] == piece && board[4] == piece && board[5] == piece) return true;
        if (board[6] == piece && board[7] == piece && board[8] == piece) return true;
        if (board[0] == piece && board[3] == piece && board[6] == piece) return true;
        if (board[1] == piece && board[4] == piece && board[7] == piece) return true;
        if (board[2] == piece && board[5] == piece && board[8] == piece) return true;
        if (board[0] == piece && board[4] == piece && board[8] == piece) return true;
        if (board[2] == piece && board[4] == piece && board[6] == piece) return true;

        return false;
    }

    /**
     * Simple function to chekc if all the spaces are taken
     * @return true if it is a tie
     */
    public boolean checkTie() {
        for (int i = 0; i < 9; i += 1) {
            if (board[i] != 'X' || board[i] != 'O') {
                // There are still spots left, continue
                return false;
            }
        }

        // If we haven't returned yet, all spots are taken.
        return true;
    }

    /**
     * Simple setter in case I need one
     */
    public void setGameOver(){
        gameOver = true;
    }
}
