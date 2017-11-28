/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

/**
 * Dit element wordt gebruikt voor alle BMR attributen die datums bevatten. Deze mogen ook deels onbekend zijn.
 * Definitie als volgt:
 *
 * <ul>
 * <li>datum: <code>[0-9]{4}(-(0[1-9]{1}|1[0-2]{1})(-(0[1-9]{1}|[1-2]{1}[0-9]{1}|3[0-1]{1})))</code></li>
 * <li>datum deels onbekend: <code>[0-9]{4}(-(0[1-9]{1}|1[0-2]{1})(-(0[1-9]{1}|[1-2]{1}[0-9]{1}|3[0-1]{1}))?)?</code>
 * </li>
 * </ul>
 */
public final class DatumElement extends AbstractBmrAttribuut<Integer> {
    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Pattern PATTERN_DATUM_MET_ONZEKERHEID =
            Pattern.compile("([0-9]{4})(-(0[1-9]|1[0-2])(-(0[1-9]{1}|[1-2][0-9]|3[0-1]))?)?");
    private static final int JAAR_GROEP = 1;
    private static final int MAAND_GROEP = 3;
    private static final int DAG_GROEP = 5;
    private static final String DATUM_ONBEKEND_DEEL = "00";
    private static final String FOUTMELDING = "De waarde '%s' is geen geldig DatumElement.";
    private static final String SCHEIDINGSTEKEN = "-";
    private static final String DATUM_ONBEKEND_JAAR = "0000";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;

    private final String jaar;
    private final String maand;
    private final String dag;
    private final boolean isGeldigeKalenderDatum;

    /**
     * Maakt een nieuw DatumElement object op basis van een {@link Integer}. Let op: Jaartallen onder het jaar 1000 kunnen niet worden meegegeven met deze
     * constructor. Gebruik hier voor de methode {@link #parseWaarde(String)}. Indien de datum geheel onbekend is, kan 0 worden meegegeven. Datum opbouw is
     * jjjjmmdd.
     * @param waarde de datum waarde, mag niet null zijn
     */
    public DatumElement(final Integer waarde) {
        super(waarde);
        final String datumString = getWaarde().toString();
        final int lengteJaar = 4;
        final int lengteJaarMaand = 6;
        final int lengteJaarMaandDag = 8;

        if (waarde == 0) {
            jaar = DATUM_ONBEKEND_JAAR;
            maand = DATUM_ONBEKEND_DEEL;
            dag = DATUM_ONBEKEND_DEEL;
        } else if (datumString.length() == lengteJaar) {
            jaar = datumString;
            maand = DATUM_ONBEKEND_DEEL;
            dag = DATUM_ONBEKEND_DEEL;
        } else if (datumString.length() == lengteJaarMaand) {
            jaar = datumString.substring(0, lengteJaar);
            maand = datumString.substring(lengteJaar, lengteJaarMaand);
            dag = DATUM_ONBEKEND_DEEL;
        } else {
            jaar = datumString.substring(0, lengteJaar);
            maand = datumString.substring(lengteJaar, lengteJaarMaand);
            dag = datumString.substring(lengteJaarMaand, lengteJaarMaandDag);
        }
        isGeldigeKalenderDatum = bepaalGeldigeKalenderDatum();
    }

    private DatumElement(final String jaar, final String maand, final String dag) {
        super(Integer.parseInt(jaar + maand + dag));
        this.jaar = jaar;
        this.maand = maand;
        this.dag = dag;
        isGeldigeKalenderDatum = bepaalGeldigeKalenderDatum();
    }

    /**
     * Bepaalt of deze datum een volledig bekende datum is.
     * @return true als deze datum voldoet aan Regel R2547
     */
    @Bedrijfsregel(Regel.R2547)
    boolean isVolledigBekendeDatum() {
        return !DATUM_ONBEKEND_DEEL.equals(maand) && !DATUM_ONBEKEND_DEEL.equals(dag);
    }

    /**
     * Maakt een DatumElement obv de gegeven string.
     * @param waarde de waarde, mag niet null zijn
     * @return DatumElement met daarin een datum met mogelijke onzekerheid
     * @throws OngeldigeWaardeException wanneer de gegeven string niet voldoet aan het gestelde formaat voor een datum met onzekerheid
     */
    public static DatumElement parseWaarde(final String waarde) throws OngeldigeWaardeException {
        Objects.requireNonNull(waarde, "waarde voor een datum mag niet null zijn");
        final Matcher datumMatcher = PATTERN_DATUM_MET_ONZEKERHEID.matcher(waarde);
        final String jaar;
        final String maand;
        final String dag;

        if (datumMatcher.matches()) {
            jaar = datumMatcher.group(JAAR_GROEP);
            if (datumMatcher.group(MAAND_GROEP) != null) {
                maand = datumMatcher.group(MAAND_GROEP);
            } else {
                maand = DATUM_ONBEKEND_DEEL;
            }
            if (datumMatcher.group(DAG_GROEP) != null) {
                dag = datumMatcher.group(DAG_GROEP);
            } else {
                dag = DATUM_ONBEKEND_DEEL;
            }
        } else {
            throw new OngeldigeWaardeException(String.format(FOUTMELDING, waarde));
        }
        return new DatumElement(jaar, maand, dag);
    }

    @Bedrijfsregel(Regel.R1274)
    @Override
    public List<MeldingElement> valideer(final BmrGroep groep) {
        final List<MeldingElement> meldingen = new ArrayList<>();
        if (isVolledigBekendeDatum() && !isGeldigeKalenderDatum()) {
            meldingen.add(MeldingElement.getInstance(Regel.R1274, groep));
        }
        return meldingen;
    }

    /**
     * Geeft aan of de waarde van dit {@link DatumElement} een geldige kalender datum is.
     * @return true indien de waarde een geldige kalender datum is.
     */
    public boolean isGeldigeKalenderDatum() {
        return isGeldigeKalenderDatum;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder().append(jaar);
        if (!DATUM_ONBEKEND_DEEL.equals(maand)) {
            result.append(SCHEIDINGSTEKEN).append(maand);
            if (!DATUM_ONBEKEND_DEEL.equals(dag)) {
                result.append(SCHEIDINGSTEKEN).append(dag);
            }
        }
        return result.toString();
    }

    private boolean bepaalGeldigeKalenderDatum() {
        try {
            LocalDate.from(DATE_FORMATTER.parse(String.valueOf(getWaarde())));
            return true;
        } catch (DateTimeException e) {
            LOG.trace("Datum voldoet niet aan de Gregoriaanse kalender", e);
            return false;
        }
    }
}
