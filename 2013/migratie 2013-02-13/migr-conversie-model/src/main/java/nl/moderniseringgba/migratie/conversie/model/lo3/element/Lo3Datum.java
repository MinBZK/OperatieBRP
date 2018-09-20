/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.element;

import java.io.Serializable;

import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Element;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Text;

/**
 * Deze class representeert de LO3 waarde datum (jjjjmmdd); er wordt geen inhoudelijke controle uitgevoerd of een datum
 * daadwerkelijk geldig is.
 * 
 * De class is immutable en threadsafe.
 */
@Requirement(Requirements.CAP002)
public final class Lo3Datum implements Lo3Element, Comparable<Lo3Datum>, Serializable {

    /**
     * Leeg Lo3Datum object.
     */
    public static final Lo3Datum NULL_DATUM = new Lo3Datum(0);
    private static final long serialVersionUID = 1L;

    @Text
    private final int datum;

    /**
     * Maakt een Lo3Datum object; er wordt geen inhoudelijke controle uitgevoerd of een datum daadwerkelijk geldig is.
     * 
     * @param datum
     *            de datum als integer in de vorm van jjjjmmdd.
     */
    public Lo3Datum(@Text final int datum) {
        this.datum = datum;
    }

    /**
     * Is een gedeelte van deze datum onbekend? Een gedeelte (jaar, maand of dag) van een datum is onbekend als er 0000
     * (jaar) of 00 (maand of dag) als waarde is ingevuld.
     * 
     * Bijvoorbeeld:
     * <ul>
     * <li>19000101 -> false</li>
     * <li>19000100 -> true</li>
     * <li>19000001 -> true</li>
     * <li>00000101 -> true</li>
     * <li>00000100 -> true</li>
     * <li>00000001 -> true</li>
     * <li>19000000 -> true</li>
     * <li>00000000 -> true</li>
     * </ul>
     * 
     * @return true als een gedeelte van de datum onbekend is, anders false
     */
    public boolean isOnbekend() {
        // CHECKSTYLE:OFF - 10000 en 100 zijn hier selectors voor jaar, maand en dag
        return datum == 0 || datum / 10000 == 0 || datum % 10000 / 100 == 0 || datum % 100 == 0;
        // CHECKSTYLE:ON
    }

    /**
     * @return het BRP equivalent van deze LO3 datum
     */
    public BrpDatum converteerNaarBrpDatum() {
        return new BrpDatum(datum);
    }

    /**
     * @return de BRP datum tijd representatie van deze LO3 datum. De tijd wordt geconverteerd naar 00:00:00
     */
    public BrpDatumTijd converteerNaarBrpDatumTijd() {
        return BrpDatumTijd.fromDatum(datum);
    }

    @Override
    public int compareTo(final Lo3Datum andereDatum) {
        return datum - andereDatum.datum;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3Datum)) {
            return false;
        }
        final Lo3Datum castOther = (Lo3Datum) other;
        return new EqualsBuilder().append(datum, castOther.datum).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(datum).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("datum", datum).toString();
    }

    public int getDatum() {
        return datum;
    }

}
