package net.wigle.wigleandroid.util;

import net.wigle.wigleandroid.model.Network;
import net.wigle.wigleandroid.model.NetworkType;

/** Readable cellular identifiers for live rows and previously stored observations. */
public final class CellDisplayUtil {
    private CellDisplayUtil() {}

    public static boolean isCell(final NetworkType type) {
        return NetworkType.GSM.equals(type) || NetworkType.WCDMA.equals(type)
                || NetworkType.LTE.equals(type) || NetworkType.NR.equals(type)
                || NetworkType.CDMA.equals(type);
    }

    public static String describe(final Network network) {
        if (network == null || !isCell(network.getType())) return "";

        final StringBuilder out = new StringBuilder(96);
        if (network.getCellRadioInfo() != null && !network.getCellRadioInfo().isEmpty()) {
            out.append(network.getCellRadioInfo()).append(" · ");
        }

        final NetworkType type = network.getType();
        final String[] fields = network.getBssid().split("_", 3);
        if (fields.length == 3) {
            if (NetworkType.CDMA.equals(type)) {
                out.append("SID ").append(fields[0]).append(" · NID ").append(fields[1])
                        .append(" · BID ").append(fields[2]);
            } else if (fields[0].length() >= 5) {
                out.append("MCC ").append(fields[0], 0, 3).append(" MNC ")
                        .append(fields[0].substring(3)).append(" · ")
                        .append(NetworkType.LTE.equals(type) || NetworkType.NR.equals(type) ? "TAC " : "LAC ")
                        .append(fields[1]).append(" · ")
                        .append(NetworkType.NR.equals(type) ? "NCI " : "CID ")
                        .append(fields[2]);
            } else {
                out.append(network.getBssid());
            }
        } else {
            out.append(network.getBssid());
        }

        final int channel = network.getFrequency();
        if (channel > 0 && channel != Integer.MAX_VALUE) {
            final String label;
            if (NetworkType.LTE.equals(type)) label = "EARFCN";
            else if (NetworkType.NR.equals(type)) label = "NRARFCN";
            else if (NetworkType.WCDMA.equals(type)) label = "UARFCN";
            else if (NetworkType.GSM.equals(type)) label = "ARFCN";
            else label = "Channel";
            out.append(" · ").append(label).append(' ').append(channel);
        }
        return out.toString();
    }
}
