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
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.RedenOntbindingHuwelijkPartnerschap;

/**
 * De Conversietabel voor reden ontbinding huwelijk/geregistreerd partnerschap. Deze mapt een
 * Lo3RedenOntbindingHuwelijkOfGpCode op BrpRedenEindeRelatieCode.
 */
public final class RedenOntbindingHuwelijkPartnerschapConversietabel extends
        AbstractConversietabel<Lo3RedenOntbindingHuwelijkOfGpCode, BrpRedenEindeRelatieCode> {

    /**
     * Maakt een RedenOntbindingHuwelijkPartnerschapConversietabel object.
     * 
     * @param redenOntbindingHuwelijkPartnerschapLijst
     *            de lijst met alle reden entries uit de conversietabel
     */
    public RedenOntbindingHuwelijkPartnerschapConversietabel(
            final List<RedenOntbindingHuwelijkPartnerschap> redenOntbindingHuwelijkPartnerschapLijst) {
        super(converteerRedenOntbindingHuwelijkPartnerschapLijst(redenOntbindingHuwelijkPartnerschapLijst));
    }

    private static List<Entry<Lo3RedenOntbindingHuwelijkOfGpCode, BrpRedenEindeRelatieCode>>
            converteerRedenOntbindingHuwelijkPartnerschapLijst(
                    final List<RedenOntbindingHuwelijkPartnerschap> redenOntbindingHuwelijkPartnerschapLijst) {
        final List<Entry<Lo3RedenOntbindingHuwelijkOfGpCode, BrpRedenEindeRelatieCode>> result =
                new ArrayList<Map.Entry<Lo3RedenOntbindingHuwelijkOfGpCode, BrpRedenEindeRelatieCode>>();
        for (final RedenOntbindingHuwelijkPartnerschap reden : redenOntbindingHuwelijkPartnerschapLijst) {
            result.add(new ConversieMapEntry<Lo3RedenOntbindingHuwelijkOfGpCode, BrpRedenEindeRelatieCode>(
                    new Lo3RedenOntbindingHuwelijkOfGpCode(reden.getLo3Code()), new BrpRedenEindeRelatieCode(reden
                            .getBrpCode())));
        }
        return result;
    }
}
