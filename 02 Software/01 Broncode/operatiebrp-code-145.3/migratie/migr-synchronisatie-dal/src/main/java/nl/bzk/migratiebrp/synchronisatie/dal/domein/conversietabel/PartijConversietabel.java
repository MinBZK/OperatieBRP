/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.ConversieMapEntry;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractPartijConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;

/**
 * Conversietabel voor LO3 naar BRP codering en vice versa.
 */
public final class PartijConversietabel extends AbstractPartijConversietabel {

    /**
     * Maakt een PartijConversietabel object.
     * @param gemeenten de lijst met brp gemeenten
     */
    public PartijConversietabel(final Collection<Gemeente> gemeenten) {
        super(PartijConversietabel.converteerGemeenten(gemeenten));
    }

    private static List<Map.Entry<Lo3GemeenteCode, BrpPartijCode>> converteerGemeenten(final Collection<Gemeente> gemeenten) {
        final List<Map.Entry<Lo3GemeenteCode, BrpPartijCode>> result = new ArrayList<>();

        for (final Gemeente gemeente : gemeenten) {
            result.add(new ConversieMapEntry<>(new Lo3GemeenteCode(gemeente.getCode()), new BrpPartijCode(
                    gemeente.getPartij().getCode())));
        }

        result.add(new ConversieMapEntry<>(Lo3GemeenteCode.RNI, BrpPartijCode.MINISTER));

        return result;
    }

}
