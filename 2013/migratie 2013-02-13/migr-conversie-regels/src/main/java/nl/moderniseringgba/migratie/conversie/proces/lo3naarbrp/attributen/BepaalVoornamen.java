/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVoornaamInhoud;

import org.springframework.stereotype.Component;

/**
 * Bepaal de voornamen obv de samengestelde naam.
 * 
 */
@Component
@Requirement({ Requirements.CEL0210, Requirements.CEL0210_LB01 })
public class BepaalVoornamen {

    /**
     * Bepaal de voornamen obv de samengestelde naam.
     * 
     * @param samengesteldeNaamStapel
     *            samengestelde naam stapel
     * @return lijst van voornaam stapels
     */
    public final List<BrpStapel<BrpVoornaamInhoud>> bepaal(
            final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel) {

        final List<List<BrpGroep<BrpVoornaamInhoud>>> voornaamLijsten =
                new ArrayList<List<BrpGroep<BrpVoornaamInhoud>>>();

        for (final BrpGroep<BrpSamengesteldeNaamInhoud> samengesteldeNaam : samengesteldeNaamStapel) {
            final String voornamenString = samengesteldeNaam.getInhoud().getVoornamen();
            if (voornamenString != null) {
                final StringTokenizer tokenizer = new StringTokenizer(voornamenString, " ", false);
                int voornaamIndex = 0;
                while (tokenizer.hasMoreTokens()) {
                    final List<BrpGroep<BrpVoornaamInhoud>> voornaamLijst = getLijst(voornaamLijsten, voornaamIndex);
                    final BrpVoornaamInhoud inhoud = new BrpVoornaamInhoud(tokenizer.nextToken(), ++voornaamIndex);
                    voornaamLijst.add(new BrpGroep<BrpVoornaamInhoud>(inhoud, samengesteldeNaam.getHistorie(),
                            samengesteldeNaam.getActieInhoud(), samengesteldeNaam.getActieVerval(), samengesteldeNaam
                                    .getActieGeldigheid()));

                }
            }
        }

        final List<BrpStapel<BrpVoornaamInhoud>> result = new ArrayList<BrpStapel<BrpVoornaamInhoud>>();
        for (final List<BrpGroep<BrpVoornaamInhoud>> voornaamLijst : voornaamLijsten) {
            result.add(new BrpStapel<BrpVoornaamInhoud>(voornaamLijst));
        }
        return result;
    }

    private static List<BrpGroep<BrpVoornaamInhoud>> getLijst(
            final List<List<BrpGroep<BrpVoornaamInhoud>>> voornaamLijsten,
            final int index) {
        // Door de volgorde waarop we deze methode aanroepen geldt dit altijd
        assert index <= voornaamLijsten.size();

        if (index == voornaamLijsten.size()) {
            voornaamLijsten.add(new ArrayList<BrpGroep<BrpVoornaamInhoud>>());
        }

        return voornaamLijsten.get(index);
    }
}
