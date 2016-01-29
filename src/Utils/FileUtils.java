package Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by Emanuele on 29/01/2016.
 */
public class FileUtils {

    public static void UploadFile(File file, OutputStream outputStream){

        try {
            BufferedImage image = ImageIO.read(file);
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
}
