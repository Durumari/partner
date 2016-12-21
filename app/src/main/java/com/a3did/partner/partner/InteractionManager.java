package com.a3did.partner.partner;

import android.os.Environment;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;

import com.a3did.partner.account.PartnerUserInfo;
import com.a3did.partner.account.UserManager;
import com.a3did.partner.adapterlist.AchievementListData;
import com.a3did.partner.adapterlist.AssistantListData;
import com.a3did.partner.adapterlist.CompletedListData;
import com.a3did.partner.adapterlist.MissedListData;
import com.a3did.partner.adapterlist.RewardListData;
import com.a3did.partner.fragmentlist.AchievementFragment;
import com.a3did.partner.fragmentlist.AssistantFragment;
import com.a3did.partner.partner.utils.AudioWriterPCM;
import com.naver.speech.clientapi.SpeechConfig;
import com.perples.recosdk.RECOBeacon;

import net.daum.mf.speech.api.TextToSpeechClient;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import static android.R.id.list;

/**
 * Created by admin on 2016-12-01.
 */

public class InteractionManager {


//    AssistantFragment mAssistantFrag;
//    AchievementFragment mAchievementFrag;

    private static InteractionManager mInstance;
    private long prevtime = 0;
    private long currenttime = 0;
    private String[] tokens;
    private String deleteString;
    private int deleteIndex =0;

