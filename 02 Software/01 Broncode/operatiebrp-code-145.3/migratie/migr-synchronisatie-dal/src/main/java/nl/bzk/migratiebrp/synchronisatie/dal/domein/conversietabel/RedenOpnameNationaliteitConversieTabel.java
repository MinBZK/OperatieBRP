/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenOpnameNationaliteit;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.ConversieMapEntry;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractRedenVerkrijgingNederlanderschapConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;

/**
 * Deze conversietabel mapt een Lo3AanduidingVerblijfstitelCode op de corresponderen BrpVerblijfsrechtCode en vice
 * versa.
 */
public final class RedenOpnameNationaliteitConversieTabel extends AbstractRedenVerkrijgingNederlanderschapConversietabel {

    /**
     * Maakt een conversie tabel object voor de reden opname nationaliteit.
     * @param redenOpnameLijst de lijst met alle conversies voor reden opname nationaliteit
     */
    public RedenOpnameNationaliteitConversieTabel(final List<RedenOpnameNationaliteit> redenOpnameLijst) {
        super(RedenOpnameNationaliteitConversieTabel.converteerRedenOpnameLijst(redenOpnameLijst));
    }

    private static List<Entry<Lo3RedenNederlandschapCode, BrpRedenVerkrijgingNederlandschapCode>> converteerRedenOpnameLijst(
            final List<RedenOpnameNationaliteit> redenOpnameLijst) {
        final List<Entry<Lo3RedenNederlandschapCode, BrpRedenVerkrijgingNederlandschapCode>> result = new ArrayList<>();
        for (final RedenOpnameNationaliteit redenOpnameNationaliteit : redenOpnameLijst) {
            final Lo3RedenNederlandschapCode lo3RedenNederlandschapCode =
                    new Lo3RedenNederlandschapCode(redenOpnameNationaliteit.getRedenOpnameNationaliteit());
            final BrpRedenVerkrijgingNederlandschapCode brpRedenVerkrijgingNederlandschapCode;
            if (redenOpnameNationaliteit.getRedenVerkrijgingNLNationaliteit() != null) {
                brpRedenVerkrijgingNederlandschapCode =
                        new BrpRedenVerkrijgingNederlandschapCode(redenOpnameNationaliteit.getRedenVerkrijgingNLNationaliteit().getCode());
            } else {
                brpRedenVerkrijgingNederlandschapCode = null;
            }
            result.add(new ConversieMapEntry<>(lo3RedenNederlandschapCode, brpRedenVerkrijgingNederlandschapCode));
        }
        return result;
    }

}
