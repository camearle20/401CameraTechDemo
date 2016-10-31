package came20.net.a401cameratechdemo;

import android.util.Log;

import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cameronearle on 10/31/16.
 */

public class PreviewSocket implements Runnable {
    private ZMQ.Context context;
    private ZMQ.Socket socket;
    private int port;

    private static List<byte[]> queue = new ArrayList<>();

    public PreviewSocket(ZMQ.Context context, int port) {
        this.context = context;
        this.port = port;
    }

    public static void addToQueue(byte[] data) {
        queue.add(data);
    }

    @Override
    public void run() {
        socket = context.socket(ZMQ.PUB);
        socket.bind("tcp://*:" + port);
        while (true) {
            if (queue.size() > 10) {
                queue.clear();
                Log.w("ImageBuffer", "Buffer Peaked");
            }
            while (queue.size() > 0) {
                socket.send(queue.remove(0));
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {}
        }
    }
}
