/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test;

import java.io.File;
import java.util.List;

/**
 * Test casus factory: basis.
 */
public abstract class TestCasusFactory {

    private String thema;
    private File outputFolder;
    private File expectedFolder;

    public final String getThema() {
        return thema;
    }

    public final void setThema(final String thema) {
        this.thema = thema;
    }

    public final File getOutputFolder() {
        return outputFolder;
    }

    public final void setOutputFolder(final File outputFolder) {
        this.outputFolder = outputFolder;
    }

    public final File getExpectedFolder() {
        return expectedFolder;
    }

    public final void setExpectedFolder(final File expectedFolder) {
        this.expectedFolder = expectedFolder;
    }

    /**
     * Lees test cassusen uit input directory.
     * 
     * @param input
     *            input directory
     * @return lijst van test cassusen
     * @throws Exception
     *             bij fouten
     */
    public abstract List<TestCasus> leesTestCasussen(final File input) throws Exception;

    /**
     * Maak de naam voor een test casus.
     * 
     * @param name
     *            basis naam
     * @param i
     *            index
     * @return naam
     */
    protected final String maakNaam(final String name, final int i) {
        if (i == 0) {
            return name;
        }
        return name.substring(0, name.indexOf(".")) + "-" + i;
    }
}
