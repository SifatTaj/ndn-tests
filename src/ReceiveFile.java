import net.named_data.jndn.Data;
import net.named_data.jndn.Interest;
import net.named_data.jndn.OnData;
import net.named_data.jndn.OnTimeout;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;

public class ReceiveFile implements OnData, OnTimeout {
    public int callbackCount_ = 0;
    public byte[] bytes_;
    private String saveLocation_;

    public ReceiveFile(String saveLocation) {
        saveLocation_ = saveLocation;
    }

    @Override
    public void onData(Interest interest, Data data) {
        ++callbackCount_;
        System.out.println("Got Data with prefix " + data.getName().toUri());
        ByteBuffer content = data.getContent().buf();
//        content.rewind();
        byte[] bytes = new byte[content.remaining()];
        content.get(bytes);
        bytes_ = bytes;
        System.out.println("Data size: " + bytes_.length);
        try {
            Files.write(new File(saveLocation_).toPath(), bytes_);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTimeout(Interest interest) {
        ++callbackCount_;
        System.out.println("Time out for interest " + interest.getName().toUri());
    }
}
