package tcp_version;

import java.io.*;
import java.net.*;
import java.util.Scanner;

class Server {
    public static void main(String args[]) throws Exception {

        Thread discoveryThread = new Thread(DiscoveryThread.getInstance());
        discoveryThread.start();

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

class DiscoveryThread implements Runnable {

    DatagramSocket socket;

    @Override
    public void run() {
        try {
            //Keep a socket open to listen to all the UDP trafic that is destined for this port
            socket = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
            socket.setBroadcast(true);

            while (true) {
                System.out.println(getClass().getName() + ">>>Ready to receive broadcast packets!");

                //Receive a packet
                byte[] recvBuf = new byte[15000];
                DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
                socket.receive(packet);

                //Packet received
                System.out.println(getClass().getName() + ">>>Discovery packet received from: " + packet.getAddress().getHostAddress());
                System.out.println(getClass().getName() + ">>>Packet received; data: " + new String(packet.getData()));

                //See if the packet holds the right command (message)
                String message = new String(packet.getData()).trim();
                if (message.equals("DISCOVER_FUIFSERVER_REQUEST")) {
                    byte[] sendData = "DISCOVER_FUIFSERVER_RESPONSE".getBytes();

                    //Send a response
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
                    socket.send(sendPacket);

                    System.out.println(getClass().getName() + ">>>Sent packet to: " + sendPacket.getAddress().getHostAddress());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static DiscoveryThread getInstance() {
        return DiscoveryThreadHolder.INSTANCE;
    }

    private static class DiscoveryThreadHolder {

        private static final DiscoveryThread INSTANCE = new DiscoveryThread();
    }

}