package bleepy.pack.com.bleepy.utils.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsManagerImpl implements PrefsManager {
    private static final String TAG = PrefsManagerImpl.class.getSimpleName();
    private Context context;
    private SharedPreferences mMyPreferences;
    private SharedPreferences.Editor mMyPreferenceEditor;
    private static PrefsManagerImpl sPreferenceInstance;
    public PrefsManagerImpl(Context context) {
        this.context=context;
        this.mMyPreferences = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE); //Constant.SHARED_PREFERENCE_NAME
    }
    public static PrefsManagerImpl getInstance(Context context) {
        if (sPreferenceInstance == null) {
            sPreferenceInstance = new PrefsManagerImpl(context.getApplicationContext());
        }
        return sPreferenceInstance;
    }
    public boolean saveKeyValuePairToPrefs(String key, String value) {
        if (value != null && key != null) {
            mMyPreferenceEditor = mMyPreferences.edit();
            mMyPreferenceEditor.putString(key, value);
            return mMyPreferenceEditor.commit();
        } else {
            //Log.e(TAG, Constant.SEPARATOR + "-----" + Constant.SEPARATOR +
            //"Saving record rejected in Shared Preferances for ...(" + key + ") due to null value");
            return false;
        }
    }

    public boolean saveKeyValuePairToPrefs(String key, int value) {
        if (key != null) {
            mMyPreferenceEditor = mMyPreferences.edit();
            mMyPreferenceEditor.putInt(key, value);
            return mMyPreferenceEditor.commit();
        } else {
            //Log.e(TAG, Constant.SEPARATOR + "-----" + Constant.SEPARATOR +
            //"Saving record rejected in Shared Preferances for ...(" + key + ") due to null value");
            return false;
        }
    }

    public boolean removeKeyFromPrefs(String key) {
        mMyPreferenceEditor = mMyPreferences.edit();
        mMyPreferenceEditor.remove(key);
        return mMyPreferenceEditor.commit();
    }


    public String getKeyValueFromPrefsByKey(String key) {
        String ret = null;
        if (mMyPreferences != null)
            ret = mMyPreferences.getString(key, null);
        return ret;
    }


    public int getIntKeyValueFromPrefsByKey(String key) {
        int ret = 0;
        if (mMyPreferences != null)
            ret = mMyPreferences.getInt(key, 0);

        int t = mMyPreferences.getInt(key, 0);
        return ret;
    }


    public boolean saveBooleanKeyValueToPrefs(String key, boolean value) {
        mMyPreferenceEditor = mMyPreferences.edit();
        mMyPreferenceEditor.putBoolean(key, value);
        return mMyPreferenceEditor.commit();
    }

    public boolean getBooleanKeyValueFromPrefs(String key) {
        boolean ret = false;
        if (mMyPreferences != null)
            ret = mMyPreferences.getBoolean(key, false);
        return ret;
    }

    public boolean clearPreferences() {
        mMyPreferenceEditor = mMyPreferences.edit();
        mMyPreferenceEditor.clear();
        mMyPreferenceEditor.apply();
        return mMyPreferenceEditor.commit();
    }



}
