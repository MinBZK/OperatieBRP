/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.init.runtime;

import org.junit.Ignore;
import org.junit.Test;

public class MainTest {

    @Test
    public void testMainHappy() {
        final String[] args = new String[] { "-config", "test-config.properties" };
        Main.setSpringConfig("runtime-test-beans.xml");
        Main.main(args);
    }

    @Test
    public void testMainNoConfig() {
        final String[] args = new String[] {};
        Main.setSpringConfig("runtime-test-beans.xml");
        Main.main(args);
    }

    @Test(expected = NullPointerException.class)
    public void testMainWrongConfig() {
        final String[] args = new String[] { "-config", "t-config.properties" };
        Main.setSpringConfig("runtime-test-beans.xml");
        Main.main(args);
    }

    @Ignore("Werkt niet op Jenkins: excel folder kan niet worden gevonden")
    @Test
    public void testMainExcelConfig() {
        // werkt met absolute paden
        final String excelFolder = MainTest.class.getResource("/excel").getFile();
        final String[] args = new String[] { "-excelvulling", excelFolder, "-config", "test-config.properties" };
        Main.setSpringConfig("runtime-test-beans.xml");
        Main.main(args);
    }
}
