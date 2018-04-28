package com.bluekeroro.android.game2048;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by BlueKeroro on 2018/4/28.
 */
public class BestScorePreferences {
    private static final String BEST_SCORE="bestScore";
    public static String getBestScore(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(BEST_SCORE,"0");
    }
    public static void  setBestScore(Context context,String score){
         PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(BEST_SCORE,score)
                .apply();
    }
}
