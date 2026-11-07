package net.wigle.wigleandroid.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CustomUploadConfigTest {
    @Test
    public void requiresHttpsAndDoesNotAllowEmbeddedCredentials() {
        assertTrue(CustomUploadConfig.isValidHttpsUrl("https://example.org/wigle/upload"));
        assertTrue(CustomUploadConfig.isValidHttpsUrl("https://example.org/upload?key=opaque"));
        assertFalse(CustomUploadConfig.isValidHttpsUrl("http://example.org/upload"));
        assertFalse(CustomUploadConfig.isValidHttpsUrl("https://user:password@example.org/upload"));
        assertFalse(CustomUploadConfig.isValidHttpsUrl("https://example.org/upload#fragment"));
        assertFalse(CustomUploadConfig.isValidHttpsUrl("not a URL"));
    }
}
