/*
 * Copyright 2013 serso aka se.solovyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

package org.solovyev.android.prefs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import javax.annotation.Nonnull;
import org.solovyev.common.text.NumberMapper;


/* The following code was written by Matthew Wiggins
 * and is released under the APACHE 2.0 license
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */

public class SeekBarPreference extends AbstractDialogPreference<Integer> implements SeekBar.OnSeekBarChangeListener {

    private int max = 0;

    public SeekBarPreference(Context context, AttributeSet attrs) {
        super(context, attrs, "50", true, NumberMapper.of(Integer.class));

        max = attrs.getAttributeIntValue(androidns, "max", 100);
    }

    @Override
    protected LinearLayout.LayoutParams getParams() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Nonnull
    @Override
    protected View createPreferenceView(@Nonnull Context context) {
        final SeekBar result = new SeekBar(context);

        result.setOnSeekBarChangeListener(this);

        return result;
    }

    @Override
    protected void initPreferenceView(@Nonnull View v, Integer value) {
        ((SeekBar) v).setMax(max);
        if (value != null) {
            ((SeekBar) v).setProgress(value);
            setValueText(value);
        }
    }

    public void onProgressChanged(SeekBar seek, int value, boolean fromTouch) {
        setValueText(value);

        persistValue(value);
    }

    private void setValueText(int value) {
        String t = String.valueOf(value);
        final String valueText = getValueText();
        updateValueText(valueText == null ? t : t.concat(valueText));
    }

    public void onStartTrackingTouch(SeekBar seek) {
    }

    public void onStopTrackingTouch(SeekBar seek) {
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public void setProgress(int progress) {
        setValue(progress);
        final View preferenceView = getPreferenceView();
        if (preferenceView != null) {
            ((SeekBar) preferenceView).setProgress(progress);
        }
    }

    public int getProgress() {
        final Integer value = getValue();
        return value == null ? 0 : value;
    }
}

