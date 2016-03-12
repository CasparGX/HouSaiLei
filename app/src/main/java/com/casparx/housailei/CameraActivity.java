package com.casparx.housailei;

import android.app.Activity;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CameraActivity extends Activity implements SurfaceHolder.Callback {

    @Bind(R.id.layout_camera)
    SurfaceView layoutCamera;
    @Bind(R.id.btn_camera)
    ImageView btnCamera;

    private Camera camera;
    private boolean isMove;
    private SurfaceHolder surfaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        init();
        surfaceholder = layoutCamera.getHolder();
        surfaceholder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceholder.addCallback(CameraActivity.this);
    }

    private void init() {
        btnCamera.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isMove = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (isMove) {
                            moveBtnCamera(x, y);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        isMove = false;
                        break;
                }
                return true;//处理了触摸消息，消息不再传递
            }
        });
    }

    private void moveBtnCamera(int x, int y) {
        int cameraX, cameraY;
        ViewGroup.LayoutParams param = btnCamera.getLayoutParams();
        cameraX = x<param.width ? 0: x;
        cameraX = (x>layoutCamera.getWidth()-param.width) ? layoutCamera.getWidth()-param.width: x;
        cameraY = y<param.width ? 0:y;
        cameraY = (y>layoutCamera.getHeight()-param.width) ? layoutCamera.getHeight()-param.width: y;
        btnCamera.setTop(cameraY);
        btnCamera.setLeft(cameraX);
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //获取camera对象
        camera = Camera.open();
        try {
            //设置预览监听
            camera.setPreviewDisplay(surfaceHolder);
            Camera.Parameters parameters = camera.getParameters();

            if (this.getResources().getConfiguration().orientation
                    != Configuration.ORIENTATION_LANDSCAPE) {
                parameters.set("orientation", "portrait");
                camera.setDisplayOrientation(90);
                parameters.setRotation(90);
            } else {
                parameters.set("orientation", "landscape");
                camera.setDisplayOrientation(0);
                parameters.setRotation(0);
            }
            camera.setParameters(parameters);
            //启动摄像头预览
            camera.startPreview();
            System.out.println("camera.startpreview");

        } catch (IOException e) {
            e.printStackTrace();
            camera.release();
            System.out.println("camera.release");
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }
}
