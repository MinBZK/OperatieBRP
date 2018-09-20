/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.time.FastDateFormat;


/**
 * Attribuut wrapper klasse voor Datum \ Tijd.
 */
@Embeddable
public class DatumTijdAttribuut extends AbstractDatumTijdAttribuut {

    private static final int    PRIME_BASE   = 131;
    private static final int    PRIME_ADD    = 37;
    private static final String DATUM_FORMAT = "yyyyMMdd";

    private static final int[] CALENDAR_FIELDS         = { Calendar.YEAR, Calendar.MONTH, Calendar.DATE, Calendar.HOUR,
        Calendar.MINUTE, Calendar.SECOND, };
    private static final int   STANDAARD_TIJD_UREN     = 12;
    private static final int   MAX_AANTAL_DATUM_VELDEN = 6;

    /**
     * Retourneert nu.
     *
     * @return DatumTijd nu
     */
    public static DatumTijdAttribuut nu() {
        return new DatumTijdAttribuut(Calendar.getInstance().getTime());
    }

    /**
     * Retourneert een datum en tijd die 24 uur (een dag) later is.
     *
     * @return een datum entijd die 24 uur (een dag) later is.
     */
    public static DatumTijdAttribuut over24Uur() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        return new DatumTijdAttribuut(cal.getTime());
    }

    /**
     * Retourneert een datum en tijd die 24 uur (een dag) eerder is.
     *
     * @return een datum entijd die 24 uur (een dag) eerder is.
     */
    public static DatumTijdAttribuut terug24Uur() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return new DatumTijdAttribuut(cal.getTime());
    }

    /**
     * Bouw een {@link nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut} instantie met de geleverde velden.
     *
     * @param jaar    jaar
     * @param maand   maandnummer, 1 based (dus januari = 1)
     * @param dag     dag
     * @param uur     uur
     * @param minuut  minuut
     * @param seconde seconde
     * @return een datumTijd instantie
     */
    public static DatumTijdAttribuut bouwDatumTijd(final int jaar, final int maand, final int dag, final int uur,
        final int minuut, final int seconde)
    {
        return datumTijd(jaar, maand, dag, uur, minuut, seconde);
    }

    /**
     * Bouw een {@link nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut} instantie met de geleverde velden.
     *
     * @param jaar  jaar
     * @param maand maand
     * @param dag   dag
     * @return DatumTijd instantie op de dag om 12 uur.
     */
    public static DatumTijdAttribuut bouwDatumTijd(final int jaar, final int maand, final int dag) {
        return datumTijd(jaar, maand, dag, STANDAARD_TIJD_UREN, 0, 0);
    }

    /**
     * Bouw een {@link nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut} instantie op basis van de geleverde velden. De volgorde is: jaar,
     * maand (1 based, dus januari = 1), dag, uur, minuut, seconde
     *
     * @param velden de velden die een datumTijd vormen
     * @return een datumTijd instantie
     */
    public static DatumTijdAttribuut datumTijd(final int... velden) {
        if (velden.length > MAX_AANTAL_DATUM_VELDEN) {
            throw new IllegalArgumentException("Te veel velden meegegeven.");
        }

        final Calendar calendar = Calendar.getInstance();
        calendar.clear();

        for (int i = 0; i < velden.length; i++) {
            int waarde = velden[i];
            if (i == 1) {
                waarde -= 1;
            }
            calendar.set(CALENDAR_FIELDS[i], waarde);
        }

        return new DatumTijdAttribuut(calendar.getTime());
    }

    /**
     * Standaard constructor.
     */
    public DatumTijdAttribuut() {
        super();
    }

    /**
     * Constructor voor DatumTijdAttribuut die de waarde toekent.
     *
     * @param waarde De waarde van het attribuut.
     */
    @JsonCreator
    public DatumTijdAttribuut(final Date waarde) {
        super(waarde);
    }

    /**
     * Test of deze datum na de opgegeven datum ligt.
     *
     * @param vergelijkingsDatum de datum waarmee vergeleken wordt.
     * @return of deze datum na de opgegeven datum ligt.
     * @see #getWaarde()
     */
    public final boolean na(final DatumTijdAttribuut vergelijkingsDatum) {
        if (vergelijkingsDatum == null || vergelijkingsDatum.getWaarde() == null) {
            throw new IllegalArgumentException("DatumTijd moet aanwezig zijn.");
        }
        return getWaarde().after(vergelijkingsDatum.getWaarde());
    }

    /**
     * Test of deze datum voor de opgegeven datum ligt.
     *
     * @param vergelijkingsDatum de datum waarmee vergeleken wordt.
     * @return of deze datum voor de opgegeven datum ligt.
     * @see #getWaarde()
     */
    public final boolean voor(final DatumTijdAttribuut vergelijkingsDatum) {
        if (vergelijkingsDatum == null || vergelijkingsDatum.getWaarde() == null) {
            throw new IllegalArgumentException("DatumTijd moet aanwezig zijn.");
        }
        return getWaarde().before(vergelijkingsDatum.getWaarde());
    }

    /**
     * Zet dit datum tijd attribuut om naar een datum attribuut. Dat wil zeggen dat het tijd-aspect wordt genegeerd.
     *
     * @return de datum
     */
    public final DatumAttribuut naarDatum() {
        if (null != getWaarde()) {
            return new DatumAttribuut(Integer.parseInt(FastDateFormat.getInstance(DATUM_FORMAT).format(getWaarde())));
        }
        return null;
    }

    /**
     * <p> <a href="http://en.wikipedia.org/wiki/Here_be_dragons">HC SVNT DRACONES</a>! DELTA-1313 Een model dat uit de database wordt gelezen, heeft als
     * waarde een {@link Timestamp} instantie. Aangezien de implementatie hiervan ook gebruik maakt van zijn super methodes, kan dit onverwachte resultaten
     * opleveren. </p> <p/> <p> Bijvoorbeeld de <code>Date.compareTo(Timestamp)</code> en <code>Timestamp.compareTo(Date)</code> van instanties gecreeerd
     * van dezelfde <code>long</code> waarde zullen niet aan de voorwaarden van het compareTo contract voldoen. </p>
     *
     * @return de waarde van het attribuut
     */
    @Override
    @JsonValue
    public final Date getWaarde() {
        Date datumTijd = super.getWaarde();

        if (datumTijd instanceof Timestamp) {
            datumTijd = new Date(datumTijd.getTime());
        }

        return datumTijd;
    }

    /**
     * Override van {@link nl.bzk.brp.model.basis.AbstractAttribuut#hashCode()} om gebruik te maken van de {@link #getWaarde()} van deze klasse ipv direct
     * het waarde attribuut te gebruiken.
     *
     * @return de hashcode van deze instantie
     * @see #getWaarde()
     */
    @Override
    public final int hashCode() {
        return new HashCodeBuilder(PRIME_BASE, PRIME_ADD).append(getWaarde()).toHashCode();
    }

    /**
     * Override van {@link nl.bzk.brp.model.basis.AbstractAttribuut#equals(Object)} om gebruik te maken van de {@link #getWaarde()} van deze klasse ipv
     * direct het waarde attribuut te gebruiken.
     *
     * @param object de ander
     * @return {@code true} als de objecten gelijk zijn
     * @see #getWaarde()
     */
    @Override
    public final boolean equals(final Object object) {
        final boolean retval;
        if (object == null || getClass() != object.getClass()) {
            retval = false;
        } else {
            final DatumTijdAttribuut that = (DatumTijdAttribuut) object;
            retval = isEqual(this.getWaarde(), that.getWaarde());
        }
        return retval;
    }
}
