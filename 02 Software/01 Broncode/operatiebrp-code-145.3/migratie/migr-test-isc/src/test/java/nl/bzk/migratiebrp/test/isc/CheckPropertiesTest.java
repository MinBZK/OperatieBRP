/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class CheckPropertiesTest {

    @Test
    public void test() throws IOException {
        checkDirectory(new File("./cit-failover"));
        checkDirectory(new File("./docker"));
        checkDirectory(new File("./integratie"));
        checkDirectory(new File("./integratie-iv"));
        checkDirectory(new File("./integratie-proefsync"));
        checkDirectory(new File("./regressie-brp"));
        checkDirectory(new File("./regressie-isc"));
        checkDirectory(new File("./regressie-sync"));
        checkDirectory(new File("./regressie-sync-init"));
        checkDirectory(new File("./regressie-voisc"));
        checkDirectory(new File("./test"));
        checkDirectory(new File("./volume"));
    }

    private void checkDirectory(final File directory) throws IOException {
        // System.out.println("echo " + directory.getCanonicalPath());
        for (final File subdirectory : directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return pathname.isDirectory();
            }
        })) {
            checkDirectory(subdirectory);
        }

        for (final File propertiesBestand : directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return pathname.isFile() && pathname.getName().endsWith(".properties");
            }
        })) {
            if (propertiesBestand.getName().equals("testcase.properties")) {
                continue;
            }

            final String naam = propertiesBestand.getName();
            final File testBestand = new File(propertiesBestand.getParent(), naam.substring(0, naam.length() - ".properties".length()));

            if (!testBestand.isFile()) {
                System.out.println("del " + propertiesBestand.getCanonicalPath());
            }
        }
    }
}
