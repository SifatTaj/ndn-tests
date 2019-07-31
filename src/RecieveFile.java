import net.named_data.jndn.Data;
import net.named_data.jndn.Interest;
import net.named_data.jndn.OnData;
import net.named_data.jndn.OnTimeout;

import java.nio.ByteBuffer;

public class RecieveFile implements OnData, OnTimeout {
    public int callbackCount_ = 0;
    public byte[] bytes_;

    @Override
    public void onData(Interest interest, Data data) {
        ++callbackCount_;
        System.out.println("Got Data with name " + data.getName().toUri());
        ByteBuffer content = data.getContent().buf();
        content.rewind();
        byte[] bytes = new byte[content.remaining()];
        content.get(bytes);
        bytes_ = bytes;
    }

    @Override
    public void onTimeout(Interest interest) {
        ++callbackCount_;
        System.out.println("Time out for interest " + interest.getName().toUri());
    }
}
