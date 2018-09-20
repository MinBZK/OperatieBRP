/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.autorisatie;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpAfnemersindicatieInhoud;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert het BRP objecttype Afnemers indicatie.
 *
 * Deze class is immutable en thread-safe.
 */
public final class BrpAfnemersindicatie {

    @Element(name = "partijCode", required = false)
    private final BrpPartijCode partijCode;

    @Element(name = "afnemersindicatieStapel", required = false)
    private final BrpStapel<BrpAfnemersindicatieInhoud> afnemersindicatieStapel;

    @Element(name = "leveringautorisatie", required = false)
    private final String leveringautorisatie;

    /**
     * Maak een nieuw BrpAfnemersindicatie object.
     *
     * @param partijCode
     *            de partijCode van de partij (afnemer)
     * @param afnemersindicatieStapel
     *            de afnemersindicatie stapel
     * @param leveringautorisatie
     *            levering autorisatie (alleen bij lezen uit database voor controle)
     */
    public BrpAfnemersindicatie(
        @Element(name = "partijCode", required = false) final BrpPartijCode partijCode,
        @Element(name = "afnemersindicatieStapel", required = false) final BrpStapel<BrpAfnemersindicatieInhoud> afnemersindicatieStapel,
        @Element(name = "leveringautorisatie", required = false) final String leveringautorisatie)
    {
        super();
        this.partijCode = partijCode;
        this.afnemersindicatieStapel = afnemersindicatieStapel;
        this.leveringautorisatie = leveringautorisatie;
    }

    /**
     * Geef de waarde van partij code.
     *
     * @return partij code
     */
    public BrpPartijCode getPartijCode() {
        return partijCode;
    }

    /**
     * Geef de waarde van afnemersindicatie stapel.
     *
     * @return afnemersindicatie stapel
     */
    public BrpStapel<BrpAfnemersindicatieInhoud> getAfnemersindicatieStapel() {
        return afnemersindicatieStapel;
    }

    /**
     * Geef de waarde van leveringautorisatie (alleen gevuld na lezen afnemersindicatie tbv test).
     *
     * @return leveringautorisatie
     */
    public String getLeveringautorisatie() {
        return leveringautorisatie;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpAfnemersindicatie)) {
            return false;
        }
        final BrpAfnemersindicatie castOther = (BrpAfnemersindicatie) other;
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
                                                                          .append("leveringautorisatie", leveringautorisatie)
                                                                          .toString();
    }

}
