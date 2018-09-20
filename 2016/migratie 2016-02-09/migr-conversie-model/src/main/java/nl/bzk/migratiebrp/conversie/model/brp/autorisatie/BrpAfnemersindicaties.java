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
 * Representatie van de afnemersindicaties voor een persoon.
 */
@Root
public final class BrpAfnemersindicaties {

    @Element(name = "administratienummer", required = false)
    private final Long administratienummer;

    @ElementList(name = "afnemersindicaties", entry = "afnemersindicatie", type = BrpAfnemersindicatie.class, required = false)
    private final List<BrpAfnemersindicatie> afnemersindicaties;

    /**
     * Maak een nieuw BrpAfnemersindicaties object.
     * 
     * @param administratienummer
     *            het administratienummer van de persoon
     * @param afnemersindicaties
     *            de afnemersindicaties
     */
    public BrpAfnemersindicaties(
        @Element(name = "administratienummer", required = false) final Long administratienummer,
        @ElementList(name = "afnemersindicaties", entry = "afnemersindicatie", type = BrpAfnemersindicatie.class, required = false) final List<BrpAfnemersindicatie> afnemersindicaties)
    {
        super();
        this.administratienummer = administratienummer;
        this.afnemersindicaties = afnemersindicaties;
    }

    /**
     * Geef de waarde van administratienummer.
     *
     * @return administratienummer
     */
    public Long getAdministratienummer() {
        return administratienummer;
    }

    /**
     * Geef de waarde van afnemersindicaties.
     *
     * @return afnemersindicaties
     */
    public List<BrpAfnemersindicatie> getAfnemersindicaties() {
        return afnemersindicaties;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpAfnemersindicaties)) {
            return false;
        }
        final BrpAfnemersindicaties castOther = (BrpAfnemersindicaties) other;
        return new EqualsBuilder().append(administratienummer, castOther.administratienummer)
                                  .append(afnemersindicaties, castOther.afnemersindicaties)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(administratienummer).append(afnemersindicaties).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("administratienummer", administratienummer)
                                                                          .append("afnemersindicaties", afnemersindicaties)
                                                                          .toString();
    }

}
