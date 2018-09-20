/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;


/**
 * Basis attribuuttype klasse voor datum (volledig) en datum deels onbekend.
 */
public abstract class AbstractDatumBasisAttribuut extends AbstractAttribuut<Integer> implements DatumBasisAttribuut {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String DATUM_FORMAT = "yyyyMMdd";
    // FastDateFormat gebruikt daar SimpleDateFormat niet thread safe is.
    private static final FastDateFormat DATUM_FORMAAT = FastDateFormat.getInstance(DATUM_FORMAT);

    private static final int AANTAL_SECONDEN_PER_MINUUT = 60;
    private static final int AANTAL_MINUTEN_PER_UUR = 60;
    private static final int AANTAL_UREN_PER_DAG = 24;
    private static final int AANTAL_MILLIS_PER_SECONDE = 1000;
    private static final int AANTAL_MILLIS_PER_MINUUT = AANTAL_MILLIS_PER_SECONDE * AANTAL_SECONDEN_PER_MINUUT;
    private static final int AANTAL_MILLIS_PER_UUR = AANTAL_MILLIS_PER_MINUUT * AANTAL_MINUTEN_PER_UUR;
    private static final int AANTAL_MILLIS_PER_DAG = AANTAL_MILLIS_PER_UUR * AANTAL_UREN_PER_DAG;
    private static final int GETAL_100 = 100;
    private static final int MOD_DAG = GETAL_100;
    private static final int MOD_MAAND = GETAL_100;
    private static final int GETAL_10000 = 10000;
    private static final int MOD_JAAR = GETAL_10000;
    private static final int MAX_DATUM = 99999999;
    private static final int MIN_DATUM = 0;
    private static final int MINIMALE_DATUM_VOLLEDIG_ONBEKENDE_DATUM = 10101;
    private static final int MAXIMALE_DATUM = 99991231;
    private static final int MAAND_PLUS_DAG = 101;
    private static final int PLUS_DAG       = 1;
    private static final String DATUM_MOET_AANWEZIG_ZIJN = "Datum moet aanwezig zijn.";
    private static final String SLASH = "/";

    /**
     * De constructor voor een abstract datum basis attribuut met een integer als waarde.
     *
     * @param waarde de datum als integer
     */
    protected AbstractDatumBasisAttribuut(final Integer waarde) {
        super(waarde);
    }

    /**
     * Test of deze datum na de opgegeven datum ligt.
     *
     * @param vergelijkingsDatum de datum waarmee vergeleken wordt.
     * @return of deze datum na de opgegeven datum ligt.
     */
    @Override
    public final boolean na(final DatumBasisAttribuut vergelijkingsDatum) {
        if (vergelijkingsDatum == null || vergelijkingsDatum.getWaarde() == null) {
            throw new IllegalArgumentException(DATUM_MOET_AANWEZIG_ZIJN);
        }
        return getWaarde() > vergelijkingsDatum.getWaarde();
    }

    /**
     * Test of deze datum na of op vergelijkingsDatum ligt.
     *
     * @param vergelijkingsDatum vergelijkingsDatum.
     * @return of deze datum na of op vergelijkingsDatum ligt.
     */
    @Override
    public final boolean naOfOp(final DatumBasisAttribuut vergelijkingsDatum) {
        return na(vergelijkingsDatum) || op(vergelijkingsDatum);
    }

    /**
     * Test of deze datum voor of op vergelijkingsDatum ligt.
     *
     * @param vergelijkingsDatum vergelijkingsDatum.
     * @return of deze datum voor of op vergelijkingsDatum ligt.
     */
    @Override
    public final boolean voorOfOp(final DatumBasisAttribuut vergelijkingsDatum) {
        return voor(vergelijkingsDatum) || op(vergelijkingsDatum);
    }

