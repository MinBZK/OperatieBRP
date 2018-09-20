/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.tussen.autorisatie;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Migratie representatie voor een autorisatie (BRP inhoud, LO3 historie).
 *
 */
@Root
public final class TussenAutorisatie {

    @Element(name = "tussenPartij", required = false)
    private final TussenPartij partij;
    @ElementList(name = "tussenLeveringsAutorisaties", entry = "tussenLeveringsautorisatie", type = TussenLeveringsautorisatie.class, required = false)
    private final List<TussenLeveringsautorisatie> leveringsautorisaties;

    /**
     * Maak een nieuw TussenAutorisatie object.
     *
     * @param partij
     *            de partij van de autorisatie
     * @param leveringsautorisaties
     *            de leveringsautorisaties van de autorisatie
     */
    public TussenAutorisatie(
        @Element(name = "tussenPartij", required = false) final TussenPartij partij,
        @ElementList(name = "tussenLeveringsAutorisaties", entry = "tussenLeveringsautorisatie", type = TussenLeveringsautorisatie.class, required = false) final List<TussenLeveringsautorisatie> leveringsautorisaties)
    {
        super();
        this.partij = partij;
        this.leveringsautorisaties = leveringsautorisaties;
    }

    /**
     * Geef de waarde van partij.
     *
     * @return partij
     */
    public TussenPartij getPartij() {
        return partij;
    }

    /**
     * Geef de waarde van leveringsautorisaties.
     *
     * @return leveringsautorisaties
     */
    public List<TussenLeveringsautorisatie> getLeveringsautorisaties() {
        return leveringsautorisaties;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TussenAutorisatie)) {
            return false;
        }
        final TussenAutorisatie castOther = (TussenAutorisatie) other;
        return new EqualsBuilder().append(partij, castOther.partij).append(leveringsautorisaties, castOther.leveringsautorisaties).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(partij).append(leveringsautorisaties).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("partij", partij)
                .append("leveringsautorisaties", leveringsautorisaties)
                .toString();
    }
}
