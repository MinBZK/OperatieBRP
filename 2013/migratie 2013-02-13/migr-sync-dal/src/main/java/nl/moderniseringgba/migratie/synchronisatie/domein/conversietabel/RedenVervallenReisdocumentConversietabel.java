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
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentRedenOntbreken;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.RedenInhoudingVermissingReisdocument;

/**
 * De conversietabel voor het converteren van de 'LO3 aanduiding inhouding dan wel vermissing Nederlands
 * reisdocument'-code naar de 'BRP Reden vervallen reisdocument'-code en vice versa.
 * 
 */
public final class RedenVervallenReisdocumentConversietabel extends
        AbstractConversietabel<Lo3AanduidingInhoudingVermissingNederlandsReisdocument, BrpReisdocumentRedenOntbreken> {

    /**
     * Maakt een RedenVervallenReisdocumentConversietabel object.
     * 
     * @param redenen
     *            de lijst met reden conversies
     */
    public RedenVervallenReisdocumentConversietabel(final List<RedenInhoudingVermissingReisdocument> redenen) {
        super(converteerRedenen(redenen));
    }

    // CHECKSTYLE:OFF max lengte van regel wordt geschonden door format issue met eclipse
    private static List<Entry<Lo3AanduidingInhoudingVermissingNederlandsReisdocument, BrpReisdocumentRedenOntbreken>>
            converteerRedenen(final List<RedenInhoudingVermissingReisdocument> redenen) {
        final List<Entry<Lo3AanduidingInhoudingVermissingNederlandsReisdocument, BrpReisdocumentRedenOntbreken>> result =
                new ArrayList<Map.Entry<Lo3AanduidingInhoudingVermissingNederlandsReisdocument, BrpReisdocumentRedenOntbreken>>();
        for (final RedenInhoudingVermissingReisdocument reden : redenen) {
            final Lo3AanduidingInhoudingVermissingNederlandsReisdocument lo3Waarde =
                    new Lo3AanduidingInhoudingVermissingNederlandsReisdocument(reden.getLo3Code());
            final BrpReisdocumentRedenOntbreken brpWaarde = new BrpReisdocumentRedenOntbreken(reden.getBrpCode());
            result.add(new ConversieMapEntry<Lo3AanduidingInhoudingVermissingNederlandsReisdocument, BrpReisdocumentRedenOntbreken>(
                    lo3Waarde, brpWaarde));
        }
        // CHECKSTYLE:ON
        return result;
    }

}
