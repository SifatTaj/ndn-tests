package tcp_version;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

class Server {
    public static void main(String args[]) throws Exception {

        String filename = "lorem.rtf";

        while (true) {

            ServerSocket ss = new ServerSocket(5000);
            System.out.println("Waiting for request");
            Socket s = ss.accept();
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            try {

                dout.writeUTF(filename);
                dout.flush();

                File f = new File(filename);
                FileInputStream fin = new FileInputStream(f);
                long sz = (int) f.length();

                byte b[] = new byte[1024];

                int read;

                dout.writeUTF(Long.toString(sz));
                dout.flush();

                while ((read = fin.read(b)) != -1) {
                    dout.write(b, 0, read);
                    dout.flush();
                }
                fin.close();

                dout.flush();
                dout.writeUTF("stop");
                System.out.println("Send Complete");
                dout.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
            s.close();
            ss.close();
        }
    }
}