/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import java.io.Serializable;
import java.util.Objects;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Een BRP attribuut dat in onderzoek kan staan.
 *
 */
public abstract class AbstractBrpAttribuutMetOnderzoek implements BrpAttribuutMetOnderzoek, Serializable {
    private static final long serialVersionUID = 1L;

    private final Object waarde;
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
    AbstractBrpAttribuutMetOnderzoek(final Object waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            throw new NullPointerException("waarde en onderzoek mogen niet beiden null zijn.");
        }
        this.waarde = waarde;
        this.onderzoek = onderzoek;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpAttribuutMetOnderzoek#isInhoudelijkGevuld()
     */
    @Override
    public final boolean isInhoudelijkGevuld() {
        final boolean result;
        if (waarde instanceof String) {
            result = !((String) waarde).isEmpty();
        } else {
            result = waarde != null;
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpAttribuutMetOnderzoek#getOnderzoek()
     */
    @Override
    public final Lo3Onderzoek getOnderzoek() {
        return onderzoek;
    }

    @Override
    public final boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !other.getClass().equals(getClass())) {
            return false;
        }

        return new EqualsBuilder().append(waarde, ((AbstractBrpAttribuutMetOnderzoek) other).waarde)
                                  .append(onderzoek, ((AbstractBrpAttribuutMetOnderzoek) other).onderzoek)
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

    /**
     * Vergelijk 2 BrpAttribuutMetOnderzoek en return <code>true</code> als de waarde gelijk (of beiden zijn null).
     * Onderzoek wordt buiten beschouwing gelaten in deze vergelijking.
     *
     * @param attribuut1
     *            Het eerste attribuut
     * @param attribuut2
     *            Het tweede attribtuut
     * @return <code>true</code> als beide attributen qua waarde gelijk zijn.
     */
    public static boolean equalsWaarde(final BrpAttribuutMetOnderzoek attribuut1, final BrpAttribuutMetOnderzoek attribuut2) {
        final boolean bothNull = attribuut1 == null && attribuut2 == null;
        return bothNull || attribuut1 != null && attribuut2 != null && Objects.equals(attribuut1.getWaarde(), attribuut2.getWaarde());
    }

    /**
     * Unwrap implementatie waarbij de waarde wordt terug gegeven.
     *
     * @param attribuut
     *            BRP attribuut die ge-unwrapped moet worden
     * @return de unwrapped waarde van het BRP attribuut
     */
    static Object unwrapImpl(final BrpAttribuutMetOnderzoek attribuut) {
        if (attribuut instanceof AbstractBrpAttribuutMetOnderzoek) {
            return ((AbstractBrpAttribuutMetOnderzoek) attribuut).waarde;
        }
        return null;
    }
}
