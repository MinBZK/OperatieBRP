/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.algemeenbrp.util.xml.annotation.Root;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van de BRP objecttype Persoon Verificatie.
 *
 * Deze class is immutable en threadsafe.
 */
@Root(strict = false)
public final class BrpVerificatieInhoud extends AbstractBrpGroepInhoud {
    @Element(name = "partij", required = true)
    private final BrpPartijCode partij;
    @Element(name = "soort", required = true)
    private final BrpString soort;
    @Element(name = "datum", required = true)
    private final BrpDatum datum;

    /**
     * Maak een BrpVerificatieInhoud object.
     * @param partij de verifierende partij
     * @param soort de soort verificatie
     * @param datum de datum van de verificatie
     */
    public BrpVerificatieInhoud(
            @Element(name = "partij", required = true) final BrpPartijCode partij,
            @Element(name = "soort", required = true) final BrpString soort,
            @Element(name = "datum", required = true) final BrpDatum datum) {
        this.partij = partij;
        this.soort = soort;
        this.datum = datum;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !BrpValidatie.isEenParameterGevuld(partij, soort, datum);
    }

    /**
     * Geef de waarde van partij van BrpVerificatieInhoud.
     * @return de waarde van partij van BrpVerificatieInhoud
     */
    public BrpPartijCode getPartij() {
        return partij;
    }

    /**
     * Geef de waarde van soort van BrpVerificatieInhoud.
     * @return de waarde van soort van BrpVerificatieInhoud
     */
    public BrpString getSoort() {
        return soort;
    }

    /**
     * Geef de waarde van datum van BrpVerificatieInhoud.
     * @return de waarde van datum van BrpVerificatieInhoud
     */
    public BrpDatum getDatum() {
        return datum;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpVerificatieInhoud)) {
            return false;
        }
        final BrpVerificatieInhoud castOther = (BrpVerificatieInhoud) other;
        return new EqualsBuilder().append(partij, castOther.partij).append(soort, castOther.soort).append(datum, castOther.datum).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(partij).append(soort).append(datum).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("partij", partij)
                .append("soort", soort)
                .append("datum", datum)
                .toString();
    }
}
