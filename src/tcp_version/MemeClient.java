package tcp_version;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;

public class MemeClient {
    public static void main(String[] args) {
        try {
            // Connecting to the server running in localhost
            Socket socket = new Socket("localhost", 4333);

            // Receiving the file through stream
            InputStream stream = socket.getInputStream();
            byte[] fileContent = new byte[8800];
            stream.read(fileContent);

            // Writing the file
            FileOutputStream fileOutputStream = new FileOutputStream("/Users/taj/Pictures/tcpMEME.jpg");
            fileOutputStream.write(fileContent);

            System.out.println("File successfully received");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
