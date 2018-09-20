/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import nl.moderniseringgba.migratie.GegevensSet;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de BRP indicatie 'Ouder'.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpOuderInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "heeftIndicatie", required = false)
    private final Boolean heeftIndicatie;

    @GegevensSet
    @Element(name = "datumAanvang", required = false)
    private final BrpDatum datumAanvang;

    /**
     * Maakt een BrpOuderInhoud object.
     * 
     * @param heeftIndicatie
     *            true als de indicatie bestaat, anders false, mag null zijn
     * @param datumAanvang
     *            datum aanvang
     */
    public BrpOuderInhoud(@Element(name = "heeftIndicatie", required = false) final Boolean heeftIndicatie, @Element(
            name = "datumAanvang", required = false) final BrpDatum datumAanvang) {
        this.heeftIndicatie = heeftIndicatie;
        this.datumAanvang = datumAanvang;
    }

    /**
     * @return true als er sprake is van een indicatie, anders false of null als de inhoud leeg is
     */
    public Boolean getHeeftIndicatie() {
        return heeftIndicatie;
    }

    /**
     * @return the datumAanvang
     */
    public BrpDatum getDatumAanvang() {
        return datumAanvang;
    }

    @Override
    public boolean isLeeg() {
        return heeftIndicatie == null && datumAanvang == null;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpOuderInhoud)) {
            return false;
        }
        final BrpOuderInhoud castOther = (BrpOuderInhoud) other;
        return new EqualsBuilder().append(heeftIndicatie, castOther.heeftIndicatie)
                .append(datumAanvang, castOther.datumAanvang).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(heeftIndicatie).append(datumAanvang).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("heeftIndicatie", heeftIndicatie).append("datumAanvang", datumAanvang).toString();
    }

}
