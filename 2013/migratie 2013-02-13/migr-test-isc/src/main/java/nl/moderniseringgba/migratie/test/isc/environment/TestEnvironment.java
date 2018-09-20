/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.isc.environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.test.isc.ProcessenTestCasus;
import nl.moderniseringgba.migratie.test.isc.TestBericht;
import nl.moderniseringgba.migratie.test.isc.TestException;
import nl.moderniseringgba.migratie.test.isc.TestNokException;
import nl.moderniseringgba.migratie.test.vergelijk.Vergelijk;

/**
 * Test omgeving (abstract).
 */
public abstract class TestEnvironment {

    /* KANAAL */
    protected static final String KANAAL_BRP = "brp";
    protected static final String KANAAL_MVI = "mvi";
    protected static final String KANAAL_SYNC = "sync";
    protected static final String KANAAL_VOSPG = "vospg";

    protected static final String KANAAL_HAND = "hand";
    protected static final String KANAAL_TRANSITIE = "trans";
    protected static final String KANAAL_DB = "db";

    private static final Logger LOG = LoggerFactory.getLogger();

    private final Map<Integer, String> idCorrelatie = new LinkedHashMap<Integer, String>();

    /**
     * Verwerk een test bericht.
     * 
     * @param bericht
     *            bericht
     * @throws TestException
     *             bij fouten
     */
    public final void verwerkBericht(final TestBericht bericht) throws TestException {
        LOG.debug("verwerkBericht(bericht={})", bericht);
        if (bericht.getUitgaand()) {
            if (KANAAL_HAND.equalsIgnoreCase(bericht.getKanaal())) {
                verwerkHandmatigeActie(bericht);
            } else if (KANAAL_TRANSITIE.equalsIgnoreCase(bericht.getKanaal())) {
                verwerkTransitieActie(bericht);
            } else if (KANAAL_DB.equalsIgnoreCase(bericht.getKanaal())) {
                verwerkDatabaseActie(bericht.getInhoud());
            } else {
                verwerkUitgaandBericht(bericht);
            }
        } else {
            if (KANAAL_HAND.equalsIgnoreCase(bericht.getKanaal())) {
                controleerOpHandmatigeActie(bericht);
            } else {
                verwerkVerwachtBericht(bericht);
            }
        }
    }

    /**
     * LET OP: Voor VOSPG moet gemeente 0599 op GBA staan en 0600 op BRP.
     */
    private void verwerkUitgaandBericht(final TestBericht bericht) {
        LOG.debug("verwerkUitgaandBericht(bericht={})", bericht);
        final Bericht teVerzendenBericht = new Bericht();
        teVerzendenBericht.inhoud = bericht.getInhoud();

        if (bericht.getHerhaalnummer() != null) {
            teVerzendenBericht.messageId = idCorrelatie.get(bericht.getHerhaalnummer());
        } else {
            teVerzendenBericht.messageId = UUID.randomUUID().toString();
        }

        if (KANAAL_VOSPG.equalsIgnoreCase(bericht.getKanaal())) {
            teVerzendenBericht.bronGemeente = bericht.getLo3Gemeente();
            teVerzendenBericht.doelGemeente = bericht.getBrpGemeente();
        }
        idCorrelatie.put(bericht.getVolgnummer(), teVerzendenBericht.messageId);

        if (bericht.getCorrelatienummer() != null) {
            LOG.debug("Uitgaand bericht correleren aan: {}", bericht.getCorrelatienummer());
            teVerzendenBericht.correlatieId = idCorrelatie.get(bericht.getCorrelatienummer());
        }

        // LOG bericht
        FileOutputStream fis = null;
        try {
            bericht.getOutputFile().getParentFile().mkdirs();
            fis = new FileOutputStream(bericht.getOutputFile());
            final PrintWriter writer = new PrintWriter(fis);
            writer.print(teVerzendenBericht.inhoud);
            writer.close();

        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (final IOException e) {
                    LOG.trace("Kan expected output niet sluiten.", e);
                }
            }
        }

