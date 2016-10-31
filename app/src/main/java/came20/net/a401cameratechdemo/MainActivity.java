package came20.net.a401cameratechdemo;

import android.hardware.Camera;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import org.zeromq.ZMQ;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Camera mCamera = null;
    private CameraView mCameraView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try{
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK); //Use open(int) to use different cameras
        } catch (Exception e){
            Log.d("ERROR", "Failed to get camera: " + e.getMessage());
        }

        Camera.Parameters parameters = mCamera.getParameters();

        System.out.println("---AVAILABLE SIZES---");
        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            System.out.println("W: " + size.width + " H: " + size.height);
        }
        System.out.println("---END AVAILABLE SIZES---");
        System.out.println("---DEFAULT SIZE---");
        System.out.println(parameters.getPreviewSize().width + "," + parameters.getPreviewSize().height);
        System.out.println("---END DEFAULT SIZE---");
        List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
        Camera.Size cs = sizes.get(6); //DROID TURBO: 640,480 (the dankest resolution)
        System.out.println("FINAL WIDTH: " + cs.width + " FINAL HEIGHT: " + cs.height);
        parameters.setPreviewSize(cs.width, cs.height);
        mCamera.setParameters(parameters);


        PreviewSocket previewSocket = new PreviewSocket(ZMQ.context(1), mCamera, 5555);
        Thread previewSocketThread = new Thread(previewSocket);
        previewSocketThread.start();


        mCamera.startPreview();


        if(mCamera != null) {
            mCameraView = new CameraView(this, mCamera);//create a SurfaceView to show camera data
            FrameLayout camera_view = (FrameLayout)findViewById(R.id.camera_view);
            camera_view.addView(mCameraView);//add the SurfaceView to the layout
        }

        ImageButton imgClose = (ImageButton)findViewById(R.id.imgClose);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });


    }
}

