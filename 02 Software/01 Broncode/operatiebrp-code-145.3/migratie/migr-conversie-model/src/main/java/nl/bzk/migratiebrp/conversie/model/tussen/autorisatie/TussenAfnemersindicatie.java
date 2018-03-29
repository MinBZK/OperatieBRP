/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.tussen.autorisatie;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpAfnemersindicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Migratie representatie voor een afnemersindicatie (BRP inhoud, LO3 historie).
 */
public final class TussenAfnemersindicatie {

    @Element(name = "partijCode", required = false)
    private final BrpPartijCode partijCode;

    @Element(name = "afnemersindicatieStapel", required = false)
    private final TussenStapel<BrpAfnemersindicatieInhoud> afnemersindicatieStapel;

    /**
     * Maak een nieuw BrpAfnemersindicatie object.
     * @param partijCode de partijCode van de partij (afnemer)
     * @param afnemersindicatieStapel de afnemersindicatie stapel
     */
    public TussenAfnemersindicatie(
            @Element(name = "partijCode", required = false) final BrpPartijCode partijCode,
            @Element(name = "afnemersindicatieStapel", required = false) final TussenStapel<BrpAfnemersindicatieInhoud> afnemersindicatieStapel) {
        this.partijCode = partijCode;
        this.afnemersindicatieStapel = afnemersindicatieStapel;
    }

    /**
     * Geef de waarde van partij code van TussenAfnemersindicatie.
     * @return de waarde van partij code van TussenAfnemersindicatie
     */
    public BrpPartijCode getPartijCode() {
        return partijCode;
    }

    /**
     * Geef de waarde van afnemersindicatie stapel van TussenAfnemersindicatie.
     * @return de waarde van afnemersindicatie stapel van TussenAfnemersindicatie
     */
    public TussenStapel<BrpAfnemersindicatieInhoud> getAfnemersindicatieStapel() {
        return afnemersindicatieStapel;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TussenAfnemersindicatie)) {
            return false;
        }
        final TussenAfnemersindicatie castOther = (TussenAfnemersindicatie) other;
        return new EqualsBuilder().append(partijCode, castOther.partijCode).append(afnemersindicatieStapel, castOther.afnemersindicatieStapel).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(partijCode).append(afnemersindicatieStapel).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("partijCode", partijCode)
                .append("afnemersindicatieStapel", afnemersindicatieStapel)
                .toString();
    }

}
