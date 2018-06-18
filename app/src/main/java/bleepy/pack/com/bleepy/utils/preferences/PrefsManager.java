package bleepy.pack.com.bleepy.utils.preferences;

public interface PrefsManager {
    String SHARED_PREFERENCES_KEY = "com.pack.ontogo";

    boolean saveKeyValuePairToPrefs(String key, String value);

    boolean saveKeyValuePairToPrefs(String key, int value);

    boolean removeKeyFromPrefs(String key);

    String getKeyValueFromPrefsByKey(String key);

    int getIntKeyValueFromPrefsByKey(String key);

    boolean saveBooleanKeyValueToPrefs(String key, boolean value);

    boolean getBooleanKeyValueFromPrefs(String key);

    boolean clearPreferences();

}
