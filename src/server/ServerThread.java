package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

class ServerThread extends Thread{

    String line=null;
    Scanner is = null;
    PrintWriter os=null;
    Socket s=null;

    public ServerThread(Socket s){
        this.s=s;
    }

    public void run() {
        try{
            is= new Scanner(new InputStreamReader(s.getInputStream()));
            os=new PrintWriter(s.getOutputStream());

        }catch(IOException e){
            System.out.println("IO error in server thread");
        }

        try {
            line=is.nextLine();
            while(line.compareTo("QUIT")!=0){

                os.println(line);
                os.flush();
                System.out.println("Response to Client  :  "+line);
                line=is.nextLine();
            }
        } /*catch (Exception e) {

            line=this.getName(); //reused String line for getting thread name
            System.out.println("IO Error/ Client "+line+" terminated abruptly");
        }*/
        catch(NullPointerException e){
            line=this.getName(); //reused String line for getting thread name
            System.out.println("Client "+line+" Closed");
        }

        finally{
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