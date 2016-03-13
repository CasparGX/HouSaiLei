package com.casparx.housailei;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraActivity extends Activity implements SurfaceHolder.Callback {

    @Bind(R.id.layout_camera)
    SurfaceView layoutCamera;
    @Bind(R.id.btn_take_photo)
    ImageView btnTakePhoto;
    @Bind(R.id.img_photo)
    ImageView imgPhoto;


    private Camera mCamera;
    private boolean isMove;
    private SurfaceHolder mHolder;
    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            String photoDir = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/Camera/";
            File tempFile = new File(photoDir + DateFormat.format("yyyy-MM-dd kk.mm.ss", System.currentTimeMillis())
                    .toString() + ".jpg");
            Log.i(photoDir,tempFile+"");
            try {
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(bytes);
                fos.close();

                showPhoto(tempFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    private int screenWidth;
    private int screenHeight;

    private void showPhoto(String path) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            Matrix matrix = new Matrix();
            matrix.setRotate(90);
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            imgPhoto.setVisibility(View.VISIBLE);
            layoutCamera.setVisibility(View.GONE);
            //Bitmap bitmap = BitmapFactory.decodeFile(path);
            imgPhoto.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_take_photo)
    void onClickBtnTakePhoto() {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setPreviewSize(1920, 1080);
        parameters.setJpegQuality(100);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    mCamera.takePicture(null, null, mPictureCallback);
                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        mHolder = layoutCamera.getHolder();
        mHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        setStartPreview(mCamera, mHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        mCamera.stopPreview();
        setStartPreview(mCamera, mHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        releaseCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera == null) {
            mCamera = getCamera();
            if (mHolder != null) {
                setStartPreview(mCamera, mHolder);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    /**
     * 获取Camera对象
     */
    private Camera getCamera() {
        Camera camera;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            camera = null;
            e.printStackTrace();
        }
        return camera;
    }


    /**
     * 开始预览相机内容
     */
    private void setStartPreview(Camera camera, SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            //将系统camera预览角度进行调整
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 释放相机资源
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }
}
