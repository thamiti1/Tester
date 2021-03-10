package TCP;

import java.net.*;
import java.io.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Base64;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.*;

public class TCPClient {
	public static void main (String args[]) {
			try {
				/**
					if (args.length != 2) {
							System.out.println("Usage: gradle TCPClient -Pport=8080 -Phost=localhost");
							System.exit(0);
					}
					**/
					int port = -1;
					try {
							port = Integer.parseInt(args[1]);
					} catch (NumberFormatException nfe) {
							System.out.println("[Port] must be an integer");
							System.exit(2);
					}
					String host = args[0];
					Socket server = new Socket(host, port);
					System.out.println("Connected to server at " + host + ":" + port);
					InputStream in = server.getInputStream();
					OutputStream out = server.getOutputStream();
					BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

					int bufLen = 1024;
					byte bytesReceived[] = new byte[bufLen];
					while(true) {
							
							System.out.println("What is your name?");
							System.out.println("Type 'quit' to exit game");
							String nameSend = stdin.readLine();
							if (nameSend.equals("quit")) {
								break;
							}

							/* send to server */
							byte bytes1[] = nameSend.getBytes();
							out.write(bytes1,0,bytes1.length);
							
							/* read from server */
							int numBytesReceived = in.read(bytesReceived, 0, bufLen);
							String nameReceive = new String(bytesReceived,0,numBytesReceived);
							
							System.out.println("Hello " + nameSend + ", how many questions would you like?");
							System.out.println("The maximum number of questions is 6");
							String numOfQuestions = stdin.readLine();
							byte bytes2[] = numOfQuestions.getBytes();
							out.write(bytes2,0,bytes2.length);
							
							System.out.println("Enter 'start' to begin game");
							String userInput = stdin.readLine();
							byte bytes3[] = userInput.getBytes();
							out.write(bytes3,0,bytes3.length);
							
							while(userInput.equals("start")) {
								System.out.println("Timer started");
								byte bytesReceived2[] = new byte[bufLen];
								
								// Question 1
								System.out.println("What is the opposite of white?");
								String answer = stdin.readLine();
								if (answer.equals("quit")) {
									byte bytes4[] = answer.getBytes();
								out.write(bytes4,0,bytes4.length);
									break;
								}
								byte bytes4[] = answer.getBytes();
								out.write(bytes4,0,bytes4.length);
								int numBytesReceived2 = in.read(bytesReceived2, 0, bufLen);
								String serverResponse = new String(bytesReceived2,0,numBytesReceived2);
								
								
								System.out.println(serverResponse);
								
								// Question 2
							System.out.println("Which is the largest number [32,12,95,78,89]");
								byte bytesReceived3[] = new byte[bufLen];
								
								String answer2 = stdin.readLine();
								if (answer2.equals("quit")) {
									break;
								}
								byte bytes5[] = answer2.getBytes();
								out.write(bytes5,0,bytes5.length);
								
								int numBytesReceived3 = in.read(bytesReceived3, 0, bufLen);
								String serverResponse2 = new String(bytesReceived3,0,numBytesReceived3);
								
								System.out.println(serverResponse2);
								
								// Question 3
								System.out.println("12*3 = ?");
								byte bytesReceived4[] = new byte[bufLen];
								
								String answer3 = stdin.readLine();
								if (answer3.equals("quit")) {
									break;
								}
								byte bytes6[] = answer3.getBytes();
								out.write(bytes6,0,bytes6.length);
								
								int numBytesReceived4 = in.read(bytesReceived4, 0, bufLen);
								String serverResponse3 = new String(bytesReceived4,0,numBytesReceived4);
								
								System.out.println(serverResponse3);
								
								// Question 4
								System.out.println("How many inches in a foot ?");
								byte bytesReceived5[] = new byte[bufLen];
								
								String answer4 = stdin.readLine();
								if (answer4.equals("quit")) {
									break;
								}
								byte bytes7[] = answer4.getBytes();
								out.write(bytes7,0,bytes7.length);
								
								int numBytesReceived5 = in.read(bytesReceived5, 0, bufLen);
								String serverResponse4 = new String(bytesReceived5,0,numBytesReceived5);
								
								System.out.println(serverResponse4);
								
								// Question 5
								System.out.println("Opposite of tall ?");
								byte bytesReceived6[] = new byte[bufLen];
								
								String answer5 = stdin.readLine();
								if (answer5.equals("quit")) {
									break;
								}
								
								byte bytes8[] = answer5.getBytes();
								out.write(bytes8,0,bytes8.length);
								
								int numBytesReceived6 = in.read(bytesReceived6, 0, bufLen);
								String serverResponse5 = new String(bytesReceived6,0,numBytesReceived6);
								
								System.out.println(serverResponse5);
								
								
								// Question 6
								System.out.println("32-10 = ?");
								byte bytesReceived7[] = new byte[bufLen];
								
								String answer6 = stdin.readLine();
								if (answer6.equals("quit")) {
									break;
								}
								byte bytes9[] = answer6.getBytes();
								out.write(bytes9,0,bytes9.length);
								
								int numBytesReceived7 = in.read(bytesReceived7, 0, bufLen);
								String serverResponse6 = new String(bytesReceived7,0,numBytesReceived7);
								
								System.out.println(serverResponse6);
								//break;
								
								
								// Used for win or loss case
								byte bytesReceived8[] = new byte[bufLen];
								
								String answer7 = stdin.readLine();
								byte bytes10[] = answer7.getBytes();
								out.write(bytes10,0,bytes10.length);
								
								int numBytesReceived8 = in.read(bytesReceived8, 0, bufLen);
								String serverResponse7 = new String(bytesReceived8,0,numBytesReceived8);
								
								System.out.println(serverResponse7);
								
							}
								
				
					}
					String nameSend = stdin.readLine();
							if (nameSend.equals("quit")) {
								server.close();
								//break;
							}
					
			} catch(Exception e) {
					e.printStackTrace();
			}
	}

}