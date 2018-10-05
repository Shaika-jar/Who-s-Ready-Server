package server.Client;
import server.ServerThread;

import java.util.ArrayList;

public class Client {
    private String name;
    private ArrayList<ServerThread> st;

    public Client(String Name, ServerThread client){
        this.name=Name;
        System.out.println("0");
        st= new ArrayList<ServerThread>();
        System.out.println("1");
        st.add(client);
        System.out.println("2");
        client.addClient(this);
        System.out.println("3");
        Verwaltung.user.add(this);
        System.out.println("4");
    }

    public String getName() {
        return name;
    }

    //Client will recieve Message
    public void recieveMessage(String message){
        for (ServerThread s : st){
            s.sendMessage(message);
        }
    }

    public void sendMessageToAll(String s){
        for (Client u : Verwaltung.user){
            if(true){
                u.recieveMessage(name+": "+s);
            }
        }
    }


}
