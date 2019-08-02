import net.named_data.jndn.Face;
import net.named_data.jndn.Interest;
import net.named_data.jndn.Name;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

public class MemeConsumer {
    public static void main(String[] args) {

        Interest.setDefaultCanBePrefix(true);

        try {
            Face face = new Face();
            RecieveFile recieveMeme = new RecieveFile("/Users/taj/Pictures/theMEME.jpg");
            Name name = new Name("/sendmeme");
//            name.append("dank");
            System.out.println("Express name " + name.toUri());

            face.expressInterest(name, recieveMeme, recieveMeme);

            while (recieveMeme.callbackCount_ < 1) {
                face.processEvents();
                Thread.sleep(5);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
