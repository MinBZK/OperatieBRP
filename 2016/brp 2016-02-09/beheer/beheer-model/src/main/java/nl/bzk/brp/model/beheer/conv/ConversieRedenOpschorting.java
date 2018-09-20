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
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3OmschrijvingRedenOpschortingBijhoudingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;

/**
 * Conversietabel ten behoeve van de reden opschorting (LO3) enerzijds, en de reden opschorting (BRP) anderzijds.
 *
 * Het betreft hier de waarden die één-op-één worden overgezet; de conversiesoftware houdt op een andere wijze rekening
 * met de situatie dat iets dat wel een reden opschorting is in de GBA, geen reden opschorting is in de BRP.
 *
 *
 *
 */
@Entity(name = "beheer.ConversieRedenOpschorting")
@Table(schema = "Conv", name = "ConvRdnOpschorting")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class ConversieRedenOpschorting {

    @Id
    @SequenceGenerator(name = "CONVERSIEREDENOPSCHORTING", sequenceName = "Conv.seq_ConvRdnOpschorting")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CONVERSIEREDENOPSCHORTING")
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = LO3OmschrijvingRedenOpschortingBijhoudingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rubr6720OmsRdnOpschortingBij"))
    private LO3OmschrijvingRedenOpschortingBijhoudingAttribuut rubriek6720OmschrijvingRedenOpschortingBijhouding;

    @Column(name = "NadereBijhaard")
    @Enumerated
    private NadereBijhoudingsaard nadereBijhoudingsaard;

    /**
     * Retourneert ID van Conversie reden opschorting.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Rubriek 6720 Omschrijving reden opschorting bijhouding van Conversie reden opschorting.
     *
     * @return Rubriek 6720 Omschrijving reden opschorting bijhouding.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public LO3OmschrijvingRedenOpschortingBijhoudingAttribuut getRubriek6720OmschrijvingRedenOpschortingBijhouding() {
        return rubriek6720OmschrijvingRedenOpschortingBijhouding;
    }

    /**
     * Retourneert Nadere bijhoudingsaard van Conversie reden opschorting.
     *
     * @return Nadere bijhoudingsaard.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NadereBijhoudingsaard getNadereBijhoudingsaard() {
        return nadereBijhoudingsaard;
    }

    /**
     * Zet ID van Conversie reden opschorting.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Rubriek 6720 Omschrijving reden opschorting bijhouding van Conversie reden opschorting.
     *
     * @param pRubriek6720OmschrijvingRedenOpschortingBijhouding Rubriek 6720 Omschrijving reden opschorting bijhouding.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setRubriek6720OmschrijvingRedenOpschortingBijhouding(
        final LO3OmschrijvingRedenOpschortingBijhoudingAttribuut pRubriek6720OmschrijvingRedenOpschortingBijhouding)
    {
        this.rubriek6720OmschrijvingRedenOpschortingBijhouding = pRubriek6720OmschrijvingRedenOpschortingBijhouding;
    }

    /**
     * Zet Nadere bijhoudingsaard van Conversie reden opschorting.
     *
     * @param pNadereBijhoudingsaard Nadere bijhoudingsaard.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNadereBijhoudingsaard(final NadereBijhoudingsaard pNadereBijhoudingsaard) {
        this.nadereBijhoudingsaard = pNadereBijhoudingsaard;
    }

}
