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
        SafetyFragment.OnFragmentInteractionListener
{


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

    private static String CLIENT_ID = "8_h3SjFEfYR7rygZLHz4"; // "내 애플리케이션"에서 Client ID를 확인해서 이곳에 적어주세요.
    private static SpeechConfig SPEECH_CONFIG = SpeechConfig.OPENAPI_KR; // or SpeechConfig.OPENAPI_EN

    private RecognitionHandler handler;
    private NaverRecognizer mNaverRecognizer;
    private String mResult;
    private AudioWriterPCM writer;
    private boolean isRunning;

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



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

}
