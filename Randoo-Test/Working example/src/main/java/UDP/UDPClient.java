package UDP;

import java.io.*;
import java.net.*;

public class UDPClient {
	public static void main(String args[]) {
		DatagramSocket sock = null;
		int port = 8080;
		String name, numberOfQuestions;
		
		try {
			sock = new DatagramSocket();
			
			InetAddress host = InetAddress.getByName("localhost");
			
			while(true)	{
				BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
				
				// NAME
				System.out.println("What is your name?");
				name = (String)stdin.readLine();
				byte[] byteNumber = name.getBytes();
				
				DatagramPacket packet = new DatagramPacket(byteNumber, byteNumber.length, host, port);
				sock.send(packet);
				
				byte[] buffer = new byte[65536];
				DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
				sock.receive(reply);
				
				byte[] data = reply.getData();
				String s = new String(data, 0, reply.getLength());
				
				if(name.equals("quit")) {
					break;
				}
				
				// NUMBER OF QUESTIONS
				BufferedReader stdin2 = new BufferedReader(new InputStreamReader(System.in));
				
				System.out.println("Okay " + s + ", how many questions would you like?");
				numberOfQuestions = (String)stdin2.readLine();
				byte[] byteNumber2 = numberOfQuestions.getBytes();
				
				DatagramPacket packet2 = new DatagramPacket(byteNumber2, byteNumber2.length, host, port);
				sock.send(packet2);
				
				byte[] buffer2 = new byte[65536];
				DatagramPacket reply2 = new DatagramPacket(buffer2, buffer2.length);
				sock.receive(reply2);
				
				byte[] data2 = reply2.getData();
				String numQuestions = new String(data2, 0, reply2.getLength());
				
				if(numberOfQuestions.equals("quit")) {
					break;
				}
				
				System.out.println(s + ", there will be " + numQuestions + " questions. We are ready to play.");
				break;
			}
		}
		
		catch(IOException e) {
			System.err.println("IOException " + e);
		}
	}
}