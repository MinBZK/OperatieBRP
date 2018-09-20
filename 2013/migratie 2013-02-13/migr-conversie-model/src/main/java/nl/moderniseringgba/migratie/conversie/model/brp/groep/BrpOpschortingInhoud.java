/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import nl.moderniseringgba.migratie.GegevensSet;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenOpschortingBijhoudingCode;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de groep BRP Opschorting.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpOpschortingInhoud extends AbstractBrpGroepInhoud {

    @GegevensSet
    @Element(name = "datumOpschorting", required = false)
    private final BrpDatum datumOpschorting;

    @Element(name = "redenOpschortingBijhoudingCode", required = false)
    private final BrpRedenOpschortingBijhoudingCode redenOpschortingBijhoudingCode;

    /**
     * Maakt een BrpOpschortingInhoud.
     * 
     * @param datumOpschorting
     *            datum opschorting
     * @param redenOpschortingBijhoudingCode
     *            reden opschorting
     */
    public BrpOpschortingInhoud(
            @Element(name = "datumOpschorting", required = false) final BrpDatum datumOpschorting,
            @Element(name = "redenOpschortingBijhoudingCode", required = false) final BrpRedenOpschortingBijhoudingCode redenOpschortingBijhoudingCode) {
        this.datumOpschorting = datumOpschorting;
        this.redenOpschortingBijhoudingCode = redenOpschortingBijhoudingCode;
    }

    @Override
    public boolean isLeeg() {
        return datumOpschorting == null && redenOpschortingBijhoudingCode == null;
    }

    /**
     * @return the datumOpschorting
     */
    public BrpDatum getDatumOpschorting() {
        return datumOpschorting;
    }

    /**
     * @return the redenOpschortingBijhoudingCode
     */
    public BrpRedenOpschortingBijhoudingCode getRedenOpschortingBijhoudingCode() {
        return redenOpschortingBijhoudingCode;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpOpschortingInhoud)) {
            return false;
        }
        final BrpOpschortingInhoud castOther = (BrpOpschortingInhoud) other;
        return new EqualsBuilder().append(datumOpschorting, castOther.datumOpschorting)
                .append(redenOpschortingBijhoudingCode, castOther.redenOpschortingBijhoudingCode).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(datumOpschorting).append(redenOpschortingBijhoudingCode).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("datumOpschorting", datumOpschorting)
                .append("redenOpschortingBijhoudingCode", redenOpschortingBijhoudingCode).toString();
    }

}
