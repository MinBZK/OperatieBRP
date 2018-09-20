/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.omgeving;

import java.util.Locale;

/**
 * Representing the operating system of the local machine
 *
 */
public class OperatingSystem {

    /**
     * types of Operating Systems
     */
    public enum OSType {
        Windows, MacOS, Linux, Other
    };

    // cached result of OS detection
    protected OSType detectedOS;

    public OperatingSystem() {
        String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {
            detectedOS = OSType.MacOS;
        } else if (OS.indexOf("win") >= 0) {
            detectedOS = OSType.Windows;
        } else if (OS.indexOf("nux") >= 0) {
            detectedOS = OSType.Linux;
        } else {
            detectedOS = OSType.Other;
        }
    }

    public OSType getOperatingSystemType() {
        return detectedOS;
    }
}
