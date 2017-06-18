package net.hadifar.downloader;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.FFmpegExecuteResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getCanonicalName();

    //todo 4: make sure be http
    private static final String INPUT_FILE = "http://184.72.239.149/vod/smil:BigBuckBunny.smil/playlist.m3u8";


    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initProgressBar();

        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });

        havePermissionForWriteStorage();


    }

    private void initProgressBar() {
        progressBar = new ProgressDialog(this);
        progressBar.setMessage("wait to download video m3u8...");
    }


    private boolean havePermissionForWriteStorage() {

        //marshmallow runtime permission
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Log.d("Permission Allowed", "true");
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 950);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    private void download() {


        String downloadPath = Environment.getExternalStorageDirectory() + "/" + "DOWNLOAD_AIO_VIDEO/";
        File dir = new File(downloadPath);
        if (!dir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();
        }


        String cmd = String.format("-i %s -acodec %s -bsf:a aac_adtstoasc -vcodec %s %s", INPUT_FILE, "copy", "copy", dir.toString() + "/bigBuckBunny.mp4");
        String[] command = cmd.split(" ");
        execFFmpegBinary(command);

    }

    private void execFFmpegBinary(String[] command) {
        try {
            FFmpeg ffmpeg = FFmpeg.getInstance(MainActivity.this);
            ffmpeg.execute(command, new FFmpegExecuteResponseHandler() {
                @Override
                public void onSuccess(String message) {
                    Log.i(TAG, "onSuccess: " + message);
                    progressBar.dismiss();
                }

                @Override
                public void onProgress(String message) {
                    Log.i(TAG, "onProgress: " + message);
                    progressBar.setMessage("Progressing: \n " + message);
                }

                @Override
                public void onFailure(String message) {
                    Log.i(TAG, "onFailure: " + message);
                    progressBar.dismiss();
                }

                @Override
                public void onStart() {
                    progressBar.show();
                }

                @Override
                public void onFinish() {
                    progressBar.dismiss();
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            e.printStackTrace();
        }
    }
}
