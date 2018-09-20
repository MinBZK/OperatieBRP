/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.misc;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.testclient.TestClient;
import nl.bzk.brp.testclient.misc.Bericht.BERICHT;
import nl.bzk.brp.testclient.misc.Bericht.STATUS;
import nl.bzk.brp.testclient.misc.Bericht.TYPE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De Class StatistiekenImpl.
 */
public class StatistiekenImpl implements Statistieken {

    /** De Constante LOG. */
    private static final Logger                 LOG                                =
                                                                                       LoggerFactory
                                                                                               .getLogger(StatistiekenImpl.class);

    /** De Constante EXPORT. */
    private static final Logger                 EXPORT                             = LoggerFactory.getLogger("Export");

    /** De Constante formatter. */
    private static final NumberFormat           FORMATTER                          = new DecimalFormat("###,##0.000");

    private static final Long                   LOG_INTERVAL                       = 10L * 1000;

    /** De start tijd. */
    private Long                                startTijd;

    /** De duur totaal. */
    private Long                                duurTotaal;

    /** De duur per bericht type. */
    private final Map<Bericht.TYPE, Long>       duurPerBerichtType                 = new HashMap<Bericht.TYPE, Long>();

    /** De duur per bericht. */
    private final Map<Bericht.BERICHT, Long>    duurPerBericht                     =
                                                                                       new HashMap<Bericht.BERICHT, Long>();

    /** De aantal verzonden. */
    private Integer                             aantalVerzonden;

    /** De aantal verzonden per bericht type. */
    private final Map<Bericht.TYPE, Integer>    aantalVerzondenPerBerichtType      =
                                                                                       new HashMap<Bericht.TYPE, Integer>();

    /** De aantal verzonden per bericht. */
    private final Map<Bericht.BERICHT, Integer> aantalVerzondenPerBericht          =
                                                                                       new HashMap<Bericht.BERICHT, Integer>();

    /** De aantal verzonden vorig. */
    private Integer                             aantalVerzondenVorig;

    private final Map<Bericht.BERICHT, Integer> aantalVerzondenVorigPerBericht     =
                                                                                       new HashMap<Bericht.BERICHT, Integer>();

    private final Map<Bericht.TYPE, Integer>    aantalVerzondenVorigPerBerichtType =
                                                                                       new HashMap<Bericht.TYPE, Integer>();

    /** De aantal verzonden flow. */
    private Integer                             aantalVerzondenFlow;

    /** De aantal succes. */
    private Integer                             aantalSucces;

    /** De aantal succes per bericht type. */
    private final Map<Bericht.TYPE, Integer>    aantalSuccesPerBerichtType         =
                                                                                       new HashMap<Bericht.TYPE, Integer>();

    /** De aantal succes per bericht. */
    private final Map<Bericht.BERICHT, Integer> aantalSuccesPerBericht             =
                                                                                       new HashMap<Bericht.BERICHT, Integer>();

    /** De aantal mislukt. */
    private Integer                             aantalMislukt;

    /** De aantal mislukt per bericht type. */
    private final Map<Bericht.TYPE, Integer>    aantalMisluktPerBerichtType        =
                                                                                       new HashMap<Bericht.TYPE, Integer>();

    /** De aantal mislukt per bericht. */
    private final Map<Bericht.BERICHT, Integer> aantalMisluktPerBericht            =
                                                                                       new HashMap<Bericht.BERICHT, Integer>();

    /** De laatst bijgewerkt. */
    private Long                                laatstBijgewerkt                   = System.currentTimeMillis();

