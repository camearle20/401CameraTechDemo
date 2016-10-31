package came20.net.a401cameratechdemo;

import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;

import java.io.ByteArrayOutputStream;

/**
 * Created by cameronearle on 10/31/16.
 */

public class ImageReceiver implements Camera.PreviewCallback {
    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Camera.Size size = camera.getParameters().getPreviewSize();
        YuvImage image = new YuvImage(data, camera.getParameters().getPreviewFormat(), size.width, size.height, null);
        image.compressToJpeg(new Rect(0,0, size.width, size.height), 100, baos);
        PreviewSocket.addToQueue(baos.toByteArray());
    }
}
