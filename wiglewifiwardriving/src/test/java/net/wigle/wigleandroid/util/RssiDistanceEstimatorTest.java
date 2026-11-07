package net.wigle.wigleandroid.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RssiDistanceEstimatorTest {
    @Test
    public void referenceAtOneMeter() {
        assertEquals(1.0, RssiDistanceEstimator.estimateMeters(-40, 2412, -40, 30), 0.001);
    }

    @Test
    public void weakerSignalMeansGreaterDistance() {
        final double close = RssiDistanceEstimator.estimateMeters(-50, 2412, -40, 30);
        final double far = RssiDistanceEstimator.estimateMeters(-70, 2412, -40, 30);
        assertTrue(far > close);
    }

    @Test
    public void higherFrequencyAccountsForExtraFreeSpaceLoss() {
        final double at24 = RssiDistanceEstimator.estimateMeters(-65, 2412, -40, 30);
        final double at5 = RssiDistanceEstimator.estimateMeters(-65, 5180, -40, 30);
        assertTrue(at5 < at24);
    }

    @Test
    public void differentIndoorPathLossChangesEstimate() {
        final double open = RssiDistanceEstimator.estimateMeters(-70, 2412, -40, 20);
        final double obstructed = RssiDistanceEstimator.estimateMeters(-70, 2412, -40, 40);
        assertTrue(open > obstructed);
    }

    @Test
    public void rejectsNonWifiFrequenciesAndInvalidReadings() {
        assertTrue(Double.isNaN(RssiDistanceEstimator.estimateMeters(-65, 0, -40, 30)));
        assertTrue(Double.isNaN(RssiDistanceEstimator.estimateMeters(0, 2412, -40, 30)));
        assertTrue(Double.isNaN(RssiDistanceEstimator.estimateMeters(-65, 2412, -40, 10)));
    }
}
