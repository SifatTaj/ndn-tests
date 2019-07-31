import net.named_data.jndn.Face;
import net.named_data.jndn.Name;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

public class TestRecieveMeme {
    public static void main(String[] args) {
        try {
            Face face = new Face();
            RecieveFile recieveMeme = new RecieveFile();
            Name name = new Name("/sendmeme");
//            name.append("dank");
            System.out.println("Express name " + name.toUri());

            face.expressInterest(name, recieveMeme, recieveMeme);

            Files.write(new File("theMEME.jpg").toPath(), recieveMeme.bytes_);

            while (recieveMeme.callbackCount_ < 1) {
                face.processEvents();
                Thread.sleep(5);
            }

        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
        }
    }
}
