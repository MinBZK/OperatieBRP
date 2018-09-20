/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.AbstractConversietabel;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.ConversieMapEntry;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.RedenVerkrijgingVerliesPaar;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.RedenVerkrijgingVerliesNlSchap;

/**
 * De conversie tabel die de 'LO3 reden verkrijging/verlies nederlandschap'-code mapt op de 'BRP Reden verkrijging NL
 * nationaliteit, BRP Reden verlies NL nationaliteit'-paar en vice versa.
 */
public final class RedenVerkrijgingVerliesNederlanderschapConversietabel extends
        AbstractConversietabel<Lo3RedenNederlandschapCode, RedenVerkrijgingVerliesPaar> {

    /**
     * Maakt een RedenVerkrijgingVerliesNederlanderschapConversietabel object.
     * 
     * @param redenen
     *            de lijst met reden conversies
     */
    public RedenVerkrijgingVerliesNederlanderschapConversietabel(final List<RedenVerkrijgingVerliesNlSchap> redenen) {
        super(converteerRedenen(redenen));
    }

    private static List<Map.Entry<Lo3RedenNederlandschapCode, RedenVerkrijgingVerliesPaar>> converteerRedenen(
            final List<RedenVerkrijgingVerliesNlSchap> redenen) {
        final List<Map.Entry<Lo3RedenNederlandschapCode, RedenVerkrijgingVerliesPaar>> result =
                new ArrayList<Map.Entry<Lo3RedenNederlandschapCode, RedenVerkrijgingVerliesPaar>>();

        for (final RedenVerkrijgingVerliesNlSchap reden : redenen) {
            final String brpRedenVerkrijgingNaam = reden.getBrpRedenVerkrijgingNaam();
            final BrpRedenVerkrijgingNederlandschapCode verkrijg =
                    brpRedenVerkrijgingNaam == null ? null : new BrpRedenVerkrijgingNederlandschapCode(
                            new BigDecimal(brpRedenVerkrijgingNaam));
            final String brpRedenVerliesNaam = reden.getBrpRedenVerliesNaam();
            final BrpRedenVerliesNederlandschapCode verlies =
                    brpRedenVerliesNaam == null ? null : new BrpRedenVerliesNederlandschapCode(new BigDecimal(
                            brpRedenVerliesNaam));
            final RedenVerkrijgingVerliesPaar paar = new RedenVerkrijgingVerliesPaar(verkrijg, verlies);
            final Lo3RedenNederlandschapCode lo3Reden = new Lo3RedenNederlandschapCode(reden.getLo3Code());

            result.add(new ConversieMapEntry<Lo3RedenNederlandschapCode, RedenVerkrijgingVerliesPaar>(lo3Reden, paar));
        }
        return result;
    }
}
