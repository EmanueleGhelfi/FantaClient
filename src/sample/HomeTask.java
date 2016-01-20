package sample;

import Constants.Communication;
import Model.*;
import Utils.CommunicationUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.concurrent.Task;

import javax.imageio.ImageIO;
import javax.jws.soap.SOAPBinding;
import javax.sound.sampled.Port;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Emanuele on 29/10/2015.
 */
public class HomeTask extends Task {

    private Home homeApp;
    private ClientClass client;
    private Type listType;



    public HomeTask(Home homeApp, ClientClass client) {
        this.homeApp = homeApp;
        this.client = client;
    }

    @Override
    protected Object call() throws Exception {
        String ob;
        String res=null;
        boolean active = true;
        //BufferedReader reader = client.getIn();
        BufferedReader reader = client.getIn();
        System.out.println("CIAO, Eccomi");
        listType = new TypeToken<ArrayList<Player>>() {
        }.getType();

        if(homeApp.getTeam()==null)
            //GetTeamFromServer();
            CommunicationUtils.SendCommunicationInfo(client.getOut(),Communication.GETTEAM,"");
        if(homeApp.getUser()==null)
           // GetUserFromServer();
            CommunicationUtils.SendCommunicationInfo(client.getOut(),Communication.GETUSER,"");

            while (active) {
                try {
                    if ((ob = reader.readLine()) != null) {
                        System.out.println(ob);
                        Gson gson = new Gson();
                        CommunicationInfo communicationInfo = gson.fromJson(ob,CommunicationInfo.class);
                        switch (communicationInfo.getCode()) {
                            /*case (Communication.READYFORTIT):
                                ArrayList<Player> formazione = homeApp.getFormazione();
                                Gson gson2 = new Gson();
                                client.getOut().println(gson2.toJson(formazione));
                                break;
                                */
                           /* case (Communication.READYFORRIS):
                                SendBench();
                                break;
                                */
                            case (Communication.READYFORTEAM):
                                GetTeamFromServer(communicationInfo.getInfo());
                                break;
                            case(Communication.READYFORUSER):
                                GetUserFromServer(communicationInfo.getInfo());
                                break;
                            case (Communication.READYFORCLASSIFICA):
                                GetClassificaFromServer(communicationInfo.getInfo());
                                break;
                            case (Communication.READYFORALLPLAYERS):
                                GetAllPlayersFromServer(communicationInfo.getInfo());
                                break;
                            /*case (Communication.READYFORMODIFIEDTEAM):
                                SendModifiedTeamToServer();
                                break;
                                */
                            case (Communication.READYFORVOTI):
                                GetVotesFromServer(communicationInfo.getInfo());
                                break;
                            case (Communication.READYFORGIORNATE):
                                GetGiornateFromServer(communicationInfo.getInfo());
                                break;
                            case (Communication.READYFORLASTDAY):
                                GetLastDayFromServer(communicationInfo.getInfo());
                                break;
                            case (Communication.READYFORIMAGE):
                                GetImageFromServer();
                                break;
                            case (Communication.READYFORRESULTS):
                                GetResultsFromServer(communicationInfo.getInfo());
                                break;
                            case (Communication.NOIMAGE):
                                AskResultToServer();
                                break;
                            case (Communication.FILE):
                                UploadImage();
                                break;
                            /*case (Communication.READYFORMODIFIEDUSER):
                                SendModifiedUser();
                                break;
                                */
                            case (Communication.USEROK):
                                OnUserOK();
                                break;
                            case (Communication.READYFORINFO):
                                GetInfoFromServer(communicationInfo.getInfo());
                                break;
                            case (Communication.USERNO):
                                //TODO: manage
                                ShowErrorPopup();
                                break;
                            case (Communication.NOTIFICATION):
                                homeApp.ShowNotification();
                                break;
                            case(Communication.OK):
                                SendLoginData();
                                break;
                            case (Communication.AUTHOK):
                                DismissDialog();
                                //controller.setOutput("AUTHOK");
                                break;
                            case (Communication.AUTHNO):
                                //SIGNAL ERROR
                                System.out.println("ERROR");
                                homeApp.setNeedToConnect(false);
                                homeApp.ShowLoginDialog();
                                break;
                            case("GETMONEY"):
                                client.getOut().println(""+homeApp.getUser().getSoldi());
                                System.out.println("Sent money");
                                break;
                            case("END"):
                                System.out.println("NEL CASE");
                                //
                                ReceiveEndPos(communicationInfo.getInfo());
                                break;
                            case("READYFORDATE"):
                                ReceiveDateFromServer(communicationInfo.getInfo());
                                break;
                        }
                    } else {
                        //active = false;
                        homeApp.setNeedToConnect(true);
                        System.out.println("SERVER OFF");
                        active=false;
                        homeApp.ShowLoginDialog();
                    }
                } catch (IOException e) {
                    homeApp.setNeedToConnect(true);
                    e.printStackTrace();
                    System.out.println("SERVER OFF");
                    active=false;
                    homeApp.ShowLoginDialog();
                }
            }

        return res;
    }

