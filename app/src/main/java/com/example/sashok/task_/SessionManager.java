package com.example.sashok.task_;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by sashok on 30.4.17.
 */

public class SessionManager {

    SharedPreferences pref;
    Context _context;
    SharedPreferences.Editor editor;

    int PRIVATE_MODE = 0;
    private static String TAG = SessionManager.class.getSimpleName();
    private static final String PREF_NAME = "DiscontAppSessionKey";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

        private static final String KEY_SESSION_KEY = "sKey";



        public void setSessionKey(String sKey) {

            editor.putString(KEY_SESSION_KEY, sKey);

            editor.commit();

            Log.d(TAG, "User login session modified!");
        }

        public String getSessionKey(){
            return pref.getString(KEY_SESSION_KEY,"null");

    }

}
