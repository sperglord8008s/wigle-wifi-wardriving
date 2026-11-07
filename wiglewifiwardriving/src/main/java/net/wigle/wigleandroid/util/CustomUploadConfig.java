package net.wigle.wigleandroid.util;

import android.content.SharedPreferences;

import okhttp3.HttpUrl;

/** Opt-in HTTPS CSV multipart upload; never forwards WiGLE credentials. */
public final class CustomUploadConfig {
    private CustomUploadConfig() {}

    public static String getEndpoint(final SharedPreferences prefs) {
        if (prefs == null) return "";
        final String value = prefs.getString(PreferenceKeys.PREF_CUSTOM_UPLOAD_URL, "");
        return value == null ? "" : value.trim();
    }

    public static boolean isEnabled(final SharedPreferences prefs) {
        return !getEndpoint(prefs).isEmpty();
    }

    public static boolean isValidHttpsUrl(final String url) {
        if (url == null || url.isEmpty() || url.length() > 2048) return false;
        final HttpUrl parsed = HttpUrl.parse(url);
        return parsed != null && "https".equals(parsed.scheme())
                && parsed.username().isEmpty() && parsed.password().isEmpty()
                && parsed.fragment() == null;
    }
}
