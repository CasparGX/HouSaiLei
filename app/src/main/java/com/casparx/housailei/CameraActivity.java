package com.casparx.housailei;

import android.app.Activity;
import android.content.Context;
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
import java.io.InputStream;
import java.util.List;

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
            Log.i(photoDir,bytes.length+"");
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
            //imgPhoto.setImageBitmap(bitmap);
            imgPhoto.setImageBitmap(readBitMap(this,fis));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_take_photo)
    void onClickBtnTakePhoto() {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        Camera.Size s = getBestSupportedSize(parameters.getSupportedPreviewSizes());
        parameters.setPreviewSize(s.width, s.height);
        s = getBestSupportedSize(parameters.getSupportedPictureSizes());
        parameters.setPictureSize(s.width,s.height);
        parameters.setJpegQuality(100);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mCamera.setParameters(parameters);
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    mCamera.takePicture(null, null, mPictureCallback);
                }
            }
        });
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     * @param context
     * @param is
     * @return
     */
    public static Bitmap readBitMap(Context context, InputStream is){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        //InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is,null,opt);
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

    /******************************************]
     *
     * 穷举法找出具有最大数目像素的尺寸
     *
     * @param sizes
     * @return
     */
    public Camera.Size getBestSupportedSize(List<Camera.Size> sizes) {
        Camera.Size bestSize = sizes.get(0);
        int largestArea = bestSize.width*bestSize.height;
        for (Camera.Size s :sizes) {
            int area =s.width*s.height;
            if (area>largestArea) {
                bestSize=s;
                largestArea = area;
            }
        }
        return bestSize;
    }
}
