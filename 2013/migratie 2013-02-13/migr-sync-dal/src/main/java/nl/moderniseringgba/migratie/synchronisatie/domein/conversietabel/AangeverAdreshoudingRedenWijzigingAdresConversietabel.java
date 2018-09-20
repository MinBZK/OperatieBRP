/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.AangeverAdreshoudingRedenWijzigingAdresPaar;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.AbstractConversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.ConversieMapEntry;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAangeverAdreshoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenWijzigingAdresCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.AangifteAdreshouding;

/**
 * De Conversietabel voor aangever adreshouding. Deze mapt een Lo3AangifteAdreshouding op de unieke combinatie van
 * BrpAangeverAdreshoudingCode en BrpRedenWijzigingAdresCode.
 */
public final class AangeverAdreshoudingRedenWijzigingAdresConversietabel extends
        AbstractConversietabel<Lo3AangifteAdreshouding, AangeverAdreshoudingRedenWijzigingAdresPaar> {

    /**
     * Maakt een AangeverAdreshoudingRedenWijzigingAdresConversietabel object.
     * 
     * @param aangifteAdreshoudingLijst
     *            de lijst met alle AangifteAdreshouding entries uit de conversietabel
     */
    public AangeverAdreshoudingRedenWijzigingAdresConversietabel(
            final List<AangifteAdreshouding> aangifteAdreshoudingLijst) {
        super(converteerAangifteAdreshoudingLijst(aangifteAdreshoudingLijst));
    }

    private static List<Map.Entry<Lo3AangifteAdreshouding, AangeverAdreshoudingRedenWijzigingAdresPaar>>
            converteerAangifteAdreshoudingLijst(final List<AangifteAdreshouding> aangifteAdreshoudingLijst) {
        final List<Map.Entry<Lo3AangifteAdreshouding, AangeverAdreshoudingRedenWijzigingAdresPaar>> result =
                new ArrayList<Map.Entry<Lo3AangifteAdreshouding, AangeverAdreshoudingRedenWijzigingAdresPaar>>();

        for (final AangifteAdreshouding aangifteAdreshouding : aangifteAdreshoudingLijst) {
            final Lo3AangifteAdreshouding lo3Code = new Lo3AangifteAdreshouding(aangifteAdreshouding.getLo3Code());

            final AangeverAdreshoudingRedenWijzigingAdresPaar paar =
                    new AangeverAdreshoudingRedenWijzigingAdresPaar(
                            maakAangeverAdreshoudingCode(aangifteAdreshouding),
                            maakRedenWijzigingAdresCode(aangifteAdreshouding));
            result.add(new ConversieMapEntry<Lo3AangifteAdreshouding, AangeverAdreshoudingRedenWijzigingAdresPaar>(
                    lo3Code, paar));
        }
        return result;
    }

    private static BrpRedenWijzigingAdresCode maakRedenWijzigingAdresCode(
            final AangifteAdreshouding aangifteAdreshouding) {
        BrpRedenWijzigingAdresCode brpRedenWijzigingAdresCode = null;
        if (aangifteAdreshouding.getBrpRedenwijzigingAdresCode() != null) {
            brpRedenWijzigingAdresCode =
                    new BrpRedenWijzigingAdresCode(aangifteAdreshouding.getBrpRedenwijzigingAdresCode());
        }
        return brpRedenWijzigingAdresCode;
    }

    private static BrpAangeverAdreshoudingCode maakAangeverAdreshoudingCode(
            final AangifteAdreshouding aangifteAdreshouding) {
        BrpAangeverAdreshoudingCode brpAangeverAdreshoudingCode = null;
        if (aangifteAdreshouding.getBrpAangeverAdreshoudingCode() != null) {
            brpAangeverAdreshoudingCode =
                    new BrpAangeverAdreshoudingCode(aangifteAdreshouding.getBrpAangeverAdreshoudingCode());
        }
        return brpAangeverAdreshoudingCode;
    }
}
