
package com.app.mobilityapp.camera;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;

import com.app.mobilityapp.MyApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Preview implements SurfaceHolder.Callback {
    private static final int DISPLAY_ORIENTATION = 90;
    private SurfaceHolder mHolder;
    public Camera mCamera;
    private SurfaceView surfaceView;
    Activity activity;
    int w = 768;
    int h;

    Preview(Activity context, SurfaceView surfaceView) {
        activity = context;
        this.surfaceView = surfaceView;
        mHolder = surfaceView.getHolder();
        mHolder.addCallback(this);
        h = getDisplaySize(activity).heightPixels - DpToPixel(100);
    }
    Preview(Activity context, SurfaceView sV, String type) {
        activity = context;
        surfaceView = sV;
        w = getDisplaySize(activity).widthPixels;
        h = getDisplaySize(activity).heightPixels - DpToPixel(135);
        activity.addContentView(new Box(activity, w, h), new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
        mHolder = surfaceView.getHolder();
        mHolder.addCallback(this);
    }
    public void setCamera(Camera camera) {
        mCamera = camera;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (mCamera != null) {
                mCamera.setPreviewDisplay(holder);
            }
        } catch (IOException exception) {
            String TAG = "Preview";
            Log.e(TAG, "IOException caused by setPreviewDisplay()", exception);
        }
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Surface will be destroyed when we return, so stop the preview.
        if (mCamera != null) {
            mCamera.stopPreview();
        }
    }

    public SurfaceHolder getHolder() {
        return mHolder;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        set_parameters(mCamera);
    }

    @SuppressLint("ClickableViewAccessibility")
    void set_image_ui(Camera cam) {
        set_parameters(cam);
    }


    @SuppressLint("ClickableViewAccessibility")
    public void set_parameters(Camera camera) {

        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
//            List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
//            //searches for good picture quality
//            Camera.Size bestDimens = null;
//            for(Camera.Size dimens : sizes){
//                if(dimens.width  <= 1024 && dimens.height <= 768){
//                    if (bestDimens == null || (dimens.width > bestDimens.width && dimens.height > bestDimens.height)) {
//                        bestDimens = dimens;
//                    }
//                }
//            }
            Camera.Size pictureSize, tmppictureSize;
            List<Camera.Size> listSupportedPictureSizes = parameters.getSupportedPictureSizes();
            pictureSize = listSupportedPictureSizes.get(0);
            pictureSize.width = 0xFFFF;
            pictureSize.height = 0xFFFF;


            // pick the lowest resolution picture (minimum height)
            for (int i = 0; i < listSupportedPictureSizes.size(); i++) {
                tmppictureSize = listSupportedPictureSizes.get(i);
//
                if (tmppictureSize.height <= pictureSize.height && tmppictureSize.width >= h && tmppictureSize.height >= w) {
                    pictureSize.width = tmppictureSize.width;
                    pictureSize.height = tmppictureSize.height;
                }
            }

            // set a valid new height-width for camera-picture.
            if (pictureSize.height != 0xFFFF) {
                Log.e("width", String.valueOf(pictureSize.width));
                Log.e("height", String.valueOf(pictureSize.height));
                parameters.setPictureSize(pictureSize.width, pictureSize.height);
            }

//            parameters.setPictureSize(bestDimens.width, bestDimens.height);
            parameters.setJpegQuality(100);
            camera.setDisplayOrientation(90);
            camera.setParameters(parameters);
            camera.startPreview();
            make_focus();

            surfaceView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    make_focus();
                    return true;
                }
            });

        }
    }


    private void make_focus() {
        if (mCamera != null) {
            Camera camera = mCamera;
            camera.cancelAutoFocus();
            Camera.Parameters parameters = camera.getParameters();
            if (parameters.getFocusMode().equals(Camera.Parameters.FOCUS_MODE_AUTO)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            }

            if (parameters.getMaxNumFocusAreas() > 0) {
                List<Camera.Area> mylist = new ArrayList<>();
                mylist.add(new Camera.Area(new Rect(-1000, -1000, 1000, 0), 1000));
                parameters.setFocusAreas(mylist);
            }

            try {
                camera.cancelAutoFocus();
                camera.setParameters(parameters);
                camera.startPreview();
                camera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        if (camera.getParameters().getFocusMode().equals(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                            Camera.Parameters parameters = camera.getParameters();
                            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                            if (parameters.getMaxNumFocusAreas() > 0) {
                                parameters.setFocusAreas(null);
                            }
                            camera.setParameters(parameters);
                            camera.startPreview();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int DpToPixel(int dp) {
        return (int) (dp * MyApplication.getInstance().getResources().getDisplayMetrics().density);
    }

    public static DisplayMetrics getDisplaySize(Context context) {
        return context.getResources().getDisplayMetrics();
    }
}
