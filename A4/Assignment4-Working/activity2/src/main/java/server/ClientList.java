package server;

import buffers.RequestProtos.*;
import buffers.ResponseProtos.*;
import java.io.*;
import java.util.*;

public class ClientList {

	class Stats {
		String name;
		int wins;
		int logins;
	}

	ArrayList<Stats> list;

	public ClientList(){
		list = new ArrayList<>();
	}

	public void insertPlayer (String name){
		Stats temp = null;
		for (Stats n : list){
			if (n.name.equals(name)){
				temp = n;
			}
		}
		if (temp == null){
			temp = new Stats();
			temp.name = name;
			temp.wins = 0;
			temp.logins = 1;
			list.add(temp);
		} else {
			temp.logins++;
		}
	}

	public void incrementWin(String name){
		Stats temp = null;
		for (Stats n : list){
			if (n.name.equals(name)){
				n.wins++;
			}
		}
	}

	public synchronized Response getClientList(){
		Response.Builder res = Response.newBuilder()
                .setResponseType(Response.ResponseType.LEADER);

        for (Stats n : list){
            Entry e = Entry.newBuilder()
                .setName(n.name)
                .setWins(n.wins)
                .setLogins(n.logins)
                .build();
            res.addLeader(e);
        }
        return res.build();
	}
}