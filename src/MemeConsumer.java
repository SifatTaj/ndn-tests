import net.named_data.jndn.Face;
import net.named_data.jndn.Interest;
import net.named_data.jndn.Name;

public class MemeConsumer {
    public static void main(String[] args) {

        // Setting defaultCanBePrefix.
        Interest.setDefaultCanBePrefix(true);

        try {
            // Creating a Face to connect to the NFD running in localhost.
            Face face = new Face();

            // Calling ReceiveFile module to receive the file.
            ReceiveFile recieveMeme = new ReceiveFile("/Users/taj/Pictures/theMEME.jpg");

            // Assigning NDN prefix.
            Name name = new Name("/sendmeme");

            // Printing the prefix.
            System.out.println("Express name " + name.toUri());

            // Creating the Interest packet and looking for "/sendmeme"
            // prefix in the FIB of the connected NDN
            face.expressInterest(name, recieveMeme, recieveMeme);

            // The main event loop.
            // Wait to receive one interest for the prefix.
            while (recieveMeme.callbackCount_ < 1) {
                face.processEvents();
                // We need to sleep for a few milliseconds so we don't use 100% of the CPU.
                Thread.sleep(5);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
