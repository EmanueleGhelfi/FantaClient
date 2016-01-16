package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Emanuele on 15/10/2015.
 */
public class ClientClass {
    private String hostName;
    private int portNumber;
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;

    public ClientClass() {
        //this.hostName = "localhost";
       //this.hostName = "192.168.1.101";
        this.hostName = "localhost";
        this.portNumber = 4444;
    }

    public ClientClass(String ip) {
        //this.hostName = "localhost";
        //this.hostName = "192.168.1.101";
        this.hostName = ip;
        this.portNumber = 4444;
    }

    public boolean init(){
        try {
            socket = new Socket(hostName, portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getHostName() {
        return hostName;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public PrintWriter getOut() {
        return out;
    }

    public BufferedReader getIn() {
        return in;
    }

    public Socket getSocket() {
        return socket;
    }
}
