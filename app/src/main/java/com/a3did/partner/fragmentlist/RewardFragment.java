package com.a3did.partner.fragmentlist;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.a3did.partner.adapterlist.AssistantListAdapter;
import com.a3did.partner.adapterlist.RewardListAdapter;
import com.a3did.partner.adapterlist.RewardListData;
import com.a3did.partner.partner.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RewardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RewardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RewardFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public String mName = "Reward";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ListView mListView;
    ArrayList<RewardListData> mDateList;
    RewardListAdapter mListAdapter;
    TextView mTextView;
    public Context mContext;
    int mStarNum = 500;
    public RewardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RewardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RewardFragment newInstance(String param1, String param2) {
        RewardFragment fragment = new RewardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reward, container, false);

        mTextView = (TextView)v.findViewById(R.id.star_number) ;
        mTextView.setText(mStarNum + "");
        mContext = v.getContext();
        mListAdapter = new RewardListAdapter();

        // 첫 번째 아이템 추가.
        mListAdapter.addItem(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_card_giftcard_black_24dp),
                "뽀로로 컴퓨터 사기", 100) ;
        // 두 번째 아이템 추가.
        mListAdapter.addItem(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_card_giftcard_black_24dp),
                "놀이동산 가기", 60) ;
        // 세 번째 아이템 추가.
        mListAdapter.addItem(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_card_giftcard_black_24dp),
                "짜장면 먹기",  10) ;

        mListAdapter.addItem(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_card_giftcard_black_24dp),
                "컴퓨터 이용 1시간 쿠폰!", 20) ;

        mListView = (ListView)v.findViewById(R.id.reward_list);
        mListView.setAdapter(mListAdapter);

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                        RewardListData data = (RewardListData)mListAdapter.getItem(position);
                        sendMessage("01062747927","[Partner] 준현이가 별을 모아서 "  + "'" + data.getTitle() + "'" + "을 선물로 받기를 원해요~^^" );
                        //sendMessage("01093348599","[Partner] 준현이가 별을 모아서 "  + "'" + data.getTitle() + "'" + "을 선물로 받기를 원해요~^^" );
                        mStarNum  -= data.getStarNum();
                        mTextView.setText(mStarNum + "");

                        mListAdapter.getList().remove(position);
                        mListAdapter.notifyDataSetChanged();

                return false;
            }
        });
        return v;
    }
    public void sendMessage(String number, String text){

        if (number.length()>0 && text.length()>0) {
            sendSMS(number, text);
        }
    }
    public void sendSMS(String smsNumber, String smsText){
        PendingIntent sentIntent = PendingIntent.getBroadcast(mContext, 0, new Intent("SMS_SENT_ACTION"), 0);
        PendingIntent deliveredIntent = PendingIntent.getBroadcast(mContext, 0, new Intent("SMS_DELIVERED_ACTION"), 0);



        mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        // 도착 완료
                        Log.d("Partner", "도착 완료");
                        //Toast.makeText(mContext, "SMS 도착 완료", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:

                        // 도착 안됨
                        Log.d("Partner", "도착 안됨");
                        //Toast.makeText(mContext, "SMS 도착 실패", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_DELIVERED_ACTION"));

        SmsManager mSmsManager = SmsManager.getDefault();
        mSmsManager.sendTextMessage(smsNumber, null, smsText, sentIntent, deliveredIntent);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
