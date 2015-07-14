package com.example.android.wizardpager.wizard.model;

import android.support.v4.app.Fragment;

import com.example.android.wizardpager.wizard.ui.WelcomeFragment;

import java.util.ArrayList;

/**
 * Created by lalongooo on 08/04/15.
 */
public class WelcomePage extends Page {

    public WelcomePage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return WelcomeFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {

    }

    @Override
    public boolean isCompleted() {
        return true; //!TextUtils.isEmpty(mData.getString(TEXT_DATA_KEY));
    }

}
