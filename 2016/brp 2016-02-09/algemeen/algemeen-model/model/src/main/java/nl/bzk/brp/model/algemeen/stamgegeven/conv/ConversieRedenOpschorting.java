/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.conv;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3OmschrijvingRedenOpschortingBijhoudingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;


/**
 * Conversietabel ten behoeve van de reden opschorting (LO3) enerzijds, en de reden opschorting (BRP) anderzijds.
 * <p/>
 * Het betreft hier de waarden die ��n-op-��n worden overgezet; de conversiesoftware houdt op een andere wijze rekening met de situatie dat iets dat wel
 * een reden opschorting is in de GBA, geen reden opschorting is in de BRP.
 */
@Entity
@Table(schema = "Conv", name = "ConvRdnOpschorting")
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ConversieRedenOpschorting extends AbstractConversieRedenOpschorting {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected ConversieRedenOpschorting() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param rubriek6720OmschrijvingRedenOpschortingBijhouding
     *                              rubriek6720OmschrijvingRedenOpschortingBijhouding van ConversieRedenOpschorting.
     * @param nadereBijhoudingsaard nadereBijhoudingsaard van ConversieRedenOpschorting.
     */
    protected ConversieRedenOpschorting(
        final LO3OmschrijvingRedenOpschortingBijhoudingAttribuut rubriek6720OmschrijvingRedenOpschortingBijhouding,
        final NadereBijhoudingsaard nadereBijhoudingsaard)
    {
        super(rubriek6720OmschrijvingRedenOpschortingBijhouding, nadereBijhoudingsaard);
    }

}
