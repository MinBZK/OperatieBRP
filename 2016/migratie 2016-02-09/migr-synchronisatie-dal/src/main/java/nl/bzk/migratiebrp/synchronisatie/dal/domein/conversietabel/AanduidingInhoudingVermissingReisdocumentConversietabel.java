/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.ConversieMapEntry;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractAanduidingInhoudingVermissingReisdocumentConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.AanduidingInhoudingVermissingReisdocument;

/**
 * De conversietabel voor het converteren van de 'LO3 aanduiding inhouding dan wel vermissing Nederlands
 * reisdocument'-code naar de 'BRP Aanduiding inhouding/vermissing reisdocument'-code en vice versa.
 *
 */
public final class AanduidingInhoudingVermissingReisdocumentConversietabel extends AbstractAanduidingInhoudingVermissingReisdocumentConversietabel {

    /**
     * Maakt een AanduidingInhoudingOfVermissingConversietabel object.
     *
     * @param aanduidingen
     *            de lijst met AanduidingInhoudingVermissingReisdocument conversies
     */
    public AanduidingInhoudingVermissingReisdocumentConversietabel(final List<AanduidingInhoudingVermissingReisdocument> aanduidingen) {
        super(converteerAanduidingen(aanduidingen));
    }

    private static List<Map.Entry<Lo3AanduidingInhoudingVermissingNederlandsReisdocument, BrpAanduidingInhoudingOfVermissingReisdocumentCode>> converteerAanduidingen(
        final List<AanduidingInhoudingVermissingReisdocument> aanduidingen)
    {
        final List<Map.Entry<Lo3AanduidingInhoudingVermissingNederlandsReisdocument, BrpAanduidingInhoudingOfVermissingReisdocumentCode>> result;
        result = new ArrayList<>();

        for (final AanduidingInhoudingVermissingReisdocument aanduiding : aanduidingen) {
            final Lo3AanduidingInhoudingVermissingNederlandsReisdocument lo3Waarde;
            final String lo3AanduidingInhoudingOfVermissingReisdocumentCode =
                    String.valueOf(aanduiding.getLo3AanduidingInhoudingOfVermissingReisdocument());
            lo3Waarde = new Lo3AanduidingInhoudingVermissingNederlandsReisdocument(lo3AanduidingInhoudingOfVermissingReisdocumentCode);
            final BrpAanduidingInhoudingOfVermissingReisdocumentCode brpWaarde;
            brpWaarde = new BrpAanduidingInhoudingOfVermissingReisdocumentCode(aanduiding.getAanduidingInhoudingOfVermissingReisdocument().getCode());
            result.add(new ConversieMapEntry<>(lo3Waarde, brpWaarde));
        }
        return result;
    }
}
