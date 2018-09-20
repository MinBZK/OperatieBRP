/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import java.io.Serializable;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * BRP document.
 * 
 * 
 * 
 */
public final class BrpDocumentInhoud extends AbstractBrpGroepInhoud implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Document omschrijving bij extra documentatie voor ouder. */
    public static final String EXTRA_DOCUMENT_OUDER_OMSCHRIJVING = "PL - Ouder ";
    /** Document omschrijving bij extra documentatie voor verbintenis. */
    public static final String EXTRA_DOCUMENT_VERBINTENIS_OMSCHRIJVING = "PL - Verbintenis stapel ";

    private final BrpSoortDocumentCode soortDocumentCode;
    private final String identificatie;
    private final String aktenummer;
    private final String omschrijving;
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
            @Element(name = "identificatie", required = false) final String identificatie,
            @Element(name = "aktenummer", required = false) final String aktenummer,
            @Element(name = "omschrijving", required = false) final String omschrijving,
            @Element(name = "partijCode", required = false) final BrpPartijCode partijCode) {
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
        return !ValidationUtils.isEenParameterGevuld(soortDocumentCode, identificatie, aktenummer, omschrijving,
                partijCode);
    }

    /**
     * @return the soortDocumentCode
     */
    @Element(name = "soortDocumentCode", required = false)
    public BrpSoortDocumentCode getSoortDocumentCode() {
        return soortDocumentCode;
    }

    /**
     * @return the identificatie
     */
    @Element(name = "identificatie", required = false)
    public String getIdentificatie() {
        return identificatie;
    }

    /**
     * @return the aktenummer
     */
    @Element(name = "aktenummer", required = false)
    public String getAktenummer() {
        return aktenummer;
    }

    /**
     * @return the omschrijving
     */
    @Element(name = "omschrijving", required = false)
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
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
                .append(identificatie, castOther.identificatie).append(aktenummer, castOther.aktenummer)
                .append(omschrijving, castOther.omschrijving).append(partijCode, castOther.partijCode).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(soortDocumentCode).append(identificatie).append(aktenummer)
                .append(omschrijving).append(partijCode).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("soortDocumentCode", soortDocumentCode).append("identificatie", identificatie)
                .append("aktenummer", aktenummer).append("omschrijving", omschrijving)
                .append("partijCode", partijCode).toString();
    }
}
