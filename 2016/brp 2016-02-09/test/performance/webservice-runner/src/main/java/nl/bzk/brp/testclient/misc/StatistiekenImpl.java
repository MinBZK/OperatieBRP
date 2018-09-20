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
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * De Class StatistiekenImpl.
 */
public class StatistiekenImpl implements Statistieken
{
    /** De Constante LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(StatistiekenImpl.class);
    
    /** De Constante EXPORT. */
    private static final Logger EXPORT = LoggerFactory.getLogger("Export");
    
    /** De Constante formatter. */
    private static final NumberFormat FORMATTER = new DecimalFormat("###,##0.000");

    private final Map<Bericht.BERICHT, TotalenDto> totallenPerBericht = new HashMap<Bericht.BERICHT, TotalenDto>();
    private final Map<Bericht.TYPE, TotalenDto> totallenPerType = new HashMap<Bericht.TYPE, TotalenDto>();
    
    /** De start tijd. */
    private volatile long startTijd;
    
    /** De laatst bijgewerkt. */
    private volatile long laatstBijgewerkt = System.currentTimeMillis();
    
    /** De aantal verzonden flow. */
    private volatile Integer aantalVerzondenFlow;
    
    private TotalenDto totaalAlles = new TotalenDto();
    private TotalenDto totaalRonde = new TotalenDto();

    public static final Statistieken INSTANCE = new StatistiekenImpl();

    /**
     * Instantieert een nieuwe statistieken impl.
     */
    private StatistiekenImpl()
    {
        reset();

        // Vul export excel met headers
        List<String> labels = createExportLabels();
        EXPORT.info(format(labels, "", ";"));
    }

