package com.a3did.partner.partner;

import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.view.Menu;

import com.a3did.partner.account.UserManager;
import com.a3did.partner.adapterlist.AchievementListData;
import com.a3did.partner.adapterlist.AssistantListData;
import com.a3did.partner.adapterlist.RewardListData;
import com.a3did.partner.partner.utils.AudioWriterPCM;
import com.naver.speech.clientapi.SpeechConfig;
import com.perples.recosdk.RECOBeacon;

import net.daum.mf.speech.api.TextToSpeechClient;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Collection;

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
    private static String CLIENT_ID = "8_h3SjFEfYR7rygZLHz4"; // "내 애플리케이션"에서 Client ID를 확인해서 이곳에 적어주세요.
    private static SpeechConfig SPEECH_CONFIG = SpeechConfig.OPENAPI_KR; // or SpeechConfig.OPENAPI_EN

    private MainActivity.RecognitionHandler handler;


    public enum MenuType{
        DEFAULT,
        ASSISTANT,
        ACHIEVEMENT,
        SAFETY,
        REWARD,
        ACCOUNT,
        COMPLETED,
        MISSED
    };


    public MenuType mSystemMode;
    public int mDetailMode;
    public static InteractionManager getInstance(){
        if(mInstance == null)
            mInstance = new InteractionManager();

        return mInstance;
    }
    private InteractionManager(){
        ttsClient = null;
        mNaverRecognizer = null;
        mActivity = null;
        mIsRecognizerReady = false;
        mSystemMode = MenuType.DEFAULT;
        mDetailMode = 0;
    }

    public void Init(TextToSpeechClient tts, MainActivity activity){

        handler = new MainActivity.RecognitionHandler(activity);
        mNaverRecognizer = new NaverRecognizer(activity, handler, CLIENT_ID, SPEECH_CONFIG);


        //Recognizer setting
        //mNaverRecognizer.recognize();
        ttsClient = tts;
        mActivity = activity;
        setVoiceRecoReady(true);
    }



    public void sendBeaconData(Collection<RECOBeacon> beacons){

    }

    public boolean isVoiceRecoReady(){
        return mIsRecognizerReady;
    }
    public void setVoiceRecoReady(boolean ready){
        mIsRecognizerReady = ready;
    }

    private String mResult;
    //Voice recognition Handler
    public void handleVoiceMessage(Message msg) {
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
                if(checkSystemMode())
                {
                    switch (mSystemMode){
                        case ACCOUNT:
                            break;
                        case ASSISTANT:
                            AssistantAction();
                            break;
                        case ACHIEVEMENT:
                            AchievementAction();
                            break;
                        case REWARD:
                            RewardAction();
                            break;
                        case COMPLETED:
                            break;
                        case MISSED:
                            break;

                    }
                }

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
                mResult = "clientInactive";
                Log.d("Partner","clientInactive");
                //btnStart.setText(R.string.str_start);
                //btnStart.setEnabled(true);
                if(!mActivity.isOnPause){
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Log.d("Partner","re-connect Recognizer");
                            //if()
                            if(!mNaverRecognizer.getSpeechRecognizer().isRunning())
                                mNaverRecognizer.recognize();


                        }
                    });
                }

            break;
        }
        Log.d("Partner","ID" + msg.what + "text result : " + mResult);




    }

    //Check SystemMode
    boolean checkSystemMode(){
        if(mResult.contains("보고 싶으시면"))
            return false;
        if(mResult.contains("파트너")) {
            if (mResult.contains("일정")) {
                ttsClient.play("네 일정 보여드릴게요.");
                mActivity.transitionFragment(R.id.nav_assistant);
                mSystemMode = MenuType.ASSISTANT;
                return true;
            } else if (mResult.contains("목표")) {
                ttsClient.play("네 목표 보여드릴게요.");
                mActivity.transitionFragment(R.id.nav_achievement);
                mSystemMode = MenuType.ACHIEVEMENT;
                return true;
            } else if (mResult.contains("보상")) {
                ttsClient.play("네 보상 탭 보여드릴게요.");
                mActivity.transitionFragment(R.id.nav_reward);
                mSystemMode = MenuType.REWARD;
                return true;
            } else if (mResult.contains("계정")) {
                ttsClient.play("네 계정 탭 보여드릴게요.");
                mActivity.transitionFragment(R.id.nav_account);
                mSystemMode = MenuType.ACCOUNT;
                return true;

            } else if (mResult.contains("보안")) {
                ttsClient.play("네 보안 탭 보여드릴게요.");
                mActivity.transitionFragment(R.id.nav_safety);
                mSystemMode = MenuType.SAFETY;
                return true;

            } else if (mResult.contains("완료")) {
                ttsClient.play("네 완료리스트 보여드릴게요.");
                mActivity.transitionFragment(R.id.nav_completed_list);
                mSystemMode = MenuType.COMPLETED;
                return true;
            } else if (mResult.contains("실패")) {
                ttsClient.play("네 실패리스트 보여드릴게요.");
                mActivity.transitionFragment(R.id.nav_missed_list);
                mSystemMode = MenuType.MISSED;
                return true;
            }
        }

        return true;
    }

    //Assistant Action
    private void AssistantAction(){
        if(mResult.contains("오늘 할 일") || mResult.contains("오늘할일"))
        {
            int size = UserManager.getInstance().getCurrentUserInfo().mScheduleInfoList.size();
            if(size == 0){
                ttsClient.play("오늘은 일정이 없어요");
            }
            else
            {
                ttsClient.play("오늘은 총 " + size + "개의 일정이 있어요");
            }
        }
        else if(mResult.contains("먼저 할 일") || mResult.contains("먼저할일"))
        {
            ArrayList<AssistantListData> data = UserManager.getInstance().getCurrentUserInfo().mScheduleInfoList;
            if(data.size() == 0){
                ttsClient.play("오늘은 일정이 없어요");
            }
            else{
                AssistantListData info = data.get(0);
                ttsClient.play("먼저 " + info.getDate() + "에, " + info.getTitle() + "가 있어요");
            }
        }
        else if(mResult.contains("할 일 다") || mResult.contains("할일 다") || mResult.contains("할일다")){
            ArrayList<AssistantListData> data = UserManager.getInstance().getCurrentUserInfo().mScheduleInfoList;
            if(data.size() == 0){
                ttsClient.play("오늘은 일정이 없어요");
            }
            else{
                String talk = "오늘 일정을 말씀드릴게요.";
                for(int index = 0 ; index < data.size() ; index++)
                    talk += data.get(index).getTitle() + ". ";
                ttsClient.play(talk+ "총 " + data.size() + "개의 일정이 있어요");
            }
        }
    }
    //Achievement Action
    private void AchievementAction(){
        if(mResult.contains("오늘 목표") || mResult.contains("오늘목표"))
        {
            int size = UserManager.getInstance().getCurrentUserInfo().mAchievementInfoList.size();
            if(size == 0){
                ttsClient.play("오늘 정해둔 목표가 없어요");
            }
            else
            {
                ttsClient.play("현재 총 " + size + "개의 목표가 있어요");
            }
        }
        else if(mResult.contains("먼저 할 목표") || mResult.contains("먼저할목표"))
        {
            ArrayList<AchievementListData> data = UserManager.getInstance().getCurrentUserInfo().mAchievementInfoList;
            if(data.size() == 0){
                ttsClient.play("오늘 정해둔 목표가 없어요");
            }
            else{
                AchievementListData info = data.get(0);
                ttsClient.play("먼저 " + info.getFromStr() + "부터, " + info.getToStr() + "까지,"+ info.getTitle() + "가 있어요");
            }
        }
        else if(mResult.contains("모든 목표") || mResult.contains("모든목표") ){
            ArrayList<AchievementListData> data = UserManager.getInstance().getCurrentUserInfo().mAchievementInfoList;
            if(data.size() == 0){
                ttsClient.play("오늘 정해둔 목표가 없어요");
            }
            else{
                String talk = "모든 목표를 말씀드릴게요.";
                for(int index = 0 ; index < data.size() ; index++)
                    talk += data.get(index).getTitle() + ". ";
                ttsClient.play(talk+ "총 " + data.size() + "개의 목표가 있어요");
            }
        }
        else if(mResult.contains("첫번째 목표 가이드") ){
            //ttsClient.play("제 앞으로 와서, 손을 눌러주세요.");
            mDetailMode = 1;

            //
            ArrayList<AchievementListData> data = UserManager.getInstance().getCurrentUserInfo().mAchievementInfoList;
            if(data.size() >= mDetailMode){
                ttsClient.play("가이드를 말씀드릴게요. " + data.get(mDetailMode - 1).getGuideList().get(0));
            }

        }else if(mResult.contains("두번째 목표 가이드") ){
            //ttsClient.play("제 앞으로 와서, 손을 눌러주세요.");
            mDetailMode = 2;
            ArrayList<AchievementListData> data = UserManager.getInstance().getCurrentUserInfo().mAchievementInfoList;
            if(data.size() >= mDetailMode){
                ttsClient.play("가이드를 말씀드릴게요. " + data.get(mDetailMode - 1).getGuideList().get(0));
            }
        }else if(mResult.contains("세번째 목표 가이드") ){
            //ttsClient.play("제 앞으로 와서, 손을 눌러주세요.");
            mDetailMode = 3;
            ArrayList<AchievementListData> data = UserManager.getInstance().getCurrentUserInfo().mAchievementInfoList;
            if(data.size() >= mDetailMode){
                ttsClient.play("가이드를 말씀드릴게요. " + data.get(mDetailMode - 1).getGuideList().get(0));
            }
        }else if(mResult.contains("네번째 목표 가이드") ){
            //ttsClient.play("제 앞으로 와서, 손을 눌러주세요.");
            mDetailMode = 4;
            ArrayList<AchievementListData> data = UserManager.getInstance().getCurrentUserInfo().mAchievementInfoList;
            if(data.size() >= mDetailMode){
                ttsClient.play("가이드를 말씀드릴게요. " + data.get(mDetailMode - 1).getGuideList().get(0));
            }
        }
    }
    //Safery Action
    private void SaferyAction(){

    }
    //Reward Action
    private void RewardAction(){
        if(mResult.contains("별 몇개") || mResult.contains("별몇개") || mResult.contains("별 몇 개") )
        {
            int size = UserManager.getInstance().getCurrentUserInfo().mStarNumber;
            ttsClient.play("총 " + size + "개의 별을 모았어요");

        }
        else if(mResult.contains("어떤 보상") || mResult.contains("어떤보상"))
        {
            int size = UserManager.getInstance().getCurrentUserInfo().mStarNumber;
            ArrayList<RewardListData> data = UserManager.getInstance().getCurrentUserInfo().mRewardInfoList;
            if(data.size() == 0){
                ttsClient.play("등록된 보상이 없어요");
            }
            else{
                int count = 0;
            for(int index = 0 ; index < data.size() ; index++){
                if(size > data.get(index).getStarNum()){
                    count++;
                }
            }
            ttsClient.play("현재 별 " + size+ "개로 받을 수 있는 보상은 총 " + count+ "가지 있네요");
            //추가로 설명 해 주려면? 아래에
            }
        }
        else if(mResult.contains("모든 보상") || mResult.contains("모든보상") ){
            ArrayList<RewardListData> data = UserManager.getInstance().getCurrentUserInfo().mRewardInfoList;
            if(data.size() == 0){
                ttsClient.play("등록된 보상이 없어요");
            }
            else{
                String talk = "등록된 보상들을 말씀드릴게요.";
                for(int index = 0 ; index < data.size() ; index++)
                    talk += data.get(index).getTitle() + ". ";
                ttsClient.play(talk+ "총 " + data.size() + "개의 보상이 있어요");
            }
        }
    }

    public void handlehapticMessage(Message msg) {
        switch (msg.what) {

        }
    }



}
