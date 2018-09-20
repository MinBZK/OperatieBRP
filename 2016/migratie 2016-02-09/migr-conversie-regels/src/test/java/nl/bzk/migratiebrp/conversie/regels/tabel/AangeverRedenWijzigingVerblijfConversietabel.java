/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.tabel;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AangeverRedenWijzigingVerblijfPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;

public class AangeverRedenWijzigingVerblijfConversietabel implements Conversietabel<Lo3AangifteAdreshouding, AangeverRedenWijzigingVerblijfPaar> {

    private static final Map<Lo3AangifteAdreshouding, AangeverRedenWijzigingVerblijfPaar> LO3_NAAR_AANGEVER_REDEN_WIJZIGING =
            Collections.unmodifiableMap(new HashMap<Lo3AangifteAdreshouding, AangeverRedenWijzigingVerblijfPaar>() {
                {
                    put(new Lo3AangifteAdreshouding("A"), new AangeverRedenWijzigingVerblijfPaar(null, new BrpRedenWijzigingVerblijfCode('A')));
                    put(new Lo3AangifteAdreshouding("B"), new AangeverRedenWijzigingVerblijfPaar(null, new BrpRedenWijzigingVerblijfCode('M')));
                    put(new Lo3AangifteAdreshouding("G"), new AangeverRedenWijzigingVerblijfPaar(
                        new BrpAangeverCode('G'),
                        new BrpRedenWijzigingVerblijfCode('P')));
                    put(new Lo3AangifteAdreshouding("H"), new AangeverRedenWijzigingVerblijfPaar(
                        new BrpAangeverCode('H'),
                        new BrpRedenWijzigingVerblijfCode('P')));
                    put(new Lo3AangifteAdreshouding("I"), new AangeverRedenWijzigingVerblijfPaar(
                        new BrpAangeverCode('I'),
                        new BrpRedenWijzigingVerblijfCode('P')));
                    put(new Lo3AangifteAdreshouding("K"), new AangeverRedenWijzigingVerblijfPaar(
                        new BrpAangeverCode('K'),
                        new BrpRedenWijzigingVerblijfCode('P')));
                    put(new Lo3AangifteAdreshouding("M"), new AangeverRedenWijzigingVerblijfPaar(
                        new BrpAangeverCode('M'),
                        new BrpRedenWijzigingVerblijfCode('P')));
                    put(new Lo3AangifteAdreshouding("O"), new AangeverRedenWijzigingVerblijfPaar(
                        new BrpAangeverCode('O'),
                        new BrpRedenWijzigingVerblijfCode('P')));
                    put(new Lo3AangifteAdreshouding("P"), new AangeverRedenWijzigingVerblijfPaar(
                        new BrpAangeverCode('P'),
                        new BrpRedenWijzigingVerblijfCode('P')));
                    put(new Lo3AangifteAdreshouding("T"), new AangeverRedenWijzigingVerblijfPaar(null, new BrpRedenWijzigingVerblijfCode('B')));
                    put(new Lo3AangifteAdreshouding("W"), new AangeverRedenWijzigingVerblijfPaar(null, new BrpRedenWijzigingVerblijfCode('I')));
                    put(new Lo3AangifteAdreshouding("."), new AangeverRedenWijzigingVerblijfPaar(null, null));
                }
            });

    @Override
    public AangeverRedenWijzigingVerblijfPaar converteerNaarBrp(final Lo3AangifteAdreshouding input) {
        if (!Validatie.isElementGevuld(input)) {
            return null;
        }

        if (!LO3_NAAR_AANGEVER_REDEN_WIJZIGING.containsKey(input)) {
            throw new IllegalArgumentException("Ongeldige code voor LO3 aangifte adreshouding naar aangever: " + input);
        }
        return LO3_NAAR_AANGEVER_REDEN_WIJZIGING.get(input);
    }

    @Override
    public Lo3AangifteAdreshouding converteerNaarLo3(final AangeverRedenWijzigingVerblijfPaar input) {
        if (input == null) {
            return null;
        }

        for (final Map.Entry<Lo3AangifteAdreshouding, AangeverRedenWijzigingVerblijfPaar> entry : LO3_NAAR_AANGEVER_REDEN_WIJZIGING.entrySet()) {
            if (entry.getValue().equals(input)) {
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("Onbekende combinatie voor BRP aangever/reden wijziging code: " + input);

    }

    @Override
    public boolean valideerLo3(final Lo3AangifteAdreshouding input) {
        return !Validatie.isElementGevuld(input) || LO3_NAAR_AANGEVER_REDEN_WIJZIGING.containsKey(input);
    }

    @Override
    public boolean valideerBrp(final AangeverRedenWijzigingVerblijfPaar input) {
        return input == null || LO3_NAAR_AANGEVER_REDEN_WIJZIGING.containsValue(input);
    }

}
