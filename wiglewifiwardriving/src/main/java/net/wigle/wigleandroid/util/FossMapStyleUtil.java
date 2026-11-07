package net.wigle.wigleandroid.util;

import android.content.SharedPreferences;

/** Shared style selection for every MapLibre screen. */
public final class FossMapStyleUtil {
    public static final String DEFAULT_STYLE_URL = "https://tiles.openfreemap.org/styles/liberty";

    private FossMapStyleUtil() { }

    public static String getConfiguredStyleUrl(final SharedPreferences preferences) {
        if (preferences == null) {
            return DEFAULT_STYLE_URL;
        }
        return resolveStyleUrl(
                preferences.getString(PreferenceKeys.PREF_FOSS_MAPS_VECTOR_TILE_STYLE, null),
                preferences.getString(PreferenceKeys.PREF_FOSS_MAPS_VECTOR_TILE_KEY, null));
    }

    /** A custom URL may be keyless; keyed services can append an API key suffix. */
    public static String resolveStyleUrl(final String styleUrl, final String key) {
        if (styleUrl == null || styleUrl.trim().isEmpty()) {
            return DEFAULT_STYLE_URL;
        }
        return styleUrl.trim() + (key == null ? "" : key.trim());
    }
}