    /**
     * Test of deze datum voor de opgegeven datum ligt.
     *
     * @param vergelijkingsDatum de datum waarmee vergeleken wordt.
     * @return of deze datum voor de opgegeven datum ligt.
     */
    @Override
    public final boolean voor(final DatumBasisAttribuut vergelijkingsDatum) {
        if (vergelijkingsDatum == null || vergelijkingsDatum.getWaarde() == null) {
            throw new IllegalArgumentException(DATUM_MOET_AANWEZIG_ZIJN);
        }
        return getWaarde() < vergelijkingsDatum.getWaarde();
    }

    /**
     * Test of deze datum dezelfde datum is als de opgegeven datum.
     *
     * @param vergelijkingsDatum de datum
     * @return true of false
     */
    @Override
    public final boolean op(final DatumBasisAttribuut vergelijkingsDatum) {
        if (vergelijkingsDatum == null || vergelijkingsDatum.getWaarde() == null) {
            throw new IllegalArgumentException(DATUM_MOET_AANWEZIG_ZIJN);
        }
        return getWaarde().equals(vergelijkingsDatum.getWaarde());
    }

    /**
     * Geeft het aantal dagen verschil aan tussen 2 datums. Dit aantal is altijd een positief (niet-negatief) getal!
     *
     * @param vergelijkingsDatum de datum
     * @return het verschil in dagen
     */
    public final int aantalDagenVerschil(final DatumBasisAttribuut vergelijkingsDatum) {
        if (vergelijkingsDatum == null || vergelijkingsDatum.getWaarde() == null) {
            throw new IllegalArgumentException(DATUM_MOET_AANWEZIG_ZIJN);
        }
        final long dezeMillis = toDate().getTime();
        final long andereMillis = vergelijkingsDatum.toDate().getTime();
        final long millisVerschil = Math.abs(dezeMillis - andereMillis);
        // Voor de zekerheid netjes afronden ivm schrikkelseconden en dergelijke grappen.
        return (int) Math.round(((double) millisVerschil) / AANTAL_MILLIS_PER_DAG);
    }

    @Override
    public final String toString() {
        if (getWaarde() == null) {
            return "";
        } else {
            return String.format("%04d-%02d-%02d", getJaar(), getMaand(), getDag());
        }
    }

    @Override
    public final boolean isVolledigDatumWaarde() {
        boolean retval = true;
        final Integer datumWaarde = getWaarde();
        if (null != datumWaarde) {
            if (datumWaarde == 0) {
                retval = false;
            } else {
                final int dag = datumWaarde % GETAL_100;
                final int maand = datumWaarde / GETAL_100 % GETAL_100;
                final int jaar = datumWaarde / GETAL_10000;
                retval = dag > 0 && maand > 0 && jaar > 0;
            }
        }
        return retval;
    }

    /**
     * Test of de opgegeven DatumAttribuut een geldige kalenderDatumAttribuut is.
     *
     * @return true als het een geldige kalenderdatum.
     */
    @Override
    public final boolean isGeldigeKalenderdatum() {
        boolean retval = false;

        if (getWaarde() != null && isVolledigDatumWaarde()) {
            final int waarde = getWaarde();
            final int dag = waarde % MOD_DAG;
            final int maand = waarde / MOD_DAG % MOD_MAAND;
            final int jaar = waarde / MOD_JAAR;

            final DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            format.setLenient(false);

            try {
                format.parse(jaar + SLASH + maand + SLASH + dag);
                retval = true;
            } catch (final ParseException e) {
                LOGGER.error("Fout opgetreden bij parsen van datum: " + this, e);
            }
        }

        return retval;
    }

    @Override
    public final boolean isGeldigTussen(final DatumBasisAttribuut datumAanvangPeriode, final DatumBasisAttribuut datumEindePeriode) {
        final boolean geldig;

        final int aanvangPeriode;
        if (datumAanvangPeriode != null) {
            aanvangPeriode = datumAanvangPeriode.getIntWaardeOfMin();
        } else {
            aanvangPeriode = MIN_DATUM;
        }

        final int eindePeriode;
        if (datumEindePeriode != null) {
            eindePeriode = datumEindePeriode.getIntWaardeOfMax();
        } else {
            eindePeriode = MAX_DATUM;
        }

        final int peil = getWaarde();
        geldig = peil >= aanvangPeriode && peil < eindePeriode;

        return geldig;
    }