    /**
     * Instantieert een nieuwe statistieken impl.
     */
    public StatistiekenImpl() {
        reset();

        // Vul export excel met headers
        List<String> labels = createExportLabels();
        EXPORT.info(format(labels, "", ";"));
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.Statistieken#reset()
     */
    @Override
    public void reset() {
        startTijd = System.currentTimeMillis();
        laatstBijgewerkt = System.currentTimeMillis();

        duurTotaal = 0L;
        aantalVerzonden = 0;
        aantalVerzondenVorig = 0;
        aantalVerzondenFlow = 0;
        aantalSucces = 0;
        aantalMislukt = 0;

        for (Bericht.TYPE type : Bericht.TYPE.values()) {
            duurPerBerichtType.put(type, 0L);
            aantalVerzondenPerBerichtType.put(type, 0);
            aantalVerzondenVorigPerBerichtType.put(type, 0);
            aantalSuccesPerBerichtType.put(type, 0);
            aantalMisluktPerBerichtType.put(type, 0);
        }

        for (Bericht.BERICHT bericht : Bericht.BERICHT.values()) {
            duurPerBericht.put(bericht, 0L);
            aantalVerzondenPerBericht.put(bericht, 0);
            aantalVerzondenVorigPerBericht.put(bericht, 0);
            aantalSuccesPerBericht.put(bericht, 0);
            aantalMisluktPerBericht.put(bericht, 0);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.Statistieken#bijwerkenNaVerzondenBericht(nl.bzk.brp.testclient.Bericht.BERICHT,
     * nl.bzk.brp.testclient.Bericht.STATUS, java.lang.Long)
     */
    @Override
    public synchronized void bijwerkenNaBericht(final BERICHT bericht, final STATUS status, final Long duur) {
        aantalVerzonden++;
        aantalVerzondenPerBerichtType.put(bericht.getType(), aantalVerzondenPerBerichtType.get(bericht.getType()) + 1);
        aantalVerzondenPerBericht.put(bericht, aantalVerzondenPerBericht.get(bericht) + 1);

        if (status.equals(STATUS.GOED)) {
            aantalSucces++;
            aantalSuccesPerBerichtType.put(bericht.getType(), aantalSuccesPerBerichtType.get(bericht.getType()) + 1);
            aantalSuccesPerBericht.put(bericht, aantalSuccesPerBericht.get(bericht) + 1);
        } else if (status.equals(STATUS.FOUT)) {
            aantalMislukt++;
            aantalMisluktPerBerichtType.put(bericht.getType(), aantalMisluktPerBerichtType.get(bericht.getType()) + 1);
            aantalMisluktPerBericht.put(bericht, aantalMisluktPerBericht.get(bericht) + 1);
        }

        duurTotaal += duur;

        Long duurBerichtType = duurPerBerichtType.get(bericht.getType());
        duurBerichtType += duur;
        duurPerBerichtType.put(bericht.getType(), duurBerichtType);

        Long duurBericht = duurPerBericht.get(bericht);
        duurBericht += duur;
        duurPerBericht.put(bericht, duurBericht);

        // Stats loggen per log interval
        if (System.currentTimeMillis() - laatstBijgewerkt > LOG_INTERVAL) {
            logStats();
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.Statistieken#bijwerkenNaBerichtenFlow()
     */
    @Override
    public synchronized void bijwerkenNaBerichtenFlow() {
        aantalVerzondenFlow++;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.brp.testclient.misc.Statistieken#isKlaar(int)
     */
    @Override
    public int getAantalVerzondenFlow() {
        return aantalVerzondenFlow;
    }

    /**
     * Log stats.
     */
    private void logStats() {
        String totaalSeconden = FORMATTER.format(Float.valueOf(getDuurTest()) / 1000);

        LOG.info("Statistieken na " + totaalSeconden + " seconden (gem. ber/sec: " + perSeconde(getDuur()) + " - "
            + aantalVerzonden + " berichten - " + aantalVerzondenFlow + " flows - " + aantalMislukt + " mislukt)");
        LOG.info("Snelheid laatste " + (LOG_INTERVAL/1000) + " seconden: totaal: " + FORMATTER.format(getHuidigeBerichtenPerSec())
            + " ber/sec, bevraging: " + FORMATTER.format(getHuidigeBerichtenPerSecPerBerichtType(TYPE.BEVRAGING))
            + " ber/sec, bijhouding: "
            + FORMATTER.format(getHuidigeBerichtenPerSecPerBerichtType(TYPE.BIJHOUDING)) + " ber/sec");
        LOG.info("Gemiddelde duur bevraging: " + getDuurSuccesBerichtType(TYPE.BEVRAGING) + " ("
            + aantalVerzondenPerBerichtType.get(TYPE.BEVRAGING) + " berichten), bijhouding: "
            + getDuurSuccesBerichtType(TYPE.BIJHOUDING) + " (" + aantalVerzondenPerBerichtType.get(TYPE.BIJHOUDING)
            + " berichten)");
        LOG.info("Flow " + TestClient.statistieken.getAantalVerzondenFlow() + " van "
            + TestClient.eigenschappen.getFlowCount());

        for (BERICHT bericht : BERICHT.values()) {
            if (duurPerBericht.get(bericht) > 0) {
                StringBuilder berichtenPerSec = new StringBuilder("");
                berichtenPerSec.append(bericht.getNaam() + bericht.getWitruimte());
                berichtenPerSec.append("gemiddelde duur (sec): " + getDuurSuccesBericht(bericht) + "\t");
                berichtenPerSec.append("(" + aantalSuccesPerBericht.get(bericht) + " succes,");
                berichtenPerSec.append(" " + aantalMisluktPerBericht.get(bericht) + " mislukt)");
                LOG.info(berichtenPerSec.toString());
            }
        }
        LOG.info("-----------------------------------------------------------------------------------------------------");

        // Vul export excel met waarden
        List<String> exportValues = createExportValues();
        EXPORT.info(format(exportValues, "", ";"));

        aantalVerzondenVorig = aantalVerzonden;

        for (Bericht.TYPE type : Bericht.TYPE.values()) {
            aantalVerzondenVorigPerBerichtType.put(type, aantalVerzondenPerBerichtType.get(type));
        }

        for (Bericht.BERICHT bericht : Bericht.BERICHT.values()) {
            aantalVerzondenVorigPerBericht.put(bericht, aantalVerzondenPerBericht.get(bericht));
        }

        laatstBijgewerkt = System.currentTimeMillis();
    }

    /**
     * Creeert de export labels.
     *
     * @return de list
     */
    private List<String> createExportLabels() {
        List<String> labels =
            new ArrayList<String>(Arrays.asList(new String[] { "Threads", "CPU's", "Totaal berichten", "TOK's",
                "TNOK's", "ms/TOK", "TOK/sec", "Ber/sec (" + (LOG_INTERVAL/1000) + " secs)","Bevr/sec (" + (LOG_INTERVAL/1000) + " secs)","Bijh/sec (" + (LOG_INTERVAL/1000) + " secs)", "flows" }));

        for (Bericht.TYPE type : Bericht.TYPE.values()) {
            labels.add("Mean " + type.toString() + "/sec");
        }
        for (Bericht.BERICHT bericht : Bericht.BERICHT.values()) {
            labels.add("Mean " + bericht.toString() + "/sec");
        }
        for (Bericht.TYPE type : Bericht.TYPE.values()) {
            labels.add("Totaal " + type.toString());
            labels.add("Succes " + type.toString());
            labels.add("Failed " + type.toString());
        }
        for (Bericht.BERICHT bericht : Bericht.BERICHT.values()) {
            labels.add("Totaal " + bericht.toString());
            labels.add("Succes " + bericht.toString());
            labels.add("Failed " + bericht.toString());
        }
        return labels;
    }

    /**
     * Creeert de export values.
     *
     * @return de list
     */
    private List<String> createExportValues() {
        List<String> exportValues =
            new ArrayList<String>(Arrays.asList(new String[] { "" + TestClient.aantalThreads,
                "" + Runtime.getRuntime().availableProcessors(), "" + aantalVerzonden, "" + aantalSucces,
                "" + aantalMislukt, "" + getDuurSucces(), "" + perSeconde(getDuurSucces()),
                "" + FORMATTER.format(getHuidigeBerichtenPerSec()), "" + FORMATTER.format(getHuidigeBerichtenPerSecPerBerichtType(TYPE.BEVRAGING)), "" + FORMATTER.format(getHuidigeBerichtenPerSecPerBerichtType(TYPE.BIJHOUDING)), "" + aantalVerzondenFlow }));

        for (Bericht.TYPE type : Bericht.TYPE.values()) {
            exportValues.add(getDuurSuccesBerichtType(type));
        }
        for (Bericht.BERICHT bericht : Bericht.BERICHT.values()) {
            exportValues.add(getDuurSuccesBericht(bericht));
        }
        for (Bericht.TYPE type : Bericht.TYPE.values()) {
            exportValues.add("" + aantalVerzondenPerBerichtType.get(type));
            exportValues.add("" + aantalSuccesPerBerichtType.get(type));
            exportValues.add("" + aantalMisluktPerBerichtType.get(type));
        }
        for (Bericht.BERICHT bericht : Bericht.BERICHT.values()) {
            exportValues.add("" + aantalVerzondenPerBericht.get(bericht));
            exportValues.add("" + aantalSuccesPerBericht.get(bericht));
            exportValues.add("" + aantalMisluktPerBericht.get(bericht));
        }
        return exportValues;
    }

    /**
     * Format.
     *
     * @param values de values
     * @param postfix de postfix
     * @param separator de separator
     * @return de string
     */
    private static String format(final List<String> values, final String postfix, final String separator) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            if (i != 0) {
                result.append(separator);
            }
            if (values.get(i) != null) {
                result.append(values.get(i));
            }
            result.append(postfix);
        }
        return result.toString();
    }

    /**
     * Per seconde.
     *
     * @param value de value
     * @return de string
     */
    private String perSeconde(final long value) {
        return FORMATTER.format(1000F / value);
    }

    /**
     * Haalt een duur test op.
     *
     * @return duur test
     */
    private Long getDuurTest() {
        return System.currentTimeMillis() - startTijd;
    }

    /**
     * Haalt een duur op.
     *
     * @return duur
     */
    private Long getDuur() {
        return deelVeilig(getDuurTest(), aantalVerzonden);
    }

    /**
     * Haalt een duur succes op.
     *
     * @return duur succes
     */
    private Long getDuurSucces() {
        return deelVeilig(getDuurTest(), aantalSucces);
    }

    /**
     * Haalt een duur mislukt op.
     *
     * @return duur mislukt
     */
    @SuppressWarnings("unused")
    private Long getDuurMislukt() {
        return deelVeilig(getDuurTest(), aantalMislukt);
    }

    /**
     * Haalt een duur flow op.
     *
     * @return duur flow
     */
    @SuppressWarnings("unused")
    private Long getDuurFlow() {
        return deelVeilig(getDuurTest(), aantalVerzondenFlow);
    }

    /**
     * Deel veilig.
     *
     * @param teller de teller
     * @param noemer de noemer
     * @return de long
     */
    private long deelVeilig(final long teller, final int noemer) {
        long resultaat;
        if (noemer == 0) {
            resultaat = -1;
        } else {
            resultaat = teller / noemer;
        }
        return resultaat;
    }

    /**
     * Haalt een duur succes bericht op.
     *
     * @param bericht de bericht
     * @return duur succes bericht
     */
    private String getDuurSuccesBericht(final BERICHT bericht) {
        String duurSucces = "";
        if (aantalSuccesPerBericht.get(bericht) > 0) {
            duurSucces =
                FORMATTER.format(((duurPerBericht.get(bericht)) / aantalVerzondenPerBericht.get(bericht)) / 1000F);
        }
        return duurSucces;
    }

    /**
     * Haalt een duur succes bericht type op.
     *
     * @param type de type
     * @return duur succes bericht type
     */
    private String getDuurSuccesBerichtType(final TYPE type) {
        String duurSucces = "";
        if (aantalSuccesPerBerichtType.get(type) > 0) {
            duurSucces =
                FORMATTER.format(((duurPerBerichtType.get(type)) / aantalVerzondenPerBerichtType.get(type)) / 1000F);
        }
        return duurSucces;
    }

    /**
     * Haalt een huidige berichten per sec op.
     *
     * @return huidige berichten per sec
     */
    private Float getHuidigeBerichtenPerSec() {
        int verzondenInTussentijd = aantalVerzonden - aantalVerzondenVorig;
        return getAantalPerSecondeSindsVorig(verzondenInTussentijd);
    }

    /**
     * Haalt een huidige berichten per sec per bericht type op.
     *
     * @param type de type
     * @return huidige berichten per sec per bericht type
     */
    private Float getHuidigeBerichtenPerSecPerBerichtType(final TYPE type) {
        int verzondenInTussentijd =
            aantalVerzondenPerBerichtType.get(type) - aantalVerzondenVorigPerBerichtType.get(type);
        return getAantalPerSecondeSindsVorig(verzondenInTussentijd);
    }

    /**
     * Haalt een huidige berichten per sec per bericht op.
     *
     * @param bericht de bericht
     * @return huidige berichten per sec per bericht
     */
    @SuppressWarnings("unused")
    private Float getHuidigeBerichtenPerSecPerBericht(final BERICHT bericht) {
        int verzondenInTussentijd =
            aantalVerzondenPerBericht.get(bericht) - aantalVerzondenVorigPerBericht.get(bericht);
        return getAantalPerSecondeSindsVorig(verzondenInTussentijd);
    }

    /**
     * Haalt een aantal per seconde sinds vorig op.
     *
     * @param aantalInTussentijd de aantal in tussentijd
     * @return aantal per seconde sinds vorig
     */
    private Float getAantalPerSecondeSindsVorig(final int aantalInTussentijd) {
        Long tijdNu = System.currentTimeMillis();
        Long tijdSindsVorig = tijdNu - laatstBijgewerkt;

        Float tijdPerBericht = (float) tijdSindsVorig / ((float) aantalInTussentijd * 1000);
        Float berichtenPerSeconde = 1 / tijdPerBericht;
        return berichtenPerSeconde;
    }
}
