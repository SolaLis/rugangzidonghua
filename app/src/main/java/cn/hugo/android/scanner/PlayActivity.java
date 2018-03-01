package cn.hugo.android.scanner;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v4.view.GestureDetectorCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PlayActivity extends Activity implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private static final String DEBUG_TAG = "top";
    private GestureDetectorCompat mDetector;
    final int RIGHT = 0;
    final int LEFT = 1;
    final int UP = 2;
    final int DOWN = 3;
    private MediaPlayer player;
    private SurfaceView view;

    private String path = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/125.mp4";

    private String path1 = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/126.mp4";


//    private String path2 = Environment.getExternalStorageDirectory()
//            .getAbsolutePath() + "/sz-3.mp4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        mDetector = new GestureDetectorCompat(this, this);
        // Set the gesture detector as the double tap
        // listener.
        mDetector.setOnDoubleTapListener(this);

        //检测读写权限
//        PermisionUtils.verifyStoragePermissions(this);

        view = (SurfaceView) findViewById(R.id.surfaceView);
        player = new MediaPlayer();
        // 设置SurfaceView自己不管理缓冲区
        view.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        // 设置播放时打开屏幕
        view.setKeepScreenOn(true);
        view.getHolder().addCallback(new Surfacelistener());

    }


    private void plays() {
        player.reset();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setLooping(true);
        // 设置需要播放的视频


        try {
//            player.setDataSource("rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov");


            if(0==QRApplication.state){
                player.setDataSource(path);
            }else if(1==QRApplication.state){
                player.setDataSource(path1);
            }

            // 把视频画面输出到SurfaceView
            player.setDisplay(view.getHolder());
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private class Surfacelistener implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                // 开始播放
                plays();
            } catch (Exception e) {

            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
        }

    }

    // 当其他的Activity被打开时，暂停播放
    @Override
    protected void onPause() {
        if (player.isPlaying()) {
            // 暂停播放
            player.stop();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (player.isPlaying()) {
            player.stop();
        }
        player.release();
        super.onDestroy();
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {

        float x = e2.getX() - e1.getX();
        float y = e2.getY() - e1.getY();

        if (x > 0) {
            doResult(RIGHT);
        } else if (x < 0) {
            doResult(LEFT);
        }
        if (y > 0) {
            doResult(DOWN);
        } else if (y < 0) {
            doResult(UP);
        }


        return true;
    }

    public void doResult(int action) {

        switch (action) {
            case RIGHT:
                Log.e("top", "go right");
                break;
            case LEFT:
                Log.e("top", "go left");
                break;
            case UP:
                Log.e("top", "go UP");
                break;
            case DOWN:
                Log.e("top", "go DOWN");

                Intent intent = new Intent(PlayActivity.this, CaptureActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }


}

