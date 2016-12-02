package com.a3did.partner.fragmentlist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.a3did.partner.account.PartnerUserInfo;
import com.a3did.partner.account.UserManager;
import com.a3did.partner.adapterlist.SafetyAdapter;
import com.a3did.partner.adapterlist.SafetyData;
import com.a3did.partner.partner.MainActivity;
import com.a3did.partner.partner.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SafetyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SafetyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SafetyFragment extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener, OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final float ZOOM_LEVEL = 16.f;
    public String mName = "Safety";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ListView mListView;
    ArrayList<String> mDateList;
    //ArrayAdapter<String> mListAdapter;
    SafetyAdapter mListAdapter;

    static final LatLng SEOUL = new LatLng(37.56, 126.97);
    private GoogleMap googleMap;
    MapView mapView;
    Context mContext;
    MainActivity mActivity;
    GoogleMap mMap = null;

    public SafetyFragment() {
        // Required empty public constructor
    }

    public void setContext(Context context) {
        mContext = context;
        mActivity = (MainActivity) context;
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

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.mapfragment);
        mapFragment.getMapAsync(this);

        mListAdapter = new SafetyAdapter();
        UserManager userManager = UserManager.getInstance();
        PartnerUserInfo userInfo = userManager.getCurrentUserInfo();
        if(userInfo != null){
            mListAdapter.setList(userInfo.mSafetyInfoList);
        }

        mListView = (ListView) v.findViewById(R.id.dangerous_area_list);
        mListView.setAdapter(mListAdapter);


        mListView.setOnItemClickListener(this);

        return v;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(mListAdapter.getList().size() == 0){
            //mMap.addMarker(new MarkerOptions().position(SEOUL).title("Dangerous Area"));
            //mMap.animateCamera(CameraUpdateFactory.newLatLng(SEOUL));
        }
        else{
            ArrayList<SafetyData> list = mListAdapter.getList();
            for(int i = 0; i < list.size() ; i++){
                mMap.addMarker(new MarkerOptions().position(list.get(i).getGeoInfo()).title(list.get(i).getTitle()));

            }
            //처음엔 첫번째 꺼로 애니메이션
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(list.get(0).getGeoInfo(), ZOOM_LEVEL));
        }


        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        UserManager userManager = UserManager.getInstance();
        PartnerUserInfo userInfo = userManager.getCurrentUserInfo();
        if(userInfo != null){
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userInfo.mSafetyInfoList.get(position).getGeoInfo(), ZOOM_LEVEL));
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
