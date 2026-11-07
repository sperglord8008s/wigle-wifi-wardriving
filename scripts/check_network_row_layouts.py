#!/usr/bin/env python3
"""Catch missing views in qualified row.xml layouts before shipping an APK.

An Android 16 crash exposed a missing cell-detail TextView in layout-v29/row.xml;
the default layout had the view, so compilation and JVM tests both passed.
"""

from pathlib import Path
import re
import sys
import xml.etree.ElementTree as ET


ROOT = Path(__file__).resolve().parents[1]
MODULE = ROOT / "wiglewifiwardriving"
ADAPTER = MODULE / "src/main/java/net/wigle/wigleandroid/ui/SetNetworkListAdapter.java"
RES = MODULE / "src/main/res"
ANDROID_ID = "{http://schemas.android.com/apk/res/android}id"


def main() -> int:
    referenced_ids = set(
        re.findall(r"row\.findViewById\(R\.id\.([A-Za-z_0-9]+)\)",
                   ADAPTER.read_text(encoding="utf-8"))
    )
    layouts = sorted(RES.glob("layout*/row.xml"))
    if not referenced_ids or not layouts:
        print("ERROR: missing adapter view references or row layouts", file=sys.stderr)
        return 1

    failed = False
    for layout in layouts:
        present_ids = {
            value.rsplit("/", 1)[-1]
            for element in ET.parse(layout).iter()
            if (value := element.get(ANDROID_ID)) and value.startswith("@+id/")
        }
        missing = referenced_ids - present_ids
        if missing:
            failed = True
            print(f"ERROR: {layout.relative_to(ROOT)} lacks: {', '.join(sorted(missing))}",
                  file=sys.stderr)
        else:
            print(f"OK: {layout.relative_to(ROOT)} ({len(referenced_ids)} required views)")
    return 1 if failed else 0


if __name__ == "__main__":
    raise SystemExit(main())
