package faife.learn.camtest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback, SurfaceTexture.OnFrameAvailableListener {
    private static final String TAG = CameraView.class.getName();

    private CamTest activity;
    private SurfaceHolder holder;
    private Camera camera;
    private boolean isInPreview;
    private boolean isConfigured;

    private static final int CAMERA_REQUEST_CODE = 1;

    public CameraView(CamTest activity) {
        super(activity);
        this.activity = activity;
        holder = getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        isInPreview = false;
        isConfigured = false;
    }

    private void startPreview() {
        if (isConfigured && camera!=null) {
            camera.startPreview();
            isInPreview=true;
        }
    }

    private void release() {
        holder.getSurface().release();
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    private void initPreview(int width, int height) {
        if (camera!=null && holder.getSurface()!=null) {
            try {
                camera.setPreviewDisplay(holder);
            }
            catch (Throwable t) {
                Log.e(TAG, "failed setting preview display", t);
            }
            if (!isConfigured) {
                Camera.Parameters parameters=camera.getParameters();
                Camera.Size size = camera.getParameters().getPreferredPreviewSizeForVideo();
                if (size!=null) {
                    parameters.setPreviewSize(size.width, size.height);
                    camera.setParameters(parameters);
                    isConfigured = true;
                }
            }
        }
    }

    public void onPause() {
        if (isInPreview) {
            camera.stopPreview();
        }
        camera.release();
        camera=null;
        isInPreview=false;
    }

    public void onResume() {
        while(activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }
        try {
            release();
            camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        } catch (Exception e) {
            Log.e(TAG, "could not open camera");
            e.printStackTrace();
        }
        startPreview();
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        initPreview(width, height);
        startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
