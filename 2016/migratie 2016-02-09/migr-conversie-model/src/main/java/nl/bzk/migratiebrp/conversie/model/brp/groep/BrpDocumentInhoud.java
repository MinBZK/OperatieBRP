/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import java.io.Serializable;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * BRP document.
 */
public final class BrpDocumentInhoud extends AbstractBrpGroepInhoud implements Serializable {

    private static final long serialVersionUID = 1L;

    private final BrpSoortDocumentCode soortDocumentCode;
    private final BrpString identificatie;
    private final BrpString aktenummer;
    private final BrpString omschrijving;
    private final BrpPartijCode partijCode;

    /**
     * Maakt een BrpDocument.
     *
     * @param soortDocumentCode
     *            soort document
     * @param identificatie
     *            identificatie
     * @param aktenummer
     *            aktenummer
     * @param omschrijving
     *            omschrijving
     * @param partijCode
     *            partij
     */
    public BrpDocumentInhoud(
        @Element(name = "soortDocumentCode", required = false) final BrpSoortDocumentCode soortDocumentCode,
        @Element(name = "identificatie", required = false) final BrpString identificatie,
        @Element(name = "aktenummer", required = false) final BrpString aktenummer,
        @Element(name = "omschrijving", required = false) final BrpString omschrijving,
        @Element(name = "partijCode", required = false) final BrpPartijCode partijCode)
    {
        super();
        this.soortDocumentCode = soortDocumentCode;
        this.identificatie = identificatie;
        this.aktenummer = aktenummer;
        this.omschrijving = omschrijving;
        this.partijCode = partijCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLeeg() {
        return !Validatie.isEenParameterGevuld(soortDocumentCode, identificatie, aktenummer, omschrijving, partijCode);
    }

    /**
     * Geef de waarde van soort document code.
     *
     * @return the soortDocumentCode
     */
    @Element(name = "soortDocumentCode", required = false)
    public BrpSoortDocumentCode getSoortDocumentCode() {
        return soortDocumentCode;
    }

    /**
     * Geef de waarde van identificatie.
     *
     * @return the identificatie
     */
    @Element(name = "identificatie", required = false)
    public BrpString getIdentificatie() {
        return identificatie;
    }

    /**
     * Geef de waarde van aktenummer.
     *
     * @return the aktenummer
     */
    @Element(name = "aktenummer", required = false)
    public BrpString getAktenummer() {
        return aktenummer;
    }

    /**
     * Geef de waarde van omschrijving.
     *
     * @return the omschrijving
     */
    @Element(name = "omschrijving", required = false)
    public BrpString getOmschrijving() {
        return omschrijving;
    }

    /**
     * Geef de waarde van partij code.
     *
     * @return the partijCode
     */
    @Element(name = "partijCode", required = false)
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
                                  .append(identificatie, castOther.identificatie)
                                  .append(aktenummer, castOther.aktenummer)
                                  .append(omschrijving, castOther.omschrijving)
                                  .append(partijCode, castOther.partijCode)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(soortDocumentCode)
                                    .append(identificatie)
                                    .append(aktenummer)
                                    .append(omschrijving)
                                    .append(partijCode)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("soortDocumentCode", soortDocumentCode)
                                                                          .append("identificatie", identificatie)
                                                                          .append("aktenummer", aktenummer)
                                                                          .append("omschrijving", omschrijving)
                                                                          .append("partijCode", partijCode)
                                                                          .toString();
    }
}
