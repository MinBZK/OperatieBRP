/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.autorisatie;

import java.util.List;
import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.algemeenbrp.util.xml.annotation.ElementList;
import nl.bzk.algemeenbrp.util.xml.annotation.Root;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Representatie van de afnemersindicaties voor een persoonslijst.
 *
 * Let op: een afnemersindicatie (code) kan op meerdere afnemers indicatie stapels voorkomen.
 */
@Root
public final class Lo3Afnemersindicatie {

    private final String aNummer;
    private final List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> afnemersindicatieStapels;

    /**
     * Maak een Lo3 afnemersindicaties lijst.
     * @param aNummer a-nummer van de persoon waarvoor de afnemersindicaties zijn
     * @param afnemersindicatieStapels de lijst met afnemersIndicatieStapels, mag null of leeg zijn
     * @throws NullPointerException als de stapel null zijn
     */
    public Lo3Afnemersindicatie(
            @Element(name = "aNummer") final String aNummer,
            @ElementList(name = "afnemersIndicatieStapels", entry = "afnemersIndicatieStapel", type = Lo3Stapel.class) final
            List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> afnemersindicatieStapels) {
        this.aNummer = aNummer;
        this.afnemersindicatieStapels = afnemersindicatieStapels;
    }

    /**
     * Geef de waarde van a nummer.
     * @return the aNummer
     */
    @Element(name = "aNummer")
    public String getANummer() {
        return aNummer;
    }

    /**
     * Geef de waarde van afnemersindicatie stapels.
     * @return de lijst met afnemersIndicatieStapels.
     */
    @ElementList(name = "afnemersIndicatieStapels", entry = "afnemersIndicatieStapel", type = Lo3Stapel.class)
    public List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> getAfnemersindicatieStapels() {
        return afnemersindicatieStapels;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3Afnemersindicatie)) {
            return false;
        }
        final Lo3Afnemersindicatie castOther = (Lo3Afnemersindicatie) other;
        return new EqualsBuilder().append(aNummer, castOther.aNummer).append(afnemersindicatieStapels, castOther.afnemersindicatieStapels).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(aNummer).append(afnemersindicatieStapels).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("aNummer", aNummer)
                .append("afnemersindicatieStapels", afnemersindicatieStapels)
                .toString();
    }
}
