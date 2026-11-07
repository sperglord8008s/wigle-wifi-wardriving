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

    private Network wifi(final String bssid) {
        return new Network(bssid, "Same SSID", 2412, "[WPA2-PSK-CCMP][ESS]", -65, NetworkType.WIFI);
    }
}
