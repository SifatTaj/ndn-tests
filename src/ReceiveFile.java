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

    // The following method is called when a data is received matching the prefix.
    @Override
    public void onData(Interest interest, Data data) {
        // Setting a counter to break the main event loop.
        ++callbackCount_;

        // Printing the prefix from which it got the required data
        System.out.println("Got Data with prefix " + data.getName().toUri());

        // Converting the received byte array to byte buffer.
        ByteBuffer content = data.getContent().buf();
        byte[] bytes = new byte[content.remaining()];
        content.get(bytes);
        bytes_ = bytes;

        // Printing the received data size.
        System.out.println("Data size: " + bytes_.length);

        // Writing the data to saveLocation path.
        try {
            Files.write(new File(saveLocation_).toPath(), bytes_);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // The following method is called when a request times out.
    @Override
    public void onTimeout(Interest interest) {
        ++callbackCount_;
        System.out.println("Time out for interest " + interest.getName().toUri());
    }
}
