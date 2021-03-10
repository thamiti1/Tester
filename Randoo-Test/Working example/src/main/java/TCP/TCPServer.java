package TCP;

import java.net.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;
import java.util.Random;
import javax.swing.Timer;

import javax.imageio.ImageIO;

import org.json.*;

public class TCPServer {
	private Timer timer;
	
	//Taken from class example Advanced Protocol
	public static JSONObject image() throws IOException {
    JSONObject json = new JSONObject();
    json.put("datatype", 2);

    json.put("type", "image");

    File file = new File("img/To-Funny-For-Words1.png");
    if (!file.exists()) {
      System.err.println("Cannot find file: " + file.getAbsolutePath());
      System.exit(-1);
    }
    // Read in image
    BufferedImage img = ImageIO.read(file);
    byte[] bytes = null;
    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      ImageIO.write(img, "png", out);
      bytes = out.toByteArray();
    }
    if (bytes != null) {
      Base64.Encoder encoder = Base64.getEncoder();
      json.put("data", encoder.encodeToString(bytes));
      return json;
    }
    return error("Unable to save image to byte array");
  }
	public static JSONObject error(String err) {
    JSONObject json = new JSONObject();
    json.put("error", err);
    return json;
  }
  
	public static void main (String args[]) {
			try {
				/**
					if (args.length != 1) {
						System.out.println("Usage: gradle TCPServer -Pport=8080");
						System.exit(0);
					}
					**/
					int port = -1;
					try {
						port = Integer.parseInt(args[0]);
					} catch (NumberFormatException nfe) {
						System.out.println("integer");
						System.exit(2);
					}
					
					Socket s;
					ServerSocket sock = new ServerSocket(port);
					System.out.println("Server ready");
					int lengthB = 1024;
					
					String questionVals = "";
					while(true) {
						
							// Starting the connection
							System.out.println("Waiting...");
							s = sock.accept();
							PrintWriter out = new PrintWriter(s.getOutputStream(), true);
							ObjectOutputStream os = null;
							InputStream input = s.getInputStream();
							System.out.println("Established connection to client");
							
							byte cInput[] = new byte[lengthB];
							int number = input.read(cInput, 0, lengthB);
							while (number != -1) {
								String name = new String(cInput, 0, number);
								System.out.println("Client Name: " + name);
								out.println(name);
								number = -1;
							}
							
							// Getting number of questions
							byte cInput2[] = new byte[lengthB];
							int questions = input.read(cInput2, 0, lengthB);
							
							if (questions > 5 || questions < 1 ){
								System.out.println("Invalid amount of questions" + questions);
								out.println("Invalid amount of questions" + questions);
							}
							
							else {	
								while (questions > 0 && questions < 5) {
									questionVals = new String(cInput2, 0, questions);
									int val = Integer.parseInt(questionVals);
									System.out.println("Client entered number of questions: " + val);
									break;
							   }
							}
							int val = Integer.parseInt(questionVals);
							
							// game starts
							byte cInput3[] = new byte[lengthB];
							int startButton = input.read(cInput3, 0, lengthB);
							while(startButton != -1) {
								System.out.println("A game has begun.");
								int correctAnswers = 0;
									
								// Question number 1
								byte inputClient[] = new byte[lengthB];
								int answerClient = input.read(inputClient, 0, lengthB);
								while (answerClient != -1) {
									String answer = new String(inputClient, 0, answerClient);
									System.out.println("User answer: " + answer);
									System.out.println("Correct answer: black");
			
									if (answer.equals("black")) {
										correctAnswers = 1;
										System.out.println("User answer was correct");
										out.println("Correct");
									} 
									else if (answer.equals("next")){
										correctAnswers = 0;
										System.out.println("User chose next question");
										out.println("Next");
									}
									else if (answer.equals("quit")){
										startButton = -1;
									}
									else {
										System.out.println("User answer was incorrect");
										out.println("Incorrect");
									}
									break;
								}

								// Will check for winning case
								// similar logic repeated for each question
								correctAnswers++;
								if (correctAnswers == val) {
									System.out.println("Player has won!");
									out.println("Game ended... You won!");
									break;
								}
									
								// Question number 2
								byte inputClient2[] = new byte[lengthB];
								int answerClient2 = input.read(inputClient2, 0, lengthB);
								while (answerClient2 != -1) {
									String answer2 = new String(inputClient2, 0, answerClient2);
									System.out.println("User answer " + answer2);
									System.out.println("Correct answer: 95");
										
									if (answer2.equals("95")) {
										correctAnswers = 2;
										System.out.println("User answer is correct");
										out.println("Correct");
									} 
									
									else if (answer2.equals("next")){
										correctAnswers = 0;
										System.out.println("User chose next question");
										out.println("Next");
									}	
									else {
										System.out.println("User answer is incorrect");
										out.println("Incorrect");
									}
									break;
								}
									
								if (correctAnswers == val) {
									System.out.println("Player has won!");
									out.println("Game ended... You won!");
									break;
								}
									
								// Question number 3
								byte inputClient3[] = new byte[lengthB];
								int answerClient3 = input.read(inputClient3, 0, lengthB);
								while (answerClient3 != -1) {
									String answer3 = new String(inputClient3, 0, answerClient3);
									System.out.println("User answer: " + answer3);
									System.out.println("Correct answer: 36");
										
									if (answer3.equals("36")) {
										correctAnswers = 3;
										System.out.println("User answer was correct");
										out.println("Correct");
									} 
									else if (answer3.equals("next")){
										correctAnswers = 0;
										System.out.println("User chose next question");
										out.println("Next");
									}
									else {
										System.out.println("User answer was incorrect");
										out.println("Incorrect");
									}
									break;
								}
									
								if (correctAnswers == val) {
									System.out.println("Player has won!");
									out.println("Game ended... You won!");
									break;
								}
									
								// Question number 4
								byte inputClient4[] = new byte[lengthB];
								int answerClient4 = input.read(inputClient4, 0, lengthB);
								while (answerClient4 != -1) {
									String answer4 = new String(inputClient4, 0, answerClient4);
									System.out.println("User answer: " + answer4);
									System.out.println("Correct answer: 12");

									if (answer4.equals("12")) {
										correctAnswers = 4;
										System.out.println("User answer was correct");
										out.println("Correct");
									} 
									else if (answer4.equals("next")){
										//correctAnswers = 0;
										System.out.println("User chose next question");
										out.println("Next");
									}
									else {
										System.out.println("User answer was incorrect");
										out.println("Incorrect");
									}
									break;
								}
									
								if (correctAnswers == val) {
									System.out.println("Player has won!");
									out.println("Game ended... You won!");
									break;
								}
									
								// Question number 5
								byte inputClient5[] = new byte[lengthB];
								int answerClient5 = input.read(inputClient5, 0, lengthB);
								while (answerClient5 != -1) {
									String answer5 = new String(inputClient5, 0, answerClient5);
									System.out.println("User answer: " + answer5);
									System.out.println("Correct answer: short");

									if (answer5.equals("short")) {
										correctAnswers = 5;
										System.out.println("User answer was correct");
										out.println("Correct");
									} 
									else if (answer5.equals("next")){
										//correctAnswers = 0;
										System.out.println("User chose next question");
										out.println("Next");
									}
									else {
										System.out.println("User answer was incorrect");
										out.println("Incorrect");
									}
									break;
								}
									
								if (correctAnswers == val) {
									System.out.println("Player has won!");
									out.println("Game ended... You won!");
									break;
								}
								
								// Question number 6
								byte inputClient6[] = new byte[lengthB];
								int answerClient6 = input.read(inputClient6, 0, lengthB);
								while (answerClient6 != -1) {
									String answer6 = new String(inputClient6, 0, answerClient6);
									System.out.println("User answer: " + answer6);
									System.out.println("Correct answer: 22");

									if (answer6.equals("22")) {
										correctAnswers = 6;
										System.out.println("User answer was correct");
										out.println("Correct");
									} 
									else if (answer6.equals("next")){
										//correctAnswers = 0;
										System.out.println("User chose next question");
										out.println("Next");
									}
									else {
										System.out.println("User answer was incorrect");
										out.println("Incorrect");
										out.println("  :( ... (Sad face) ");
									}
									break;
								}

								if (correctAnswers == val) {
									System.out.println("Player has won!");
									
									JSONObject returnMessage;
									returnMessage = image();
									byte[] output = JsonUtils.toByteArray(returnMessage);
								
									
									out.println("Game ended... You won!");
									break;
								}
									
								System.out.println("Game ended... Player lost!");
								//os.writeObject("Lost the game");
								out.println("Game has ended... You lost");
								//break;
							}

							input.close();
							s.close();
							System.out.println("Socket Closed.");
					}
			} catch(Exception e) {
					e.printStackTrace();
			}
	}
}