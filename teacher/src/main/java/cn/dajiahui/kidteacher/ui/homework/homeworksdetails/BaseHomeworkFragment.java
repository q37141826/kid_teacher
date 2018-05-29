package cn.dajiahui.kidteacher.ui.homework.homeworksdetails;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.fxtx.framework.ui.FxFragment;

import java.io.IOException;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.ui.homework.myinterface.CheckHomework;
import cn.dajiahui.kidteacher.ui.homework.view.AudioDialog;


/**
 * Created by lenovo on 2018/1/5.
 */

public abstract class BaseHomeworkFragment extends FxFragment implements CheckHomework {
    public GetBaseHomeworkFragment baseHomeworkFragment;

    public MediaPlayer mediaPlayer;
    public AudioDialog audioDialog;

    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        bundle = getArguments();
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mediaPlayer = new MediaPlayer();
        /*设置动画*/
        settingRing();
        if (audioDialog == null) {
            audioDialog = new AudioDialog(getActivity()) {
            };
            audioDialog.setTitle(R.string.prompt);
            audioDialog.setMessage(R.string.no_audio);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        baseHomeworkFragment = (GetBaseHomeworkFragment) activity;
    }

    @Override
    public abstract void setArguments(Bundle bundle);

    /*公共接口 音乐播放器*/
    public interface GetBaseHomeworkFragment {
        public void getBaseHomeworkFragment(BaseHomeworkFragment baseHomeworkFragment);
    }

    @Override
    public void onStop() {
        super.onStop();
        mediaPlayer.stop();
    }

    @Override
    public void onPause() {
        super.onPause();

        mediaPlayer.stop();
    }

    public void playMp3(String mp3path) {
        if (mediaPlayer.isPlaying() == false) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(mp3path);
            mediaPlayer.prepare();
            mediaPlayer.start();
            baseHomeworkFragment = (BaseHomeworkFragment.GetBaseHomeworkFragment) getActivity();
            baseHomeworkFragment.getBaseHomeworkFragment(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (animationDrawable != null && !animationDrawable.isRunning()) {
                    animationDrawable.start();
                }

            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                if (animationDrawable != null && animationDrawable.isRunning()) {
                    animationDrawable.stop();
                }

            }
        });
    }

    public AnimationDrawable animationDrawable;

    @SuppressLint("ResourceType")
    private void settingRing() {
        // 通过逐帧动画的资源文件获得AnimationDrawable示例
        animationDrawable = (AnimationDrawable) getResources().getDrawable(
                R.drawable.ring);


    }

    /*结束音频*/
    public void stopAutio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    } /*结束动画*/

    public void stopAnimation() {
        if (animationDrawable != null && animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
    }


    /*翻页回来*/
    @Override
    public void submitHomework(Object questionModle) {

    }
}
