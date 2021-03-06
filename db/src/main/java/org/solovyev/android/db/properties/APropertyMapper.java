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

package org.solovyev.android.db.properties;

import android.database.Cursor;
import org.solovyev.android.properties.AProperty;
import org.solovyev.android.properties.Properties;
import org.solovyev.common.Converter;

import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 5/30/12
 * Time: 7:26 PM
 */
public class APropertyMapper implements Converter<Cursor, AProperty> {

    @Nonnull
    private static final APropertyMapper instance = new APropertyMapper();

    private APropertyMapper() {
    }

    @Nonnull
    public static APropertyMapper getInstance() {
        return instance;
    }

    @Nonnull
    @Override
    public AProperty convert(@Nonnull Cursor cursor) {
        final String id = cursor.getString(0);
        final String name = cursor.getString(1);
        final String value = cursor.getString(2);
        return Properties.newProperty(name, value);
    }
}
