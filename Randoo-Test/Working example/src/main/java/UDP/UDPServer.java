package UDP;

import java.io.*;
import java.net.*;

public class UDPServer {
	public static void main(String args[]) {
		DatagramSocket sock = null;
		
		try {
			sock = new DatagramSocket(8080);
			System.out.println("Server wating...");
			
			byte[] buf = new byte[65536];
			DatagramPacket receive = new DatagramPacket(buf, buf.length);
			
			while(true) {
				sock.receive(receive);
				System.out.println("Client has connected to server.");
				byte[] data = receive.getData();
				String userName = new String(data, 0, receive.getLength());
				
				System.out.println("Client name: " + userName);
				DatagramPacket sent = new DatagramPacket(userName.getBytes() , userName.getBytes().length , receive.getAddress() , receive.getPort());
				sock.send(sent);
				
				// NUMBER OF QUESTIONS
				byte[] buf2 = new byte[65536];
				DatagramPacket receive2 = new DatagramPacket(buf2, buf2.length);
				sock.receive(receive2);
				byte[] data2 = receive2.getData();
				String numQuestions = new String(data2, 0, receive2.getLength());
				
				System.out.println("Received string of " + numQuestions);
				DatagramPacket sent2 = new DatagramPacket(numQuestions.getBytes() , numQuestions.getBytes().length , receive2.getAddress() , receive2.getPort());
				sock.send(sent2);
				
			}
		}
		
		catch(IOException e){
			System.err.println("IOException " + e);
		}
	}
}