/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import nl.moderniseringgba.migratie.GegevensSet;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerantwoordelijkeCode;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeerd de BRP groep bijhoudingsverantwoordelijke.
 * 
 * Deze class is immutable en dus thread safe.
 */
public final class BrpBijhoudingsverantwoordelijkheidInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "verantwoordelijkeCode", required = false)
    private final BrpVerantwoordelijkeCode verantwoordelijkeCode;

    @GegevensSet
    @Element(name = "datumBijhoudingsverantwoordelijkheid", required = false)
    private final BrpDatum datumBijhoudingsverantwoordelijkheid;

    /**
     * Maak een BrpBijhoudingsverantwoordelijkheidInhoud.
     * 
     * @param verantwoordelijkeCode
     *            verantwoordelijke
     * @param datumBijhoudingsverantwoordelijkheid
     *            datum bijhoudingsverantwoordelijkheid
     */
    public BrpBijhoudingsverantwoordelijkheidInhoud(
            @Element(name = "verantwoordelijkeCode", required = false) final BrpVerantwoordelijkeCode verantwoordelijkeCode,
            @Element(name = "datumBijhoudingsverantwoordelijkheid", required = false) final BrpDatum datumBijhoudingsverantwoordelijkheid) {
        super();
        this.verantwoordelijkeCode = verantwoordelijkeCode;
        this.datumBijhoudingsverantwoordelijkheid = datumBijhoudingsverantwoordelijkheid;
    }

    @Override
    public boolean isLeeg() {
        return !ValidationUtils.isEenParameterGevuld(verantwoordelijkeCode, datumBijhoudingsverantwoordelijkheid);
    }

    /**
     * @return the verantwoordelijkeCode
     */
    public BrpVerantwoordelijkeCode getVerantwoordelijkeCode() {
        return verantwoordelijkeCode;
    }

    /**
     * @return the datumBijhoudingsverantwoordelijkheid
     */
    public BrpDatum getDatumBijhoudingsverantwoordelijkheid() {
        return datumBijhoudingsverantwoordelijkheid;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpBijhoudingsverantwoordelijkheidInhoud)) {
            return false;
        }
        final BrpBijhoudingsverantwoordelijkheidInhoud castOther = (BrpBijhoudingsverantwoordelijkheidInhoud) other;
        return new EqualsBuilder().append(verantwoordelijkeCode, castOther.verantwoordelijkeCode)
                .append(datumBijhoudingsverantwoordelijkheid, castOther.datumBijhoudingsverantwoordelijkheid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(verantwoordelijkeCode).append(datumBijhoudingsverantwoordelijkheid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("verantwoordelijkeCode", verantwoordelijkeCode)
                .append("datumBijhoudingsverantwoordelijkheid", datumBijhoudingsverantwoordelijkheid).toString();
    }

}
