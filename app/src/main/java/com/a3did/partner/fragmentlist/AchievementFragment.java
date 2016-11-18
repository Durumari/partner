package com.a3did.partner.fragmentlist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.a3did.partner.adapterlist.AchievementListAdapter;
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
        /*mDateList = new ArrayList<String>();
        mDateList.add("Goal 1");
        mDateList.add("Goal 2");
        mDateList.add("Goal 3");
        mDateList.add("Goal 4");
        mDateList.add("Goal 5");
        mListView = (ListView)v.findViewById(R.id.goal_list);
        mListAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1,mDateList);
        mListView.setAdapter(mListAdapter);*/

        mListAdapter = new AchievementListAdapter();

        mListAdapter.addItem(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_bookmark_border_black_24dp),
                "수학 시험 100점 맞기", 10, "11월 19일 오후 3시", "12월 20일 오후 3시");
        mListAdapter.addItem(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_bookmark_border_black_24dp),
                "집 어지럽히지 않고 5일 유지하기", 5, "11월 19일 오후 4시", "11월 24일 오후 4시");
        mListAdapter.addItem(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_bookmark_border_black_24dp),
                "두시간 집중해서 독서하기", 1, "11월 19일 오후 5시", "11월 29일 오후 7시");
        mListAdapter.addItem(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_bookmark_border_black_24dp),
                "운동 주 5회 하기", 2, "11월 19일 오후 9시", "11월 26일 오후 9시");
        mListAdapter.addItem(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_bookmark_border_black_24dp),
                "강아지 목욕시켜주기", 1, "11월 20일 오후 8시", "11월 20일 오후 9시");

        mListView = (ListView)v.findViewById(R.id.goal_list);
        mListView.setAdapter(mListAdapter);
        return v;
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
