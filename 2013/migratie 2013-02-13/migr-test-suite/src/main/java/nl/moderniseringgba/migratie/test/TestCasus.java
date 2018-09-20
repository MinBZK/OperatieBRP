/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test;

import java.io.File;
import java.io.FileOutputStream;

import nl.moderniseringgba.isc.esb.message.lo3.impl.Lg01Bericht;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.proces.logging.Logging;
import nl.moderniseringgba.migratie.test.output.TestOutput;
import nl.moderniseringgba.migratie.test.resultaat.TestResultaat;

/**
 * Test casus: basis.
 */
public abstract class TestCasus {

    private static final String FOLDER_XML = "xml-";
    private static final String FOLDER_HTML = "html-";
    private static final String FOLDER_LG01 = "lg01-";

    private static final String EXTENSIE_XML = ".xml";
    private static final String EXTENSIE_HTML = ".html";
    private static final String EXTENSIE_LG01 = ".txt";

    private final String thema;
    private final String naam;
    private final File outputFolder;
    private final File expectedFolder;

    /**
     * Test casus.
     * 
     * @param thema
     *            thema
     * @param naam
     *            naam
     * @param outputFolder
     *            output folder
     * @param expectedFolder
     *            verwachting folder
     */
    protected TestCasus(final String thema, final String naam, final File outputFolder, final File expectedFolder) {
        this.thema = thema;
        this.naam = naam;
        this.outputFolder = outputFolder;
        this.expectedFolder = expectedFolder;
    }

    public final String getThema() {
        return thema;
    }

    public final String getNaam() {
        return naam;
    }

    public final File getOutputFolder() {
        return outputFolder;
    }

    public final File getExpectedFolder() {
        return expectedFolder;
    }

    /**
     * Voer een test casus uit.
     * 
     * @return test resultaat
     */
    public abstract TestResultaat run();

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Output HTML en XML.
     * 
     * @param object
     *            object om te outputten
     * @param stap
     *            stap
     * @return filenaam van de HTML
     */
    protected final String debugOutputXmlEnHtml(final Object object, final String stap) {
        return debugOutputXmlEnHtml(object, stap, null);
    }

    /**
     * Output HTML en XML.
     * 
     * @param object
     *            object om te outputten
     * @param stap
     *            stap
     * @param optie
     *            optioneel suffix voor de bestandsnaam
     * @return filenaam van de HTML
     */
    protected final String debugOutputXmlEnHtml(final Object object, final String stap, final String optie) {
        final File xmlFile =
                new File(outputFolder, FOLDER_XML + stap + File.separator + naam
                        + (optie == null ? "" : ("-" + optie)) + EXTENSIE_XML);
        final String htmlFilename = FOLDER_HTML + stap + File.separator + naam + EXTENSIE_HTML;
        final File htmlFile = new File(outputFolder, htmlFilename);

        TestOutput.outputXmlEnHtml(object, xmlFile, htmlFile);

        return htmlFilename;
    }

    /**
     * Output LG01.
     * 
     * @param lo3
     *            lo3 persoonlijst
     * @param stap
     *            stap
     */
    protected final void debugOutputLg01(final Lo3Persoonslijst lo3, final String stap) {
        final Lg01Bericht bericht = new Lg01Bericht();
        bericht.setLo3Persoonslijst(lo3);

        try {
            final File lg01File = new File(outputFolder, FOLDER_LG01 + stap + File.separator + naam + EXTENSIE_LG01);
            lg01File.getParentFile().mkdirs();

            final FileOutputStream fos = new FileOutputStream(lg01File);
            fos.write(bericht.format().getBytes());
            fos.close();
            // CHECKSTYLE:OFF
        } catch (final Exception e) {
            // CHECKSTYLE:ON
            // Silently ignore
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Lees verwachte lo3 persoonslijst.
     * 
     * @param stap
     *            stap
     * @return lo3 persoonslijst
     */
    protected final Lo3Persoonslijst leesVerwachteLo3Persoonslijst(final String stap) {
        final File expectedXmlFile =
                new File(expectedFolder, FOLDER_XML + stap + File.separator + naam + EXTENSIE_XML);
        return TestOutput.readXml(Lo3Persoonslijst.class, expectedXmlFile);
    }

    /**
     * Lees verwachte brp persoonslijst.
     * 
     * @param stap
     *            stap
     * @return brp persoonslijst
     */
    protected final BrpPersoonslijst leesVerwachteBrpPersoonslijst(final String stap) {
        final File expectedXmlFile =
                new File(expectedFolder, FOLDER_XML + stap + File.separator + naam + EXTENSIE_XML);
        return TestOutput.readXml(BrpPersoonslijst.class, expectedXmlFile);
    }

    /**
     * Lees verwachte logging.
     * 
     * @param stap
     *            stap
     * @return logging
     */
    protected final Logging leesVerwachteLogging(final String stap) {
        final File expectedXmlFile =
                new File(expectedFolder, FOLDER_XML + stap + File.separator + naam + EXTENSIE_XML);
        return TestOutput.readXml(Logging.class, expectedXmlFile);
    }
}
