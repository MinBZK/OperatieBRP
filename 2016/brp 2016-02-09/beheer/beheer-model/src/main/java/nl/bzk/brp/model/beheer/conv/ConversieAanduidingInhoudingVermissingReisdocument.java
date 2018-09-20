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
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3AanduidingInhoudingDanWelVermissingNLReisdocumentAttribuut;
import nl.bzk.brp.model.beheer.kern.AanduidingInhoudingVermissingReisdocument;

/**
 * Conversietabel ten behoeve van de reden inhouding/vermissing reisdocument (LO3) enerzijds, en de reden vervallen
 * reisdocument (BRP) anderzijds.
 *
 *
 *
 */
@Entity(name = "beheer.ConversieAanduidingInhoudingVermissingReisdocument")
@Table(schema = "Conv", name = "ConvAandInhingVermissingReis")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class ConversieAanduidingInhoudingVermissingReisdocument {

    @Id
    @SequenceGenerator(name = "CONVERSIEAANDUIDINGINHOUDINGVERMISSINGREISDOCUMENT", sequenceName = "Conv.seq_ConvAandInhingVermissingReis")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CONVERSIEAANDUIDINGINHOUDINGVERMISSINGREISDOCUMENT")
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = LO3AanduidingInhoudingDanWelVermissingNLReisdocumentAttribuut.WAARDE_VELD_NAAM, column = @Column(
            name = "Rubr3570AandInhingDanWelVerm"))
    private LO3AanduidingInhoudingDanWelVermissingNLReisdocumentAttribuut rubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AandInhingVermissingReisdoc")
    private AanduidingInhoudingVermissingReisdocument aanduidingInhoudingVermissingReisdocument;

    /**
     * Retourneert ID van Conversie aanduiding inhouding/vermissing reisdocument.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Rubriek 3570 Aanduiding inhouding dan wel vermissing Nederlands reisdocument van Conversie aanduiding
     * inhouding/vermissing reisdocument.
     *
     * @return Rubriek 3570 Aanduiding inhouding dan wel vermissing Nederlands reisdocument.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public LO3AanduidingInhoudingDanWelVermissingNLReisdocumentAttribuut getRubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument() {
        return rubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument;
    }

    /**
     * Retourneert Aanduiding inhouding/vermissing reisdocument van Conversie aanduiding inhouding/vermissing
     * reisdocument.
     *
     * @return Aanduiding inhouding/vermissing reisdocument.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public AanduidingInhoudingVermissingReisdocument getAanduidingInhoudingVermissingReisdocument() {
        return aanduidingInhoudingVermissingReisdocument;
    }

    /**
     * Zet ID van Conversie aanduiding inhouding/vermissing reisdocument.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Rubriek 3570 Aanduiding inhouding dan wel vermissing Nederlands reisdocument van Conversie aanduiding
     * inhouding/vermissing reisdocument.
     *
     * @param pRubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument Rubriek 3570 Aanduiding inhouding
     *            dan wel vermissing Nederlands reisdocument.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setRubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument(
        final LO3AanduidingInhoudingDanWelVermissingNLReisdocumentAttribuut pRubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument)
    {
        this.rubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument = pRubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument;
    }

    /**
     * Zet Aanduiding inhouding/vermissing reisdocument van Conversie aanduiding inhouding/vermissing reisdocument.
     *
     * @param pAanduidingInhoudingVermissingReisdocument Aanduiding inhouding/vermissing reisdocument.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setAanduidingInhoudingVermissingReisdocument(final AanduidingInhoudingVermissingReisdocument pAanduidingInhoudingVermissingReisdocument) {
        this.aanduidingInhoudingVermissingReisdocument = pAanduidingInhoudingVermissingReisdocument;
    }

}
