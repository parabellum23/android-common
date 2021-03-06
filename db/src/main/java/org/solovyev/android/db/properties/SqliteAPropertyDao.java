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

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import javax.annotation.Nonnull;
import org.solovyev.android.properties.AProperty;
import org.solovyev.android.db.AbstractSQLiteHelper;
import org.solovyev.android.db.AndroidDbUtils;
import org.solovyev.android.db.DbExec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 8:40 PM
 */
public class SqliteAPropertyDao extends AbstractSQLiteHelper implements APropertyDao {

    @Nonnull
    private final String tableName;

    @Nonnull
    private final String idColumnName;

    @Nonnull
    private final String propertyNameColumnName;

    @Nonnull
    private final String propertyValueColumnName;

    public SqliteAPropertyDao(@Nonnull Context context,
                              @Nonnull SQLiteOpenHelper sqliteOpenHelper,
                              @Nonnull String tableName,
                              @Nonnull String idColumnName,
                              @Nonnull String propertyNameColumnName,
                              @Nonnull String propertyValueColumnName) {
        super(context, sqliteOpenHelper);
        this.tableName = tableName;
        this.idColumnName = idColumnName;
        this.propertyNameColumnName = propertyNameColumnName;
        this.propertyValueColumnName = propertyValueColumnName;
    }

    @Nonnull
    @Override
    public List<AProperty> loadPropertiesById(@Nonnull Object id) {
        return AndroidDbUtils.doDbQuery(getSqliteOpenHelper(), new PropertyByIdDbQuery(getContext(), getSqliteOpenHelper(), tableName, idColumnName, id));
    }

    @Override
    public void removePropertiesById(@Nonnull Object id) {
        AndroidDbUtils.doDbExec(getSqliteOpenHelper(), new RemovePropertiesDbExec(id, tableName, idColumnName));
    }

    @Override
    public void insertProperty(@Nonnull Object id, @Nonnull AProperty property) {
        AndroidDbUtils.doDbExec(getSqliteOpenHelper(), new InsertPropertyDbExec(id, property, tableName, idColumnName, propertyNameColumnName, propertyValueColumnName));
    }

    @Override
    public void insertProperties(@Nonnull Object id, @Nonnull Collection<AProperty> properties) {
        final List<DbExec> execs = new ArrayList<DbExec>(properties.size());

        for (AProperty property : properties) {
            execs.add(new InsertPropertyDbExec(id, property, tableName, idColumnName, propertyNameColumnName, propertyValueColumnName));
        }

        AndroidDbUtils.doDbExecs(getSqliteOpenHelper(), execs);
    }
}
