/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze marker interface typeert LO3 Elementen.
 * 
 */
public abstract class AbstractLo3Element implements Lo3Element, Serializable {
    private static final long serialVersionUID = 1L;

    @Element(name = "waarde", required = false)
    private final String waarde;
    @Element(name = "onderzoek", required = false)
    private final Lo3Onderzoek onderzoek;

    /**
     * Constructor voor implementerende classes.
     * 
     * @param waarde
     *            waarde van dit element
     * @param onderzoek
     *            onderzoek van dit element
     */
    AbstractLo3Element(final String waarde, final Lo3Onderzoek onderzoek) {
        this.waarde = waarde;
        this.onderzoek = onderzoek;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Element#isInhoudelijkGevuld()
     */
    @Override
    public final boolean isInhoudelijkGevuld() {
        return waarde != null && !waarde.isEmpty();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Element#getWaarde()
     */
    @Override
    public final String getWaarde() {
        return waarde;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Element#getOnderzoek()
     */
    @Override
    public final Lo3Onderzoek getOnderzoek() {
        return onderzoek;
    }

    /**
     * Vergelijk 2 Lo3Element en return <code>true</code> als de waarde gelijk (of beiden zijn null). Onderzoek wordt
     * buiten beschouwing gelaten in deze vergelijking.
     * 
     * @param element1
     *            Het eerste element
     * @param element2
     *            Het tweede element
     * @return <code>true</code> als beide attributen qua waarde gelijk zijn.
     */
    public static boolean equalsWaarde(final Lo3Element element1, final Lo3Element element2) {
        final boolean result;
        if (element1 != null) {
            result = element1.equalsWaarde(element2);
        } else {
            result = element2 == null  || element2.getWaarde() == null;
        }
        return result;
    }

    @Override
    public final boolean equalsWaarde(final Lo3Element element) {
        final boolean result;
        if (waarde == null) {
            result = element == null || element.getWaarde() == null;
        } else {
            result = element != null && waarde.equals(element.getWaarde());
        }
        return result;
    }

    @Override
    public final boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !other.getClass().equals(getClass())) {
            return false;
        }

        return new EqualsBuilder().append(waarde, ((AbstractLo3Element) other).waarde)
                                  .append(onderzoek, ((AbstractLo3Element) other).onderzoek)
                                  .isEquals();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(waarde).append(onderzoek).toHashCode();
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                                                                          .append("waarde", waarde)
                                                                          .append("onderzoek", onderzoek)
                                                                          .toString();
    }
}
