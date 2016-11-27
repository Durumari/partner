package com.a3did.partner.fragmentlist;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.a3did.partner.account.PartnerUserInfo;
import com.a3did.partner.account.UserManager;
import com.a3did.partner.adapterlist.AchievementListAdapter;
import com.a3did.partner.adapterlist.AchievementListData;
import com.a3did.partner.adapterlist.AssistantListData;
import com.a3did.partner.adapterlist.CompletedListData;
import com.a3did.partner.adapterlist.MissedListData;
import com.a3did.partner.partner.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AchievementFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AchievementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AchievementFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public String mName = "Achievement";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ListView mListView;
    AchievementListAdapter mListAdapter;

    public AchievementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AchievementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AchievementFragment newInstance(String param1, String param2) {
        AchievementFragment fragment = new AchievementFragment();
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
        View v = inflater.inflate(R.layout.fragment_achievement, container, false);

        mListAdapter = new AchievementListAdapter();

        UserManager userManager = UserManager.getInstance();
        PartnerUserInfo userInfo = userManager.getCurrentUserInfo();
        if(userInfo != null){
            mListAdapter.setList(userInfo.mAchievementInfoList);
        }

        mListView = (ListView)v.findViewById(R.id.goal_list);
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                PopupMenu popup= new PopupMenu(getContext(), view);//view는 오래 눌러진 뷰를 의미
                popup.getMenuInflater().inflate(R.menu.menu_achievement, popup.getMenu());
                //Popup menu의 메뉴아이템을 눌렀을  때 보여질 ListView 항목의 위치
                //Listener 안에서 사용해야 하기에 final로 선언
                final int index= position;
                //Popup Menu의 MenuItem을 클릭하는 것을 감지하는 listener 설정
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // TODO Auto-generated method stub
                        switch( item.getItemId() ){
                            case R.id.complete:
                                moveDataToCompletedList(index);
                                break;
                            case R.id.delete:
                                moveDataToMissedList(index);
                                break;
                            case R.id.guide:
                                giveGuideInformation(index);
                                break;
                        }
                        return false;
                    }
                });

                popup.show();//Popup Menu 보이기
                return false;
            }
        });
        return v;
    }

    public void giveGuideInformation(int index){
        UserManager userManager = UserManager.getInstance();
        PartnerUserInfo userInfo = userManager.getCurrentUserInfo();
        if(userInfo != null){
            AchievementListData data = userInfo.mAchievementInfoList.get(index);

            //첫번째 가이드만 보여주는 거로 일단 설정
            if(data.getGuideList().size() != 0){
                String guideStr = data.getGuideList().get(0);
                AlertDialog.Builder builder = new  AlertDialog.Builder(getContext());
                builder.setTitle("Guide for Goal");
                builder.setMessage(guideStr);
                builder.setPositiveButton("OK", null);
                builder.show();
            }
        }
    }

    public void moveDataToCompletedList(int index){
        UserManager userManager = UserManager.getInstance();
        PartnerUserInfo userInfo = userManager.getCurrentUserInfo();
        if(userInfo != null){
            //리스트 정보를 옮긴다.
            AchievementListData data = userInfo.mAchievementInfoList.get(index);
            CompletedListData cData = new CompletedListData();
            cData.setItem(ContextCompat.getDrawable(getContext(), R.drawable.ic_goal_black_24dp), data.getTitle());
            userInfo.mCompletedInfoList.add(cData);

            //Reward 를 주기 위한 Star number 추가
            userInfo.mStarNumber += data.getStarNumber();
            userInfo.mAchievementInfoList.remove(index);
            mListAdapter.notifyDataSetChanged();
        }
    }

    public void moveDataToMissedList(int index){
        UserManager userManager = UserManager.getInstance();
        PartnerUserInfo userInfo = userManager.getCurrentUserInfo();
        if(userInfo != null){
            //리스트 정보를 옮긴다.
            AchievementListData data = userInfo.mAchievementInfoList.get(index);
            MissedListData cData = new MissedListData();
            cData.setItem(ContextCompat.getDrawable(getContext(), R.drawable.ic_goal_black_24dp), data.getTitle());
            userInfo.mMissedInfoList.add(cData);
            userInfo.mAchievementInfoList.remove(index);
            mListAdapter.notifyDataSetChanged();
        }
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
