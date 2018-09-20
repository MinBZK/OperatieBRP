/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.AbstractConversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.ConversieMapEntry;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentSoort;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.SoortNlReisdocument;

/**
 * De conversietabel om 'Lo3 Soort Nederlands reisdocument'-code (GBA Tabel 48) te converteren naar de 'BRP Soort
 * Nederlands reisdocument'-code en vice versa.
 * 
 */
public final class SoortNlReisdocumentConversietabel extends
        AbstractConversietabel<Lo3SoortNederlandsReisdocument, BrpReisdocumentSoort> {

    /**
     * Maakt een SoortNlReisdocumentConversietabel object.
     * 
     * @param soortNlReisdocumentLijst
     *            de lijst met SoortNlReisdocument conversies.
     */
    public SoortNlReisdocumentConversietabel(final List<SoortNlReisdocument> soortNlReisdocumentLijst) {
        super(converteerSoortNlReisdocumentLijst(soortNlReisdocumentLijst));
    }

    private static List<Map.Entry<Lo3SoortNederlandsReisdocument, BrpReisdocumentSoort>>
            converteerSoortNlReisdocumentLijst(final List<SoortNlReisdocument> soortNlReisdocumentLijst) {
        final List<Map.Entry<Lo3SoortNederlandsReisdocument, BrpReisdocumentSoort>> result =
                new ArrayList<Map.Entry<Lo3SoortNederlandsReisdocument, BrpReisdocumentSoort>>();
        for (final SoortNlReisdocument soortNlReisdocument : soortNlReisdocumentLijst) {
            result.add(new ConversieMapEntry<Lo3SoortNederlandsReisdocument, BrpReisdocumentSoort>(
                    new Lo3SoortNederlandsReisdocument(soortNlReisdocument.getLo3Code()), new BrpReisdocumentSoort(
                            soortNlReisdocument.getBrpCode())));
        }
        return result;
    }
}
