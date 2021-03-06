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

package org.solovyev.android.list;

import org.solovyev.common.JPredicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


/**
 * User: serso
 * Date: 6/6/12
 * Time: 1:09 AM
 */
public class PrefixFilter<T> implements JPredicate<T> {

    @Nonnull
    private String prefix;

    public PrefixFilter(@Nonnull String prefix) {
        this.prefix = prefix;
    }

    @Override
    public boolean apply(@Nullable T input) {
        boolean shown = false;

        if (input != null) {
            final String valueText = input.toString().toLowerCase();

            // First match against the whole, non-splitted value
            if (valueText.startsWith(prefix)) {
                shown = true;
            } else {
                final String[] words = valueText.split(" ");

                for (String word : words) {
                    if (word.startsWith(prefix)) {
                        shown = true;
                        break;
                    }
                }
            }
        }

        return shown;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (o == this) {
            return true;
        }

        if (o instanceof PrefixFilter) {
            final PrefixFilter that = (PrefixFilter) o;
            if (this.prefix.equals(that.prefix)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        return prefix.hashCode();
    }
}