package sample;

import Constants.Communication;
import Model.AllPlayer;
import Model.ClientClass;
import Model.CommunicationInfo;
import Model.Player;
import Utils.CommunicationUtils;
import Utils.FileUtils;
import com.google.gson.Gson;
import javafx.concurrent.Task;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Emanuele on 17/12/2015.
 */
public class RegisterTask extends Task {

    private RegisterMain clientApp;
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
        //GetPlayersFromServer();
        //out.println(Communication.GETDATA);
        CommunicationUtils.SendCommunicationInfo(out,Communication.GETDATA,"");
        while (active){
            if ((ob = in.readLine()) != null){
                System.out.println("Sono register task, ho ricevuto: "+ob);
                CommunicationInfo communicationInfo = gson.fromJson(ob,CommunicationInfo.class);
                switch (communicationInfo.getCode()){
                    case(Communication.FILE):
                        //TODO: SEND FILE
                        FileUtils.UploadFile(clientApp.getSelectedFile(),client.getSocket().getOutputStream());
                        break;
                    case(Communication.AUTHOK):
                        active=false;
                        clientApp.GoToHome();
                        break;
                    case (Communication.AUTHNO):
                        System.out.println("No");
                        ShowError();
                        break;
                    case (Communication.GETDATA):
                        GetPlayersFromServer(communicationInfo.getInfo());
                        break;


                }

            }
        }


        return null;
    }

    private void ShowError() {
        clientApp.ShowError();
    }

    /*private void SendFile() {
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
    */

    private void GetPlayersFromServer(String info) {

        System.out.println(Communication.GETDATA);
        String por = null;
        String def = null;
        String cen = null;
        String att = null;
        Gson gson = new Gson();
        AllPlayer allPlayer = gson.fromJson(info,AllPlayer.class);
            goalkeepers = allPlayer.getGoalkeeper();
            clientApp.setPor(goalkeepers);
            System.out.println("Player: " + goalkeepers);
            defensors = allPlayer.getDefensors();
            clientApp.setDef(defensors);
            System.out.println("Player: " + defensors);
            midfielders =allPlayer.getMidfielders();
            clientApp.setCen(midfielders);
            System.out.println("Player: " + midfielders);
            strikers = allPlayer.getStrikers();
            clientApp.setAtt(strikers);
            System.out.println("Player: " + strikers);
    }


    public RegisterTask(RegisterMain clientApp, PrintWriter out, BufferedReader in, ClientClass client){
        this.clientApp = clientApp;
        this.out=out;
        this.in = in;
        this.client = client;
    }
}
