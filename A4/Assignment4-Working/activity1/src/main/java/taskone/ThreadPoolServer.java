package taskone;

import java.net.*;
import java.io.*;
import taskone.*;
import org.json.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

class Worker implements Runnable {
    protected Socket conn;
    protected int id;
    protected StringList list;

    public Worker (Socket sock, int connectionId, StringList strings) {
        this.conn = sock;
        this.id = connectionId;
        this.list = strings;
    }

    public void run () {
        Performer performer = new Performer(this.conn, this.list);
        performer.doPerform();
        try {
            System.out.println("close socket of client ");
            this.conn.close();
        } catch (Exception e) {
            e.printStackTrace(); 
        }    
    }
}

public class ThreadPoolServer extends Thread {

	public static void main (String[] args) throws IOException {

		int port;
        int workers;
        ExecutorService executor = null;
        StringList strings = new StringList();

        if (args.length != 2) {
            // gradle runServer -Pport=9099 -q --console=plain
            System.out.println("Usage: gradle runServer -Pport=9099 -Pworker=5 -q --console=plain");
            System.exit(1);
        }
        port = -1;
        workers = -1;
        try {
            port = Integer.parseInt(args[0]);
            workers = Integer.parseInt(args[1]);
        } catch (NumberFormatException nfe) {
            System.out.println("[Port] must be an integer");
            System.exit(2);
        }
        executor = Executors.newFixedThreadPool(workers);
        ServerSocket server = new ServerSocket(port);
        System.out.println("Server Started...");
        int connectionID = 1;
        while (true) {
            System.out.println("Accepting a Request...");
            Socket sock = server.accept();
            connectionID++;
            System.out.println("Incoming connection with ID: " + connectionID);

            //Performer performer = new Performer(sock, strings);
            //performer.doPerform();
            Runnable w = new Worker(sock, connectionID, strings);
            //serverThread.start();
            executor.execute(w);
        }

	}

}