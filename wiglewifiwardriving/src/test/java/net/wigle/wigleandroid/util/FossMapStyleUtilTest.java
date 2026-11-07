package net.wigle.wigleandroid.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FossMapStyleUtilTest {
    @Test
    public void keylessDefaultRequiresNoConfiguration() {
        assertEquals(FossMapStyleUtil.DEFAULT_STYLE_URL,
                FossMapStyleUtil.resolveStyleUrl(null, null));
        assertEquals(FossMapStyleUtil.DEFAULT_STYLE_URL,
                FossMapStyleUtil.resolveStyleUrl("  ", "unused"));
    }

    @Test
    public void customKeylessStyleIsNotDiscarded() {
        assertEquals("https://maps.example.org/styles/ours",
                FossMapStyleUtil.resolveStyleUrl(" https://maps.example.org/styles/ours ", ""));
    }

    @Test
    public void optionalKeySuffixStillSupportsExistingProviders() {
        assertEquals("https://maps.example.org/style.json?key=secret",
                FossMapStyleUtil.resolveStyleUrl("https://maps.example.org/style.json?key=", "secret"));
    }
}
