package Utils;

import Model.CommunicationInfo;
import com.google.gson.Gson;

import java.io.PrintWriter;

/**
 * Created by Emanuele on 20/01/2016.
 */
public class CommunicationUtils {

    public static void SendCommunicationInfo(PrintWriter out, String code, String toSend ) {
        CommunicationInfo communicationInfo = new CommunicationInfo(code,toSend);
        Gson gson = new Gson();
        String communicationToSend = gson.toJson(communicationInfo);
        out.println(communicationToSend);
    }
}
