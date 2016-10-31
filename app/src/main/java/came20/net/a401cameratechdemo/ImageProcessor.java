package came20.net.a401cameratechdemo;


import android.hardware.Camera;
import android.util.Log;

import org.zeromq.ZMQ;

import java.util.ArrayList;
import java.util.List;

import boofcv.struct.image.GrayF32;

/**
 * Created by cameronearle on 10/31/16.
 */

public class ImageProcessor implements Runnable {


    private Camera camera;
    private static List<byte[]> queue = new ArrayList<>();

    public ImageProcessor(Camera camera) {
        this.camera = camera;
    }

    public static void addToQueue(byte[] data) {
        queue.add(data);
    }

    private GrayF32 convertImage(byte[] data) {
        return null;
    }

    @Override
    public void run() {
        while (true) {
            if (queue.size() > 10) {
                queue.clear();
                Log.w("ImageProcessor-Buffer", "Buffer Peaked");
            }

            while (queue.size() > 0) {
                byte[] image = queue.remove(0);

            }
        }
    }
}
