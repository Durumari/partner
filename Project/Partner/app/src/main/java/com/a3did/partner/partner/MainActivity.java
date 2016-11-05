package com.a3did.partner.partner;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.a3did.partner.fragmentlist.AccountFragment;
import com.a3did.partner.fragmentlist.AchievementFragment;
import com.a3did.partner.fragmentlist.AssistantFragment;
import com.a3did.partner.fragmentlist.CompletedListFragment;
import com.a3did.partner.fragmentlist.DefaultFragment;
import com.a3did.partner.fragmentlist.MissedListFragment;
import com.a3did.partner.fragmentlist.RewardFragment;
import com.a3did.partner.fragmentlist.SafetyFragment;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);

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

                DialogHtmlView(title);
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


    }
