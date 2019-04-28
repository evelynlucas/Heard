package org.pursuit.heard.searchFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.pursuit.heard.R;

public class LoadFragment extends Fragment {

    private MoveToVPFragment listener;

    public LoadFragment() {}

    public static LoadFragment newInstance() {
      return new LoadFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_load, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        listener.onNearbyFound();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MoveToVPFragment) {
            listener = (MoveToVPFragment) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface MoveToVPFragment {
        void onNearbyFound();
    }
}

