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

package org.solovyev.android.samples.menu;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.solovyev.android.list.ListAdapter;
import org.solovyev.android.list.ListItem;
import org.solovyev.android.list.ListItemOnClickData;
import org.solovyev.android.list.SimpleMenuOnClick;
import org.solovyev.android.menu.LabeledMenuItem;
import org.solovyev.android.samples.R;
import org.solovyev.android.view.TextViewBuilder;

import javax.annotation.Nonnull;
import java.util.Arrays;

/**
 * User: serso
 * Date: 8/10/12
 * Time: 1:39 PM
 */
public class MenuListItem implements ListItem {

    private final int captionResId;

    private final int sortOrder;

    public MenuListItem(int captionResId, int sortOrder) {
        this.captionResId = captionResId;
        this.sortOrder = sortOrder;
    }

    @Override
    public OnClickAction getOnClickAction() {
        return new OnClickAction() {
            @Override
            public void onClick(@Nonnull Context context, @Nonnull ListAdapter<? extends ListItem> adapter, @Nonnull ListView listView) {
                Toast.makeText(context, context.getString(R.string.long_press_to_open_menu), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public OnClickAction getOnLongClickAction() {
        return new SimpleMenuOnClick<MenuListItem>(Arrays.asList(MenuItemMenu.values()), this, String.valueOf(captionResId));
    }

    @Nonnull
    @Override
    public View updateView(@Nonnull Context context, @Nonnull View view) {
        if (this.getTag().equals(view.getTag())) {
            fillView(context, (TextView) view);
            return view;
        } else {
            return build(context);
        }
    }

    @Nonnull
    @Override
    public View build(@Nonnull Context context) {
        final TextView view = TextViewBuilder.newInstance(R.layout.acl_menu_list_item, getTag()).build(context);

        fillView(context, view);

        return view;
    }

    private void fillView(@Nonnull Context context, @Nonnull TextView view) {
        view.setText(context.getString(captionResId));
    }

    @Nonnull
    private String getTag() {
        return "menu_list_item";
    }

    public int getSortOrder() {
        return sortOrder;
    }

    private static enum MenuItemMenu implements LabeledMenuItem<ListItemOnClickData<MenuListItem>> {

        show_number(R.string.show_menu_number) {
            @Override
            public void onClick(@Nonnull ListItemOnClickData<MenuListItem> data, @Nonnull Context context) {
                Toast.makeText(context, context.getString(R.string.show_menu_number_text, String.valueOf(data.getDataObject().getSortOrder())), Toast.LENGTH_SHORT).show();
            }
        },

        show_name(R.string.show_menu_name) {
            @Override
            public void onClick(@Nonnull ListItemOnClickData<MenuListItem> data, @Nonnull Context context) {
                Toast.makeText(context, context.getString(R.string.show_menu_name_text, context.getString(data.getDataObject().captionResId)), Toast.LENGTH_SHORT).show();
            }
        };

        private final int captionResId;

        private MenuItemMenu(int captionResId) {
            this.captionResId = captionResId;
        }

        @Nonnull
        @Override
        public String getCaption(@Nonnull Context context) {
            return context.getString(captionResId);
        }
    }
}
