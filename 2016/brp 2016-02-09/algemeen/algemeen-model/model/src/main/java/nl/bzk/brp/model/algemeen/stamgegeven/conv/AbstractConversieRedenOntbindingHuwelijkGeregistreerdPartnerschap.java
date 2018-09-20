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
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3RedenOntbindingNietigverklaringHuwelijkGeregistreerdPartnerschapAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatie;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

/**
 * Conversietabel ten behoeve van de reden ontbinding/nietigverklaring huwelijk/geregistreerd partnerschap (LO3)
 * enerzijds, en de reden einde relatie (BRP) anderzijds.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap {

    @Id
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = LO3RedenOntbindingNietigverklaringHuwelijkGeregistreerdPartnerschapAttribuut.WAARDE_VELD_NAAM, column = @Column(
            name = "Rubr0741RdnOntbindingHuwelij"))
    @JsonProperty
    private LO3RedenOntbindingNietigverklaringHuwelijkGeregistreerdPartnerschapAttribuut rubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap;

    @ManyToOne
    @JoinColumn(name = "RdnEindeRelatie")
    @Fetch(value = FetchMode.JOIN)
    private RedenEindeRelatie redenEindeRelatie;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param rubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap
     *            rubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap van
     *            ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap.
     * @param redenEindeRelatie redenEindeRelatie van ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap.
     */
    protected AbstractConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap(
        final LO3RedenOntbindingNietigverklaringHuwelijkGeregistreerdPartnerschapAttribuut rubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap,
        final RedenEindeRelatie redenEindeRelatie)
    {
        this.rubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap = rubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap;
        this.redenEindeRelatie = redenEindeRelatie;

    }

    /**
     * Retourneert ID van Conversie reden ontbinding huwelijk/geregistreerd partnerschap.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Rubriek 0741 Reden ontbinding huwelijk/geregistreerd partnerschap van Conversie reden ontbinding
     * huwelijk/geregistreerd partnerschap.
     *
     * @return Rubriek 0741 Reden ontbinding huwelijk/geregistreerd partnerschap.
     */
    public final LO3RedenOntbindingNietigverklaringHuwelijkGeregistreerdPartnerschapAttribuut getRubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap()
    {
        return rubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap;
    }

    /**
     * Retourneert Reden einde relatie van Conversie reden ontbinding huwelijk/geregistreerd partnerschap.
     *
     * @return Reden einde relatie.
     */
    public final RedenEindeRelatie getRedenEindeRelatie() {
        return redenEindeRelatie;
    }

}
