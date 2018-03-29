/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.autorisatie;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpGroepInhoud;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van de groep BRP Dienstbundel Lo3Rubriek.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpDienstbundelLo3Rubriek extends AbstractBrpGroepInhoud {

    @Element(name = "conversieRubriek", required = false)
    private final String conversieRubriek;

    /**
     * Maak een nieuw BrpLo3Rubriek object.
     * @param conversieRubriek Id van de bijbehorende conversie LO3 rubriek
     */
    public BrpDienstbundelLo3Rubriek(@Element(name = "conversieRubriek", required = false) final String conversieRubriek) {
        super();
        this.conversieRubriek = conversieRubriek;
    }

    /**
     * Geef de leeg.
     * @return false
     */
    @Override
    public boolean isLeeg() {
        return false;
    }

    /**
     * Geef de waarde van conversieRubriek.
     * @return het conversieRubriek
     */
    public String getConversieRubriek() {
        return conversieRubriek;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpDienstbundelLo3Rubriek)) {
            return false;
        }
        final BrpDienstbundelLo3Rubriek castOther = (BrpDienstbundelLo3Rubriek) other;
        return new EqualsBuilder().append(conversieRubriek, castOther.conversieRubriek).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(conversieRubriek).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("conversieRubriekId", conversieRubriek).toString();
    }

}
