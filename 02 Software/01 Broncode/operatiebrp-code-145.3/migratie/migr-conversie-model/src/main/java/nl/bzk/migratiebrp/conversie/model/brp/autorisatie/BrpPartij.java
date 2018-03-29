/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.autorisatie;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpPartijInhoud;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert het BRP objecttype Partij.
 *
 * Deze class is immutable en thread-safe.
 */
public final class BrpPartij {

    @Element(name = "id", required = false)
    private final Long id;
    @Element(name = "naam", required = false)
    private final String naam;
    @Element(name = "partijCode", required = false)
    private final BrpPartijCode partijCode;
    @Element(name = "partijStapel", required = false)
    private final BrpStapel<BrpPartijInhoud> partijStapel;

    /**
     * Maak een nieuw BrpPartij object.
     * @param id de id van de partij
     * @param naam de naam van de partij
     * @param partijCode de code van de partij
     * @param partijStapel de partij stapels
     */
    public BrpPartij(
            @Element(name = "id", required = false) final Long id,
            @Element(name = "naam", required = false) final String naam,
            @Element(name = "partijCode", required = false) final BrpPartijCode partijCode,
            @Element(name = "partijStapel", required = false) final BrpStapel<BrpPartijInhoud> partijStapel) {
        super();
        this.id = id;
        this.naam = naam;
        this.partijCode = partijCode;
        this.partijStapel = partijStapel;
    }

    /**
     * Geef de waarde van id.
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Geef de waarde van naam.
     * @return naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Geef de waarde van partij code.
     * @return partij code
     */
    public BrpPartijCode getPartijCode() {
        return partijCode;
    }

    /**
     * Geef de waarde van partij stapel.
     * @return partij stapel
     */
    public BrpStapel<BrpPartijInhoud> getPartijStapel() {
        return partijStapel;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpPartij)) {
            return false;
        }
        final BrpPartij castOther = (BrpPartij) other;
        return new EqualsBuilder().append(naam, castOther.naam)
                .append(partijCode, castOther.partijCode)
                .append(partijStapel, castOther.partijStapel)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(naam).append(partijCode).append(partijStapel).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("naam", naam)
                .append("partijCode", partijCode)
                .append("partijStapel", partijStapel)
                .toString();
    }

}