    public TextToSpeechClient ttsClient;
    public MainActivity mActivity;
    public NaverRecognizer mNaverRecognizer;
    private boolean mIsRecognizerReady;
    private AudioWriterPCM writer;
    private static String CLIENT_ID = "8_h3SjFEfYR7rygZLHz4"; // "내 애플리케이션"에서 Client ID를 확인해서 이곳에 적어주세요.
    private static SpeechConfig SPEECH_CONFIG = SpeechConfig.OPENAPI_KR; // or SpeechConfig.OPENAPI_EN
    private MainActivity.RecognitionHandler handler;
    private MainActivity.HapticHandler mHapticHandler;
    public ArrayList<String> defaultSpeech = new ArrayList<String>();
    private int hapticinputcount = 0;
    public int hapticpress = 0;
    public int hapticpress2 = 0;
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
        defaultSpeech.add("안녕하세요, 저는 파트너입니다");
        defaultSpeech.add("저한테 물어보기전에 파트너라고 앞에 먼저 불러주세요");
        defaultSpeech.add("궁금한게 있으면, 목표, 일정, 보상 에 대해서 물어봐주세요");
        defaultSpeech.add("오늘 일정 물어봐요");
        defaultSpeech.add("목표가 어떤게 있는지 확인하려면, 파트너, 목표 리스트 좀 보여줘, 라고 말해주세요");
        defaultSpeech.add("어떤 선물이 있는 지 궁금하신가요? 파트너, 보상 리스트 좀 보여줘, 라고 말해주세요");
        defaultSpeech.add("제 배를 봐주세요. 많은 정보를 볼 수 있습니다");
        defaultSpeech.add("어떤 선물이 있는 지 궁금하신가요? 파트너, 보상 리스트 좀 보여줘, 라고 말해주세요");
        defaultSpeech.add("다음 목표를 보시려면 , '먼저 할 목표' 라고 말해주세요 ");
        defaultSpeech.add("다음 일정를  , '먼저 할 목표' 라고 말해주세요 ");
        defaultSpeech.add("어떤 선물이 있는 지 궁금하신가요? 파트너, 보상 리스트 좀 보여줘, 라고 말해주세요");




    }

    public void Init(TextToSpeechClient tts, MainActivity activity){

        handler = new MainActivity.RecognitionHandler(activity);
        mNaverRecognizer = new NaverRecognizer(activity, handler, CLIENT_ID, SPEECH_CONFIG);
        mHapticHandler = new MainActivity.HapticHandler(activity);


        //Recognizer setting
        //mNaverRecognizer.recognize();
        ttsClient = tts;
        mActivity = activity;
        setVoiceRecoReady(true);



    }
    public MainActivity.HapticHandler getHaptic(){
        return mHapticHandler;
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
        else if (mResult.contains("완료 했어") || mResult.contains("완료했어")){
            ttsClient.play("손을 만져줘");
            //hapticpress = 1;
            ttsClient.play("손을 만져줘");
            Log.d("speech text:" , mResult);
            String delims = "[ ]";
            tokens = mResult.split(delims);
            hapticpress = 1;
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

            ttsClient.play("제 앞으로 와서, 손을 눌러주세요.");
            mDetailMode = 1;

        }else if(mResult.contains("두번째 목표 가이드") ){
            mDetailMode = 2;

        }else if(mResult.contains("세번째 목표 가이드") ){
            mDetailMode = 3;

        }else if(mResult.contains("네번째 목표 가이드") ){
            //ttsClient.play("제 앞으로 와서, 손을 눌러주세요.");
            mDetailMode = 4;

        }
        else if (mResult.contains("완료 했어") || mResult.contains("완료했어")){
            ttsClient.play("손을 만져줘");
            Log.d("speech text:" , mResult);
            String delims = "[ ]";
            tokens = mResult.split(delims);
            hapticpress2 = 1;

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
            case 1:
                currenttime = System.currentTimeMillis();
                if (currenttime - prevtime > 1500 ) {
                    Log.d("HANDLER", "input is present" + hapticpress + hapticpress2+ mDetailMode);

                    if (hapticpress == 0 && hapticpress2 == 0 && mDetailMode == 0) {
                        Random randomGenerator = new Random();
                        int randomInt = randomGenerator.nextInt(4);
                        String randspeech = defaultSpeech.get(randomInt);
                        ttsClient.play(randspeech);
                    } else if (hapticpress == 0 && hapticpress2 == 0 && mDetailMode > 0) {
                        ArrayList<AchievementListData> data = UserManager.getInstance().getCurrentUserInfo().mAchievementInfoList;
                        if (data.size() >= mDetailMode) {
                            ttsClient.play("가이드를 말씀드릴게요. " + data.get(mDetailMode - 1).getGuideList().get(0));
                        }
                        mDetailMode = 0;
                    }

                    else if (hapticpress == 1 && hapticpress2 == 0 && mDetailMode == 0) {
                        ttsClient.play("일정 완료를 체크하였습니다.");

                        ArrayList<AssistantListData> data = UserManager.getInstance().getCurrentUserInfo().mScheduleInfoList;
                        Log.d("handler", "number of data: " + data.size());
                        for (int i = 0; i < data.size(); i++) {
                            // TODO:Check what is done and output
                            Log.d("handler", "data title" + data.get(i).getTitle() + "token:" +tokens.length);
                            for (int j = 0 ; j < tokens.length; j++){
                                Log.d("handler", "token:"+tokens[j]);
                                if (data.get(i).getTitle().contains(tokens[j])){
                                    Log.d("handler", "token contain:"+tokens[j]);
                                    deleteString = data.get(i).getTitle();
                                    deleteIndex = i;

                                }

                            }


                        }
                        AssistantFragment assistant = (AssistantFragment) mActivity.getSupportFragmentManager().findFragmentById(R.id.partner_container);
                        if (assistant != null) {
                            Log.d("test", "assistant getget");

                            assistant.moveDataToCompletedList(deleteIndex);
                            deleteIndex = 0;
                        }
                        hapticpress = 0;

                    }

                    else if (hapticpress == 0 && hapticpress2 == 1 && mDetailMode == 0) {
                        ttsClient.play("목표 완료를 체크하였습니다.");

                        ArrayList<AchievementListData> data = UserManager.getInstance().getCurrentUserInfo().mAchievementInfoList;
                        Log.d("handler", "number of data" + data.size());
                        for (int i = 0; i < data.size(); i++) {
                            // TODO:Check what is done and output index
                            Log.d("handler", "data title" + data.get(i).getTitle() + "token:" +tokens.length);
                            for (int j = 0 ; j < tokens.length; j++){
                                Log.d("handler", "token:"+tokens[j]);
                                if (data.get(i).getTitle().contains(tokens[j])){
                                    Log.d("handler", "token contain:"+tokens[j]);
                                    deleteString = data.get(i).getTitle();
                                    deleteIndex = i;
                                }

                            }



                        }

                        AchievementFragment achievement = (AchievementFragment) mActivity.getSupportFragmentManager().findFragmentById(R.id.partner_container);
                        if (achievement != null) {
                            Log.d("test", "assistant getget");
                            Log.d("handler", "index: "+deleteIndex);
                            achievement.moveDataToCompletedList(deleteIndex);
                            deleteIndex = 0;
                        }
                        hapticpress2 = 0;
                    }

                    prevtime = currenttime;

                    Log.d("time", "prevtime:"+ prevtime);
                }
                break;
            case 2:
                Log.d("HANDLER","no input present");
                break;

            default:
                break;


        }
    }



}
