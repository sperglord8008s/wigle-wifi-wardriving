package net.wigle.wigleandroid.ui;

import net.wigle.wigleandroid.model.Network;
import net.wigle.wigleandroid.model.NetworkType;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class NetworkListSorterTest {
    @Test
    public void sortByFullMacEvenWhenNamesAndOuiMatch() {
        final Network middle = wifi("02:12:34:56:78:0a");
        final Network first = wifi("02:12:34:56:78:02");
        final Network last = wifi("02:12:34:56:78:ff");
        final List<Network> networks = new ArrayList<>(Arrays.asList(last, middle, first));

        Collections.sort(networks, NetworkListSorter.macCompare);

        assertEquals(Arrays.asList(first, middle, last), networks);
    }

    @Test
    public void suffixSortPlacesDifferentOuiWithSameTailTogether() {
        final Network differentTail = wifi("10:20:33:44:55:67");
        final Network radioOne = wifi("aa:bb:33:44:55:66");
        final Network radioTwo = wifi("cc:dd:33:44:55:66");
        final List<Network> networks = new ArrayList<>(Arrays.asList(radioTwo, differentTail, radioOne));

        Collections.sort(networks, NetworkListSorter.macSuffixCompare);

        assertEquals(Arrays.asList(radioOne, radioTwo, differentTail), networks);
    }

    @Test
    public void suffixKeyAcceptsMixedCaseAndAlternativeSeparators() {
        assertEquals("33445566aabb", NetworkListSorter.suffixFirstBssidKey("AA-BB-33-44-55-66"));
        assertEquals("33445566aabb", NetworkListSorter.suffixFirstBssidKey("aa:bb:33:44:55:66"));
    }

    private Network wifi(final String bssid) {
        return new Network(bssid, "Same SSID", 2412, "[WPA2-PSK-CCMP][ESS]", -65, NetworkType.WIFI);
    }
}
