package came20.net.a401cameratechdemo;

import android.hardware.Camera;

/**
 * Created by cameronearle on 10/31/16.
 */

public class ImageReceiver implements Camera.PreviewCallback {

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        PreviewSocket.addToQueue(data);
    }
}
