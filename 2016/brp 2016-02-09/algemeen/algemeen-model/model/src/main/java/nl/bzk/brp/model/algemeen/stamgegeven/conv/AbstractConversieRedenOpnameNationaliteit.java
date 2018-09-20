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
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3RedenOpnameNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

/**
 * Conversietabel ten behoeve van de conversie tussen Reden opnamen nationaliteit (LO3) en de Reden verkrijging van
 * Persoon \ Nationaliteit (BRP).
 *
 * Zie OT:"Conversie reden beëindigen nationaliteit" voor nadere toelichting.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractConversieRedenOpnameNationaliteit {

    @Id
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = LO3RedenOpnameNationaliteitAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rubr6310RdnOpnameNation"))
    @JsonProperty
    private LO3RedenOpnameNationaliteitAttribuut rubriek6310RedenOpnameNationaliteit;

    @ManyToOne
    @JoinColumn(name = "RdnVerk")
    @Fetch(value = FetchMode.JOIN)
    private RedenVerkrijgingNLNationaliteit redenVerkrijging;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractConversieRedenOpnameNationaliteit() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param rubriek6310RedenOpnameNationaliteit rubriek6310RedenOpnameNationaliteit van
     *            ConversieRedenOpnameNationaliteit.
     * @param redenVerkrijging redenVerkrijging van ConversieRedenOpnameNationaliteit.
     */
    protected AbstractConversieRedenOpnameNationaliteit(
        final LO3RedenOpnameNationaliteitAttribuut rubriek6310RedenOpnameNationaliteit,
        final RedenVerkrijgingNLNationaliteit redenVerkrijging)
    {
        this.rubriek6310RedenOpnameNationaliteit = rubriek6310RedenOpnameNationaliteit;
        this.redenVerkrijging = redenVerkrijging;

    }

    /**
     * Retourneert ID van Conversie reden opname nationaliteit.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Rubriek 6310 Reden opname nationaliteit van Conversie reden opname nationaliteit.
     *
     * @return Rubriek 6310 Reden opname nationaliteit.
     */
    public final LO3RedenOpnameNationaliteitAttribuut getRubriek6310RedenOpnameNationaliteit() {
        return rubriek6310RedenOpnameNationaliteit;
    }

    /**
     * Retourneert Reden verkrijging van Conversie reden opname nationaliteit.
     *
     * @return Reden verkrijging.
     */
    public final RedenVerkrijgingNLNationaliteit getRedenVerkrijging() {
        return redenVerkrijging;
    }

}
