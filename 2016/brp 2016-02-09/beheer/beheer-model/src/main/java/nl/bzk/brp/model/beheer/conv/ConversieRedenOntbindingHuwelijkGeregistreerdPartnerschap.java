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
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3RedenOntbindingNietigverklaringHuwelijkGeregistreerdPartnerschapAttribuut;
import nl.bzk.brp.model.beheer.kern.RedenEindeRelatie;

/**
 * Conversietabel ten behoeve van de reden ontbinding/nietigverklaring huwelijk/geregistreerd partnerschap (LO3)
 * enerzijds, en de reden einde relatie (BRP) anderzijds.
 *
 *
 *
 */
@Entity(name = "beheer.ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap")
@Table(schema = "Conv", name = "ConvRdnOntbindingHuwelijkGer")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap {

    @Id
    @SequenceGenerator(name = "CONVERSIEREDENONTBINDINGHUWELIJKGEREGISTREERDPARTNERSCHAP", sequenceName = "Conv.seq_ConvRdnOntbindingHuwelijkGer")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CONVERSIEREDENONTBINDINGHUWELIJKGEREGISTREERDPARTNERSCHAP")
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = LO3RedenOntbindingNietigverklaringHuwelijkGeregistreerdPartnerschapAttribuut.WAARDE_VELD_NAAM, column = @Column(
            name = "Rubr0741RdnOntbindingHuwelij"))
    private LO3RedenOntbindingNietigverklaringHuwelijkGeregistreerdPartnerschapAttribuut rubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RdnEindeRelatie")
    private RedenEindeRelatie redenEindeRelatie;

    /**
     * Retourneert ID van Conversie reden ontbinding huwelijk/geregistreerd partnerschap.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Rubriek 0741 Reden ontbinding huwelijk/geregistreerd partnerschap van Conversie reden ontbinding
     * huwelijk/geregistreerd partnerschap.
     *
     * @return Rubriek 0741 Reden ontbinding huwelijk/geregistreerd partnerschap.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public LO3RedenOntbindingNietigverklaringHuwelijkGeregistreerdPartnerschapAttribuut getRubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap() {
        return rubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap;
    }

    /**
     * Retourneert Reden einde relatie van Conversie reden ontbinding huwelijk/geregistreerd partnerschap.
     *
     * @return Reden einde relatie.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public RedenEindeRelatie getRedenEindeRelatie() {
        return redenEindeRelatie;
    }

    /**
     * Zet ID van Conversie reden ontbinding huwelijk/geregistreerd partnerschap.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Rubriek 0741 Reden ontbinding huwelijk/geregistreerd partnerschap van Conversie reden ontbinding
     * huwelijk/geregistreerd partnerschap.
     *
     * @param pRubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap Rubriek 0741 Reden ontbinding
     *            huwelijk/geregistreerd partnerschap.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setRubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap(
        final LO3RedenOntbindingNietigverklaringHuwelijkGeregistreerdPartnerschapAttribuut pRubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap)
    {
        this.rubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap = pRubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap;
    }

    /**
     * Zet Reden einde relatie van Conversie reden ontbinding huwelijk/geregistreerd partnerschap.
     *
     * @param pRedenEindeRelatie Reden einde relatie.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setRedenEindeRelatie(final RedenEindeRelatie pRedenEindeRelatie) {
        this.redenEindeRelatie = pRedenEindeRelatie;
    }

}
