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
 * Deze class representeert de inhoud van de BRP indicatie 'Geprivilegieerde?'.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpGeprivilegieerdeIndicatieInhoud extends AbstractBrpGroepInhoud implements
        BrpIndicatieGroepInhoud {

    @Element(name = "heeftIndicatie", required = false)
    private final Boolean heeftIndicatie;

    /**
     * Maakt een BrpGeprivilegieerdeIndicatieInhoud object.
     * 
     * @param heeftIndicatie
     *            true als de indicatie bestaat, anders false, mag null zijn
     */
    public BrpGeprivilegieerdeIndicatieInhoud(
            @Element(name = "heeftIndicatie", required = false) final Boolean heeftIndicatie) {
        this.heeftIndicatie = heeftIndicatie;
    }

    /**
     * @return true als er sprake is van een indicatie, anders false of null als de inhoud leeg is
     */
    @Override
    public Boolean getHeeftIndicatie() {
        return heeftIndicatie;
    }

    @Override
    public boolean isLeeg() {
        return heeftIndicatie == null;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpGeprivilegieerdeIndicatieInhoud)) {
            return false;
        }
        final BrpGeprivilegieerdeIndicatieInhoud castOther = (BrpGeprivilegieerdeIndicatieInhoud) other;
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
