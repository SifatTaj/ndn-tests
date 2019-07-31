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

    @Override
    public void onInterest(Name name, Interest interest, Face face, long l, InterestFilter interestFilter) {
        ++responseCount_;
        Data data = new Data(interest.getName());

        try {
            byte[] fileContent = Files.readAllBytes(file_.toPath());
            data.setContent(new Blob(fileContent));
            keyChain_.sign(data);
            face.putData(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRegisterFailed(Name name) {
        ++responseCount_;
        System.out.println("Register failed for prefix " + name.toUri());
    }
}
