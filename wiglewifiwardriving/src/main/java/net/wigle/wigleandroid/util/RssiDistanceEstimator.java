package net.wigle.wigleandroid.util;

/**
 * A deliberately approximate RSSI-to-range model for Wi-Fi networks.
 *
 * No transmitter power or antenna calibration is available in a normal Wi-Fi scan. This
 * model therefore cannot measure AP distance; it provides a relative, environment-dependent
 * estimate. 5/6 GHz free-space frequency loss is accounted for relative to 2412 MHz.
 */
public final class RssiDistanceEstimator {
    public static final int DEFAULT_RSSI_AT_ONE_METER = -40;
    public static final int DEFAULT_PATH_LOSS_TENTHS = 30; // exponent 3.0, typical indoor guess

    private static final double REFERENCE_FREQUENCY_MHZ = 2412.0;

    private RssiDistanceEstimator() { }

    /**
     * @param rssiDbm current received signal strength, dBm
     * @param frequencyMhz Wi-Fi centre frequency, MHz
     * @param referenceRssiDbm expected RSSI at 1 m on 2412 MHz (user adjustable)
     * @param pathLossTenths path-loss exponent multiplied by ten (e.g. 30 = 3.0)
     * @return approximate metres, or NaN if data/calibration is not meaningful
     */
    public static double estimateMeters(final int rssiDbm, final int frequencyMhz,
                                        final int referenceRssiDbm, final int pathLossTenths) {
        if (rssiDbm > -10 || rssiDbm < -110 || frequencyMhz < 2300 || frequencyMhz > 7200
                || referenceRssiDbm < -80 || referenceRssiDbm > -20
                || pathLossTenths < 20 || pathLossTenths > 50) {
            return Double.NaN;
        }
        final double referenceAtBand = referenceRssiDbm
                - 20.0 * Math.log10(frequencyMhz / REFERENCE_FREQUENCY_MHZ);
        final double exponent = pathLossTenths / 10.0;
        return Math.pow(10.0, (referenceAtBand - rssiDbm) / (10.0 * exponent));
    }
}
