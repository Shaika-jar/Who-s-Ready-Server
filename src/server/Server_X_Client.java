package server;
// echo server
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;


public class Server_X_Client{

    public static void main(String args[]){
        Socket s=null;
        ServerSocket ss2=null;
        System.out.println("Server Listening......");

        try{
            ss2 = new ServerSocket(4445); // can also use static final PORT_NUM , when defined
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Server error");
        }
        while(true){
            try{
                s= ss2.accept();
                System.out.println("connection Established");
                ServerThread st=new ServerThread(s);
                st.start();
            }
            catch(Exception e){
                e.printStackTrace();
                System.out.println("Connection Error");
            }
        }
    }
}