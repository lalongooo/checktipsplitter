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

package com.checktipsplitter.wizard.model;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.checktipsplitter.wizard.ui.FreeTextFragment;

import java.util.ArrayList;

public class FreeTextPage extends Page {

    public static String NAME_DATA_KEY;
    private String displayText;
    private String hint;


    public FreeTextPage(ModelCallbacks callbacks, String title, String dataKey, String reviewDisplayText, String hint) {
        super(callbacks, title);
        NAME_DATA_KEY = dataKey;
        displayText = reviewDisplayText;
        this.hint = hint;
    }

    @Override
    public Fragment createFragment() {
        return FreeTextFragment.create(getKey(), hint);
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem(displayText, mData.getString(NAME_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(NAME_DATA_KEY));
    }
}
