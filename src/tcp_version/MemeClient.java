package tcp_version;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;

class Client {

    protected static void receiveData() throws Exception {
        String address = "localhost";

        Socket s = new Socket(address, 5000);
        DataInputStream din = new DataInputStream(s.getInputStream());

        String filename = "data_from_tcp.rtf";
        try {
            byte b[] = new byte[1024];
            FileOutputStream fos = new FileOutputStream(new File(filename), true);
            long bytesRead;
            do {
                bytesRead = din.read(b, 0, b.length);
                fos.write(b, 0, b.length);
            } while (!(bytesRead < 1024));
            System.out.println("Received");
            fos.close();

            s.close();
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }
    public static void main(String args[]) throws Exception {
        long start = System.nanoTime();
        receiveData();
        long time = System.nanoTime() - start;
        System.out.println("Execution Time: " + time + "ns");
    }
}