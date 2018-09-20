/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.AbstractConversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.ConversieMapEntry;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.RedenOpschorting;

/**
 * Deze conversietabel map een Lo3RedenOpschortingBijhoudingCode op de corresponderen BrpRedenOpschortingBijhoudingCode
 * vice versa.
 */
public final class RedenOpschortingConversietabel extends
        AbstractConversietabel<Lo3RedenOpschortingBijhoudingCode, BrpRedenOpschortingBijhoudingCode> {

    /**
     * Maakt een RedenOpschortingConversietabel object.
     * 
     * @param redenOpschortingLijst
     *            de lijst met alle reden opschorting conversies
     */
    public RedenOpschortingConversietabel(final List<RedenOpschorting> redenOpschortingLijst) {
        super(converteerRedenOpschortingLijst(redenOpschortingLijst));
    }

    private static List<Entry<Lo3RedenOpschortingBijhoudingCode, BrpRedenOpschortingBijhoudingCode>>
            converteerRedenOpschortingLijst(final List<RedenOpschorting> redenOpschortingLijst) {
        final List<Entry<Lo3RedenOpschortingBijhoudingCode, BrpRedenOpschortingBijhoudingCode>> result =
                new ArrayList<Map.Entry<Lo3RedenOpschortingBijhoudingCode, BrpRedenOpschortingBijhoudingCode>>();
        for (final RedenOpschorting redenOpschorting : redenOpschortingLijst) {
            result.add(new ConversieMapEntry<Lo3RedenOpschortingBijhoudingCode, BrpRedenOpschortingBijhoudingCode>(
                    new Lo3RedenOpschortingBijhoudingCode(redenOpschorting.getLo3Code()),
                    BrpRedenOpschortingBijhoudingCode.valueOfCode(redenOpschorting.getBrpCode())));
        }
        return result;
    }

}
