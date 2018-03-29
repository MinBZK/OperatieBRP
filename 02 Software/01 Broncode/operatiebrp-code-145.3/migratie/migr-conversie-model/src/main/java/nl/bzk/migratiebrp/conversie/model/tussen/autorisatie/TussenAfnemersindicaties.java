/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.tussen.autorisatie;

import java.util.List;
import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.algemeenbrp.util.xml.annotation.ElementList;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Migratie representatie voor alle afnemersindicaties van een persoon (BRP inhoud, LO3 historie).
 */

public final class TussenAfnemersindicaties {

    @Element(name = "administratienummer")
    private final String administratienummer;

    @ElementList(name = "afnemersindicaties", entry = "afnemersindicatie", type = TussenAfnemersindicatie.class)
    private final List<TussenAfnemersindicatie> afnemersindicaties;

    /**
     * Maak een nieuw BrpAfnemersindicaties object.
     * @param administratienummer het administratienummer van de persoon
     * @param afnemersindicaties de afnemersindicaties
     */
    public TussenAfnemersindicaties(@Element(name = "administratienummer") final String administratienummer,
                                    @ElementList(name = "afnemersindicaties", type = TussenAfnemersindicatie.class, entry = "afnemersindicatie") final
                                    List<TussenAfnemersindicatie> afnemersindicaties) {
        super();
        this.administratienummer = administratienummer;
        this.afnemersindicaties = afnemersindicaties;
    }

    /**
     * Geef de waarde van administratienummer van TussenAfnemersindicaties.
     * @return de waarde van administratienummer van TussenAfnemersindicaties
     */
    public String getAdministratienummer() {
        return administratienummer;
    }

    /**
     * Geef de waarde van afnemersindicaties van TussenAfnemersindicaties.
     * @return de waarde van afnemersindicaties van TussenAfnemersindicaties
     */
    public List<TussenAfnemersindicatie> getAfnemersindicaties() {
        return afnemersindicaties;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TussenAfnemersindicaties)) {
            return false;
        }
        final TussenAfnemersindicaties castOther = (TussenAfnemersindicaties) other;
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
