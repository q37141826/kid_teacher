package com.fxtx.framework.lamemp3;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import java.io.File;
import java.io.IOException;
import java.util.Timer;

/**
 * MP3录制集合类。提供了mp3的播放和录制功能
 *
 * @author Administrator
 */
public class Mp3Audio {
    private Mp3Recorder recorder; // 录制对象


    private MediaPlayer mPlayer;// 播放对象
    private Context context;
    private AudioListener listener; // 监听对象
    private Timer timer;


    private boolean isPause;//暂停状态

    public Mp3Audio(Context context, AudioListener listeners) {
        this.context = context;
        recorder = new Mp3Recorder(context, listeners);
        this.listener = listeners;

    }


    /**
     * 设置录音最大时限 单位秒
     */
    public void setMp3MaxTimers(int timers) {
        recorder.setMaxTime(timers);
    }

    //初始化录音
    public void initAudio(String filePath) {
        mPlayer = null;
        mPlayer = new MediaPlayer();
        timer = new Timer();
        mPlayer.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // 反馈 播放结束
                timer.cancel();// 结束计时
                mPlayer.stop();
                isPause = false;
                listener.onPlayStop();
            }
        });
        try {
            mPlayer.setDataSource(filePath);
            mPlayer.prepare();
            timer.schedule(new TimerRunning(), 0, 400);
        } catch (IOException e) {
            e.printStackTrace();
            listener.onError(e.getMessage());
        }
    }

    public void startAudio(String filepath) {

        if (isPause) {
            //属于暂停
            mPlayer.start();
            listener.onPlayStart();
            return;
        }

        initAudio(filepath);
        try {
            isPause = false;
            mPlayer.start();
            listener.onPlayStart();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            listener.onError(e.getMessage());
        } catch (SecurityException e) {
            e.printStackTrace();
            listener.onError(e.getMessage());
        } catch (IllegalStateException e) {
            e.printStackTrace();
            listener.onError(e.getMessage());
        }
    }

    public boolean isPlayer() {
        if (mPlayer != null)
            return mPlayer.isPlaying();
        else {
            return false;
        }
    }

    public int getAudioDuration() {
        return mPlayer != null ? mPlayer.getDuration() / 1000 : 0;
    }

    /**
     * ͣ停止播放
     */
    public void stopAudio() {
        if (timer != null) {
            timer.cancel();// 结束计时
        }
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        listener.onPlayStop();

    }

    public void pauseAudio() {
        if (mPlayer != null) {
            mPlayer.pause();
        }
        isPause = true;
    }

    /**
     * 开始录制
     */
    public void startRecorder() {
        recorder.startRecording();
    }

    /**
     * 停止录制
     */
    public void stopRecorder() {
        recorder.stopRecording();
    }


    /**
     * 获取录制文件存放地址
     *
     * @return
     */
    public File getFile() {
        return recorder.getFile();
    }

    class TimerRunning extends java.util.TimerTask {

        @Override
        public void run() {
            if(listener!=null && mPlayer!=null)
            listener.onDuration(mPlayer.getCurrentPosition() / 1000); // 接口回调
        }
    }

    /**
     * 获取时间 录音文件的时间长度
     *
     * @return
     */
    public int getMaxDuration() {
        return recorder.getMaxDuration();
    }

    public void setPause(boolean isPause) {
        this.isPause = isPause;
    }


    public MediaPlayer getmPlayer() {
        return mPlayer;
    }
}
