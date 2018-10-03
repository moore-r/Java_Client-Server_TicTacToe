package com.company.client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * The client class, gets moves from the user, sends them to
 * the server
 */
public class TicClient {

    /**
     * Main implements pretty much everything for the client
     * @param args no args
     * @throws IOException just in case
     */
    public static void main(String[] args) throws IOException{
        // Some variables
        DataInputStream from;
        DataOutputStream to;
        Scanner reader = new Scanner(System.in);
        Socket cSock;
        boolean gameOver = false;
        String cDisplay;

        // First get the Ip address from the user
        System.out.println("Enter the IP address of the machine to connect to: ");
        String IP = reader.nextLine();

        try {
            // Create the socket
            cSock = new Socket(IP, 8080);

            // Create input and output streams
            from = new DataInputStream(new BufferedInputStream(cSock.getInputStream()));
            to = new DataOutputStream(cSock.getOutputStream());

            // Get the empty game board from the server and display it
            cDisplay = from.readUTF();
            displayClientBoard(cDisplay);

            // Now play the game
            while(!gameOver){
                // Get the move from the client
                System.out.println("Where would you like to move? 0-8");
                String cMove = reader.nextLine();

                // Send the move to the server
                to.writeUTF(cMove);

                // Receive the board twice
                for(int i = 0; i < 2; i += 1) {
                    cDisplay = from.readUTF();

                    // Check if the game is over
                    if(cDisplay.contentEquals("over")){
                        gameOver = true;
                        break;
                    }
                    else
                        displayClientBoard(cDisplay);
                }
            }

            // Display the game over message
            cDisplay = from.readUTF();
            System.out.print(cDisplay);

            // Display the board one last time
            cDisplay = from.readUTF();
            System.out.println("Final board: ");
            System.out.print(cDisplay);

            // Close everything
            from.close();
            to.close();
            cSock.close();

        }
        catch (IOException p){
            System.out.println(p);
        }

    }

    /**
     * Simple function to display the board
     * @param board the board
     */
    public static void displayClientBoard(String board){
        System.out.print(board);
    }
}
