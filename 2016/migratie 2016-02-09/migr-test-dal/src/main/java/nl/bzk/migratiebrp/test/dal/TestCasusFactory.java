/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.dal;

import java.io.File;
import java.util.List;

/**
 * Test casus factory: interface.
 */
public interface TestCasusFactory {

    /**
     * Geeft het thema (subfolder) terug.
     * 
     * @return het thema
     */
    String getThema();

    /**
     * Zet het thema (subfolder).
     * 
     * @param thema
     *            het thema waaruit getest wordt
     */
    void setThema(String thema);

    /**
     * Geeft de folder terug waar de output komt.
     * 
     * @return de output folder
     */
    File getOutputFolder();

    /**
     * Zet de output folder.
     * 
     * @param outputFolder
     *            de folder waar de output heen moet
     */
    void setOutputFolder(File outputFolder);

    /**
     * Geeft de folder terug waar de verwachte resultaten staan.
     * 
     * @return de folder waar de verwachte resultaten staan
     */
    File getExpectedFolder();

    /**
     * Zet de folder waar de verwachte resultaten staan.
     * 
     * @param expectedFolder
     *            de folder met verwachte resultaten
     */
    void setExpectedFolder(File expectedFolder);

    /**
     * Zet de folder waar het thema staat.
     * 
     * @param themaFolder
     *            de folder van het thema
     */
    void setThemaFolder(File themaFolder);

    /**
     * Geeft de folder van het thema terug.
     * 
     * @return de folder van het thema
     */
    File getThemaFolder();

    /**
     * Lees test cassusen uit input directory.
     *
     * @param input
     *            input directory
     * @return lijst van test cassusen
     * @throws Exception
     *             bij fouten
     */
    List<TestCasus> leesTestCasussen(final File input) throws Exception;
}
