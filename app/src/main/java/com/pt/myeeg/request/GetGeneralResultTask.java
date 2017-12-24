package com.pt.myeeg.request;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pt.myeeg.fragments.content.BaseContentFragment;

/**
 * Created by Jorge Zepeda Tinoco on 24/12/17.
 */

public class GetGeneralResultTask extends BaseContentFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onGetGeneralResultsSuccess(String response) {
        super.onGetGeneralResultsSuccess(response);
    }

    @Override
    public void onGetGeneralResultsFail(String response) {
        super.onGetGeneralResultsFail(response);
    }
}
