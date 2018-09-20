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
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3RedenOpnameNationaliteitAttribuut;
import nl.bzk.brp.model.beheer.kern.RedenVerkrijgingNLNationaliteit;

/**
 * Conversietabel ten behoeve van de conversie tussen Reden opnamen nationaliteit (LO3) en de Reden verkrijging van
 * Persoon \ Nationaliteit (BRP).
 *
 * Zie OT:"Conversie reden beëindigen nationaliteit" voor nadere toelichting.
 *
 *
 *
 */
@Entity(name = "beheer.ConversieRedenOpnameNationaliteit")
@Table(schema = "Conv", name = "ConvRdnOpnameNation")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class ConversieRedenOpnameNationaliteit {

    @Id
    @SequenceGenerator(name = "CONVERSIEREDENOPNAMENATIONALITEIT", sequenceName = "Conv.seq_ConvRdnOpnameNation")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CONVERSIEREDENOPNAMENATIONALITEIT")
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = LO3RedenOpnameNationaliteitAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rubr6310RdnOpnameNation"))
    private LO3RedenOpnameNationaliteitAttribuut rubriek6310RedenOpnameNationaliteit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RdnVerk")
    private RedenVerkrijgingNLNationaliteit redenVerkrijging;

    /**
     * Retourneert ID van Conversie reden opname nationaliteit.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Rubriek 6310 Reden opname nationaliteit van Conversie reden opname nationaliteit.
     *
     * @return Rubriek 6310 Reden opname nationaliteit.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public LO3RedenOpnameNationaliteitAttribuut getRubriek6310RedenOpnameNationaliteit() {
        return rubriek6310RedenOpnameNationaliteit;
    }

    /**
     * Retourneert Reden verkrijging van Conversie reden opname nationaliteit.
     *
     * @return Reden verkrijging.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public RedenVerkrijgingNLNationaliteit getRedenVerkrijging() {
        return redenVerkrijging;
    }

    /**
     * Zet ID van Conversie reden opname nationaliteit.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Rubriek 6310 Reden opname nationaliteit van Conversie reden opname nationaliteit.
     *
     * @param pRubriek6310RedenOpnameNationaliteit Rubriek 6310 Reden opname nationaliteit.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setRubriek6310RedenOpnameNationaliteit(final LO3RedenOpnameNationaliteitAttribuut pRubriek6310RedenOpnameNationaliteit) {
        this.rubriek6310RedenOpnameNationaliteit = pRubriek6310RedenOpnameNationaliteit;
    }

    /**
     * Zet Reden verkrijging van Conversie reden opname nationaliteit.
     *
     * @param pRedenVerkrijging Reden verkrijging.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setRedenVerkrijging(final RedenVerkrijgingNLNationaliteit pRedenVerkrijging) {
        this.redenVerkrijging = pRedenVerkrijging;
    }

}
