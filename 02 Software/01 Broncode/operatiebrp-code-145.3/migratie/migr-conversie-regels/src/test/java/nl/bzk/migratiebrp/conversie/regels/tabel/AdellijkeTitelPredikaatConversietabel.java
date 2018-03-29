/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.tabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.ConversieMapEntry;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractAdellijkeTitelPredikaatConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;

public class AdellijkeTitelPredikaatConversietabel extends AbstractAdellijkeTitelPredikaatConversietabel {

    private static final List<Map.Entry<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar>> CONVERSIE_LIJST = new ArrayList<>();

    static {
        CONVERSIE_LIJST.add(new ConversieMapEntry<>(new Lo3AdellijkeTitelPredikaatCode("B"), new AdellijkeTitelPredikaatPaar(
                new BrpCharacter('B'),
                null,
                BrpGeslachtsaanduidingCode.MAN)));
        CONVERSIE_LIJST.add(new ConversieMapEntry<>(new Lo3AdellijkeTitelPredikaatCode("BS"), new AdellijkeTitelPredikaatPaar(
                new BrpCharacter('B'),
                null,
                BrpGeslachtsaanduidingCode.VROUW)));
        CONVERSIE_LIJST.add(new ConversieMapEntry<>(new Lo3AdellijkeTitelPredikaatCode("G"), new AdellijkeTitelPredikaatPaar(
                new BrpCharacter('G'),
                null,
                BrpGeslachtsaanduidingCode.MAN)));
        CONVERSIE_LIJST.add(new ConversieMapEntry<>(new Lo3AdellijkeTitelPredikaatCode("GI"), new AdellijkeTitelPredikaatPaar(
                new BrpCharacter('G'),
                null,
                BrpGeslachtsaanduidingCode.VROUW)));
        CONVERSIE_LIJST.add(new ConversieMapEntry<>(new Lo3AdellijkeTitelPredikaatCode("H"), new AdellijkeTitelPredikaatPaar(
                new BrpCharacter('H'),
                null,
                BrpGeslachtsaanduidingCode.MAN)));
        CONVERSIE_LIJST.add(new ConversieMapEntry<>(new Lo3AdellijkeTitelPredikaatCode("HI"), new AdellijkeTitelPredikaatPaar(
                new BrpCharacter('H'),
                null,
                BrpGeslachtsaanduidingCode.VROUW)));
        CONVERSIE_LIJST.add(new ConversieMapEntry<>(new Lo3AdellijkeTitelPredikaatCode("M"), new AdellijkeTitelPredikaatPaar(
                new BrpCharacter('M'),
                null,
                BrpGeslachtsaanduidingCode.MAN)));
        CONVERSIE_LIJST.add(new ConversieMapEntry<>(new Lo3AdellijkeTitelPredikaatCode("MI"), new AdellijkeTitelPredikaatPaar(
                new BrpCharacter('M'),
                null,
                BrpGeslachtsaanduidingCode.VROUW)));
        CONVERSIE_LIJST.add(new ConversieMapEntry<>(new Lo3AdellijkeTitelPredikaatCode("P"), new AdellijkeTitelPredikaatPaar(
                new BrpCharacter('P'),
                null,
                BrpGeslachtsaanduidingCode.MAN)));
        CONVERSIE_LIJST.add(new ConversieMapEntry<>(new Lo3AdellijkeTitelPredikaatCode("PS"), new AdellijkeTitelPredikaatPaar(
                new BrpCharacter('P'),
                null,
                BrpGeslachtsaanduidingCode.VROUW)));
        CONVERSIE_LIJST.add(new ConversieMapEntry<>(new Lo3AdellijkeTitelPredikaatCode("R"), new AdellijkeTitelPredikaatPaar(
                new BrpCharacter('R'),
                null,
                BrpGeslachtsaanduidingCode.MAN)));
        CONVERSIE_LIJST.add(new ConversieMapEntry<>(new Lo3AdellijkeTitelPredikaatCode("R"), new AdellijkeTitelPredikaatPaar(
                new BrpCharacter('R'),
                null,
                BrpGeslachtsaanduidingCode.VROUW)));
        CONVERSIE_LIJST.add(new ConversieMapEntry<>(new Lo3AdellijkeTitelPredikaatCode("JH"), new AdellijkeTitelPredikaatPaar(
                null,
                new BrpCharacter('J'),
                BrpGeslachtsaanduidingCode.MAN)));
        CONVERSIE_LIJST.add(new ConversieMapEntry<>(new Lo3AdellijkeTitelPredikaatCode("JV"), new AdellijkeTitelPredikaatPaar(
                null,
                new BrpCharacter('J'),
                BrpGeslachtsaanduidingCode.VROUW)));
    }

    public AdellijkeTitelPredikaatConversietabel() {
        super(CONVERSIE_LIJST);
    }
}
