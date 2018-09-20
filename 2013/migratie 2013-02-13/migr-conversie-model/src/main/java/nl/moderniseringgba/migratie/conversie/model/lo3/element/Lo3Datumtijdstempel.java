/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.element;

import java.io.Serializable;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Element;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Text;

/**
 * Deze class representeert de LO3 waarde datum (jjjjmmddhhmmssμμμ). Er wordt geen controle uitgevoerd op de invoer.
 * 
 * De class is immutable en threadsafe.
 * 
 */
public final class Lo3Datumtijdstempel implements Lo3Element, Comparable<Lo3Datumtijdstempel>, Serializable {
    private static final long serialVersionUID = 1L;

    @Text
    private final long datum;

    /**
     * Maakt een Lo3Datum object .
     * 
     * @param datum
     *            de datum als integer in de vorm van jjjjmmddhhmmssuuu.
     */
    public Lo3Datumtijdstempel(@Text final long datum) {
        this.datum = datum;
    }

    /**
     * @return de BRP datum tijd representatie van deze LO3 datum.
     */
    public BrpDatumTijd converteerNaarBrpDatumTijd() {
        return BrpDatumTijd.fromDatumTijdMillis(this.datum);
    }

    @Override
    public int compareTo(final Lo3Datumtijdstempel andereDatum) {
        final long result = this.datum - andereDatum.datum;

        return result > 0 ? 1 : result < 0 ? -1 : 0;
    }

    /**
     * @return datum tijdstempel
     */
    public long getDatum() {
        return this.datum;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3Datumtijdstempel)) {
            return false;
        }
        final Lo3Datumtijdstempel castOther = (Lo3Datumtijdstempel) other;
        return new EqualsBuilder().append(this.datum, castOther.datum).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.datum).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("datum", this.datum).toString();
    }

}
