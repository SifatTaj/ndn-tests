package tcp_version;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.logging.Logger;

class Client {

    protected static String discoverServerIP() {
        DatagramSocket c;
        // Find the server using UDP broadcast
        try {
            //Open a random port to send the package
            c = new DatagramSocket();
            c.setBroadcast(true);

            byte[] sendData = "DISCOVER_FUIFSERVER_REQUEST".getBytes();

            //Try the 255.255.255.255 first
            try {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), 8888);
                c.send(sendPacket);
                System.out.println(Client.class.getName() + ">>> Request packet sent to: 255.255.255.255 (DEFAULT)");
            } catch (Exception e) {
            }

            // Broadcast the message over all the network interfaces
            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();

                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue; // Don't want to broadcast to the loopback interface
                }

                for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                    InetAddress broadcast = interfaceAddress.getBroadcast();
                    if (broadcast == null) {
                        continue;
                    }

                    // Send the broadcast package!
                    try {
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
                        c.send(sendPacket);
                    } catch (Exception e) {
                    }

                    System.out.println(Client.class.getName() + ">>> Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
                }
            }

            System.out.println(Client.class.getName() + ">>> Done looping over all network interfaces. Now waiting for a reply!");

            //Wait for a response
            byte[] recvBuf = new byte[15000];
            DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
            c.receive(receivePacket);

            //We have a response
            System.out.println(Client.class.getName() + ">>> Broadcast response from server: " + receivePacket.getAddress().getHostAddress());

            //Check if the message is correct
            String message = new String(receivePacket.getData()).trim();
            if (message.equals("DISCOVER_FUIFSERVER_RESPONSE")) {
                //DO SOMETHING WITH THE SERVER'S IP (for example, store it in your controller)
//                Controller_Base.setServerIp(receivePacket.getAddress());
                System.out.println("Server IP: " + receivePacket.getAddress());
            }

            //Close the port!
            c.close();
            return receivePacket.getAddress().toString();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    protected static void receiveData() throws Exception {
        String address = discoverServerIP().substring(1);

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