package client;

import java.net.*;
import java.io.*;

import org.json.*;

import buffers.RequestProtos.Request;
import buffers.ResponseProtos.Response;
import buffers.ResponseProtos.Entry;

import java.util.*;
import java.util.stream.Collectors;

class SockBaseClient {
	
    public static void main (String args[]) throws Exception {

        SockBaseClient c = new SockBaseClient();
        int port = -1;
        // Make sure two arguments are given
        if (args.length != 2) {
            System.out.println("Expected arguments: <host(String)> <port(int)>");
            System.exit(1);
        }
        String host = args[0];
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException nfe) {
            System.out.println("[Port] must be integer");
            System.exit(2);
        }
        c.start(port, host);
    }
    private BufferedReader input;
	private Socket serverSock;
    private OutputStream out;
    private InputStream in;
    private int i1; private int i2;
    private int port; 

    public SockBaseClient () {
        input = null;
		serverSock = null;
        out = null; in = null;
        i1 = 0; i2 = 0;
        port = 9099;
    }

    //Request methods from protobuff
	private Request game_New () {
        Request val = Request.newBuilder()
            .setOperationType(Request.OperationType.NEW)
            .build();
        return val;
    }
	
    private Request message_Leader () {
        Request val = Request.newBuilder()
            .setOperationType(Request.OperationType.LEADER)
            .build();
        return val;
    }
	
	  private Request clientAnswer (String string) {
        Request val = Request.newBuilder()
            .setOperationType(Request.OperationType.ANSWER)
            .setAnswer(string)
            .build();
        return val;
    }
	
	//Sets up the leaderboard
    private void Board() {
        try {
            Request val = message_Leader();
            val.writeDelimitedTo(out);
            Response res = Response.parseDelimitedFrom(in);
            System.out.println("........LEADERBOARD........");
            for (Entry lead: res.getLeaderList()){
                System.out.println(lead.getName() + ": " + lead.getWins());
            }
			
            System.out.println("\n");
			System.out.println("\n");
        } catch (Exception e) {
            System.out.println("Problem bringing up the leaderboard");
			return;
        }
    }

    private void Game () {
        Request val;
        try {
            val = game_New();
            val.writeDelimitedTo(out);
        } catch (IOException e) {
            System.out.println("Problem writing to client");
            return;
        }
		boolean state = true;
        while (state) {
            try {
                Response res = Response.parseDelimitedFrom(in);
				Scanner sc = new Scanner(System.in);
                
				if (res.getResponseType() == Response.ResponseType.WON) {
                    System.out.println("You Won! Great Job!");
					System.out.println("");
                    state = false;
                }
				else if (res.getResponseType() == Response.ResponseType.TASK) {
                    System.out.println("............IMAGE............\n" + res.getImage());
                    System.out.println("Enter answer to the task: " + res.getTask());
                    String line = sc.nextLine();
                    val = clientAnswer(line);
                    val.writeDelimitedTo(out);
                }  
            } catch (Exception e) {
                System.out.println("There was an problem");
                state = false;
                break;
            }
         }
    }

    private void start (int p, String h) {
         // Ask user for username
        System.out.println("Please provide your name for the server.");
        String str = "";
        //some error handling
		try {
            input = new BufferedReader(new InputStreamReader(System.in));
            str = input.readLine();
        } catch (Exception e) {
            System.out.println("Problem with input entered");
			System.out.println("Exit");
            System.exit(1);
        }

        // Build the first request object just including the name
        Request val = Request.newBuilder()
                .setOperationType(Request.OperationType.NAME)
                .setName(str).build();
        Response res;
        try {
            // connect to the server
            serverSock = new Socket(h, p);

            // write to the server
            out = serverSock.getOutputStream();
            in = serverSock.getInputStream();

            val.writeDelimitedTo(out);

            boolean state = true;
			int selection;
			
            // read from the server
            while (state) {
                res = Response.parseDelimitedFrom(in);

            // print the server response. 
                System.out.println(res.getGreeting());

                try {
                    str = input.readLine();
                    selection = Integer.parseInt(str);
                    if (!(selection >= 0 && selection <= 2))
                        throw new Exception();
                } catch (Exception e) {
                    System.out.println("Invalid selection!");
                    continue;
                }

				//From client input, quit, leaderboard, or play game
                switch (selection) {
                    case 2: 
                        Game();
                        break;
                    case 1:
                        Board();
                        break;
					case 0: 
                        state = false;
                        break;	
                    default: 
                        System.out.println("exit");
                        state = false;
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Encountered a problem system wil exit");
            System.exit(1);
        } finally {
			/** not needed at the moment
			if (in != null)   in.close();
            if (out != null)  out.close();
            if (serverSock != null) serverSock.close();
			**/
        }
    }

}