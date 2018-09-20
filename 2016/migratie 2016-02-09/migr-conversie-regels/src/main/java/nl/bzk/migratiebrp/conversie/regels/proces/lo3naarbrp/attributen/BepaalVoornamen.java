/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud;
import org.springframework.stereotype.Component;

/**
 * Bepaal de voornamen obv de samengestelde naam.
 * 
 */
@Component
@Requirement({Requirements.CEL0210, Requirements.CEL0210_LB01 })
public class BepaalVoornamen {

    /**
     * Bepaal de voornamen obv de samengestelde naam.
     * 
     * @param samengesteldeNaamStapel
     *            samengestelde naam stapel
     * @return lijst van voornaam stapels
     */
    public final List<BrpStapel<BrpVoornaamInhoud>> bepaal(final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel) {

        final List<List<BrpGroep<BrpVoornaamInhoud>>> voornaamLijsten = new ArrayList<>();

        for (final BrpGroep<BrpSamengesteldeNaamInhoud> samengesteldeNaam : samengesteldeNaamStapel) {
            final String voornamenString = BrpString.unwrap(samengesteldeNaam.getInhoud().getVoornamen());
            if (voornamenString != null) {
                final StringTokenizer tokenizer = new StringTokenizer(voornamenString, " ", false);
                int voornaamIndex = 0;
                while (tokenizer.hasMoreTokens()) {
                    final List<BrpGroep<BrpVoornaamInhoud>> voornaamLijst = getLijst(voornaamLijsten, voornaamIndex);
                    final BrpString voornaam =
                            new BrpString(tokenizer.nextToken(), samengesteldeNaam.getInhoud().getVoornamen().getOnderzoek());
                    final BrpVoornaamInhoud inhoud = new BrpVoornaamInhoud(voornaam, new BrpInteger(++voornaamIndex));
                    voornaamLijst.add(new BrpGroep<>(
                        inhoud,
                        samengesteldeNaam.getHistorie(),
                        samengesteldeNaam.getActieInhoud(),
                        samengesteldeNaam.getActieVerval(),
                        samengesteldeNaam.getActieGeldigheid()));

                }
            }
        }

        final List<BrpStapel<BrpVoornaamInhoud>> result = new ArrayList<>();
        for (final List<BrpGroep<BrpVoornaamInhoud>> voornaamLijst : voornaamLijsten) {
            result.add(new BrpStapel<>(voornaamLijst));
        }
        return result;
    }

    private static List<BrpGroep<BrpVoornaamInhoud>> getLijst(final List<List<BrpGroep<BrpVoornaamInhoud>>> voornaamLijsten, final int index)
    {
        // Door de volgorde waarop we deze methode aanroepen geldt dit altijd
        assert index <= voornaamLijsten.size();

        if (index == voornaamLijsten.size()) {
            voornaamLijsten.add(new ArrayList<BrpGroep<BrpVoornaamInhoud>>());
        }

        return voornaamLijsten.get(index);
    }
}
