/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Deze class representeert de inhoud van de BRP groep Afgeleid Administratief voor objecttype Persoon.
 * 
 * Deze class is immutable en threadsafe.
 */
@Root(strict = false)
public final class BrpPersoonAfgeleidAdministratiefInhoud extends AbstractBrpGroepInhoud {
    @Element(name = "indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig", required = true)
    private final BrpBoolean indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig;

    /**
     * Maak een BrpAfgeleidAdministratiefInhoud object.
     * 
     * @param indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig
     *            indicatie onverwerkt bijhoudingsvoorstel niet-ingezetene aanwezig
     */
    public BrpPersoonAfgeleidAdministratiefInhoud(
        @Element(name = "indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig",
                required = true) final BrpBoolean indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig)
    {
        this.indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig = indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return false;
    }

    /**
     * Geef de waarde van indicatie onverwerkt bijhoudingsvoorstel niet ingezetene aanwezig.
     *
     * @return indicatie onverwerkt bijhoudingsvoorstel niet ingezetene aanwezig
     */
    public BrpBoolean getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig() {
        return indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpPersoonAfgeleidAdministratiefInhoud)) {
            return false;
        }
        final BrpPersoonAfgeleidAdministratiefInhoud castOther = (BrpPersoonAfgeleidAdministratiefInhoud) other;
        return new EqualsBuilder().append(
            indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig,
            castOther.indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(
            "indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig",
            indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig).toString();
    }

}
