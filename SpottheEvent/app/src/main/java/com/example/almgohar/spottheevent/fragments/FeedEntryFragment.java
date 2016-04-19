package com.example.almgohar.spottheevent.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.almgohar.spottheevent.R;
import com.example.almgohar.spottheevent.activites.EventActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FeedEntryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FeedEntryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedEntryFragment extends Fragment {
    private String id;
    private String eventName;
    private String eventDescription;
    private String imageURL;
    private View thisView;

    private OnFragmentInteractionListener mListener;

    public FeedEntryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param eventDescription Parameter 1.
     * @param eventName Parameter 2.
     * @param imageURL Parameter 3.
     * @return A new instance of fragment FeedEntryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedEntryFragment newInstance(String id, String eventDescription, String eventName, String imageURL) {
        FeedEntryFragment fragment = new FeedEntryFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("eventName", eventName);
        args.putString("eventDescription", eventDescription);
        args.putString("imageURL", imageURL);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Log.d("IN FRAG", getArguments().getString("eventName"));
            id = getArguments().getString("id");
            imageURL = getArguments().getString("imageURL");
            eventDescription = getArguments().getString("eventDescription");
            eventName = getArguments().getString("eventName");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        thisView = inflater.inflate(R.layout.fragment_feed_entry, container, false);
        thisView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), EventActivity.class);
                i.putExtra("event_id", id);
                startActivity(i);
            }
        });
        TextView name = (TextView) thisView.findViewById(R.id.textView4);
        TextView desc = (TextView) thisView.findViewById(R.id.textView3);
        name.setText(eventName);
        desc.setText(eventDescription);
        return thisView;
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
