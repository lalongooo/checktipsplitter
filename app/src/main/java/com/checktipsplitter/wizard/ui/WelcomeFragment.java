package com.checktipsplitter.wizard.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.checktipsplitter.R;
import com.checktipsplitter.network.ExchangeRateUpdater;
import com.checktipsplitter.ui.ActivityMain;
import com.checktipsplitter.utils.PrefUtils;

public class WelcomeFragment extends Fragment {

    private ProgressBar progressBar;
    private ExchangeRateUpdater.OnExchangeRateSyncComplete onExchangeRateSyncComplete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_welcome, container, false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        if (PrefUtils.shouldSyncExchangeRates(getActivity())) {
            ExchangeRateUpdater.updateExchangeRates(getActivity(), new ExchangeRateUpdater.OnExchangeRateSyncComplete[]{new ExchangeRateUpdater.OnExchangeRateSyncComplete() {
                @Override
                public void onExchangeRateSyncComplete() {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }, onExchangeRateSyncComplete});
        } else {
            onExchangeRateSyncComplete.onExchangeRateSyncComplete();
        }

        return rootView;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof ExchangeRateUpdater.OnExchangeRateSyncComplete)) {
            throw new ClassCastException("Activity must implement ExchangeRateUpdater.OnExchangeRateSyncComplete");
        }

        onExchangeRateSyncComplete = (ExchangeRateUpdater.OnExchangeRateSyncComplete) activity;
    }
}
