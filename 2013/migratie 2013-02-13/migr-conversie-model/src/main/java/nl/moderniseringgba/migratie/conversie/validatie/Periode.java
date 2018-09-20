/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.validatie;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Periode.
 * 
 * Deze class is immutable en threadsafe.
 */
public final class Periode {

    private final long begin;
    private final long einde;

    /**
     * Maak een periode adhv BrpDatum object.
     * 
     * @param beginDatum
     *            De begin datum. Als deze null is wordt Long.MIN_VALUE ingevuld.
     * @param eindDatum
     *            De eind datum. Als deze null is wordt Long.MAX_VALUE ingevuld.
     */
    public Periode(final BrpDatum beginDatum, final BrpDatum eindDatum) {
        final Paar datumPaar =
                new Paar(beginDatum == null ? Long.MIN_VALUE : beginDatum.getDatum(),
                        eindDatum == null ? Long.MAX_VALUE : eindDatum.getDatum());
        begin = datumPaar.getMin();
        einde = datumPaar.getMax();
    }

    /**
     * Maak een periode adhv BrpDatumTijd object.
     * 
     * @param beginDatumTijd
     *            De begin datum/tijd. Als deze null is wordt Long.MIN_VALUE ingevuld.
     * @param eindDatumTijd
     *            De eind datum/tijd. Als deze null is wordt Long.MAX_VALUE ingevuld.
     */
    public Periode(final BrpDatumTijd beginDatumTijd, final BrpDatumTijd eindDatumTijd) {
        final Paar datumPaar =
                new Paar(beginDatumTijd == null ? Long.MIN_VALUE : beginDatumTijd.getDatumTijd(),
                        eindDatumTijd == null ? Long.MAX_VALUE : eindDatumTijd.getDatumTijd());
        begin = datumPaar.getMin();
        einde = datumPaar.getMax();
    }

    /**
     * Maak een periode.
     * 
     * @param begin
     *            begindatum
     * @param einde
     *            einddatum
     */
    public Periode(final long begin, final long einde) {
        final Paar datumPaar = new Paar(begin, einde);
        this.begin = datumPaar.getMin();
        this.einde = datumPaar.getMax();
    }

    /**
     * Bepaal of deze periode overlap heeft met een andere periode. Periodes zijn inclusief begindatum maar exclusief
     * einddatum.
     * 
     * @param periode
     *            periode
     * @return true, als deze periode overlap heeft met de gegeven periode; anders false
     */
    public boolean heeftOverlap(final Periode periode) {
        final boolean result;

        if (begin < periode.begin) {
            // Deze periode begint voor de andere periode, dus kijken
            // of deze einddatum na de begindatum van de andere periode
            // ligt
            result = einde > periode.begin;
        } else if (begin == periode.begin) {
            result = true;
        } else {
            // Deze periode begint na de andere periode, dus kijken of de
            // einddatum van de andere periode ligt na het begin van deze
            // periode
            result = periode.einde > begin;
        }

        return result;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Periode)) {
            return false;
        }
        final Periode castOther = (Periode) other;
        return new EqualsBuilder().append(begin, castOther.begin).append(einde, castOther.einde).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(begin).append(einde).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("begin", begin).append("einde", einde).toString();
    }

    /**
     * Helper class voor het bepalen van een minimale en maximale waarde van twee long waarden zodat deze gedurende
     * object creatie aan het juiste veld kunnen worden toegewezen.
     */
    private static final class Paar {
        private final long waarde1;
        private final long waarde2;

        public Paar(final long waarde1, final long waarde2) {
            this.waarde1 = waarde1;
            this.waarde2 = waarde2;
        }

        private long getMin() {
            return Math.min(waarde1, waarde2);
        }

        private long getMax() {
            return Math.max(waarde1, waarde2);
        }
    }
}
