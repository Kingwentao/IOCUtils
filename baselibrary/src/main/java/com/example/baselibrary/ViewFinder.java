package com.example.baselibrary;

import android.app.Activity;
import android.view.View;

/**
 * author: created by wentaoKing
 * date: created in 2019-12-29
 * description:
 */
public class ViewFinder {

    private Activity mActivity;
    private View mView;

    public ViewFinder(Activity activity){
        mActivity = activity;
    }

    public ViewFinder(View view){
        mView = view;
    }


    public View findViewById(int viewId) {
        if(mActivity != null){
            return mActivity.findViewById(viewId);
        }else {
            return mView.findViewById(viewId);
        }
    }
}
