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
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3OmschrijvingRedenOpschortingBijhoudingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Immutable;

/**
 * Conversietabel ten behoeve van de reden opschorting (LO3) enerzijds, en de reden opschorting (BRP) anderzijds.
 *
 * Het betreft hier de waarden die één-op-één worden overgezet; de conversiesoftware houdt op een andere wijze rekening
 * met de situatie dat iets dat wel een reden opschorting is in de GBA, geen reden opschorting is in de BRP.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractConversieRedenOpschorting {

    @Id
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = LO3OmschrijvingRedenOpschortingBijhoudingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rubr6720OmsRdnOpschortingBij"))
    @JsonProperty
    private LO3OmschrijvingRedenOpschortingBijhoudingAttribuut rubriek6720OmschrijvingRedenOpschortingBijhouding;

    @Column(name = "NadereBijhaard")
    private NadereBijhoudingsaard nadereBijhoudingsaard;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractConversieRedenOpschorting() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param rubriek6720OmschrijvingRedenOpschortingBijhouding rubriek6720OmschrijvingRedenOpschortingBijhouding van
     *            ConversieRedenOpschorting.
     * @param nadereBijhoudingsaard nadereBijhoudingsaard van ConversieRedenOpschorting.
     */
    protected AbstractConversieRedenOpschorting(
        final LO3OmschrijvingRedenOpschortingBijhoudingAttribuut rubriek6720OmschrijvingRedenOpschortingBijhouding,
        final NadereBijhoudingsaard nadereBijhoudingsaard)
    {
        this.rubriek6720OmschrijvingRedenOpschortingBijhouding = rubriek6720OmschrijvingRedenOpschortingBijhouding;
        this.nadereBijhoudingsaard = nadereBijhoudingsaard;

    }

    /**
     * Retourneert ID van Conversie reden opschorting.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Rubriek 6720 Omschrijving reden opschorting bijhouding van Conversie reden opschorting.
     *
     * @return Rubriek 6720 Omschrijving reden opschorting bijhouding.
     */
    public final LO3OmschrijvingRedenOpschortingBijhoudingAttribuut getRubriek6720OmschrijvingRedenOpschortingBijhouding() {
        return rubriek6720OmschrijvingRedenOpschortingBijhouding;
    }

    /**
     * Retourneert Nadere bijhoudingsaard van Conversie reden opschorting.
     *
     * @return Nadere bijhoudingsaard.
     */
    public final NadereBijhoudingsaard getNadereBijhoudingsaard() {
        return nadereBijhoudingsaard;
    }

}
