package taskone;

import java.net.*;
import java.io.*;
import taskone.*;
import org.json.*;

public class ThreadedServer extends Thread {

	private int i;
	private StringList vals;
	private Socket z;

	public ThreadedServer (StringList vals, int val_Connect, Socket s) {
		this.z = s;
		this.vals = vals;
		this.i = val_Connect;
	}

	public void perform () {  //originaly run
		Performer performer = new Performer(this.z, this.vals);
		performer.doPerform();
		try {
            System.out.println("client socket terminated ");
            this.z.close();
        } catch (Exception e) {
            e.printStackTrace(); 
        }            

	}

	//Modified from previous assignment and added threaded ability
	public static void main (String[] args) throws IOException {

		int port;
        StringList strings = new StringList();

        if (args.length != 1) {
            // gradle runServer -Pport=9099 -q --console=plain
            System.out.println("Usage: gradle runServer -Pport=9099 -q --console=plain");
            System.exit(1);
        }
        port = -1;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            System.out.println("[Port] must be an integer");
            System.exit(2);
        }
        ServerSocket server = new ServerSocket(port);
        System.out.println("Server Started...");
        int val_Connect = 1;
        while (true) {
            System.out.println("Accepting a Request...");
            Socket s = server.accept();
            val_Connect++;
            System.out.println("Incoming connection with ID: " + val_Connect);

            //Performer performer = new Performer(sock, strings);
            //performer.doPerform();
            ThreadedServer threads =  new ThreadedServer(strings, val_Connect, s);
            threads.start();
        }

	}

}