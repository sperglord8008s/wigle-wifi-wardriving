package net.wigle.wigleandroid.util;

import net.wigle.wigleandroid.model.Network;
import net.wigle.wigleandroid.model.NetworkType;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CellDisplayUtilTest {
    @Test
    public void nrSummaryShowsAllAvailableLiveIdentifiers() {
        Network network = new Network("50503_4501_123456789", "Telstra", 642144,
                "NR;50503", -90, NetworkType.NR);
        network.setCellRadioInfo("Serving · PCI 184");
        assertEquals("Serving · PCI 184 · MCC 505 MNC 03 · TAC 4501 · NCI 123456789 · NRARFCN 642144",
                CellDisplayUtil.describe(network));
    }

    @Test
    public void historicalLteSummaryDoesNotInventMissingPci() {
        Network network = new Network("50501_4321_123456", "Carrier", 1500,
                "LTE;50501", -70, NetworkType.LTE);
        assertEquals("MCC 505 MNC 01 · TAC 4321 · CID 123456 · EARFCN 1500",
                CellDisplayUtil.describe(network));
    }

    @Test
    public void wifiIsNotACell() {
        Network wifi = new Network("11:22:33:44:55:66", "AP", 2412, "[WPA2]", -60, NetworkType.WIFI);
        assertFalse(CellDisplayUtil.isCell(wifi.getType()));
        assertEquals("", CellDisplayUtil.describe(wifi));
        assertTrue(CellDisplayUtil.isCell(NetworkType.GSM));
    }
}
