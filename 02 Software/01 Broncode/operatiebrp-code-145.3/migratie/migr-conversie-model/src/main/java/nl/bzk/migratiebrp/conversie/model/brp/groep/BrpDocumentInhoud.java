/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import java.io.Serializable;
import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * BRP document.
 */
public final class BrpDocumentInhoud extends AbstractBrpGroepInhoud implements Serializable {

    private static final long serialVersionUID = 1L;

    private final BrpSoortDocumentCode soortDocumentCode;
    private final BrpString aktenummer;
    private final BrpString omschrijving;
    private final BrpPartijCode partijCode;

    /**
     * Maakt een BrpDocument.
     * @param soortDocumentCode soort document
     * @param aktenummer aktenummer
     * @param omschrijving omschrijving
     * @param partijCode partij
     */
    public BrpDocumentInhoud(
            @Element(name = "soortDocumentCode") final BrpSoortDocumentCode soortDocumentCode,
            @Element(name = "aktenummer") final BrpString aktenummer,
            @Element(name = "omschrijving") final BrpString omschrijving,
            @Element(name = "partijCode") final BrpPartijCode partijCode) {
        super();
        this.soortDocumentCode = soortDocumentCode;
        this.aktenummer = aktenummer;
        this.omschrijving = omschrijving;
        this.partijCode = partijCode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !BrpValidatie.isEenParameterGevuld(soortDocumentCode, aktenummer, omschrijving, partijCode);
    }

    /**
     * Geef de waarde van soort document code van BrpDocumentInhoud.
     * @return de waarde van soort document code van BrpDocumentInhoud
     */
    @Element(name = "soortDocumentCode")
    public BrpSoortDocumentCode getSoortDocumentCode() {
        return soortDocumentCode;
    }

    /**
     * Geef de waarde van aktenummer van BrpDocumentInhoud.
     * @return de waarde van aktenummer van BrpDocumentInhoud
     */
    @Element(name = "aktenummer")
    public BrpString getAktenummer() {
        return aktenummer;
    }

    /**
     * Geef de waarde van omschrijving van BrpDocumentInhoud.
     * @return de waarde van omschrijving van BrpDocumentInhoud
     */
    @Element(name = "omschrijving")
    public BrpString getOmschrijving() {
        return omschrijving;
    }

    /**
     * Geef de waarde van partij code van BrpDocumentInhoud.
     * @return de waarde van partij code van BrpDocumentInhoud
     */
    @Element(name = "partijCode")
    public BrpPartijCode getPartijCode() {
        return partijCode;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpDocumentInhoud)) {
            return false;
        }
        final BrpDocumentInhoud castOther = (BrpDocumentInhoud) other;
        return new EqualsBuilder().append(soortDocumentCode, castOther.soortDocumentCode)
                .append(aktenummer, castOther.aktenummer)
                .append(omschrijving, castOther.omschrijving)
                .append(partijCode, castOther.partijCode)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(soortDocumentCode)
                .append(aktenummer)
                .append(omschrijving)
                .append(partijCode)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("soortDocumentCode", soortDocumentCode)
                .append("aktenummer", aktenummer)
                .append("omschrijving", omschrijving)
                .append("partijCode", partijCode)
                .toString();
    }
}
