/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de BRP groep immigratie.
 * 
 * Deze class is immutable en thread safe.
 * 
 */
public final class BrpImmigratieInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "landVanwaarIngeschreven", required = false)
    private final BrpLandCode landVanwaarIngeschreven;
    @Element(name = "datumVestigingInNederland", required = false)
    private final BrpDatum datumVestigingInNederland;

    /**
     * Maak een BrpImmigratieInhoud object.
     * 
     * @param landVanwaarIngeschreven
     *            land (code) vanwaar ingeschreven
     * @param datumVestigingInNederland
     *            datum vestiging in Nederland
     */
    public BrpImmigratieInhoud(
            @Element(name = "landVanwaarIngeschreven", required = false) final BrpLandCode landVanwaarIngeschreven,
            @Element(name = "datumVestigingInNederland", required = false) final BrpDatum datumVestigingInNederland) {
        this.landVanwaarIngeschreven = landVanwaarIngeschreven;
        this.datumVestigingInNederland = datumVestigingInNederland;
    }

    @Override
    public boolean isLeeg() {
        return landVanwaarIngeschreven == null && datumVestigingInNederland == null;
    }

    public BrpDatum getDatumVestigingInNederland() {
        return datumVestigingInNederland;
    }

    public BrpLandCode getLandVanwaarIngeschreven() {
        return landVanwaarIngeschreven;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpImmigratieInhoud)) {
            return false;
        }
        final BrpImmigratieInhoud castOther = (BrpImmigratieInhoud) other;
        return new EqualsBuilder().append(landVanwaarIngeschreven, castOther.landVanwaarIngeschreven)
                .append(datumVestigingInNederland, castOther.datumVestigingInNederland).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(landVanwaarIngeschreven).append(datumVestigingInNederland).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("landVanwaarIngeschreven", landVanwaarIngeschreven)
                .append("datumVestigingInNederland", datumVestigingInNederland).toString();
    }
}
