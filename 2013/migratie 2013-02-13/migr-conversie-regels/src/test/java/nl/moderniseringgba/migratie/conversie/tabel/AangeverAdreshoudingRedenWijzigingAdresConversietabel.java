/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.tabel;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.AangeverAdreshoudingRedenWijzigingAdresPaar;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAangeverAdreshoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenWijzigingAdresCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AangifteAdreshouding;

public class AangeverAdreshoudingRedenWijzigingAdresConversietabel implements
        Conversietabel<Lo3AangifteAdreshouding, AangeverAdreshoudingRedenWijzigingAdresPaar> {

    @SuppressWarnings("serial")
    private static final Map<Lo3AangifteAdreshouding, AangeverAdreshoudingRedenWijzigingAdresPaar> LO3_NAAR_AANGEVER_ADRESHOUDING_REDEN_WIJZIGING =
            // CHECKSTYLE:OFF Test klasse en deze mapping is noodzakelijk
            Collections
                    .unmodifiableMap(new HashMap<Lo3AangifteAdreshouding, AangeverAdreshoudingRedenWijzigingAdresPaar>() {
                        // CHECKSTYLE:ON
                        {
                            put(new Lo3AangifteAdreshouding("A"), new AangeverAdreshoudingRedenWijzigingAdresPaar(
                                    null, new BrpRedenWijzigingAdresCode("A")));
                            put(new Lo3AangifteAdreshouding("B"), new AangeverAdreshoudingRedenWijzigingAdresPaar(
                                    null, new BrpRedenWijzigingAdresCode("M")));
                            put(new Lo3AangifteAdreshouding("G"), new AangeverAdreshoudingRedenWijzigingAdresPaar(
                                    new BrpAangeverAdreshoudingCode("G"), new BrpRedenWijzigingAdresCode("P")));
                            put(new Lo3AangifteAdreshouding("H"), new AangeverAdreshoudingRedenWijzigingAdresPaar(
                                    new BrpAangeverAdreshoudingCode("H"), new BrpRedenWijzigingAdresCode("P")));
                            put(new Lo3AangifteAdreshouding("I"), new AangeverAdreshoudingRedenWijzigingAdresPaar(
                                    new BrpAangeverAdreshoudingCode("I"), new BrpRedenWijzigingAdresCode("P")));
                            put(new Lo3AangifteAdreshouding("K"), new AangeverAdreshoudingRedenWijzigingAdresPaar(
                                    new BrpAangeverAdreshoudingCode("K"), new BrpRedenWijzigingAdresCode("P")));
                            put(new Lo3AangifteAdreshouding("M"), new AangeverAdreshoudingRedenWijzigingAdresPaar(
                                    new BrpAangeverAdreshoudingCode("M"), new BrpRedenWijzigingAdresCode("P")));
                            put(new Lo3AangifteAdreshouding("O"), new AangeverAdreshoudingRedenWijzigingAdresPaar(
                                    new BrpAangeverAdreshoudingCode("O"), new BrpRedenWijzigingAdresCode("P")));
                            put(new Lo3AangifteAdreshouding("P"), new AangeverAdreshoudingRedenWijzigingAdresPaar(
                                    new BrpAangeverAdreshoudingCode("P"), new BrpRedenWijzigingAdresCode("P")));
                            put(new Lo3AangifteAdreshouding("T"), new AangeverAdreshoudingRedenWijzigingAdresPaar(
                                    null, new BrpRedenWijzigingAdresCode("B")));
                            put(new Lo3AangifteAdreshouding("W"), new AangeverAdreshoudingRedenWijzigingAdresPaar(
                                    null, new BrpRedenWijzigingAdresCode("I")));
                            put(new Lo3AangifteAdreshouding("."), new AangeverAdreshoudingRedenWijzigingAdresPaar(
                                    null, null));
                        }
                    });

    @Override
    public AangeverAdreshoudingRedenWijzigingAdresPaar converteerNaarBrp(final Lo3AangifteAdreshouding input) {
        if (input == null) {
            return null;
        }

        if (!LO3_NAAR_AANGEVER_ADRESHOUDING_REDEN_WIJZIGING.containsKey(input)) {
            throw new IllegalArgumentException(
                    "Ongeldige code voor LO3 aangifte adreshouding naar aangever adreshouding: " + input);
        }
        return LO3_NAAR_AANGEVER_ADRESHOUDING_REDEN_WIJZIGING.get(input);
    }

    @Override
    public Lo3AangifteAdreshouding converteerNaarLo3(final AangeverAdreshoudingRedenWijzigingAdresPaar input) {
        if (input == null) {
            return null;
        }

        for (final Map.Entry<Lo3AangifteAdreshouding, AangeverAdreshoudingRedenWijzigingAdresPaar> entry : LO3_NAAR_AANGEVER_ADRESHOUDING_REDEN_WIJZIGING
                .entrySet()) {
            if (entry.getValue().equals(input)) {
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException(
                "Onbekende combinatie voor BRP aangever adreshouding/reden wijziging code: " + input);

    }

    @Override
    public boolean valideerLo3(final Lo3AangifteAdreshouding input) {
        return input == null || LO3_NAAR_AANGEVER_ADRESHOUDING_REDEN_WIJZIGING.containsKey(input);
    }

    @Override
    public boolean valideerBrp(final AangeverAdreshoudingRedenWijzigingAdresPaar input) {
        return input == null || LO3_NAAR_AANGEVER_ADRESHOUDING_REDEN_WIJZIGING.containsValue(input);
    }

}
