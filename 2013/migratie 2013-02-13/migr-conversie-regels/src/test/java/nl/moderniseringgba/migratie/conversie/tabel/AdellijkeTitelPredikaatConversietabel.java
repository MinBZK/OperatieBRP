/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.tabel;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;

public class AdellijkeTitelPredikaatConversietabel implements
        Conversietabel<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar> {

    @SuppressWarnings("serial")
    private static final Map<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar> LO3_NAAR_ADELLIJKE_TITEL_PREDIKAAT =
    // CHECKSTYLE:OFF Test klasse en mapping is nu zo noodzakelijk
            Collections.unmodifiableMap(new HashMap<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar>() {
                // CHECKSTYLE:ON
                {
                    // CHECKSTYLE:OFF - Hier worden constante string gebruikt
                    put(new Lo3AdellijkeTitelPredikaatCode("B"), new AdellijkeTitelPredikaatPaar('B', null,
                            BrpGeslachtsaanduidingCode.MAN));
                    put(new Lo3AdellijkeTitelPredikaatCode("BS"), new AdellijkeTitelPredikaatPaar('B', null,
                            BrpGeslachtsaanduidingCode.VROUW));
                    put(new Lo3AdellijkeTitelPredikaatCode("G"), new AdellijkeTitelPredikaatPaar('G', null,
                            BrpGeslachtsaanduidingCode.MAN));
                    put(new Lo3AdellijkeTitelPredikaatCode("GI"), new AdellijkeTitelPredikaatPaar('G', null,
                            BrpGeslachtsaanduidingCode.VROUW));
                    put(new Lo3AdellijkeTitelPredikaatCode("H"), new AdellijkeTitelPredikaatPaar('H', null,
                            BrpGeslachtsaanduidingCode.MAN));
                    put(new Lo3AdellijkeTitelPredikaatCode("HI"), new AdellijkeTitelPredikaatPaar('H', null,
                            BrpGeslachtsaanduidingCode.VROUW));
                    put(new Lo3AdellijkeTitelPredikaatCode("M"), new AdellijkeTitelPredikaatPaar('M', null,
                            BrpGeslachtsaanduidingCode.MAN));
                    put(new Lo3AdellijkeTitelPredikaatCode("MI"), new AdellijkeTitelPredikaatPaar('M', null,
                            BrpGeslachtsaanduidingCode.VROUW));
                    put(new Lo3AdellijkeTitelPredikaatCode("P"), new AdellijkeTitelPredikaatPaar('P', null,
                            BrpGeslachtsaanduidingCode.MAN));
                    put(new Lo3AdellijkeTitelPredikaatCode("PS"), new AdellijkeTitelPredikaatPaar('P', null,
                            BrpGeslachtsaanduidingCode.VROUW));
                    put(new Lo3AdellijkeTitelPredikaatCode("R"), new AdellijkeTitelPredikaatPaar('R', null,
                            BrpGeslachtsaanduidingCode.MAN));
                    put(new Lo3AdellijkeTitelPredikaatCode("JH"), new AdellijkeTitelPredikaatPaar(null, 'J',
                            BrpGeslachtsaanduidingCode.MAN));
                    put(new Lo3AdellijkeTitelPredikaatCode("JV"), new AdellijkeTitelPredikaatPaar(null, 'J',
                            BrpGeslachtsaanduidingCode.VROUW));
                }
            });

    @Override
    public AdellijkeTitelPredikaatPaar converteerNaarBrp(final Lo3AdellijkeTitelPredikaatCode input) {
        if (input == null) {
            return null;
        }

        // Adellijke titel (en predikaat) is een dynamisch voorkomen, maar er is besloten om deze als statisch
        // te behandelen

        if (!LO3_NAAR_ADELLIJKE_TITEL_PREDIKAAT.containsKey(input)) {
            throw new IllegalArgumentException(
                    "Onbekende code voor LO3 adellijke titel/predikaat code maar adellijke titel: " + input);
        }

        return LO3_NAAR_ADELLIJKE_TITEL_PREDIKAAT.get(input);
    }

    @Override
    public Lo3AdellijkeTitelPredikaatCode converteerNaarLo3(final AdellijkeTitelPredikaatPaar input) {
        if (input == null || input.getAdellijkeTitel() == null && input.getPredikaat() == null) {
            return null;
        }

        for (final Map.Entry<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar> entry : LO3_NAAR_ADELLIJKE_TITEL_PREDIKAAT
                .entrySet()) {
            if (entry.getValue().equals(input)) {
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("Onbekende combinatie voor BRP adellijke titel/predikaat code: " + input);
    }

    @Override
    public boolean valideerLo3(final Lo3AdellijkeTitelPredikaatCode input) {
        return input == null || LO3_NAAR_ADELLIJKE_TITEL_PREDIKAAT.containsKey(input);
    }

    @Override
    public boolean valideerBrp(final AdellijkeTitelPredikaatPaar input) {
        return input == null || LO3_NAAR_ADELLIJKE_TITEL_PREDIKAAT.containsValue(input);
    }
}
