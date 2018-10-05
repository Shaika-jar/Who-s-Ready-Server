package server;
import server.Client.Client;
import server.Client.Verwaltung;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread extends Thread{

    String line=null;
    Scanner is = null;
    PrintWriter os=null;
    Socket s=null;

    Client c;

    public ServerThread(Socket s){
        this.s=s;
    }

    public ServerThread(Socket s, Client c){
        this.c=c;
        this.s=s;
    }

    public void addClient(Client c){
        this.c=c;
    }


    public void sendMessage(final String message) {
        Runnable runnable = new Runnable() {
            public void run() {
                if (os != null) {
                    System.out.println("Sending: " + message);
                    os.println(message);
                    os.flush();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }


    public void run() {
        try{
            is= new Scanner(new InputStreamReader(s.getInputStream()));
            os=new PrintWriter(s.getOutputStream());

        }catch(IOException e){
            System.out.println("IO error in server thread");
        }

        try {
            line = is.nextLine();
            this.setName(line);
            new Client(line, this);
            System.out.println("Client added");
            for(Client u: Verwaltung.user){
                if(!u.equals(c))
                    c.recieveMessage(u.getName()+" ist online");
            }
            c.sendMessageToAll("joined the chatroom");
            System.out.println("Chat sended");
            while(line.compareTo("QUIT")!=0){

                //os.println(line);
                //os.flush();
                System.out.println("Response to Client  :  "+line);
                line=is.nextLine();
                c.sendMessageToAll(line);
            }
        }
        catch(Exception e){
            line=this.getName(); //reused String line for getting thread name
            System.out.println("Client "+line+" Closed");
        }

        finally{
            c.sendMessageToAll("leaved");
            Verwaltung.user.remove(c);
            try{
                System.out.println("Connection Closing..");
                if (is!=null){
                    is.close();
                    System.out.println(" Socket Input Stream Closed");
                }

                if(os!=null){
                    os.close();
                    System.out.println("Socket Out Closed");
                }
                if (s!=null){
                    s.close();
                    System.out.println("Socket Closed");
                }

            }
            catch(IOException ie){
                System.out.println("Socket Close Error");
            }
        }//end finally
    }
}