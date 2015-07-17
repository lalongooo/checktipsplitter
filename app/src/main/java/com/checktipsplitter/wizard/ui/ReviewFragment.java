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
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.checktipsplitter.R;
import com.checktipsplitter.wizard.model.AbstractWizardModel;
import com.checktipsplitter.wizard.model.ModelCallbacks;
import com.checktipsplitter.wizard.model.Page;
import com.checktipsplitter.wizard.model.ReviewItem;
import com.checktipsplitter.ui.ActivityMain;

import java.util.ArrayList;
import java.util.List;

public class ReviewFragment extends ListFragment implements ModelCallbacks {

    private static final String FINAL_RESULT_PAGE_KEY = "final_result_page_key";
    private Callbacks mCallbacks;
    private AbstractWizardModel mWizardModel;
    private List<ReviewItem> mCurrentReviewItems;
    private ReviewAdapter mReviewAdapter;

    public ReviewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReviewAdapter = new ReviewAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page, container, false);

        TextView titleView = (TextView) rootView.findViewById(android.R.id.title);
        titleView.setText(R.string.review_title);
        titleView.setTextColor(getResources().getColor(R.color.review_green));

        ListView listView = (ListView) rootView.findViewById(android.R.id.list);
        setListAdapter(mReviewAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(((AppCompatActivity) activity).getSupportFragmentManager().getFragments().get(0) instanceof Callbacks)) {
            throw new ClassCastException("Activity must implement fragment's callbacks");
        }

        mCallbacks = (Callbacks) ((ActivityMain) activity).getSupportFragmentManager().getFragments().get(0);
        ;

        mWizardModel = mCallbacks.onGetModel();
        mWizardModel.registerListener(this);
        onPageTreeChanged();
    }

    @Override
    public void onPageTreeChanged() {
        onPageDataChanged(null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;

        mWizardModel.unregisterListener(this);
    }

    @Override
    public void onPageDataChanged(Page changedPage) {
        ArrayList<ReviewItem> reviewItems = new ArrayList<>();
        for (Page page : mWizardModel.getCurrentPageSequence()) {
            page.getReviewItems(reviewItems);
        }

        reviewItems.add(new ReviewItem("Total C/U", "10.0", FINAL_RESULT_PAGE_KEY));


        mCurrentReviewItems = reviewItems;

        if (mReviewAdapter != null) {
            mReviewAdapter.notifyDataSetInvalidated();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mCallbacks.onEditScreenAfterReview(mCurrentReviewItems.get(position).getPageKey());
    }

    public interface Callbacks {
        AbstractWizardModel onGetModel();

        void onEditScreenAfterReview(String pageKey);
    }

    private class ReviewAdapter extends BaseAdapter {
        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return true;
        }

        @Override
        public Object getItem(int position) {
            return mCurrentReviewItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mCurrentReviewItems.get(position).hashCode();
        }

        @Override
        public View getView(int position, View view, ViewGroup container) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View rootView = inflater.inflate(R.layout.list_item_review, container, false);

            ReviewItem reviewItem = mCurrentReviewItems.get(position);
            String value = reviewItem.getDisplayValue();
            if (TextUtils.isEmpty(value)) {
                value = "(None)";
            }

            TextView tv1 = (TextView) rootView.findViewById(android.R.id.text1);
            tv1.setText(reviewItem.getTitle().toUpperCase());
            TextView tv2 = (TextView) rootView.findViewById(android.R.id.text2);
            tv2.setText(value);

            if (reviewItem.getPageKey().equals(FINAL_RESULT_PAGE_KEY)) {
                tv1.setTextAppearance(getActivity(), R.style.TextAppearance_AppCompat_Medium);
                tv1.setTextColor(getResources().getColor(R.color.blue_main));
                tv1.setTypeface(Typeface.DEFAULT_BOLD);
                tv2.setTextColor(getResources().getColor(R.color.blue_main));
                tv2.setTypeface(Typeface.DEFAULT_BOLD);
            }

            return rootView;
        }

        @Override
        public int getCount() {
            return mCurrentReviewItems.size();
        }
    }
}
