package com.a3did.partner.fragmentlist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.a3did.partner.partner.MainActivity;
import com.a3did.partner.partner.R;
import android.view.View.OnClickListener;
import static com.a3did.partner.partner.R.id.toolbar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DefaultFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DefaultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DefaultFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public String mName = "Partner";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ImageView[] images = new ImageView[4];
    MainActivity mActivity;
    Context mContext;

    public void setContext(Context context) {
        mContext = context;
        mActivity = (MainActivity) context;
    }
    public DefaultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DefaultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DefaultFragment newInstance(String param1, String param2) {
        DefaultFragment fragment = new DefaultFragment();
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

        View view = inflater.inflate(R.layout.fragment_default, container, false);
        images[0] = (ImageView) view.findViewById(R.id.imageView1);
        images[1] = (ImageView) view.findViewById(R.id.imageView2);
        images[2] = (ImageView) view.findViewById(R.id.imageView3);
        images[3] = (ImageView) view.findViewById(R.id.imageView4);
        for (int i = 0; i < images.length; i++) {
            images[i].setOnClickListener(mOnClickListener);
        }

        return view;

    }
    private final OnClickListener mOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageView1:
                    Log.d("log", "Assistance");
                    mActivity.transitionFragment(R.id.nav_assistant);
                    break;

                case R.id.imageView2:
                    Log.d("log", "Achievement");
                    mActivity.transitionFragment(R.id.nav_achievement);
                    break;

                case R.id.imageView3:
                    Log.d("log", "Reward");
                    mActivity.transitionFragment(R.id.nav_reward);
                    break;

                case R.id.imageView4:
                    Log.d("log", "Completed List");
                    mActivity.transitionFragment(R.id.nav_completed_list);
                    break;

                case R.id.imageMoomin:
                    Log.d("log", "Moomin");
                    break;

                default:
                    Log.d("log", "none of above");
                    break;


                // put your onclick code here
            }
//            for (int i = 0; i < images.length; i++) {
//                if (v != images[i]) {
//                    images[i].setVisibility(View.VISIBLE);
//                }
//            }
//            v.setVisibility(View.GONE);
        }
    };

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
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
//    public void onClickHandler (View v)
//    {
//        switch (v.getId()) {
//            case R.id.imageView1:
//                Log.d("log", "assistance");
//
//                break;
//
//            case R.id.imageView2:
//                Log.d("log", "Achievement");
//                break;
//
//            case R.id.imageView3:
//                Log.d("log", "Assistance");
//                break;
//
//            case R.id.imageView4:
//                Log.d("log", "Reward");
//                break;
//
//            case R.id.imageMoomin:
//                Log.d("log", "Moomin");
//                break;
//
//            default:
//                Log.d("log", "Completed List");
//                break;
//
//
//            // put your onclick code here
//        }
//    }


}
