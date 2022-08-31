package com.example.curriculum_design.Face_Camera;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Surface;

import java.io.ByteArrayOutputStream;

/**
 * 相机类，相机的调用
 */
public class FrontCamera {
    static final String TAG = "Camera";
    Camera mCamera;
    int mCurrentCamIndex = 0;
    boolean previewing;

    public void setCamera(Camera camera) {
        this.mCamera = camera;
    }

    public int getCurrentCamIndex() {
        return this.mCurrentCamIndex;
    }

    public boolean getPreviewing() {
        return this.previewing;
    }

    //初始化相机
    public Camera initCamera() {
        int cameraCount = 0;
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        Log.e(TAG, "cameraCount: " + cameraCount);
        previewing = true;

        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            //在这里打开的是前置摄像头,可修改打开后置OR前置
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                try {
                    cam = Camera.open(camIdx);
                    mCurrentCamIndex = camIdx;
                } catch (RuntimeException e) {
                    Log.e(TAG, "Camera failed to open: " + e.getLocalizedMessage());
                }
            }
        }
        return cam;
    }

    /**
     * 停止相机
     *
     * @param mCamera 需要停止的相机对象
     */
    public void StopCamera(Camera mCamera) {
        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
        previewing = false;
    }

    /**
     * 旋转屏幕后自动适配（若只用到竖的，也可不要）
     * 已经在manifests中让此Activity只能竖屏了
     *
     * @param activity 相机显示在的Activity
     * @param cameraId 相机的ID
     * @param camera   相机对象
     */
    public static void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {
            // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

}

class FaceTask extends AsyncTask {
    private byte[] mData;
    Camera mCamera;
    private static final String TAG = "CameraTag";
    //构造函数
    FaceTask(byte[] data , Camera camera)
    {
        this.mData = data;
        this.mCamera = camera;

    }
    @Override
    protected Object doInBackground(Object[] params) {
        Camera.Parameters parameters = mCamera.getParameters();
        int imageFormat = parameters.getPreviewFormat();
        int w = parameters.getPreviewSize().width;
        int h = parameters.getPreviewSize().height;

        Rect rect = new Rect(0, 0, w, h);
        YuvImage yuvImg = new YuvImage(mData, imageFormat, w, h, null);
        try {
            ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
            yuvImg.compressToJpeg(rect, 100, outputstream);
            Bitmap rawbitmap = BitmapFactory.decodeByteArray(outputstream.toByteArray(), 0, outputstream.size());
            Log.i(TAG, "onPreviewFrame: rawbitmap:" + rawbitmap.toString());
        }
        catch (Exception e)
        {
            Log.e(TAG, "onPreviewFrame: 获取相机实时数据失败" + e.getLocalizedMessage());
        }
        return null;
    }
}
