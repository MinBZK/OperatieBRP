/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingNationaliteit;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.ConversieMapEntry;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractRedenVerliesNederlanderschapConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;

/**
 * Deze conversietabel mapt een Lo3AanduidingVerblijfstitelCode op de corresponderen BrpVerblijfsrechtCode en vice
 * versa.
 */
public final class RedenBeeindigingNationaliteitConversietabel extends AbstractRedenVerliesNederlanderschapConversietabel {

    /**
     * Maakt een conversie tabel object voor de reden beeindiging nationaliteit.
     * @param redenBeeindigingLijst de lijst met alle conversies voor reden opname nationaliteit
     */
    public RedenBeeindigingNationaliteitConversietabel(final List<RedenBeeindigingNationaliteit> redenBeeindigingLijst) {
        super(RedenBeeindigingNationaliteitConversietabel.converteerRedenBeeindigingLijst(redenBeeindigingLijst));
    }

    private static List<Entry<Lo3RedenNederlandschapCode, BrpRedenVerliesNederlandschapCode>> converteerRedenBeeindigingLijst(
            final List<RedenBeeindigingNationaliteit> redenBeeindigingLijst) {
        final List<Entry<Lo3RedenNederlandschapCode, BrpRedenVerliesNederlandschapCode>> result = new ArrayList<>();
        for (final RedenBeeindigingNationaliteit redenBeeindigingNationaliteit : redenBeeindigingLijst) {
            final Lo3RedenNederlandschapCode lo3RedenNederlandschapCode =
                    new Lo3RedenNederlandschapCode(redenBeeindigingNationaliteit.getRedenBeeindigingNationaliteit());
            final BrpRedenVerliesNederlandschapCode brpRedenVerliesNederlandschapCode;
            if (redenBeeindigingNationaliteit.getRedenVerliesNLNationaliteit() != null) {
                brpRedenVerliesNederlandschapCode =
                        new BrpRedenVerliesNederlandschapCode(redenBeeindigingNationaliteit.getRedenVerliesNLNationaliteit().getCode());
            } else {
                brpRedenVerliesNederlandschapCode = null;
            }

            result.add(new ConversieMapEntry<>(lo3RedenNederlandschapCode, brpRedenVerliesNederlandschapCode));
        }
        return result;
    }

}