    @Override
    public final boolean voorOfOpDatumSoepel(final DatumBasisAttribuut vergelijkDatum) {
        return testVoorDatumSoepel(vergelijkDatum, true);
    }

    @Override
    public final boolean voorDatumSoepel(final DatumBasisAttribuut vergelijkDatum) {
        return testVoorDatumSoepel(vergelijkDatum, false);
    }

    /**
     * Vergelijk of datum ligt op of voor vergelijkDatum. Beide datums kunnen deels onbekend zijn en er wordt soepel mee gerekend
     * <p/>
     * Let op dat geen null pointers zijn.
     *
     * @param vergelijkDatum      vergelijkDatum.
     * @param isOpOokGoed dit maakt kleiner of gelijk.
     * @return als datum1 voor of op datum2 ligt.
     */
    private boolean testVoorDatumSoepel(final DatumBasisAttribuut vergelijkDatum, final boolean isOpOokGoed) {
        final boolean resultaat;

        if (vergelijkDatum == null || getWaarde() == 0
            || vergelijkDatum.getWaarde() == 0 || getJaar() == 0
            || vergelijkDatum.getJaar() == 0
            || getJaar() < vergelijkDatum.getJaar())
        {
            resultaat = true;
        } else {
            // we hebben in ieder geval een volledig jaar
            if (getJaar() > vergelijkDatum.getJaar()) {
                resultaat = false;
            } else if (getMaand() == 0 || vergelijkDatum.getMaand() == 0 || getMaand() < vergelijkDatum.getMaand()) {
                // jaar is hetzelfde, nu naar de maand
                resultaat = true;
            } else if (getMaand() > vergelijkDatum.getMaand()) {
                resultaat = false;
            } else if (getDag() == 0 || vergelijkDatum.getDag() == 0 || getDag() < vergelijkDatum.getDag()) {
                // maand is ook hetzelfde, nu de dag
                resultaat = true;
            } else {
                // vergelijking van de dag
                if (isOpOokGoed) {
                    resultaat = getDag() <= vergelijkDatum.getDag();
                } else {
                    resultaat = getDag() < vergelijkDatum.getDag();
                }

            }
        }

        return resultaat;
    }

    @Override
    public final int getIntWaarde(final int defaultWaarde) {
        if (getWaarde() == null) {
            return defaultWaarde;
        } else {
            return getWaarde();
        }
    }

    @Override
    public final int getIntWaardeOfMin() {
        return getIntWaarde(MIN_DATUM);
    }

    @Override
    public final int getIntWaardeOfMax() {
        return getIntWaarde(MAX_DATUM);
    }

    @Override
    public final int getJaar() {
        if (getWaarde() == null) {
            return 0;
        } else {
            return getWaarde() / MOD_JAAR;
        }
    }

    @Override
    public final int getMaand() {
        if (getWaarde() == null) {
            return 0;
        } else {
            return getWaarde() / MOD_DAG % MOD_MAAND;
        }
    }

    @Override
    public final int getDag() {
        if (getWaarde() == null) {
            return 0;
        } else {
            return getWaarde() % MOD_DAG;
        }
    }

    @Override
    public final Date toDate() {
        if (getWaarde() != null) {
            final int datumWaarde = getWaarde();
            try {
                return DateUtils.parseDateStrictly(Integer.toString(datumWaarde), new String[]{DATUM_FORMAT});
            } catch (final ParseException e) {
                throw new IllegalArgumentException(String.format("Kan datum met waarde: '%1$d' niet converteren naar Date.", datumWaarde), e);
            }
        }
        return null;
    }

