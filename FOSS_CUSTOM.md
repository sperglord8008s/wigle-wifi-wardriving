# WiGLE WiFi — FOSS custom build

This branch extends WiGLE's `foss-main` Android variant. It does not require
Google Play services for its map, and it preserves the upstream application ID,
`net.wigle.wigleandroid`.

## Features

- **BSSID sorting:** conventional MAC sort, plus *MAC suffix (related AP
  radios)*. The latter compares trailing bytes first to bring some multiple-radio
  access points together even when the leading octets differ. It is a heuristic,
  **not** positive identification of a shared physical access point.
- **Wi-Fi range estimate:** optional RSSI-based metres alongside frequency,
  with configurable 1-metre calibration and path-loss exponent. Indoor walls,
  antenna orientation, transmit power and multipath can make the estimate very
  inaccurate. It is not a measurement of true distance.
- **Cell-site list fix and details:** Wi-Fi scans no longer erase entries from
  the independent cellular receiver. A short hold avoids flicker when Android
  temporarily supplies an empty cell snapshot. Cell rows display available
  MCC/MNC, LAC/TAC, CID/NCI, radio channel, PCI/PSC/BSIC and serving status.
- **FOSS map:** the FOSS build uses MapLibre and a keyless OpenFreeMap style.
- **Custom upload destination:** optionally send exported WiGLE-format CSV to
  a user-controlled HTTPS endpoint instead of the official WiGLE uploader.

## Custom endpoint contract

Leave the custom URL empty to keep the standard WiGLE upload behaviour.
Otherwise, enter a trusted **HTTPS** URL in Settings. A manual custom upload
asks for confirmation of the destination.

The app sends `multipart/form-data` with one CSV attachment in the field named
`file`. Any HTTP 2xx response acknowledges receipt; the endpoint does **not**
need to return WiGLE's JSON response format. No WiGLE API token or credentials
are sent to the custom host, and redirects are not followed. A separate
upload cursor keeps custom and official upload progress independent. Changing
the custom destination starts a new backlog at the new endpoint.

The CSV contains network observations and potentially precise location data.
Only configure an endpoint you control or trust. The app does not provide a
custom-server implementation or an additional authentication mechanism.

## Installation and updates

The branch produces a debug-labelled APK with the **original package ID**. It
cannot replace a Play Store or F-Droid WiGLE installation in place because those
installations have different signing certificates. Export the database first,
then uninstall the differently signed installation once. Settings are not
automatically migrated.

Subsequent APKs from this branch use the same privately stored signing key in
GitHub Actions and can update the earlier custom build without uninstalling it.
The signing key and password are stored as private Actions secrets; neither is
in the public source tree.

## Build and verification

The `foss-custom` GitHub Actions workflow runs a static check of every
qualified `row.xml` against the adapter's required views, JVM unit tests, and
`assembleDebug -PwigleFossMain=true`. That row check prevents recurrence of a
crash on Android 10+ caused by a missing view in `layout-v29/row.xml`.

Compilation and automated tests cannot replace testing on a real handset.
The current APK is a test build, not an upstream WiGLE release. Submit
focused upstream fixes separately, following WiGLE's contribution rules.
