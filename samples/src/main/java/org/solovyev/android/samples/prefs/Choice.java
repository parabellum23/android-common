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

package org.solovyev.android.samples.prefs;

import org.solovyev.android.Labeled;
import org.solovyev.android.samples.R;

/**
* User: serso
* Date: 8/8/12
* Time: 2:56 AM
*/
public enum Choice implements Labeled {

    to_be(R.string.to_be),
    not_to_be(R.string.not_to_be);

    private final int captionResId;

    Choice(int captionResId) {
        this.captionResId = captionResId;
    }


    @Override
    public int getCaptionResId() {
        return this.captionResId;
    }
}
