/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.validatie;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Periode.
 *
 * Deze class is immutable en threadsafe.
 */
public final class Periode implements Comparable<Periode> {

    private final long begin;
    private final long einde;

    /**
     * Maak een periode adhv Timestamp objecten.
     *
     * @param datumTijdRegistratie
     *            De registratie datum/tijd. Als deze null is, wordt Long.MIN_VALUE ingevuld.
     * @param datumTijdVerval
     *            De verval datum/tijd. Als deze null is, wordt Long.MAX_VALUE ingevuld.
     */
    public Periode(final Timestamp datumTijdRegistratie, final Timestamp datumTijdVerval) {
        this(datumTijdRegistratie == null ? null : datumTijdRegistratie.getTime(), datumTijdVerval == null ? null : datumTijdVerval.getTime());
    }

    /**
     * Maak een periode.
     *
     * @param begin
     *            begindatum
     * @param einde
     *            einddatum
     */
    public Periode(final Long begin, final Long einde) {
        final Paar datumPaar = new Paar(begin == null ? Long.MIN_VALUE : begin, einde == null ? Long.MAX_VALUE : einde);
        this.begin = datumPaar.getMin();
        this.einde = datumPaar.getMax();
    }

    /**
     * Geef de waarde van begin.
     *
     * @return begin
     */
    public long getBegin() {
        return begin;
    }

    /**
     * Geef de waarde van einde.
     *
     * @return einde
     */
    public long getEinde() {
        return einde;
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
        } else {
            // Deze periode begint na de andere periode, dus kijken of de
            // einddatum van de andere periode ligt na het begin van deze
            // periode
            result = begin == periode.begin || periode.einde > begin;
        }

        return result;
    }

    /**
     * @return de lengte van deze periode = einde - begin
     */
    public long lengte() {
        return einde - begin;
    }

    /**
     * Verwijderd de overlap in periodes met de meegegeven periode in deze periode.
     *
     * @param anderePeriode
     *            de andere periode waarmee overlap wordt bepaald
     * @return de lijst met periodes die het resultaat is van het 'aftrekken' van de meegegeven periode van deze periode
     */
    public Set<Periode> remove(final Periode anderePeriode) {
        final Set<Periode> result = new HashSet<>();
        if (anderePeriode.lengte() > 0 && anderePeriode.getBegin() < getEinde() && anderePeriode.getEinde() > getBegin()) {
            final Periode deel1 = new Periode(begin, anderePeriode.begin < begin ? begin : anderePeriode.begin);
            final Periode deel2 = new Periode(einde, anderePeriode.einde > einde ? einde : anderePeriode.einde);
            if (deel1.lengte() > 0) {
                result.add(deel1);
            }
            if (deel2.lengte() > 0) {
                result.add(deel2);
            }
        } else {
            result.add(this);
        }
        return result;
    }

    /**
     * Verwijderd van een set met periodes alle overlap met de meegegeven periode.
     *
     * @param periodeSet
     *            de lijst waarvan de overlappende periode wordt 'afgetrokken'
     * @param anderePeriode
     *            deze periode wordt van de periodeSet 'afgetrokken'
     * @return de periodes binnen de periodeSet waarin geen overlap is met de andere periode
     */
    public static Set<Periode> removeFromSet(final Set<Periode> periodeSet, final Periode anderePeriode) {
        final Set<Periode> result = new HashSet<>();

        for (final Periode periode : periodeSet) {
            result.addAll(periode.remove(anderePeriode));
        }
        return result;
    }

    /**
     * Verwijderd van een set met periodes alle overlap met de meegegeven periode set.
     *
     * @param periodeSet
     *            de lijst waarvan de overlappende periodes worden 'afgetrokken'
     * @param anderePeriodeSet
     *            deze set van periodes wordt van de periodeSet 'afgetrokken'
     * @return de periodes binnen de periodeSet waarin geen overlap is met de andere periodes
     */
    public static Set<Periode> removeFromSet(final Set<Periode> periodeSet, final Set<Periode> anderePeriodeSet) {
        if (anderePeriodeSet.isEmpty()) {
            return periodeSet;
        } else {
            final Set<Periode> overigePeriodes = new HashSet<>(anderePeriodeSet);
            final Iterator<Periode> overigePeriodesIterator = overigePeriodes.iterator();
            final Periode teVerwijderenPeriode = overigePeriodesIterator.next();
            overigePeriodesIterator.remove();
            return Periode.removeFromSet(Periode.removeFromSet(periodeSet, teVerwijderenPeriode), overigePeriodes);
        }
    }

    /**
     * Natuurlijke sortering. Eerst op begindatum, dan op einddatum.
     *
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final Periode that) {
        int result = 0;

        if (begin < that.begin) {
            result = -1;
        } else if (begin > that.begin) {
            result = 1;
        }

        if (result == 0) {
            if (einde < that.einde) {
                result = -1;
            } else if (einde > that.einde) {
                result = 1;
            }
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
                                                                          .append("begin", begin)
                                                                          .append("einde", einde)
                                                                          .toString();
    }

    /**
     * Helper class voor het bepalen van een minimale en maximale waarde van twee long waarden zodat deze gedurende
     * object creatie aan het juiste veld kunnen worden toegewezen.
     */
    private static final class Paar {
        private final long waarde1;
        private final long waarde2;

        /**
         * Convenient constructor voor helper klasse.
         *
         * @param waarde1
         *            De eerste waarde van het paar.
         * @param waarde2
         *            De tweede waarde van het paar.
         */
        public Paar(final long waarde1, final long waarde2) {
            this.waarde1 = waarde1;
            this.waarde2 = waarde2;
        }

        /**
         * Geef de waarde van min.
         *
         * @return min
         */
        private long getMin() {
            return Math.min(waarde1, waarde2);
        }

        /**
         * Geef de waarde van max.
         *
         * @return max
         */
        private long getMax() {
            return Math.max(waarde1, waarde2);
        }
    }
}
