/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

import com.google.common.hash.HashCode;
import java.time.ZonedDateTime;

/**
 * Representeer een datumtijd constante.
 */
public final class DatumtijdLiteral extends DatumLiteral {
    private final int uur;
    private final int minuut;
    private final int seconde;

    /**
     * @param jaar    jaar deel
     * @param maand   maan deel
     * @param dag     dag deel
     * @param uur     uur deel
     * @param minuut  minuut deel
     * @param seconde seconde deel
     */
    public DatumtijdLiteral(final Datumdeel jaar, final Datumdeel maand, final Datumdeel dag, final int uur,
                            final int minuut,
                            final int seconde) {
        super(jaar, maand, dag);
        this.uur = uur;
        this.minuut = minuut;
        this.seconde = seconde;
    }

    /**
     * @param waarde een DateTime object
     */
    public DatumtijdLiteral(final ZonedDateTime waarde) {
        super(waarde);
        uur = waarde.getHour();
        minuut = waarde.getMinute();
        seconde = waarde.getSecond();
    }

    /**
     * @return aantal uren
     */
    public int getUur() {
        return uur;
    }

    /**
     * @return aantal minuten
     */
    public int getMinuut() {
        return minuut;
    }

    /**
     * @return aantal seconden.
     */
    public int getSeconde() {
        return seconde;
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.DATUMTIJD;
    }

    @Override
    public String toString() {
        return String.format("%s%c%02d%c%02d%c%02d", super.toString(), '/', uur, '/', minuut, '/', seconde);
    }

    @Override
    public int hashCode() {
        return HashCode.fromInt(alsInteger()).hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DatumtijdLiteral that = (DatumtijdLiteral) o;
        return alsInteger() == that.alsInteger();
    }
}
