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

package org.solovyev.android.db;

import android.database.sqlite.SQLiteDatabase;
import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 4/28/12
 * Time: 2:20 AM
 */
public interface DbExec {

    long SQL_ERROR = -1;

    /**
     * Method executes database operation.
     * If insert operation is done and error is occurred {@link DbExec#SQL_ERROR} is returned.
     *
     * @param db sqlite database instance
     * @return number of affected rows (for update or delete) or newly generated ID (for insert)
     */
    long exec(@Nonnull SQLiteDatabase db);
}
