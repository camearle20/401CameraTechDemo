package came20.net.a401cameratechdemo;


import android.util.Log;

import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cameronearle on 10/31/16.
 */

public class ImageProcessor implements Runnable {

    private static List<byte[]> queue = new ArrayList<>();

    public static void addToQueue(byte[] data) {
        queue.add(data);
    }

    @Override
    public void run() {
        while (true) {
            if (queue.size() > 10) {
                queue.clear();
                Log.w("ImageBuffer", "Buffer Peaked");
            }

            while (queue.size() > 0) {

            }
        }
    }
}
