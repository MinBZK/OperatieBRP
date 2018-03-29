/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenOpschorting;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.ConversieMapEntry;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractRedenOpschortingConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;

/**
 * Deze conversietabel map een Lo3RedenOpschortingBijhoudingCode op de corresponderen BrpRedenOpschortingBijhoudingCode
 * vice versa.
 */
public final class RedenOpschortingConversietabel extends AbstractRedenOpschortingConversietabel {

    /**
     * Maakt een RedenOpschortingConversietabel object.
     * @param redenOpschortingLijst de lijst met alle reden opschorting conversies
     */
    public RedenOpschortingConversietabel(final List<RedenOpschorting> redenOpschortingLijst) {
        super(converteerRedenOpschortingLijst(redenOpschortingLijst));
    }

    private static List<Entry<Lo3RedenOpschortingBijhoudingCode, BrpNadereBijhoudingsaardCode>> converteerRedenOpschortingLijst(
            final List<RedenOpschorting> redenOpschortingLijst) {
        final List<Entry<Lo3RedenOpschortingBijhoudingCode, BrpNadereBijhoudingsaardCode>> result = new ArrayList<>();
        for (final RedenOpschorting redenOpschorting : redenOpschortingLijst) {
            final Lo3RedenOpschortingBijhoudingCode lo3RedenOpschortingBijhoudingCode =
                    new Lo3RedenOpschortingBijhoudingCode(String.valueOf(redenOpschorting.getLo3OmschrijvingRedenOpschorting()));
            final BrpNadereBijhoudingsaardCode brpNadereBijhoudingsaardCode =
                    new BrpNadereBijhoudingsaardCode(redenOpschorting.getRedenOpschorting().getCode());
            result.add(new ConversieMapEntry<>(lo3RedenOpschortingBijhoudingCode, brpNadereBijhoudingsaardCode));
        }
        return result;
    }
}
