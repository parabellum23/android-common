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

package net.robotmedia.billing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import javax.annotation.Nonnull;

public class BillingReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(@Nonnull Context context, @Nonnull Intent intent) {
		final String action = intent.getAction();
		BillingController.debug("Received " + action);

		final BillingResponseType responseType = BillingResponseType.fromIntentAction(intent);

		if (responseType != null) {
			responseType.doAction(context, intent);
		} else {
			Log.w(this.getClass().getSimpleName(), "Unexpected action: " + action);
		}
	}
}