    /**
     * Format.
     * 
     * @param values
     *            de values
     * @param postfix
     *            de postfix
     * @param separator
     *            de separator
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

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.brp.testclient.Statistieken#reset()
     */
    @Override
    public void reset() {
        startTijd = System.currentTimeMillis();
        laatstBijgewerkt = System.currentTimeMillis();
        aantalVerzondenFlow = 0;

        totaalAlles = new TotalenDto();
        totaalRonde = new TotalenDto();
        for (Bericht.TYPE type : Bericht.TYPE.values()) {
            totallenPerType.put(type, new TotalenDto());
        }
        for (Bericht.BERICHT bericht : Bericht.BERICHT.values()) {
            totallenPerBericht.put(bericht, new TotalenDto());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nl.bzk.brp.testclient.Statistieken#bijwerkenNaVerzondenBericht(nl.bzk
     * .brp.testclient.Bericht.BERICHT, nl.bzk.brp.testclient.Bericht.STATUS,
     * java.lang.Long)
     */
    @Override
    public void bijwerkenNaBericht(final DuurDto duurDto) {

        synchronized (this) {

            final Bericht.STATUS status = duurDto.getStatus();
            final Bericht.BERICHT bericht = duurDto.getBericht();
            long duur = duurDto.getDuurQuery();

            // --- bijhouden van de totallen nu.
            // per type (bevraging/bijhouding)
            TotalenDto totalenType = totallenPerType.get(bericht.getType());
            TotalenDto totalenBericht = totallenPerBericht.get(bericht);
            if (STATUS.FOUT.equals(status)) {
                totalenType.setAantalFout(totalenType.getAantalFout() + 1);
                totalenBericht.setAantalFout(totalenBericht.getAantalFout() + 1);
                totaalAlles.setAantalFout(totaalAlles.getAantalFout() + 1);
                totaalRonde.setAantalFout(totaalRonde.getAantalFout() + 1);
                totalenType.setTotaalDuurFout(totalenType.getTotaalDuurFout() + duur);
                totalenBericht.setTotaalDuurFout(totalenBericht.getTotaalDuurFout() + duur);
                totaalAlles.setTotaalDuurFout(totaalAlles.getTotaalDuurFout() + duur);
                totaalRonde.setTotaalDuurFout(totaalRonde.getTotaalDuurFout() + duur);
            }
            if (STATUS.GOED.equals(status)) {
                totalenType.setAantalGoed(totalenType.getAantalGoed() + 1);
                totalenBericht.setAantalGoed(totalenBericht.getAantalGoed() + 1);
                totaalAlles.setAantalGoed(totaalAlles.getAantalGoed() + 1);
                totaalRonde.setAantalGoed(totaalRonde.getAantalGoed() + 1);
                totalenType.setTotaalDuurGoed(totalenType.getTotaalDuurGoed() + duur);
                totalenBericht.setTotaalDuurGoed(totalenBericht.getTotaalDuurGoed() + duur);
                totaalAlles.setTotaalDuurGoed(totaalAlles.getTotaalDuurGoed() + duur);
                totaalRonde.setTotaalDuurGoed(totaalRonde.getTotaalDuurGoed() + duur);
            }
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
    @Override
    public void logStats() {

        synchronized (this) {

            int threadCount = TestClient.eigenschappen.getAantalThreads();
            long rondeTijd = System.currentTimeMillis() - laatstBijgewerkt;
            long totNuToeTijd = System.currentTimeMillis() - startTijd;
            // String totaalSeconden =
            // FORMATTER.format(Float.valueOf(getDuurTest())
            // / 1000);
            String rondeSeconden = FORMATTER.format(Float.valueOf(rondeTijd) / Constanten.DUIZEND);
            String totNuToeSeconden = FORMATTER.format(Float.valueOf(totNuToeTijd) / Constanten.DUIZEND);

            LOG.info("Flow #" + getAantalVerzondenFlow() + " van totaal aantal flows "
                    + TestClient.eigenschappen.getFlowCount() + ", threadCount=" + threadCount);

            LOG.info("Ronde  tijd " + rondeSeconden + " sec. " + " aantal verzonden "
                    + (totaalRonde.getAantalGoed() + totaalRonde.getAantalFout()) + " berichten = "
                    + getAantalPerSeconde(rondeTijd, totaalRonde.getAantalGoed() + totaalRonde.getAantalFout(), 1)
                    + " / sec incl. overhead en fouten.");
            LOG.info("Totaal tijd " + totNuToeSeconden + " sec. " + " aantal verzonden "
                    + (totaalAlles.getAantalGoed() + totaalAlles.getAantalFout()) + " berichten = "
                    + getAantalPerSeconde(totNuToeTijd, totaalAlles.getAantalGoed() + totaalAlles.getAantalFout(), 1)
                    + " / sec incl. overhead en fouten.");
            LOG.info("-------------------------------------------------------------------------------------------");
            printBerekenOverzichtPerRequest("Alle berichten\t",
                    sumTotallen(totallenPerType.get(TYPE.BEVRAGING), totallenPerType.get(TYPE.BIJHOUDING)), threadCount);
            LOG.info("                ---------------------------------------------------------------------------");

            printBerekenOverzichtPerRequest("Bevraging \t\t", totallenPerType.get(TYPE.BEVRAGING), threadCount);
            printBerekenOverzichtPerRequest("Bijhouding \t\t", totallenPerType.get(TYPE.BIJHOUDING), threadCount);
            LOG.info("                ---------------------------------------------------------------------------");

            for (BERICHT bericht : BERICHT.values()) {
                printBerekenOverzichtPerRequest(bericht.getNaam() + bericht.getWitruimte(),
                        totallenPerBericht.get(bericht), threadCount);
            }

            LOG.info("-------------------------------------------------------------------------------------------");
            // Vul export excel met waarden
            List<String> exportValues = createExportValues(rondeTijd, totNuToeTijd);
            EXPORT.info(format(exportValues, "", ";"));

            // update /reset waarden voor de volgende ronde.
            totaalRonde = new TotalenDto();
            laatstBijgewerkt = System.currentTimeMillis();
        }
    }
    /**
     * Creeert de export labels.
     * 
     * @return de list
     */
    private List<String> createExportLabels() {
        List<String> labels = new ArrayList<String>(Arrays.asList(new String[]{"Threads", "CPU's", "Totaal berichten",
                "TOK's", "TNOK's", "ms/TOK", "TOK/sec", "Ber/sec", "Bevr/sec", "Bijh/sec", "flows"}));

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
     * exporteer statistieken waarden naar de excel sheet. Dit gebeurt per
     * ronde.
     * 
     * @param rondeTijd
     *            de ronde tijd
     * @param totNuToeTijd
     *            de totale doorloop tijd
     * @return een list van strings met waarden
     */
    private List<String> createExportValues(final long rondeTijd, final long totNuToeTijd) {

        int threadCount = TestClient.eigenschappen.getAantalThreads();

        List<String> exportValues = new ArrayList<String>(Arrays.asList(new String[]{
                "" + threadCount,
                "" + Runtime.getRuntime().availableProcessors(),
                "" + (totaalAlles.getAantalGoed() + totaalAlles.getAantalFout()),
                "" + totaalAlles.getAantalGoed(),
                "" + totaalAlles.getAantalFout(),
                "" + deelVeilig(totNuToeTijd, totaalAlles.getAantalGoed()),
                "" + getAantalPerSeconde(totNuToeTijd, totaalAlles.getAantalGoed() + totaalAlles.getAantalFout(), 1),
                "" + getAantalPerSeconde(rondeTijd, totaalRonde.getAantalGoed() + totaalRonde.getAantalFout(), 1),
                ""
                        + getAantalPerSeconde(totNuToeTijd, totallenPerType.get(TYPE.BEVRAGING).getAantalGoed()
                                + totallenPerType.get(TYPE.BEVRAGING).getAantalFout(), 1),
                ""
                        + getAantalPerSeconde(totNuToeTijd, totallenPerType.get(TYPE.BIJHOUDING).getAantalGoed()
                                + totallenPerType.get(TYPE.BIJHOUDING).getAantalFout(), 1), "" + aantalVerzondenFlow}));

        for (Bericht.TYPE type : Bericht.TYPE.values()) {
            exportValues.add(getGemiddeldeDuur(totallenPerType.get(type).getTotaalDuurGoed(), totallenPerType.get(type)
                    .getAantalGoed()));
        }
        for (Bericht.BERICHT bericht : Bericht.BERICHT.values()) {
            exportValues.add(getGemiddeldeDuur(totallenPerBericht.get(bericht).getTotaalDuurGoed(), totallenPerBericht
                    .get(bericht).getAantalGoed()));
        }
        for (Bericht.TYPE type : Bericht.TYPE.values()) {
            exportValues.add(""
                    + (totallenPerType.get(type).getAantalGoed() + totallenPerType.get(type).getAantalFout()));
            exportValues.add("" + totallenPerType.get(type).getAantalGoed());
            exportValues.add("" + totallenPerType.get(type).getAantalFout());
        }
        for (Bericht.BERICHT bericht : Bericht.BERICHT.values()) {
            exportValues.add(""
                    + (totallenPerBericht.get(bericht).getAantalGoed() + totallenPerBericht.get(bericht)
                            .getAantalFout()));
            exportValues.add("" + totallenPerBericht.get(bericht).getAantalGoed());
            exportValues.add("" + totallenPerBericht.get(bericht).getAantalFout());
        }
        return exportValues;
    }

    /**
     * Format 4 digit.
     * 
     * @param value
     *            value
     * @return string
     */
    private String format4Digit(final int value) {
        return StringUtils.leftPad("" + value, Constanten.VIER, ' ');
    }

    /**
     * Print bereken overzicht per request.
     * 
     * @param prefix
     *            prefix
     * @param totallen
     *            totallen
     * @param threadCountParam
     *            thread count
     */
    private void printBerekenOverzichtPerRequest(final String prefix, final TotalenDto totallen,
            final Integer threadCountParam)
    {
        if (null != totallen && (totallen.getAantalFout() + totallen.getAantalGoed() > 0)) {
            StringBuilder sb = new StringBuilder(prefix);
            sb.append("gemiddeld: ").append(getGemiddeldeDuur(totallen.getTotaalDuurGoed(), totallen.getAantalGoed()))
                    .append(" s ").append("(").append(format4Digit(totallen.getAantalGoed())).append(" succes,")
                    .append(" ").append(format4Digit(totallen.getAantalFout())).append(" mislukt)");
            if (threadCountParam != null) {
                sb.append(" theoretisch ")
                        .append(getAantalPerSeconde(totallen.getTotaalDuurGoed(), totallen.getAantalGoed(),
                                threadCountParam)).append(" ber/s");
            }
            LOG.info(sb.toString());
        }
    }

    /**
     * Bereken de gemiddel duur van een actie in sec (0,xxx).
     * 
     * @param duur
     *            de totale duur in ms.
     * @param aantal
     *            het aantal acties.
     * @return geformatteerde string (0,xxx)
     */
    private String getGemiddeldeDuur(final long duur, final long aantal) {
        String waarde = "";
        if (aantal > 0) {
            waarde = FORMATTER.format((1.0 * duur) / aantal / Constanten.DUIZEND);
        }
        return waarde;
    }

    /**
     * Bereke hoeveel actie per seconde is, rekening houden met aantal thread (
     * * n-threads).
     * 
     * @param duur
     *            duur in ms.
     * @param aantal
     *            aantal acties
     * @param threadCountParam
     *            aantal treads.
     * @return aantal acties per seconde (yyy,xxx)
     */
    private String getAantalPerSeconde(final long duur, final long aantal, final Integer threadCountParam) {
        String waarde = "0";
        if (aantal > 0) {
            Double perSec = (1.0 * threadCountParam) / ((1.0 * duur) / aantal / Constanten.DUIZEND);
            waarde = FORMATTER.format(perSec);
        }
        return waarde;
    }

    /**
     * Tel totallen bij elkaar op en geef een nieuwe totalen record.
     * 
     * @param dtoLijst
     *            lijst van totallen object.
     * @return de som van al deze totallen.
     */
    private TotalenDto sumTotallen(final TotalenDto... dtoLijst) {
        TotalenDto totaal = new TotalenDto();
        for (TotalenDto d : dtoLijst) {
            totaal.setAantalFout(totaal.getAantalFout() + d.getAantalFout());
            totaal.setAantalGoed(totaal.getAantalGoed() + d.getAantalGoed());
            totaal.setTotaalDuurFout(totaal.getTotaalDuurFout() + d.getTotaalDuurFout());
            totaal.setTotaalDuurGoed(totaal.getTotaalDuurGoed() + d.getTotaalDuurGoed());
        }
        return totaal;
    }

    /**
     * Deel veilig.
     * 
     * @param teller
     *            de teller
     * @param noemer
     *            de noemer
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

}
