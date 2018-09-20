/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.attribuut;

import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpAttribuut;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Text;

/**
 * Deze class representeert het BRP datum type.
 * 
 * Deze class is immutable en threadsafe.
 * 
 * 
 * 
 */
@Requirement(Requirements.CAP002)
public final class BrpDatum implements BrpAttribuut, Comparable<BrpDatum> {

    /** De onbekende datum (0). */
    public static final BrpDatum ONBEKEND = new BrpDatum(0);

    @Text
    private final int datum;

    /**
     * Maakt een BrpDatum object .
     * 
     * @param datum
     *            de datum als integer in de vorm van jjjjmmdd.
     */
    public BrpDatum(@Text final int datum) {
        if (!ValidationUtils.isGeldigDatumFormaatYYYYMMDD(datum)) {
            throw new IllegalArgumentException(String.format("Datum is niet van het formaat jjjjmmdd: %s", datum));
        }
        this.datum = datum;
    }

    @Override
    public int compareTo(final BrpDatum andereDatum) {
        return Integer.valueOf(datum).compareTo(Integer.valueOf(andereDatum.datum));
    }

    /**
     * Converteer naar lo3 datum.
     * 
     * @return lo3 datum
     */
    public Lo3Datum converteerNaarLo3Datum() {
        return new Lo3Datum(datum);
    }

    /**
     * @return de datum als integer in de vorm van jjjjmmdd
     */
    public int getDatum() {
        return datum;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpDatum)) {
            return false;
        }
        final BrpDatum castOther = (BrpDatum) other;
        return new EqualsBuilder().append(datum, castOther.datum).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(datum).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("datum", datum).toString();
    }
}
