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
import com.checktipsplitter.model.Currency;
import com.checktipsplitter.rest.mashape.MashapeCurrencyExchangeRestClient;
import com.checktipsplitter.rest.openexchangerates.OpenExchangeRatesRestClient;
import com.checktipsplitter.utils.PrefUtils;
import com.checktipsplitter.wizard.model.WelcomePage;
import com.checktipsplitter.ui.ActivityMain;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.ResponseCallback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class WelcomeFragment extends Fragment {

    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private WelcomePage mPage;
    private ProgressBar progressBar;
    private OnExchangeRateSyncComplete onExchangeRateSyncComplete;

    public WelcomeFragment() {
    }

    public interface OnExchangeRateSyncComplete {
        void exchangeRateSyncComplete();
    }

    public static WelcomeFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        WelcomeFragment fragment = new WelcomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (WelcomePage) mCallbacks.onGetPage(mKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_welcome, container, false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        updateExchangeRates();
        return rootView;
    }

    private void updateExchangeRates() {
        if (PrefUtils.shouldSyncExchangeRates(getActivity())) {
            progressBar.setVisibility(View.VISIBLE);
            MashapeCurrencyExchangeRestClient.get().getListQuotes(new Callback<ArrayList<String>>() {
                @Override
                public void success(final ArrayList<String> strings, Response response) {
                    OpenExchangeRatesRestClient.get().getCurrencies(new ResponseCallback() {
                        @Override
                        public void success(Response response) {

                            List<Currency> currencies = new ArrayList<>();

                            String responseText = new String(
                                    ((TypedByteArray) response.getBody()).getBytes()
                            );

                            JsonObject jsonObject = new JsonParser()
                                    .parse(responseText)
                                    .getAsJsonObject();

                            for (Map.Entry entry : jsonObject.entrySet()) {
                                JsonElement jsonElement = (JsonElement) entry.getValue();

                                if (strings.contains(entry.getKey().toString())) {
                                    currencies.add(new Currency(entry.getKey().toString(), jsonElement.getAsString()));
                                }
                            }




                            PrefUtils.saveLastExchangeRateSync(getActivity());

                            PrefUtils.saveCurrencyValuesDescription(getActivity(), new Gson().toJson(currencies));
                            PrefUtils.getCurrencyValuesDescription(getActivity());


                            onExchangeRateSyncComplete.exchangeRateSyncComplete();
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
                }

                @Override
                public void failure(RetrofitError error) {
                }
            });
        } else {
            onExchangeRateSyncComplete.exchangeRateSyncComplete();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(((AppCompatActivity) activity).getSupportFragmentManager().getFragments().get(0) instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Parent must implement PageFragmentCallbacks");
        }

        if (!(((AppCompatActivity) activity).getSupportFragmentManager().getFragments().get(0) instanceof OnExchangeRateSyncComplete)) {
            throw new ClassCastException("Parent must implement onExchangeRateSyncComplete");
        }

        onExchangeRateSyncComplete = (OnExchangeRateSyncComplete) ((ActivityMain) activity).getSupportFragmentManager().getFragments().get(0);
        mCallbacks = (PageFragmentCallbacks) ((ActivityMain) activity).getSupportFragmentManager().getFragments().get(0);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
    }
}
