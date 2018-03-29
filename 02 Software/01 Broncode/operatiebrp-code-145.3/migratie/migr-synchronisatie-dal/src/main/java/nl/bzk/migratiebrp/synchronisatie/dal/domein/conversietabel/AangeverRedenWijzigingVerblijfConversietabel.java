/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AangifteAdreshouding;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AangeverRedenWijzigingVerblijfPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.ConversieMapEntry;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractAangeverRedenWijzigingVerblijfConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AangifteAdreshouding;

/**
 * De Conversietabel voor aangever adreshouding. Deze mapt een Lo3AangifteAdreshouding op de unieke combinatie van
 * BrpAangeverCode en BrpRedenWijzigingVerblijfCode.
 */
public final class AangeverRedenWijzigingVerblijfConversietabel extends AbstractAangeverRedenWijzigingVerblijfConversietabel {

    /**
     * Maakt een AangeverRedenWijzigingVerblijfConversietabel object.
     * @param aangifteAdreshoudingLijst de lijst met alle AangifteAdreshouding entries uit de conversietabel
     */
    public AangeverRedenWijzigingVerblijfConversietabel(final List<AangifteAdreshouding> aangifteAdreshoudingLijst) {
        super(converteerAangifteAdreshoudingLijst(aangifteAdreshoudingLijst));
    }

    private static List<Map.Entry<Lo3AangifteAdreshouding, AangeverRedenWijzigingVerblijfPaar>> converteerAangifteAdreshoudingLijst(
            final List<AangifteAdreshouding> aangifteAdreshoudingLijst) {
        final List<Map.Entry<Lo3AangifteAdreshouding, AangeverRedenWijzigingVerblijfPaar>> result = new ArrayList<>();

        for (final AangifteAdreshouding aangifteAdreshouding : aangifteAdreshoudingLijst) {
            final Lo3AangifteAdreshouding lo3Code =
                    new Lo3AangifteAdreshouding(String.valueOf(aangifteAdreshouding.getLo3OmschrijvingAangifteAdreshouding()));

            final AangeverRedenWijzigingVerblijfPaar paar =
                    new AangeverRedenWijzigingVerblijfPaar(
                            maakAangeverAdreshoudingCode(aangifteAdreshouding),
                            maakRedenWijzigingAdresCode(aangifteAdreshouding));
            result.add(new ConversieMapEntry<>(lo3Code, paar));
        }
        return result;
    }

    private static BrpRedenWijzigingVerblijfCode maakRedenWijzigingAdresCode(final AangifteAdreshouding aangifteAdreshouding) {
        BrpRedenWijzigingVerblijfCode brpRedenWijzigingVerblijfCode = null;
        if (aangifteAdreshouding.getRedenWijzigingVerblijf() != null) {
            brpRedenWijzigingVerblijfCode = new BrpRedenWijzigingVerblijfCode(aangifteAdreshouding.getRedenWijzigingVerblijf().getCode());
        }
        return brpRedenWijzigingVerblijfCode;
    }

    private static BrpAangeverCode maakAangeverAdreshoudingCode(final AangifteAdreshouding aangifteAdreshouding) {
        BrpAangeverCode brpAangeverCode = null;
        if (aangifteAdreshouding.getAangever() != null) {
            brpAangeverCode = new BrpAangeverCode(aangifteAdreshouding.getAangever().getCode());
        }
        return brpAangeverCode;
    }
}
