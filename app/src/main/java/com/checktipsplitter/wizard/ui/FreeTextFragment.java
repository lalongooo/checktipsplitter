/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.checktipsplitter.wizard.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.checktipsplitter.R;
import com.checktipsplitter.wizard.model.FreeTextPage;
import com.checktipsplitter.ui.ActivityMain;

public class FreeTextFragment extends Fragment {
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private FreeTextPage mPage;
    private TextView hint;
    private EditText mEditText;
    private String hintText;
    private int inputType;

    public static FreeTextFragment create(String key, String hint, int inputType) {
        Bundle args = new Bundle();
        args.putString("hint", hint);
        args.putInt("input_type", inputType);
        args.putString(ARG_KEY, key);

        FreeTextFragment fragment = new FreeTextFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public FreeTextFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (FreeTextPage) mCallbacks.onGetPage(mKey);
        hintText = args.getString("hint");
        inputType = args.getInt("input_type");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page_free_text, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

        mEditText = ((EditText) rootView.findViewById(R.id.your_name));
        mEditText.setText(mPage.getData().getString(FreeTextPage.DATA_KEY));
        mEditText.setInputType(inputType);
        hint = ((TextView) rootView.findViewById(R.id.hint));
        hint.setText(hintText);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(((AppCompatActivity) activity).getSupportFragmentManager().getFragments().get(0) instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
        }

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

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (inputType == (InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL)) {
                    mPage.getData().putString(FreeTextPage.DATA_KEY, (!TextUtils.isEmpty(editable.toString())) ? String.valueOf(Float.valueOf(editable.toString())) : null);
                } else if (inputType == InputType.TYPE_CLASS_NUMBER) {
                    mPage.getData().putString(FreeTextPage.DATA_KEY, (!TextUtils.isEmpty(editable.toString())) ? String.valueOf(Integer.valueOf(editable.toString())) : null);
                } else {
                    mPage.getData().putString(FreeTextPage.DATA_KEY, (editable != null) ? editable.toString() : null);
                }
                mPage.notifyDataChanged();
            }
        });
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        // In a future update to the support library, this should override setUserVisibleHint
        // instead of setMenuVisibility.
        if (mEditText != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (!menuVisible) {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        }
    }
}
