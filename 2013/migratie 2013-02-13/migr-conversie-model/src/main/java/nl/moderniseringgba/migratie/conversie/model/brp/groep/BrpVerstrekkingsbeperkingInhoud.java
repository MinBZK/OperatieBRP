/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpIndicatieGroepInhoud;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de groep BRP Verstrekkingsbeperking.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpVerstrekkingsbeperkingInhoud extends AbstractBrpGroepInhoud implements BrpIndicatieGroepInhoud {

    @Element(name = "verstrekkingsbeperking", required = false)
    private final Boolean heeftIndicatie;

    /**
     * Maakt een BrpVerstrekkingsbeperkingInhoud.
     * 
     * @param heeftIndicatie
     *            indicatie verstrekkingsbeperking
     */
    public BrpVerstrekkingsbeperkingInhoud(
            @Element(name = "verstrekkingsbeperking", required = false) final Boolean heeftIndicatie) {
        super();
        this.heeftIndicatie = heeftIndicatie;
    }

    @Override
    public boolean isLeeg() {
        return heeftIndicatie == null;
    }

    /**
     * @return the verstrekkingsbeperking
     */
    @Override
    public Boolean getHeeftIndicatie() {
        return heeftIndicatie;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpVerstrekkingsbeperkingInhoud)) {
            return false;
        }
        final BrpVerstrekkingsbeperkingInhoud castOther = (BrpVerstrekkingsbeperkingInhoud) other;
        return new EqualsBuilder().append(heeftIndicatie, castOther.heeftIndicatie).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(heeftIndicatie).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("heeftIndicatie", heeftIndicatie).toString();
    }

}
