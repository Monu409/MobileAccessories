
package com.app.mobilityapp.camera;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mobilityapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class CameraActivity extends Activity {
    private static final String TAG = "CameraActivity";
    private Preview preview;
    private Camera camera;
    private SurfaceView surfaceView;
    private ImageView swich_cmra, flash_btn;
    private String path, fileName;
    private boolean isProcessing;
    private OrientationEventListener orientationListener;
    public static int outputOrientation;
    ProgressDialog progressDialog;
    File outFile;
    private boolean isFlashOn;
    static File dir;
    int numCams, cam_id;
    FloatingActionButton take_picture;
    TextView msg_tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.main);
        surfaceView = findViewById(R.id.surfaceView);
        take_picture = findViewById(R.id.take_picture);
        swich_cmra = findViewById(R.id.swich_cmra);
        flash_btn = findViewById(R.id.flash_btn);
        msg_tv = findViewById(R.id.scan_msg_tv);

        getInformation();
        numCams = Camera.getNumberOfCameras();
        if (numCams > 0) {
            startCamera();
            cam_id = 0;
        }
        setListener();
    }

    boolean is_time = true, is_scan;

    private void getInformation() {
        fileName = getIntent().getStringExtra("name");
        path = getIntent().getStringExtra("path");
        is_time = getIntent().getBooleanExtra("istime", true);
        is_scan = getIntent().getBooleanExtra("isScan", false);
        if (!is_scan)
            preview = new Preview(this, surfaceView);
        else {
            preview = new Preview(this, surfaceView, "");
            msg_tv.setVisibility(View.VISIBLE);
            msg_tv.setText("Click to scan label. Please ensure that the label is upright and the batch number is in the center of the screen.");
        }
    }

    private void setListener() {
        take_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (isProcessing)
                    return;
                isProcessing = true;
                camera.takePicture(null, null, jpegCallback);
            }
        });

        flash_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFlashOn) {
                    turnOfFlash();
                    flash_btn.setImageResource(R.drawable.flashoff);
                } else {
                    turnOnFlash();
                    flash_btn.setImageResource(R.drawable.flashon);
                }
                isFlashOn = !isFlashOn;

            }
        });
        swich_cmra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numCams > 1 && camera != null) {
                    preview.mCamera.stopPreview();
                    preview.getHolder().removeCallback(preview);
                    preview.mCamera.release();
                    preview = new Preview(CameraActivity.this, surfaceView);
                    if (cam_id == 0) {
                        cam_id = 1;
                        switch_camera(cam_id);

                    } else {
                        cam_id = 0;
                        switch_camera(cam_id);
                    }
                }

            }

        });
    }

    private void switch_camera(int cam_id) {
        try {
            preview.mCamera = Camera.open(cam_id);
            preview.mCamera.setPreviewDisplay(preview.getHolder());
            preview.mCamera.startPreview();
            preview.set_image_ui(preview.mCamera);
            camera = preview.mCamera;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void startCamera() {
        try {
            camera = Camera.open(0);
            camera.startPreview();
            preview.setCamera(camera);
        } catch (RuntimeException ex) {
            Toast.makeText(getApplicationContext(), "CameraTest not found", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (camera != null) {
            try {
                camera.reconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (orientationListener == null) {
            initOrientationListener();
        }
        orientationListener.enable();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (camera != null) {
            camera.stopPreview();
            preview.setCamera(null);
            camera.release();
            camera = null;
        }
    }

    Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            new SaveImageTask().execute(data);
        }
    };

    private class SaveImageTask extends AsyncTask<byte[], Void, File> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(CameraActivity.this);
        }

        @Override
        protected File doInBackground(byte[]... data) {
            return saveBitmap(data[0]);
        }

        @Override
        protected void onPostExecute(File file) {
            Intent sendBackResult = new Intent();
            sendBackResult.setData(Uri.fromFile(file));
            setResult(RESULT_OK, sendBackResult);
            finish();
        }
    }

    public File saveBitmap(byte[] data) {
        Bitmap loadedImage = BitmapFactory.decodeByteArray(data, 0, data.length);
        Matrix rotateMatrix = new Matrix();
        if (cam_id == 1) {
            float[] mirrorY = {-1, 0, 0, 0, 1, 0, 0, 0, 1};
            rotateMatrix = new Matrix();
            Matrix matrixMirrorY = new Matrix();
            matrixMirrorY.setValues(mirrorY);
            rotateMatrix.postConcat(matrixMirrorY);
            rotateMatrix.preRotate(0);
        }

        if (loadedImage.getWidth() > loadedImage.getHeight())
            rotateMatrix.postRotate(outputOrientation);
        Bitmap rotatedBitmap;
        if (!is_scan) {
            rotatedBitmap = Bitmap.createBitmap(loadedImage, 0, 0, loadedImage.getWidth(), loadedImage.getHeight(), rotateMatrix, false);
            if (is_time)
                rotatedBitmap = drawTextToBitmap(rotatedBitmap);
        } else {
            rotatedBitmap = Bitmap.createBitmap(loadedImage, loadedImage.getWidth() / 2 - 350, loadedImage.getHeight() / 2 - 350, 700, 700, rotateMatrix, false);
        }
        Bitmap bmOverlay = Bitmap.createBitmap(rotatedBitmap.getWidth(), rotatedBitmap.getHeight(), rotatedBitmap.getConfig());

        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(rotatedBitmap, 0f, 0f, null);
        dir = new File(path);
        if (!dir.exists())
            dir.mkdir();
        outFile = new File(dir, fileName);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(outFile);
            bmOverlay.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return outFile;
    }

    public Bitmap drawTextToBitmap(Bitmap bitmap) {
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Time now = new Time();
        now.setToNow();
        String gText = now.format("%k:%M:%S");
        Resources resources = getResources();
        float scale = resources.getDisplayMetrics().density;
        Canvas canvas = new Canvas(mutableBitmap);
        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.WHITE);
//        paint.setColor(Color.rgb(61, 61, 61));
        // text size in pixels
        paint.setTextSize((int) (24 * scale));
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
        // draw text to the Canvas center
        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);
        int x = (mutableBitmap.getWidth() - bounds.width() - 50);
        int y = (mutableBitmap.getHeight() + bounds.height() - 70);
        canvas.drawText(gText, x, y, paint);
        return mutableBitmap;
    }


    private void initOrientationListener() {
        orientationListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {
                if (camera != null && orientation != ORIENTATION_UNKNOWN) {
                    int newOutputOrientation = getCameraPictureRotation(orientation);
                    if (newOutputOrientation != outputOrientation) {
                        outputOrientation = newOutputOrientation;
                        Camera.Parameters params = camera.getParameters();
                        Log.e("orientation", "is " + outputOrientation);
                        params.setRotation(outputOrientation);
                        try {
                            camera.setParameters(params);
                        } catch (Exception e) {
                            Log.e("Exception", "Exception updating camera parameters in orientation change", e);
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
    }

    private int getCameraPictureRotation(int orientation) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(0, info);
        int rotation;
        orientation = (orientation + 45) / 90 * 90;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            rotation = (info.orientation - orientation + 360) % 360;
        } else { // back-facing camera
            rotation = (info.orientation + orientation) % 360;
        }
        return (rotation);
    }

    private void turnOnFlash() {
        Camera.Parameters p = camera.getParameters();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(p);
        camera.startPreview();
    }

    private void turnOfFlash() {
        Camera.Parameters params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);
    }

}
