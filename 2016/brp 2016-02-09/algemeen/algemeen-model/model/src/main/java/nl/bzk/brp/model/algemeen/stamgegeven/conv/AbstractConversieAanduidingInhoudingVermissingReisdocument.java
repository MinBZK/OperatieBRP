/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.conv;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3AanduidingInhoudingDanWelVermissingNLReisdocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingInhoudingVermissingReisdocument;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

/**
 * Conversietabel ten behoeve van de reden inhouding/vermissing reisdocument (LO3) enerzijds, en de reden vervallen
 * reisdocument (BRP) anderzijds.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractConversieAanduidingInhoudingVermissingReisdocument {

    @Id
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = LO3AanduidingInhoudingDanWelVermissingNLReisdocumentAttribuut.WAARDE_VELD_NAAM, column = @Column(
            name = "Rubr3570AandInhingDanWelVerm"))
    @JsonProperty
    private LO3AanduidingInhoudingDanWelVermissingNLReisdocumentAttribuut rubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument;

    @ManyToOne
    @JoinColumn(name = "AandInhingVermissingReisdoc")
    @Fetch(value = FetchMode.JOIN)
    private AanduidingInhoudingVermissingReisdocument aanduidingInhoudingVermissingReisdocument;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractConversieAanduidingInhoudingVermissingReisdocument() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param rubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument
     *            rubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument van
     *            ConversieAanduidingInhoudingVermissingReisdocument.
     * @param aanduidingInhoudingVermissingReisdocument aanduidingInhoudingVermissingReisdocument van
     *            ConversieAanduidingInhoudingVermissingReisdocument.
     */
    protected AbstractConversieAanduidingInhoudingVermissingReisdocument(
        final LO3AanduidingInhoudingDanWelVermissingNLReisdocumentAttribuut rubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument,
        final AanduidingInhoudingVermissingReisdocument aanduidingInhoudingVermissingReisdocument)
    {
        this.rubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument = rubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument;
        this.aanduidingInhoudingVermissingReisdocument = aanduidingInhoudingVermissingReisdocument;

    }

    /**
     * Retourneert ID van Conversie aanduiding inhouding/vermissing reisdocument.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Rubriek 3570 Aanduiding inhouding dan wel vermissing Nederlands reisdocument van Conversie aanduiding
     * inhouding/vermissing reisdocument.
     *
     * @return Rubriek 3570 Aanduiding inhouding dan wel vermissing Nederlands reisdocument.
     */
    public final LO3AanduidingInhoudingDanWelVermissingNLReisdocumentAttribuut getRubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument() {
        return rubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument;
    }

    /**
     * Retourneert Aanduiding inhouding/vermissing reisdocument van Conversie aanduiding inhouding/vermissing
     * reisdocument.
     *
     * @return Aanduiding inhouding/vermissing reisdocument.
     */
    public final AanduidingInhoudingVermissingReisdocument getAanduidingInhoudingVermissingReisdocument() {
        return aanduidingInhoudingVermissingReisdocument;
    }

}
