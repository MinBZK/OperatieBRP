/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.conv;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3NederlandsReisdocumentAttribuut;
import nl.bzk.brp.model.beheer.kern.SoortNederlandsReisdocument;

/**
 * Conversietabel ten behoeve van de conversie tussen Nederlands reisdocument (LO3) enerzijds, en soort Nederlands
 * reisdocument (BRP) anderzijds.
 *
 *
 *
 */
@Entity(name = "beheer.ConversieSoortNLReisdocument")
@Table(schema = "Conv", name = "ConvSrtNLReisdoc")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class ConversieSoortNLReisdocument {

    @Id
    @SequenceGenerator(name = "CONVERSIESOORTNLREISDOCUMENT", sequenceName = "Conv.seq_ConvSrtNLReisdoc")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CONVERSIESOORTNLREISDOCUMENT")
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = LO3NederlandsReisdocumentAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rubr3511NLReisdoc"))
    private LO3NederlandsReisdocumentAttribuut rubriek3511NederlandsReisdocument;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SrtNLReisdoc")
    private SoortNederlandsReisdocument soortNederlandsReisdocument;

    /**
     * Retourneert ID van Conversie soort NL reisdocument.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Rubriek 3511 Nederlands reisdocument van Conversie soort NL reisdocument.
     *
     * @return Rubriek 3511 Nederlands reisdocument.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public LO3NederlandsReisdocumentAttribuut getRubriek3511NederlandsReisdocument() {
        return rubriek3511NederlandsReisdocument;
    }

    /**
     * Retourneert Soort Nederlands reisdocument van Conversie soort NL reisdocument.
     *
     * @return Soort Nederlands reisdocument.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public SoortNederlandsReisdocument getSoortNederlandsReisdocument() {
        return soortNederlandsReisdocument;
    }

    /**
     * Zet ID van Conversie soort NL reisdocument.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Rubriek 3511 Nederlands reisdocument van Conversie soort NL reisdocument.
     *
     * @param pRubriek3511NederlandsReisdocument Rubriek 3511 Nederlands reisdocument.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setRubriek3511NederlandsReisdocument(final LO3NederlandsReisdocumentAttribuut pRubriek3511NederlandsReisdocument) {
        this.rubriek3511NederlandsReisdocument = pRubriek3511NederlandsReisdocument;
    }

    /**
     * Zet Soort Nederlands reisdocument van Conversie soort NL reisdocument.
     *
     * @param pSoortNederlandsReisdocument Soort Nederlands reisdocument.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setSoortNederlandsReisdocument(final SoortNederlandsReisdocument pSoortNederlandsReisdocument) {
        this.soortNederlandsReisdocument = pSoortNederlandsReisdocument;
    }

}
