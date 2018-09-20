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
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifte;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.AutoriteitVanAfgifte;

/**
 * Conversie van 'LO3 Autoriteit van afgifte'-code naar 'BRP Autoriteit van afgifte reisdocument'-code en vice versa.
 * 
 */
public final class AutoriteitVanAfgifteReisdocumentConversietabel extends
        AbstractConversietabel<Lo3AutoriteitVanAfgifteNederlandsReisdocument, BrpReisdocumentAutoriteitVanAfgifte> {

    /**
     * Maakt een AutoriteitVanAfgifteReisdocumentConversietabel object.
     * 
     * @param autoriteitVanAfgifteLijst
     *            de lijst met autoriteit van afgifte conversies
     */
    public AutoriteitVanAfgifteReisdocumentConversietabel(final List<AutoriteitVanAfgifte> autoriteitVanAfgifteLijst) {
        super(converteerAutoriteitVanAfgifteLijst(autoriteitVanAfgifteLijst));
    }

    // CHECKSTYLE:OFF max lengte van regel wordt geschonden door format issue met eclipse
    private static List<Entry<Lo3AutoriteitVanAfgifteNederlandsReisdocument, BrpReisdocumentAutoriteitVanAfgifte>>
            converteerAutoriteitVanAfgifteLijst(final List<AutoriteitVanAfgifte> autoriteitVanAfgifteLijst) {
        final List<Entry<Lo3AutoriteitVanAfgifteNederlandsReisdocument, BrpReisdocumentAutoriteitVanAfgifte>> result =
                new ArrayList<Map.Entry<Lo3AutoriteitVanAfgifteNederlandsReisdocument, BrpReisdocumentAutoriteitVanAfgifte>>();
        for (final AutoriteitVanAfgifte autoriteitVanAfgifte : autoriteitVanAfgifteLijst) {
            result.add(new ConversieMapEntry<Lo3AutoriteitVanAfgifteNederlandsReisdocument, BrpReisdocumentAutoriteitVanAfgifte>(
                    new Lo3AutoriteitVanAfgifteNederlandsReisdocument(autoriteitVanAfgifte.getLo3Code()),
                    new BrpReisdocumentAutoriteitVanAfgifte(autoriteitVanAfgifte.getBrpCode())));
        }
        return result;
        // CHECKSTYLE:ON
    }

}
