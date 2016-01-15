package sample;

import Constants.Communication;
import Controllers.RegisterController;
import Model.ClientClass;
import Model.Player;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.concurrent.Task;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Created by Emanuele on 17/12/2015.
 */
public class RegisterTask extends Task {

    private Second clientApp;
    private PrintWriter out;
    private BufferedReader in;
    private ClientClass client;
    private ArrayList<Player> goalkeepers;
    private ArrayList<Player> defensors;
    private ArrayList<Player> midfielders;
    private ArrayList<Player> strikers;

    @Override
    protected Object call() throws Exception {
        boolean active = true;
        String ob = null;
        Gson gson = new Gson();
        GetPlayersFromServer();
        while (active){
            if ((ob = in.readLine()) != null){
                System.out.println("Sono register task, ho ricevuto: "+ob);
                switch (ob){
                    case (Communication.READY):
                        String userJson = gson.toJson(clientApp.getNewUser());
                        out.println(userJson);
                        System.out.println(userJson);
                        break;
                    case(Communication.FILE):
                        //TODO: SEND FILE
                        SendFile();
                        break;
                    case(Communication.AUTHOK):
                        active=false;
                        clientApp.GoToHome();
                        break;
                    case (Communication.AUTHNO):
                        System.out.println("No");
                        ShowError();
                        break;


                }

            }
        }


        return null;
    }

    private void ShowError() {
        clientApp.ShowError();
    }

    private void SendFile() {
        File myFile = clientApp.getSelectedFile();
        try {
            OutputStream outputStream = client.getSocket().getOutputStream();
            BufferedImage image = ImageIO.read(myFile);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image,"jpg",byteArrayOutputStream);
            byteArrayOutputStream.flush();

            byte[] bytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();

            System.out.println("Sending image to server. :"+bytes.length);
            DataOutputStream dos = new DataOutputStream(outputStream);

            dos.writeInt(bytes.length);
            dos.write(bytes,0,bytes.length);
            System.out.println("Image sent to server. ");



            System.out.println("Transfer Complete");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void GetPlayersFromServer() {
        out.println(Communication.GETDATA);
        System.out.println(Communication.GETDATA);
        String por = null;
        String def = null;
        String cen = null;
        String att = null;
        try {
            por = in.readLine();
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Player>>() {
            }.getType();
            goalkeepers = gson.fromJson(por, listType);
            clientApp.setPor(goalkeepers);
            System.out.println("Player: " + goalkeepers);
            out.println(Communication.OKPOR);
            def=in.readLine();
            defensors = gson.fromJson(def, listType);
            clientApp.setDef(defensors);
            System.out.println("Player: " + defensors);
            out.println(Communication.OKDEF);
            cen=in.readLine();
            midfielders = gson.fromJson(cen, listType);
            clientApp.setCen(midfielders);
            System.out.println("Player: " + midfielders);
            out.println(Communication.OKCEN);
            att=in.readLine();
            strikers = gson.fromJson(att, listType);
            clientApp.setAtt(strikers);
            System.out.println("Player: " + strikers);
            out.println(Communication.OKATT);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public RegisterTask(Second clientApp, PrintWriter out, BufferedReader in, ClientClass client){
        this.clientApp = clientApp;
        this.out=out;
        this.in = in;
        this.client = client;
    }
}
