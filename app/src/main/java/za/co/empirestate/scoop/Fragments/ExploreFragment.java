package za.co.empirestate.scoop.Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import za.co.empirestate.scoop.R;

public class ExploreFragment extends android.support.v4.app.Fragment {

    public static ExploreFragment newInstance(int pos) {
        ExploreFragment fragment = new ExploreFragment();
        Bundle args = new Bundle();
        args.putInt("d",pos);

        fragment.setArguments(args);
        return fragment;
    }

    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