        verzendBericht(bericht.getKanaal(), teVerzendenBericht);
    }

    // CHECKSTYLE:OFF - Complexity: splitsen maakt het onoverzichtelijk
    private void verwerkVerwachtBericht(final TestBericht bericht) throws TestException {
        // CHECKSTYLE:ON
        LOG.debug("verwerkVerwachtBericht(bericht={})", bericht);

        // Output bestand als .expected
        final String outputName = bericht.getOutputFile().getName();
        final int extIndex = outputName.lastIndexOf('.');
        final String expectedName = outputName.substring(0, extIndex) + ".expected" + outputName.substring(extIndex);

        FileOutputStream expectedFis = null;
        try {
            bericht.getOutputFile().getParentFile().mkdirs();
            expectedFis = new FileOutputStream(new File(bericht.getOutputFile().getParentFile(), expectedName));
            final PrintWriter writer = new PrintWriter(expectedFis);
            writer.print(bericht.getInhoud());
            writer.close();

        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (expectedFis != null) {
                try {
                    expectedFis.close();
                    // CHECKSTYLE:OFF
                } catch (final IOException e) {
                    // CHECKSTYLE:ON
                    // Ignore, can't do anything about it
                }
            }
        }

        // Correlatie
        String correlatieId;
        if (bericht.getCorrelatienummer() != null) {

            correlatieId = idCorrelatie.get(bericht.getCorrelatienummer());
        } else {
            correlatieId = null;
        }

        final Bericht ontvangenBericht = ontvangBericht(bericht.getKanaal(), correlatieId);
        if (ontvangenBericht == null) {
            throw new TestException("Geen bericht ontvangen");
        }

        if (KANAAL_VOSPG.equalsIgnoreCase(bericht.getKanaal())
                && (!bericht.getBrpGemeente().equalsIgnoreCase(ontvangenBericht.doelGemeente) || !bericht
                        .getLo3Gemeente().equalsIgnoreCase(ontvangenBericht.bronGemeente))) {
            throw new TestException("Adressering is onjuist: Verwacht BRP: " + bericht.getBrpGemeente()
                    + ", ontvangen BRP: " + ontvangenBericht.doelGemeente + "\n" + "Verwacht LO3: "
                    + bericht.getLo3Gemeente() + ", ontvangen LO3: " + ontvangenBericht.bronGemeente);
        }

        LOG.debug("OntvangenBericht.messageId: " + ontvangenBericht.messageId);
        LOG.debug("OntvangenBericht.correlatieId: " + ontvangenBericht.correlatieId);
        LOG.debug("OntvangenBericht.inhoud: " + ontvangenBericht.inhoud);

        idCorrelatie.put(bericht.getVolgnummer(), ontvangenBericht.messageId);

        // LOG output
        FileOutputStream fis = null;
        try {
            bericht.getOutputFile().getParentFile().mkdirs();
            fis = new FileOutputStream(bericht.getOutputFile());
            final PrintWriter writer = new PrintWriter(fis);
            writer.print(ontvangenBericht.inhoud);
            writer.close();

        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (final IOException e) {
                    LOG.trace("Kan output niet sluiten.", e);
                }
            }
        }

        if (KANAAL_SYNC.equalsIgnoreCase(bericht.getKanaal())) {
            // Vergelijk XML (speciale casus voor BRP acties)
            if (!XmlVergelijker.vergelijkXmlMetActies(bericht.getInhoud(), ontvangenBericht.inhoud)) {
                throw new TestNokException("Inhoud niet juist (xml met acties vergelijking)");
            }
        } else if (KANAAL_BRP.equalsIgnoreCase(bericht.getKanaal())) {
            // Vergelijk XML
            if (!XmlVergelijker.vergelijkXml(bericht.getInhoud(), ontvangenBericht.inhoud)) {
                throw new TestNokException("Inhoud niet juist (xml vergelijking)");
            }
        } else {
            // Vergelijk String
            if (!Vergelijk.vergelijk(bericht.getInhoud(), ontvangenBericht.inhoud)) {
                System.out.println("Inhoudelijk incorrect");
                throw new TestNokException("Inhoud niet juist (string vergelijking)");
            }
        }
    }

    /**
     * Start hook.
     */
    public void start() {
        // Hook
    }

    /**
     * Correlatie opschonen.
     */
    public final void clearCorrelatie() {
        idCorrelatie.clear();
    }

    /**
     * Before testcase hook.
     * 
     * @param testCase
     *            testcase
     */
    public void beforeTestCase(final ProcessenTestCasus testCase) {
        // Hook
    }

    /**
     * After testcase hook.
     * 
     * @param testCase
     *            testcase
     * @return false als de testcase als NOK aangemerkt moet worden, anders true
     */
    // CHECKSTYLE:OFF
    public boolean afterTestCase(final ProcessenTestCasus testCase) {
        // CHECKSTYLE:ON
        return true;
    }

    /**
     * Shutdown hook.
     */
    public void shutdown() {
        // Hook
    }

    private void controleerOpHandmatigeActie(final TestBericht bericht) throws TestException {
        final List<Integer> keys = new ArrayList<Integer>(idCorrelatie.keySet());
        controleerOpHandmatigeActie(idCorrelatie.get(keys.get(0)));
    }

    private void verwerkHandmatigeActie(final TestBericht bericht) throws TestException {
        final List<Integer> keys = new ArrayList<Integer>(idCorrelatie.keySet());
        verwerkHandmatigeActie(idCorrelatie.get(keys.get(0)), bericht.getInhoud());
    }

    /**
     * Verwerk handmatige actie (beheerder actie).
     * 
     * @param berichtId
     *            het eerste bericht wat is gerelateerd aan het proces.
     * @param pad
     *            te kiezen pad voor de beheerder actie (restart variabele)
     * @throws TestException
     *             bij fouten
     */
    public abstract void verwerkHandmatigeActie(final String berichtId, final String pad) throws TestException;

    /**
     * Controleer dat het proces in een handmatige actie (beheerder actie) zit.
     * 
     * @param berichtId
     *            het eerste bericht wat is gerelateerd aan het proces.
     * @throws TestException
     *             bij fouten
     */
    public abstract void controleerOpHandmatigeActie(final String berichtId) throws TestException;

    private void verwerkTransitieActie(final TestBericht bericht) throws TestException {
        final String berichtId;

        if (bericht.getCorrelatienummer() == null) {
            final List<Integer> keys = new ArrayList<Integer>(idCorrelatie.keySet());
            berichtId = idCorrelatie.get(keys.get(keys.size() - 1));
        } else {
            berichtId = idCorrelatie.get(bericht.getCorrelatienummer());
        }

        verwerkTransitieActie(berichtId, bericht.getInhoud());
    }

    /**
     * Verwerk een transitie (meestal gebruikt voor timeout simulatie).
     * 
     * @param berichtId
     *            het bericht id wat is ontvangen waarvoor de transitie gedaan moet worden.
     * 
     * @param pad
     *            te kiezen transitie
     * @throws TestException
     *             bij fouten
     */
    public abstract void verwerkTransitieActie(final String berichtId, final String pad) throws TestException;

    /**
     * Verwerk een database actie (meestal gebruikt voor database simulatie).
     * 
     * @param inhoud
     *            database inhoud
     * @throws TestException
     *             bij fouten
     * 
     */
    public abstract void verwerkDatabaseActie(String inhoud) throws TestException;

    /**
     * Verzend een bericht.
     * 
     * @param kanaal
     *            kanaal
     * @param bericht
     *            bericht
     */
    public abstract void verzendBericht(final String kanaal, final Bericht bericht);

    /**
     * Ontvang een gecorreleerd bericht.
     * 
     * @param kanaal
     *            kanaal
     * @param correlatieId
     *            correlatie id
     * @return bericht
     */
    public abstract Bericht ontvangBericht(final String kanaal, final String correlatieId);

    /**
     * Controleer of het proces is beeindigd.
     * 
     * @return false, als het proces is gevonden en niet is beeindigd, anders true
     */
    public final boolean controleerProcesBeeindigd() {
        final List<Integer> keys = new ArrayList<Integer>(idCorrelatie.keySet());
        return controleerProcesBeeindigd(idCorrelatie.get(keys.get(0)));
    }

    /**
     * Controleer of het proces is beeindigd.
     * 
     * @param berichtId
     *            het eerste bericht wat is gerelateerd aan het proces.
     * @return false, als het proces is gevonden en niet is beeindigd, anders true
     */
    public abstract boolean controleerProcesBeeindigd(final String berichtId);

    /**
     * Geef de proces instance van het proces wat is gestart voor de huidige testcasus.
     * 
     * @return proces instance id
     */
    public final Long getProcesInstanceId() {
        final List<Integer> keys = new ArrayList<Integer>(idCorrelatie.keySet());
        return keys.isEmpty() ? null : getProcesInstanceId(idCorrelatie.get(keys.get(0)));
    }

    /**
     * Geef de proces instance van het proces wat is gestart voor de huidige testcasus.
     * 
     * @param berichtId
     *            het eerste bericht wat is gerelateerd aan het proces.
     * @return proces instance id
     */
    public abstract Long getProcesInstanceId(final String berichtId);

    /**
     * Bericht structuur intern in de test environment.
     */
    public static class Bericht {
        // CHECKSTYLE:OFF
        /** Message id. */
        public String messageId;
        /** Correlatie id. */
        public String correlatieId;
        public String bronGemeente;
        public String doelGemeente;
        /** Inhoud. */
        public String inhoud;
        // CHECKSTYLE:ON
    }

}
