/* Copyright 2020 Braden Farmer
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

package com.farmerbb.taskbar.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.farmerbb.taskbar.util.AppEntry;
import com.farmerbb.taskbar.util.DesktopIconInfo;
import com.farmerbb.taskbar.util.U;

import org.json.JSONArray;
import org.json.JSONException;

public class DesktopIconSelectAppActivity extends AbstractSelectAppActivity {

    private DesktopIconInfo desktopIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        desktopIcon = (DesktopIconInfo) getIntent().getSerializableExtra("desktop_icon");
    }

    @Override
    public void selectApp(AppEntry entry) {
        desktopIcon.entry = entry;

        try {
            SharedPreferences pref = U.getSharedPreferences(this);
            JSONArray icons = new JSONArray(pref.getString("desktop_icons", "[]"));
            icons.put(desktopIcon.toJson(this));

            pref.edit().putString("desktop_icons", icons.toString()).apply();
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("com.farmerbb.taskbar.REFRESH_DESKTOP_ICONS"));
        } catch (JSONException e) { /* Gracefully fail */ }

        finish();
    }
}