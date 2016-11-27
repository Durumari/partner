package com.a3did.partner.fragmentlist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.a3did.partner.adapterlist.SafetyAdapter;
import com.a3did.partner.partner.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SafetyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SafetyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SafetyFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public String mName = "Safety";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ListView mListView;
    ArrayList<String> mDateList;
    //ArrayAdapter<String> mListAdapter;
    SafetyAdapter mListAdapter;

    public SafetyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SafetyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SafetyFragment newInstance(String param1, String param2) {
        SafetyFragment fragment = new SafetyFragment();
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
        View v = inflater.inflate(R.layout.fragment_safety, container, false);
/*        mDateList = new ArrayList<String>();
        mDateList.add("Dangerous Area 1");
        mDateList.add("Dangerous Area 2");
        mDateList.add("Dangerous Area 3");
        mDateList.add("Dangerous Area 4");
        mDateList.add("Dangerous Area 5");
        mDateList.add("Dangerous Area 6");
        mListView = (ListView)v.findViewById(R.id.dangerous_area_list);
        mListAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1,mDateList);
        mListView.setAdapter(mListAdapter);*/
        mListAdapter = new SafetyAdapter();

        mListAdapter.addItem(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_home_black_24dp),
                "자기 전에 영양제먹기", "11월 19일 오후 9시") ;
        mListView = (ListView)v.findViewById(R.id.dangerous_area_list);
        mListView.setAdapter(mListAdapter);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                //String value = (String) adapter.getItemAtPosition(position);
                Log.d("TEST", "list is pressed");
                // assuming string and if you want to get the value on click of list item
                // do what you intend to do on click of listview row
//                FragmentManager fm = getFragmentManager();
//                FragmentBoxOffice f = (FragmentBoxOffice) fm.findFragmentByTag(FragmentBoxOffice.TAG);
//                if (f == null) {
//                    f = new FragmentBoxOffice();
//                    fm.beginTransaction()
//                            .replace(R.id.map, f, FragmentBoxOffice.TAG)
//                                    //.addToBackStack(null);  // uncomment this line if you want to be able to return to the prev. fragment with "back" button
//                            .commit();
//                }
//                MapFragment mapFragment;
//                mapFragment =  (MapFragment)getFragmentManager().findFragmentById(R.id.map);
//                mapFragment.getMapAsync(this);
            }
        });

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
