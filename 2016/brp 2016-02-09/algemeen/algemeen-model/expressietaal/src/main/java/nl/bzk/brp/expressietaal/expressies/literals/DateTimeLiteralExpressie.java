/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.literals;

import nl.bzk.brp.expressietaal.Characters;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.ExpressieType;
import org.joda.time.DateTime;

/**
 * Representeer een datumtijd constante.
 */
public class DateTimeLiteralExpressie extends DatumLiteralExpressie {
    private static final int EENENDERTIG = 31;
    private final Datumdeel uur;
    private final Datumdeel minuut;
    private final Datumdeel seconde;

    /**
     * @param jaar jaar deel
     * @param maand maan deel
     * @param dag dag deel
     * @param uur uur deel
     * @param minuut minuut deel
     * @param seconde seconde deel
     */
    public DateTimeLiteralExpressie(final Datumdeel jaar, final Datumdeel maand, final Datumdeel dag, final Datumdeel uur,
        final Datumdeel minuut,
        final Datumdeel seconde)
    {
        super(jaar, maand, dag);
        this.uur = uur;
        this.minuut = minuut;
        this.seconde = seconde;
    }

    /**
     *
     * @param waarde een datetime object
     */
    public DateTimeLiteralExpressie(final Object waarde) {
        this(new DateTime(waarde));
    }

    /**
     *
     * @param waarde een DateTime object
     */
    public DateTimeLiteralExpressie(final DateTime waarde) {
        super(waarde);
        uur = new Datumdeel(waarde.hourOfDay().get());
        minuut = new Datumdeel(waarde.minuteOfHour().get());
        seconde = new Datumdeel(waarde.secondOfMinute().get());
    }

    @Override
    public final ExpressieType getType(final Context context) {
        return ExpressieType.DATUMTIJD;
    }

    @Override
    public final int compareTo(final DatumLiteralExpressie o) {

        final int resultaat;
        if (o instanceof DateTimeLiteralExpressie) {
            final DateTimeLiteralExpressie ander = (DateTimeLiteralExpressie) o;
            final int stringVergelijk = alsVergelijkbareString().compareTo(ander.alsVergelijkbareString());
            // dit is nodig omdat de operators verkeerde vergelijkingen doen (enkel -1, 0 en 1...)
            if (stringVergelijk < 0) {
                resultaat = -1;
            } else if (stringVergelijk > 0) {
                resultaat = 1;
            } else {
                resultaat = 0;
            }
        } else {
            resultaat = super.compareTo(o);
        }
        return resultaat;
    }

    /**
     *
     * @return uur deel
     */
    public final Datumdeel getUur() {
        return uur;
    }

    /**
     *
     * @return minuut deel
     */
    public final Datumdeel getMinuut() {
        return minuut;
    }

    /**
     *
     * @return seconde deel
     */
    public final Datumdeel getSeconde() {
        return seconde;
    }

    @Override
    public final String stringRepresentatie() {
        return String.format("%s%c%02d%c%02d%c%02d", super.stringRepresentatie(), Characters.DATUM_SCHEIDINGSTEKEN, getUur().getWaarde(), Characters
                .DATUM_SCHEIDINGSTEKEN, getMinuut().getWaarde(),
            Characters.DATUM_SCHEIDINGSTEKEN, getSeconde().getWaarde());
    }

    private String alsVergelijkbareString() {
        return String.format("%04d%02d%02d%02d%02d%02d", getJaar().getWaarde(), getMaand().getWaarde(), getDag().getWaarde(), getUur().getWaarde(),
            getMinuut().getWaarde(), getSeconde().getWaarde());
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        final DateTimeLiteralExpressie that = (DateTimeLiteralExpressie) o;

        if (uur != null ? !uur.equals(that.uur) : that.uur != null) {
            return false;
        }
        if (minuut != null ? !minuut.equals(that.minuut) : that.minuut != null) {
            return false;
        }
        return !(seconde != null ? !seconde.equals(that.seconde) : that.seconde != null);

    }

    @Override
    public final int hashCode() {
        int result = super.hashCode();
        result = EENENDERTIG * result + (uur != null ? uur.hashCode() : 0);
        result = EENENDERTIG * result + (minuut != null ? minuut.hashCode() : 0);
        result = EENENDERTIG * result + (seconde != null ? seconde.hashCode() : 0);
        return result;
    }
}
