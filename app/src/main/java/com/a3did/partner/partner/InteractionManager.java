package com.a3did.partner.partner;

import android.os.Environment;
import android.os.Message;
import android.util.Log;

import com.a3did.partner.account.UserManager;
import com.a3did.partner.partner.utils.AudioWriterPCM;

import net.daum.mf.speech.api.TextToSpeechClient;

import java.awt.font.TextAttribute;

/**
 * Created by admin on 2016-12-01.
 */

public class InteractionManager {

    private static InteractionManager mInstance;

    public TextToSpeechClient ttsClient;
    public MainActivity mActivity;
    public NaverRecognizer mNaverRecognizer;
    private boolean mIsRecognizerReady;
    private AudioWriterPCM writer;

    public enum MenuType{
        ASSISTANT,
        ACHIEVEMENT,
        SAFETY,
        REWARD,
        ACCOUNT,
        COMPLETED,
        MISSED
    };

    public int mSystemMode;
    public int mDetailMode;

    private InteractionManager(){
        ttsClient = null;
        mNaverRecognizer = null;
        mActivity = null;
        mIsRecognizerReady = false;
        mSystemMode = 0;
        mDetailMode = 0;
    }
    public static InteractionManager getInstance(){
        if(mInstance == null)
            mInstance = new InteractionManager();

        return mInstance;
    }

    public boolean isVoiceRecoReady(){
        return mIsRecognizerReady;
    }
    public void setVoiceRecoReady(boolean ready){
        mIsRecognizerReady = ready;
    }

    private String mResult;
    //Voice recognition Handler
    private void handleMessage(Message msg) {
        switch (msg.what) {
            case R.id.clientReady:
                // Now an user can speak.
                //txtResult.setText("Connected");
                writer = new AudioWriterPCM(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/NaverSpeechTest");
                writer.open("Test");
                break;

            case R.id.audioRecording:
                writer.write((short[]) msg.obj);
                break;

            case R.id.partialResult:
                // Extract obj property typed with String.
                mResult = (String) (msg.obj);
                //txtResult.setText(mResult);
                break;

            case R.id.finalResult:
                // Extract obj property typed with String array.
                // The first element is recognition result for speech.
                String[] results = (String[]) msg.obj;
                mResult = results[0];
                //txtResult.setText(mResult);
                if(ttsClient == null)
                    break;

            break;

            case R.id.recognitionError:
                if (writer != null) {
                    writer.close();
                }

                mResult = "Error code : " + msg.obj.toString();
                //txtResult.setText(mResult);
                //btnStart.setText(R.string.str_start);
                //btnStart.setEnabled(true);

                Log.d("Partner","recognitionError");


                break;

            case R.id.clientInactive:
                if (writer != null) {
                    writer.close();
                }

                Log.d("Partner","clientInactive");
                //btnStart.setText(R.string.str_start);
                //btnStart.setEnabled(true);
                break;
        }
        Log.d("Partner","ID" + msg.what + "text result : " + mResult);




    }

    //Check SystemMode
    void checkSystemMode(){
        if(mResult.contains("파트너")) {
            if (mResult.contains("일정")) {
                ttsClient.play("네 일정 보여드릴게요.");
                mActivity.transitionFragment(R.id.nav_assistant);
            } else if (mResult.contains("목표")) {
                ttsClient.play("네 목표 보여드릴게요.");
                mActivity.transitionFragment(R.id.nav_achievement);
            } else if (mResult.contains("보상")) {
                ttsClient.play("네 보상 탭 보여드릴게요.");
                mActivity.transitionFragment(R.id.nav_reward);
            } else if (mResult.contains("설정")) {
                ttsClient.play("네 설정 탭 보여드릴게요.");
                mActivity.transitionFragment(R.id.nav_account);
            } else if (mResult.contains("완료")) {
                ttsClient.play("네 완료리스트 보여드릴게요.");
                mActivity.transitionFragment(R.id.nav_completed_list);
            } else if (mResult.contains("실패")) {
                ttsClient.play("네 실패리스트 보여드릴게요.");
                mActivity.transitionFragment(R.id.nav_missed_list);
            }
        }
    }

    //Assistant Action

    //Achievement Action

    //Safery Action

    //Reward Action

    public void handleVoiceMessage(Message msg) {
        switch (msg.what) {
        }
    }

    public void handlehapticMessage(Message msg) {
        switch (msg.what) {

        }
    }
}
