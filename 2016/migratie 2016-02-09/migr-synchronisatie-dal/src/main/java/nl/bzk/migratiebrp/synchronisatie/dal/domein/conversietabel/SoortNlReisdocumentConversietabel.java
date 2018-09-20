/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.ConversieMapEntry;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractSoortNlReisdocumentConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.SoortNlReisdocument;

/**
 * De conversietabel om 'Lo3 Soort Nederlands reisdocument'-code (GBA Tabel 48) te converteren naar de 'BRP Soort
 * Nederlands reisdocument'-code en vice versa.
 * 
 */
public final class SoortNlReisdocumentConversietabel extends AbstractSoortNlReisdocumentConversietabel {

    /**
     * Maakt een SoortNlReisdocumentConversietabel object.
     * 
     * @param soortNlReisdocumentLijst
     *            de lijst met SoortNlReisdocument conversies.
     */
    public SoortNlReisdocumentConversietabel(final List<SoortNlReisdocument> soortNlReisdocumentLijst) {
        super(converteerSoortNlReisdocumentLijst(soortNlReisdocumentLijst));
    }

    private static List<Map.Entry<Lo3SoortNederlandsReisdocument, BrpSoortNederlandsReisdocumentCode>> converteerSoortNlReisdocumentLijst(
        final List<SoortNlReisdocument> soortNlReisdocumentLijst)
    {
        final List<Map.Entry<Lo3SoortNederlandsReisdocument, BrpSoortNederlandsReisdocumentCode>> result = new ArrayList<>();
        for (final SoortNlReisdocument soortNlReisdocument : soortNlReisdocumentLijst) {
            result.add(new ConversieMapEntry<>(
                new Lo3SoortNederlandsReisdocument(soortNlReisdocument.getLo3NederlandsReisdocument()),
                new BrpSoortNederlandsReisdocumentCode(soortNlReisdocument.getSoortNederlandsReisdocument().getCode())));
        }
        return result;
    }

}
