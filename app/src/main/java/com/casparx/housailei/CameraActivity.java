package com.casparx.housailei;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.casparx.housailei.model.DemoModel;

import java.io.File;
import java.io.FileInputStream;
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
    @Bind(R.id.btn_gallery)
    ImageView btnGallery;
    @Bind(R.id.btn_tip)
    ImageView btnTip;
    @Bind(R.id.btn_delete)
    ImageView btnDelete;
    @Bind(R.id.btn_ok)
    ImageView btnOk;
    @Bind(R.id.tip_pic)
    ImageView tipPic;
    @Bind(R.id.tip_title)
    TextView tipTitle;
    @Bind(R.id.tip_content)
    TextView tipContent;
    @Bind(R.id.tip_layout)
    LinearLayout tipLayout;
    private Camera mCamera;
    private boolean isMove;
    private SurfaceHolder mHolder;
    private File tempFile;
    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            String photoDir = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/Camera/";
            tempFile = new File(photoDir + DateFormat.format("yyyy-MM-dd kk.mm.ss", System.currentTimeMillis())
                    .toString() + ".jpg");
            Log.i(photoDir, bytes.length + "");
            try {
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(bytes);
                fos.close();

                Uri uri = Uri.fromFile(tempFile);
                showPhoto(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    private int screenWidth;
    private int screenHeight;
    private DemoModel demoModel;
    private boolean isTip;
    private boolean isTaked;

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param is
     * @return
     */
    public static Bitmap readBitMap(Context context, InputStream is) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        //InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    @OnClick(R.id.btn_tip)
    void onClickBtnTip(View view) {
        showTipPopupwindow();
    }

    @OnClick(R.id.tip_layout) void onClickTipLayout(){
        tipLayout.setVisibility(View.GONE);
        isTip = false;
    }

    private void showTipPopupwindow() {
        ViewGroup.LayoutParams params = tipPic.getLayoutParams();
        params.height = layoutCamera.getWidth();
        params.width = params.height;
        tipPic.setLayoutParams(params);
        tipPic.setImageBitmap(DemoAdapter.readBitMap(this, demoModel.getResId()));
        tipTitle.setText(demoModel.getTitle());
        tipContent.setText(demoModel.getDec());
        tipLayout.setVisibility(View.VISIBLE);
        isTip = true;
    }

    @OnClick(R.id.btn_ok)
    void onClickBtnOk(View view) {
        //通知扫描文件
        MediaScannerConnection.scanFile(CameraActivity.this, new String[]{tempFile + ""}, null, null);
        initCamera();
        onTakePhoto();
        setStartPreview(mCamera, mHolder);
        isTaked = false;
    }

    @OnClick(R.id.btn_delete)
    void onClickBtnDelete(View view) {
        tempFile.delete();
        initCamera();
        onTakePhoto();
        setStartPreview(mCamera, mHolder);
        isTaked = false;
    }

    private void onTakePhoto() {
        imgPhoto.setVisibility(View.GONE);
        btnDelete.setVisibility(View.GONE);
        btnOk.setVisibility(View.GONE);

        btnTakePhoto.setVisibility(View.VISIBLE);
        btnGallery.setVisibility(View.VISIBLE);
        btnTip.setVisibility(View.VISIBLE);
        layoutCamera.setVisibility(View.VISIBLE);
    }

    private void showPhoto(Uri uri) {
        isTaked = true;
        imgPhoto.setVisibility(View.VISIBLE);
        btnDelete.setVisibility(View.VISIBLE);
        btnOk.setVisibility(View.VISIBLE);

        layoutCamera.setVisibility(View.GONE);
        btnGallery.setVisibility(View.GONE);
        btnTip.setVisibility(View.GONE);
        btnTakePhoto.setVisibility(View.GONE);

        //releaseCamera();

        FileInputStream fis = null;
        Bitmap bm = compressBitmap(null, null, this, uri, 4, false);
        //imgPhoto.setImageBitmap(readBitMap(this, fis));
        imgPhoto.setImageBitmap(bm);
    }

    @OnClick(R.id.btn_take_photo)
    void onClickBtnTakePhoto() {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        Camera.Size s = getBestSupportedSize(parameters.getSupportedPreviewSizes());
        parameters.setPreviewSize(s.width, s.height);
        s = getBestSupportedSize(parameters.getSupportedPictureSizes());
        parameters.setPictureSize(s.width, s.height);
        mCamera.setDisplayOrientation(90);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        initCamera();

        Intent intent = getIntent();
        if (intent.hasExtra("pic")) {
            demoModel = new DemoModel();
            demoModel.setResId(intent.getIntExtra("pic", 1));
            demoModel.setDec(intent.getStringExtra("dec"));
            demoModel.setTitle(intent.getStringExtra("title"));
            btnTip.setImageBitmap(DemoAdapter.readBitMap(this, demoModel.getResId()));
        }


    }

    private void initCamera() {
        mCamera = getCamera();
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

    /******************************************
     * ]
     * <p/>
     * 穷举法找出具有最大数目像素的尺寸
     *
     * @param sizes
     * @return
     */
    public Camera.Size getBestSupportedSize(List<Camera.Size> sizes) {
        Camera.Size bestSize = sizes.get(0);
        int largestArea = bestSize.width * bestSize.height;
        for (Camera.Size s : sizes) {
            int area = s.width * s.height;
            if (area > largestArea) {
                bestSize = s;
                largestArea = area;
            }
        }
        return bestSize;
    }

    /**
     * 图片压缩处理，size参数为压缩比，比如size为2，则压缩为1/4
     **/
    private Bitmap compressBitmap(String path, byte[] data, Context context, Uri uri, int size, boolean width) {
        BitmapFactory.Options options = null;
        if (size > 0) {
            BitmapFactory.Options info = new BitmapFactory.Options();
/**如果设置true的时候，decode时候Bitmap返回的为数据将空*/
            info.inJustDecodeBounds = false;
            decodeBitmap(path, data, context, uri, info);
            int dim = info.outWidth;
            if (!width) dim = Math.max(dim, info.outHeight);
            options = new BitmapFactory.Options();
/**把图片宽高读取放在Options里*/
            options.inSampleSize = size;
        }
        Bitmap bm = null;
        try {
            bm = decodeBitmap(path, data, context, uri, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }


    /**
     * 把byte数据解析成图片
     */
    private Bitmap decodeBitmap(String path, byte[] data, Context context, Uri uri, BitmapFactory.Options options) {
        Bitmap result = null;
        if (path != null) {
            result = BitmapFactory.decodeFile(path, options);
        } else if (data != null) {
            result = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        } else if (uri != null) {
            ContentResolver cr = context.getContentResolver();
            InputStream inputStream = null;
            try {
                inputStream = cr.openInputStream(uri);
                result = BitmapFactory.decodeStream(inputStream, null, options);
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isTip) {
            onClickTipLayout();
        } else if (isTaked) {
            onClickBtnDelete(new View(this));
        }
    }
}
