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

package org.solovyev.android.captcha;

import android.os.Parcel;
import android.os.Parcelable;
import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 5/28/12
 * Time: 11:37 PM
 */
public class Captcha implements Parcelable {

    @Nonnull
    private final String captchaSid;

    @Nonnull
    private final String captchaImage;

    public Captcha(@Nonnull String captchaSid, @Nonnull String captchaImage) {
        this.captchaSid = captchaSid;
        this.captchaImage = captchaImage;
    }

    @Nonnull
    public String getCaptchaSid() {
        return captchaSid;
    }

    @Nonnull
    public String getCaptchaImage() {
        return captchaImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(captchaSid);
        out.writeString(captchaImage);
    }

    @Nonnull
    public ResolvedCaptcha resolve(@Nonnull String captchaKey) {
        return new ResolvedCaptcha(captchaSid, captchaKey);
    }
}