    private void ReceiveEndPos(String pos) {
            homeApp.showEndPopup(pos);
    }

    private void DismissDialog() {
        homeApp.dismissDialog();
    }

    private void SendLoginData() {
        System.out.println("INVIO USER...");
        //client.getOut().println(user);
        Gson gson = new Gson();
        String userString = gson.toJson(homeApp.getUserToSend());
        client.getOut().println(userString);
    }

    //Show an error popup in AndamentoController
    private void ShowErrorPopup() {
        homeApp.ShowErrorPopup();
    }

    private void OnUserOK() {
        homeApp.OnApprovedModifiedData();
    }

    private void GetInfoFromServer(String stringInfo) {
            //String stringInfo = client.getIn().readLine();
            Gson gson = new Gson();
            InfoClass info = gson.fromJson(stringInfo,InfoClass.class);
            homeApp.setInfo(info);


    }

    private void SendModifiedUser() {
        User userTemp = homeApp.getUserTemp();
        Gson gson = new Gson();
        String userString = gson.toJson(userTemp);
        client.getOut().println(userString);
    }

    private void SendBench() {

        ArrayList<Player> formazione = homeApp.getBench();
        Gson gson2 = new Gson();
        client.getOut().println(gson2.toJson(formazione));
    }

    private void UploadImage() {
        File myFile = homeApp.getSelectedFile();
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

    //function called if the users doesn't have a profile image
    private void AskResultToServer() {
        client.getOut().println(Communication.GETRESULTS);
    }

    private void GetResultsFromServer(String resultString) {
        //client.getOut().println(Communication.READYFORRESULTS);
        //Receive results from server
            //String resultString = client.getIn().readLine();
            Gson gson = new Gson();
            Type resultType = new TypeToken<ArrayList<Results>>() {
            }.getType();
            ArrayList<Results> results = gson.fromJson(resultString,resultType);
            CommunicationUtils.SendCommunicationInfo(client.getOut(),Communication.GETINFO,"");
            homeApp.setResults(results);
    }

    private void GetImageFromServer() {

        client.getOut().println(Communication.READYFORIMAGE);
        //Receive file from server

        File file = new File("image.jpg");


        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            InputStream inputStream = client.getSocket().getInputStream();
            DataInputStream dis = new DataInputStream(inputStream);

            int len = dis.readInt();
            System.out.println("Image Size: " + len / 1024 + "KB");
            byte[] data = new byte[len];
            dis.readFully(data);


            InputStream ian = new ByteArrayInputStream(data);
            BufferedImage bImage = ImageIO.read(ian);

            System.out.println("COMPLETE!");
            ImageIO.write(bImage, "jpg", file);
            homeApp.setProfileImage(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GetUserFromServer(String userString) {
            Gson gson = new Gson();
            System.out.println(userString);
            User user = gson.fromJson(userString,User.class);
            homeApp.setUser(user);
            //Initialize all with users'info
            homeApp.initWelcome();
            //client.getOut().println(Communication.CANSENDTEAM);
            CommunicationUtils.SendCommunicationInfo(client.getOut(),Communication.CANSENDTEAM,"");
            //ReceiveDateFromServer();
        }

    private void ReceiveDateFromServer(String date) {
            System.out.println("Prossima: "+date);
            homeApp.EnableSendButton(date);
    }

    private void GetLastDayFromServer(String classifica) {
        //client.getOut().println(Communication.READYFORLASTDAY);
       // try {
            //String classifica = client.getIn().readLine();
            System.out.println(classifica);
            Type teamType = new TypeToken<ArrayList<SimpleTeam>>() {
            }.getType();
            Gson gson = new Gson();
            ArrayList teams = gson.fromJson(classifica,teamType);
            homeApp.setLastDayteams(teams);
            homeApp.initLastDayList();
            //GetGiornateFromServer();
    }

    private void GetVotesFromServer(String voti) {
        //client.getOut().println(""+homeApp.getDayToAsk());
        System.out.println("DAY TO ASK "+homeApp.getDayToAsk());
        //String voti = null;
            System.out.println(voti);
            Type teamType = new TypeToken<ArrayList<PlayerVoto>>() {
            }.getType();
            Gson gson = new Gson();
            ArrayList votiArray = gson.fromJson(voti,teamType);
            homeApp.changeLvVoti(""+homeApp.getDayToAsk(),votiArray);

    }

    private void SendModifiedTeamToServer() {
        Gson gson = new Gson();
        String teamJson = gson.toJson(homeApp.getTeam());
        client.getOut().println(teamJson);
    }

    private void GetAllPlayersFromServer(String info) {
        //client.getOut().println(Communication.READYFORALLPLAYERS);
            //System.out.println();
            Type playerType = new TypeToken<ArrayList<Player>>(){}.getType();
            Gson gson = new Gson();
            ArrayList allPlayers = gson.fromJson(info,playerType);
            PopulateArrays(allPlayers);
            homeApp.initTableMercato();
        }


    private void PopulateArrays(ArrayList<Player> allplayers) {
        ArrayList<Player> goalKeepers = new ArrayList<>();
        ArrayList<Player> defensors = new ArrayList<>();
        ArrayList<Player> midfielders = new ArrayList<>();
        ArrayList<Player> strikers = new ArrayList<>();
        for(int i = 0;i<allplayers.size();i++){
            switch (allplayers.get(i).getRuolo()){
                case 'P':
                    goalKeepers.add(allplayers.get(i));
                    break;
                case 'D':
                    defensors.add(allplayers.get(i));
                    break;
                case 'C':
                    midfielders.add(allplayers.get(i));
                    break;
                case 'A':
                    strikers.add(allplayers.get(i));
                    break;

            }

        }

        homeApp.setGoalkeepers(goalKeepers);
        homeApp.setDefensors(defensors);
        homeApp.setMidfielders(midfielders);
        homeApp.setStrikers(strikers);
    }

    private void GetClassificaFromServer(String info) {
        //client.getOut().println(Communication.READYFORCLASSIFICA);
            //String classifica = client.getIn().readLine();
            System.out.println(info);
            Type teamType = new TypeToken<ArrayList<SimpleTeam>>() {
            }.getType();
            Gson gson = new Gson();
            ArrayList teams = gson.fromJson(info,teamType);
            homeApp.setTeams(teams);
            homeApp.initClassifica();
            //GetGiornateFromServer();
    }

    private void GetGiornateFromServer(String giornate) {
        //client.getOut().println(Communication.READYFORGIORNATE);
            //String giornate = client.getIn().readLine();
            int giornateInt = Integer.parseInt(giornate);
            homeApp.InitComboBoxVoti(giornateInt);
            homeApp.setDayToAsk(giornateInt);
            //AskVotesToServer();
    }

    private void AskVotesToServer() {
        //client.getOut().println("GETVOTI");
        /*try {
            String response = client.getIn().readLine();
            if(response.equals("READYFORVOTI")){
                client.getOut().println(giornate);
                String voti = client.getIn().readLine();
                System.out.println(voti);
                Type teamType = new TypeToken<ArrayList<PlayerVoto>>() {
                }.getType();
                Gson gson = new Gson();
                ArrayList votiArray = gson.fromJson(voti,teamType);
                homeApp.changeLvVoti(giornate,votiArray);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    private void GetTeamFromServer(String teamString) {
        System.out.println("Sono Home Task: GETTEAM");
            Gson gson = new Gson();
            System.out.println(teamString);
            ArrayList<Player> team = gson.fromJson(teamString,listType);
            homeApp.setTeam(team);
            homeApp.initTable();
        }


}



