import net.named_data.jndn.*;
import net.named_data.jndn.security.KeyChain;
import net.named_data.jndn.util.Blob;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SendFile implements OnInterestCallback, OnRegisterFailed {
    KeyChain keyChain_;
    Name certificateName_;
    int responseCount_ = 0;
    File file_;

    public SendFile(KeyChain keyChain, File file) {
        keyChain_ = keyChain;
        file_ = file;
    }

    // The following function is called when an interest packet is received matching the prefix.
    @Override
    public void onInterest(Name name, Interest interest, Face face, long l, InterestFilter interestFilter) {
        // Setting a counter to break the main event loop.
        ++responseCount_;
        Data data = new Data(interest.getName());

        // Encoding the dsta.
        try {
            // Converting the file to a byte array.
            byte[] fileContent = Files.readAllBytes(file_.toPath());

            // Printing size of the file.
            System.out.println("Data Size: " + fileContent.length);

            // Putting the file in a data packet.
            data.setContent(new Blob(fileContent));

            // Signing the data.
            keyChain_.sign(data);

            // Pushing the data to the connected NFD.
            face.putData(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // The following function is called when a request times out.
    @Override
    public void onRegisterFailed(Name name) {
        ++responseCount_;
        System.out.println("Register failed for prefix " + name.toUri());
    }
}
