import net.named_data.jndn.Face;
import net.named_data.jndn.Interest;
import net.named_data.jndn.Name;

import java.util.concurrent.TimeUnit;

public class MemeConsumer {

    public static long start = 0;
    public static long totalTime = 0;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0 ; i < 10 ; ++i) {
            // Setting defaultCanBePrefix.
            Interest.setDefaultCanBePrefix(true);

            try {
                // Creating a Face to connect to the NFD running in localhost.
                Face face = new Face();

                // Calling ReceiveFile module to receive the file.
                ReceiveFile receiveFile = new ReceiveFile("data_from_ndn.rtf");

                // Assigning NDN prefix.
                Name name = new Name("/sendtestdata");

                // Printing the prefix.
//            System.out.println("Express name " + name.toUri());

                // Creating the Interest packet and looking for "/sendmeme"
                // prefix in the FIB of the connected NFD

                start = System.nanoTime();

                face.expressInterest(name, receiveFile, receiveFile);

                // The main event loop.
                // Wait to receive one interest for the prefix.
                while (receiveFile.callbackCount_ < 1) {
                    face.processEvents();
                    // We need to sleep for a few milliseconds so we don't use 100% of the CPU.
                    Thread.sleep(5);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println("Average time: " + totalTime/10);
    }
}
