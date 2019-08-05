package tcp_version;

import java.io.File;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

public class MemeServer {

    public static String filePath = "meme.jpg";

    public static void main(String[] args) {
        try {
            // Listening to the Socket for incoming request
            ServerSocket serverSocket = new ServerSocket(4333);
            Socket socket = serverSocket.accept();

            // Converting the file to byte array
            File meme = new File(filePath);
            byte[] fileContent = Files.readAllBytes(meme.toPath());

            // Sending the file through stream
            OutputStream stream = socket.getOutputStream();
            stream.write(fileContent);

            System.out.println("File successfully sent");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
