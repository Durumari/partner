package com.a3did.partner.partner;

import android.app.Activity;
import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.a3did.partner.fragmentlist.AccountFragment;
import com.a3did.partner.fragmentlist.AchievementFragment;
import com.a3did.partner.fragmentlist.AssistantFragment;
import com.a3did.partner.fragmentlist.CompletedListFragment;
import com.a3did.partner.fragmentlist.DefaultFragment;
import com.a3did.partner.fragmentlist.MissedListFragment;
import com.a3did.partner.fragmentlist.RewardFragment;
import com.a3did.partner.fragmentlist.SafetyFragment;
import com.a3did.partner.partner.utils.AudioWriterPCM;
import com.naver.speech.clientapi.SpeechConfig;

import net.daum.mf.speech.api.TextToSpeechClient;
import net.daum.mf.speech.api.TextToSpeechListener;
import net.daum.mf.speech.api.TextToSpeechManager;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AccountFragment.OnFragmentInteractionListener,
        AchievementFragment.OnFragmentInteractionListener,
        AssistantFragment.OnFragmentInteractionListener,
        CompletedListFragment.OnFragmentInteractionListener,
        DefaultFragment.OnFragmentInteractionListener,
        MissedListFragment.OnFragmentInteractionListener,
        RewardFragment.OnFragmentInteractionListener,
        SafetyFragment.OnFragmentInteractionListener, TextToSpeechListener {


    DefaultFragment mDefaultFragment;
    AccountFragment mAccountFragemt;
    AssistantFragment mAssistantFragment;
    AchievementFragment mAchievementFragment;
    SafetyFragment mSafetyFragment;
    RewardFragment mRewardFragment;
    CompletedListFragment mCompletedListFragment;
    MissedListFragment mMissedListFragment;
    Toolbar toolbar;
    FloatingActionButton fab;
    int mFragmentID;

    public static final String DAUM_KEY = "3d03690d5e27935dc3be9bb14c52ee98";
    private static String CLIENT_ID = "8_h3SjFEfYR7rygZLHz4"; // "내 애플리케이션"에서 Client ID를 확인해서 이곳에 적어주세요.
    private static SpeechConfig SPEECH_CONFIG = SpeechConfig.OPENAPI_KR; // or SpeechConfig.OPENAPI_EN

    private RecognitionHandler handler;
    private NaverRecognizer mNaverRecognizer;
    private String mResult;
    private AudioWriterPCM writer;
    private boolean isRunning;
    public static TextToSpeechClient ttsClient;

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
                if(mResult.contains("파트너"))
                {
                    ttsClient.play("네 최준현님");
                }
                else if(mResult.contains("일정"))
                {
                    ttsClient.play("네 일정 탭으로 넘어갑니다.");
                    transitionFragment(R.id.nav_assistant);
                }
                else if(mResult.contains("목표"))
                {
                    ttsClient.play("네 목표 탭으로 넘어갑니다.");
                    transitionFragment(R.id.nav_achievement);
                }
                else if(mResult.contains("보상"))
                {
                    ttsClient.play("네 보상 탭으로 넘어갑니다.");
                    transitionFragment(R.id.nav_reward);
                }
                else if(mResult.contains("설정"))
                {
                    ttsClient.play("네 설정 탭으로 넘어갑니다.");
                    transitionFragment(R.id.nav_account);
                }
                else if(mResult.contains("완료"))
                {
                    ttsClient.play("네 완료리스트 탭으로 넘어갑니다.");
                    transitionFragment(R.id.nav_completed_list);
                }
                else if(mResult.contains("실패"))
                {
                    ttsClient.play("네 실패리스트 탭으로 넘어갑니다.");
                    transitionFragment(R.id.nav_missed_list);
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
                isRunning = false;
                break;

            case R.id.clientInactive:
                if (writer != null) {
                    writer.close();
                }

                //btnStart.setText(R.string.str_start);
                //btnStart.setEnabled(true);
                isRunning = false;
                break;
        }
        Log.d("Partner","ID" + msg.what + "text result : " + mResult);



    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);


        //Newton talk API 연동
        TextToSpeechManager.getInstance().initializeLibrary(getApplicationContext());

        if (ttsClient != null && ttsClient.isPlaying()) {
            ttsClient.stop();
            return;
        }

        String speechMode;
        speechMode = TextToSpeechClient.NEWTONE_TALK_2;
        speechMode = TextToSpeechClient.NEWTONE_TALK_1;

        String voiceType;
        voiceType = TextToSpeechClient.VOICE_MAN_READ_CALM;
        voiceType = TextToSpeechClient.VOICE_WOMAN_READ_CALM;
        voiceType = TextToSpeechClient.VOICE_MAN_DIALOG_BRIGHT;
        voiceType = TextToSpeechClient.VOICE_WOMAN_DIALOG_BRIGHT;


        double speechSpeed;
        speechSpeed = 0.5;
        speechSpeed = 2.0;
        speechSpeed = 1.0;
        ttsClient = new TextToSpeechClient.Builder()
                .setApiKey(DAUM_KEY)
                .setSpeechMode(speechMode)
                .setSpeechSpeed(speechSpeed)
                .setSpeechVoice(voiceType)
                .setListener(MainActivity.this)
                .build();
        ttsClient.play("파트너 앱 실행합니다.");

        //Naver API 연동
        handler = new RecognitionHandler(this);
        mNaverRecognizer = new NaverRecognizer(this, handler, CLIENT_ID, SPEECH_CONFIG);



        //Recognizer setting
        /*if (!isRunning) {
            // Start button is pushed when SpeechRecognizer's state is inactive.
            // Run SpeechRecongizer by calling recognize().
            mNaverRecognizer.recognize();
        } else {
            // This flow is occurred by pushing start button again
            // when SpeechRecognizer is running.
            // Because it means that a user wants to cancel speech
            // recognition commonly, so call stop().
            mNaverRecognizer.getSpeechRecognizer().stop();
        }*/



        //Initialize fragment
        mDefaultFragment = new DefaultFragment();
        mAccountFragemt = new AccountFragment();
        mAssistantFragment = new AssistantFragment();
        mAchievementFragment = new AchievementFragment();
        mSafetyFragment = new SafetyFragment();
        mRewardFragment = new RewardFragment();
        mCompletedListFragment = new CompletedListFragment();
        mMissedListFragment = new MissedListFragment();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setTitle(mDefaultFragment.mName);
                mFragmentID = R.layout.fragment_default;
                getSupportFragmentManager().beginTransaction().replace(R.id.partner_container,mDefaultFragment).commit();
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Recognizer setting test
                if (!isRunning) {
                    // Start button is pushed when SpeechRecognizer's state is inactive.
                    // Run SpeechRecongizer by calling recognize().
                    mNaverRecognizer.recognize();
                    isRunning = true;
                } else {
                    // This flow is occurred by pushing start button again
                    // when SpeechRecognizer is running.
                    // Because it means that a user wants to cancel speech
                    // recognition commonly, so call stop().
                    mNaverRecognizer.getSpeechRecognizer().stop();
                }


                String title = "";
                switch (mFragmentID)
                {
                    case R.layout.fragment_default:
                        title = "Partner";
                        break;
                    case R.layout.fragment_assistant:
                        title = "Assistant";
                        break;
                    case R.layout.fragment_achievement:
                        title = "Achievement";
                        break;
                    case R.layout.fragment_safety:
                        title = "Safety";
                        break;
                    case R.layout.fragment_reward:
                        title = "Reward";
                        break;
                    case R.layout.fragment_account:
                        title = "Account";
                        break;
                    case R.layout.fragment_completed_list:
                        title = "CompletedList";
                        break;
                    case R.layout.fragment_missed_list:
                        title = "MissedList";
                        break;
                    default:

                };

                //DialogHtmlView(title);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        toolbar.setTitle(mDefaultFragment.mName);
        getSupportFragmentManager().beginTransaction().replace(R.id.partner_container,mDefaultFragment).commit();
        mFragmentID = R.layout.fragment_default;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    // Android Life cycle
    @Override
    protected void onResume() {
        super.onResume();
        mNaverRecognizer.getSpeechRecognizer().initialize();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mNaverRecognizer.getSpeechRecognizer().stopImmediately();
        mNaverRecognizer.getSpeechRecognizer().release();
        isRunning = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TextToSpeechManager.getInstance().finalizeLibrary();
    }
    ////////////////////////////////////////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void transitionFragment(int id)
    {
        switch (id)
        {
            /*case R.id.nav_main:
                getSupportFragmentManager().beginTransaction().replace(R.id.partner_container,mDefaultFragment).commit();
                toolbar.setTitle(mDefaultFragment.mName);
                break;*/
            case R.id.nav_assistant:
                getSupportFragmentManager().beginTransaction().replace(R.id.partner_container,mAssistantFragment).commit();
                toolbar.setTitle(mAssistantFragment.mName);
                mFragmentID = R.layout.fragment_assistant;
                break;
            case R.id.nav_achievement:
                getSupportFragmentManager().beginTransaction().replace(R.id.partner_container,mAchievementFragment).commit();
                toolbar.setTitle(mAchievementFragment.mName);
                mFragmentID = R.layout.fragment_achievement;
                break;
            case R.id.nav_safety:
                getSupportFragmentManager().beginTransaction().replace(R.id.partner_container,mSafetyFragment).commit();
                toolbar.setTitle(mSafetyFragment.mName);
                mFragmentID = R.layout.fragment_safety;
                break;
            case R.id.nav_reward:
                getSupportFragmentManager().beginTransaction().replace(R.id.partner_container,mRewardFragment).commit();
                toolbar.setTitle(mRewardFragment.mName);
                mFragmentID = R.layout.fragment_reward;
                break;
            case R.id.nav_account:
                getSupportFragmentManager().beginTransaction().replace(R.id.partner_container,mAccountFragemt).commit();
                toolbar.setTitle(mAccountFragemt.mName);
                mFragmentID = R.layout.fragment_account;
                break;
            case R.id.nav_completed_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.partner_container,mCompletedListFragment).commit();
                toolbar.setTitle(mCompletedListFragment.mName);
                mFragmentID = R.layout.fragment_completed_list;
                break;
            case R.id.nav_missed_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.partner_container,mMissedListFragment).commit();
                toolbar.setTitle(mMissedListFragment.mName);
                mFragmentID = R.layout.fragment_missed_list;
                break;

            default:

        };



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return;
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        transitionFragment(id);


        return true;
    }
    private void DialogHtmlView(String title) {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setMessage("Dialog Test");
        ab.setTitle(title);
        ab.setPositiveButton("ok", null);
        ab.show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }







    // Declare handler for handling SpeechRecognizer thread's Messages.
    static class RecognitionHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        RecognitionHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }


    //////////////////////////////Newton talk Listener//////
    @Override
    public void onFinished() {
        int intSentSize = ttsClient.getSentDataSize();
        int intRecvSize = ttsClient.getReceivedDataSize();

        final String strInacctiveText = "onFinished() SentSize : " + intSentSize + " RecvSize : " + intRecvSize;

        Log.i("Partner", strInacctiveText);
        //ttsClient = null;
    }

    @Override
    public void onError(int code, String s) {
        handleError(code);

        //ttsClient = null;
    }

    private void handleError(int errorCode) {
        String errorText;
        switch (errorCode) {
            case TextToSpeechClient.ERROR_NETWORK:
                errorText = "네트워크 오류";
                break;
            case TextToSpeechClient.ERROR_NETWORK_TIMEOUT:
                errorText = "네트워크 지연";
                break;
            case TextToSpeechClient.ERROR_CLIENT_INETRNAL:
                errorText = "음성합성 클라이언트 내부 오류";
                break;
            case TextToSpeechClient.ERROR_SERVER_INTERNAL:
                errorText = "음성합성 서버 내부 오류";
                break;
            case TextToSpeechClient.ERROR_SERVER_TIMEOUT:
                errorText = "음성합성 서버 최대 접속시간 초과";
                break;
            case TextToSpeechClient.ERROR_SERVER_AUTHENTICATION:
                errorText = "음성합성 인증 실패";
                break;
            case TextToSpeechClient.ERROR_SERVER_SPEECH_TEXT_BAD:
                errorText = "음성합성 텍스트 오류";
                break;
            case TextToSpeechClient.ERROR_SERVER_SPEECH_TEXT_EXCESS:
                errorText = "음성합성 텍스트 허용 길이 초과";
                break;
            case TextToSpeechClient.ERROR_SERVER_UNSUPPORTED_SERVICE:
                errorText = "음성합성 서비스 모드 오류";
                break;
            case TextToSpeechClient.ERROR_SERVER_ALLOWED_REQUESTS_EXCESS:
                errorText = "허용 횟수 초과";
                break;
            default:
                errorText = "정의하지 않은 오류";
                break;
        }

        final String statusMessage = errorText + " (" + errorCode + ")";

        Log.i("Newton talk error", statusMessage);
    }
    ///////////////////////////////////////////////////////
}
