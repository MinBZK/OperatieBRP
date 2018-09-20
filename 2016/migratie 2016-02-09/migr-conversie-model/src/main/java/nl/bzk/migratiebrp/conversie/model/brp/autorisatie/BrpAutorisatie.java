/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.autorisatie;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Deze class representeert de migratie specifieke kijk op een BRP Autorisatie.
 *
 */
@Root
public final class BrpAutorisatie {

    @Element(name = "partij", required = false)
    private final BrpPartij partij;
    @ElementList(name = "leveringsautorisatieLijst", entry = "leveringsautorisatie", type = BrpLeveringsautorisatie.class, required = false)
    private final List<BrpLeveringsautorisatie> leveringsautorisatieLijst;

    /**
     * Maak een nieuw BrpAutorisatie object.
     *
     * @param partij
     *            de partij van de autorisatie
     * @param leveringsautorisatieLijst
     *            de leveringautorisatieLijst van de autorisatie
     */
    public BrpAutorisatie(
        @Element(name = "partij", required = false) final BrpPartij partij,
        @ElementList(name = "leveringsautorisatieLijst", entry = "leveringsautorisatie", type = BrpLeveringsautorisatie.class, required = false)
        final List<BrpLeveringsautorisatie> leveringsautorisatieLijst)
    {
        super();
        this.partij = partij;
        this.leveringsautorisatieLijst = leveringsautorisatieLijst;
    }

    /**
     * Geef de waarde van partij.
     *
     * @return partij
     */
    public BrpPartij getPartij() {
        return partij;
    }

    /**
     * Geef de waarde van autorisatie lijst.
     *
     * @return autorisatie lijst
     */
    public List<BrpLeveringsautorisatie> getLeveringsAutorisatieLijst() {
        return leveringsautorisatieLijst;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpAutorisatie)) {
            return false;
        }
        final BrpAutorisatie castOther = (BrpAutorisatie) other;
        return new EqualsBuilder().append(partij, castOther.partij).append(leveringsautorisatieLijst, castOther.leveringsautorisatieLijst).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(partij).append(leveringsautorisatieLijst).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("partij", partij)
                                                                          .append("leveringsautorisatieLijst", leveringsautorisatieLijst)
                                                                          .toString();
    }
}
