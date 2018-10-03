package com.company.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * The server class, creates the board and gets moves from a player
 * Overall organizes the whole game
 */
public class Server {
    public static TicTacToe tac;

    /**
     * The main class implements most things here, should have broken it up into more functions
     * @param args no command line args
     * @throws IOException just in case
     */
    public static void main(String[] args) throws IOException{
        // Variables
        ServerSocket tictac;
        Socket sSock;
        DataInputStream rec;
        DataOutputStream send;
        Scanner read;
        String display;
        int checkWin = 3;

        try {

            // First we need to get the server to connect to the client
            tictac = new ServerSocket(8080);

            // Accept the connection
            sSock = tictac.accept();

            // Set up the data receiver and transmitter
            rec = new DataInputStream(new BufferedInputStream(sSock.getInputStream()));
            send = new DataOutputStream(sSock.getOutputStream());

            // Set up a quick input scanner
            read = new Scanner(System.in);

            // Build and print the board
            tac = new TicTacToe();

            // Before the game begins, send the empty game board to the client and self
            display = tac.printBoard();
            send.writeUTF(display);
            System.out.print(display);

            // The actual game, moves happen here, client goes first
            checkWin = checkOver();
            while(checkWin == 3){

                // The clients move
                String move = rec.readUTF();
                int numMove = Integer.parseInt(move);
                tac.makePlay(0, numMove);

                // Send to the client and to self the new board
                display = tac.printBoard();
                send.writeUTF(display);
                System.out.print(display);

                // Check if the game is over again
                checkWin = checkOver();
                if(checkWin != 3)
                    break;

                // The servers move
                System.out.println("Where would you like to move? 0-8");
                String sMove = read.nextLine();
                int sIntMove = Integer.parseInt(sMove);
                tac.makePlay(1,sIntMove);

                // Send the client the new board and display it to ourselves
                display = tac.printBoard();
                send.writeUTF(display);
                System.out.print(display);
            }

            // The game is over, notify the client
            send.writeUTF("over");

            // Display the winner
            showBoard();

            // Notify the client of the winner
            String winner = winnerMessage(checkWin);
            send.writeUTF(winner);
            display = tac.printBoard();
            send.writeUTF(display);

            // Close everything
            tictac.close();
            sSock.close();
            rec.close();
            send.close();

        }
        catch (IOException i){
            System.out.println(i);
        }
    }

    /**
     * This class calls the function in the tic tac toe class to check if the game
     * is over and facillitates its response
     * @return 3 if the game is still going
     */
    public static int checkOver(){
        int checker = tac.check();

        // Keep the game going
        switch (checker){
            case 0:
                System.out.println("\n\nYou lost. Game Over.\n\n");
                showBoard();
                break;
            case 1:
                System.out.println("\n\nYou won! Game Over.\n\n");
                showBoard();
                break;
            case 2:
                System.out.println("\n\nTie. Game Over.\n\n");
                showBoard();
                break;
            // Default continues game
            default:
                return checker;
        }

        return checker;
    }

    /**
     * Simple function that prints the board to the server
     */
    public static void showBoard(){
        String toDisplay = tac.printBoard();
        System.out.print(toDisplay);
    }

    /**
     * This function gets the winning message to send to the client
     * @param checker
     * @return
     */
    public static String winnerMessage(int checker) {
        String toReturn;

        switch (checker) {
            case 0:
                toReturn = "\n\nYou won! Game Over.\n\n";
                break;
            case 1:
                toReturn = "\n\nYou lost. Game Over.\n\n";
                break;
            default:
                toReturn = "\n\nTie. Game Over.\n\n";
                break;
        }

        return toReturn;
    }
}