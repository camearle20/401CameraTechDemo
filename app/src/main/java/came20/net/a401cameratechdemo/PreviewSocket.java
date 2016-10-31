package came20.net.a401cameratechdemo;

import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.Log;
import android.util.Size;

import org.zeromq.ZMQ;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cameronearle on 10/31/16.
 */

public class PreviewSocket implements Runnable {
    private ZMQ.Context context;
    private Camera camera;
    private Camera.Size size;
    private int format;
    private ZMQ.Socket socket;
    private int port;

    private static List<byte[]> queue = new ArrayList<>();

    public PreviewSocket(ZMQ.Context context, Camera camera, int port) {
        this.context = context;
        this.camera = camera;
        this.port = port;

        this.size = this.camera.getParameters().getPreviewSize();
        this.format = this.camera.getParameters().getPreviewFormat();
    }

    public static void addToQueue(byte[] data) {
        queue.add(data);
    }

    private byte[] convertImage(byte[] data) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        YuvImage image = new YuvImage(data, format, size.width, size.height, null);
        image.compressToJpeg(new Rect(0,0, size.width, size.height), 100, baos);
        return baos.toByteArray();
    }

    @Override
    public void run() {
        socket = context.socket(ZMQ.PUB);
        socket.bind("tcp://*:" + port);
        while (true) {
            if (queue.size() > 10) {
                queue.clear();
                Log.w("PreviewSocket-Buffer", "Buffer Peaked");
            }
            while (queue.size() > 0) {
                socket.send(convertImage(queue.remove(0)));
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {}
        }
    }
}