    /**
     * Bepaal de minimale DatumEvtDeelsOnbekendAttribuut bij een deels onbekende datum. Merk op: geen test op null pointer.
     *
     * @return de minimale geldige datum.
     */
    public final DatumEvtDeelsOnbekendAttribuut getMinimaalDeelOnbekendDatum() {
        int waarde = getWaarde();
        if (waarde == 0) {
            // Geheel onbekend, wat is dan het minumum?
            waarde = MINIMALE_DATUM_VOLLEDIG_ONBEKENDE_DATUM;
        } else if (waarde % MOD_JAAR == 0) {
            // maand onbeked, voeg een maand + 1 dag
            waarde += MAAND_PLUS_DAG;
        } else if (waarde % MOD_MAAND == 0) {
            waarde += PLUS_DAG;
        }
        return new DatumEvtDeelsOnbekendAttribuut(waarde);
    }

    /**
     * Bepaal de maximale DatumAttribuut bij een deels onbende datum. Merk op: geen test op null pointer.
     *
     * @return de maximale geldige datum.
     */
    public final DatumEvtDeelsOnbekendAttribuut getMaximaalDeelOnbekendDatum() {
        final int waarde = getWaarde();
        final DatumEvtDeelsOnbekendAttribuut maximaalDeelOnbekendDatum;

        if (waarde == 0) {
            // jaar is helemaal onbekend
            maximaalDeelOnbekendDatum = new DatumEvtDeelsOnbekendAttribuut(MAXIMALE_DATUM);
        } else if (waarde % MOD_JAAR == 0) {
            // maand onbekend, voeg + 1 jaar - 1 dag
            maximaalDeelOnbekendDatum = new DatumEvtDeelsOnbekendAttribuut(waarde + MAAND_PLUS_DAG);
            maximaalDeelOnbekendDatum.voegJaarToe(1);
            maximaalDeelOnbekendDatum.voegDagToe(-1);
        } else if (waarde % MOD_MAAND == 0) {
            // dag onbekend, voeg + 1 maand - 1 dag
            maximaalDeelOnbekendDatum = new DatumEvtDeelsOnbekendAttribuut(waarde + PLUS_DAG);
            maximaalDeelOnbekendDatum.voegMaandToe(1);
            maximaalDeelOnbekendDatum.voegDagToe(-1);
        } else {
            // Datum is niet deels onbekend, retourneer de oorspronkelijke datum
            maximaalDeelOnbekendDatum = new DatumEvtDeelsOnbekendAttribuut(waarde);
        }
        return maximaalDeelOnbekendDatum;
    }

    /**
     * Voeg n-iets toe (kan ook negatief zijn) aan een bepaald datum; moet wel een volledig DatumAttribuut zijn.
     *
     * @param calendarType moet zijn Calendar.YEAR, MONTH, DATE zijn.
     * @param aantal       hoeveelheid.
     */
    public final void voegIetsToe(final int calendarType, final Integer aantal) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(toDate());
        cal.add(calendarType, aantal);
        final String datumString = DATUM_FORMAAT.format(cal.getTime());
        setWaarde(Integer.parseInt(datumString));
    }

    /**
     * Voeg een n-dagen toe (kan ook negatief zijn) aan een bepaald datum; moet wel een volledig DatumAttribuut zijn.
     *
     * @param aantalDagen aantal dagen.
     */
    public final void voegDagToe(final Integer aantalDagen) {
        voegIetsToe(Calendar.DATE, aantalDagen);
    }

    /**
     * Voeg een n-maanden toe (kan ook negatief zijn) aan een bepaald datum; moet wel een volledig DatumAttribuut zijn.
     *
     * @param aantalMaanden aantal maanden.
     */
    public final void voegMaandToe(final Integer aantalMaanden) {
        voegIetsToe(Calendar.MONTH, aantalMaanden);
    }

    /**
     * Voeg een n-jaren toe (kan ook negatief zijn) aan een bepaald datum; moet wel een volledig DatumAttribuut zijn.
     *
     * @param aantalJaren aantal jaren.
     */
    public final void voegJaarToe(final Integer aantalJaren) {
        voegIetsToe(Calendar.YEAR, aantalJaren);
    }
}
