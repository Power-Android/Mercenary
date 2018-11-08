package com.power.mercenary.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utils {

    public static <V> V readAreaJson(Context context, Class<V> type) {
        InputStreamReader inputReader;
        try {
            inputReader = new InputStreamReader(context
                    .getResources().getAssets().open("area.json"));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bufReader.readLine()) != null) {
                sb.append(line);
            }
            return GsonUtils.fromJson(sb.toString(), type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
