package com.checktipsplitter.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.checktipsplitter.R;
import com.checktipsplitter.BaseActivity;
import com.checktipsplitter.network.ExchangeRateUpdater;
import com.checktipsplitter.wizard.ui.WelcomeFragment;

public class ActivityMain extends BaseActivity implements ExchangeRateUpdater.OnExchangeRateSyncComplete{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new WelcomeFragment()).commit();
    }

    @Override
    public void onExchangeRateSyncComplete() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragmentAddBill()).commit();
    }
}
