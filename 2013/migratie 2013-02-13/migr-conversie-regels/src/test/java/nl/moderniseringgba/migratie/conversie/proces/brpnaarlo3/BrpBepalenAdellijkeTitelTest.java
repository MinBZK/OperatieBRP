/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3;

import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper.datum;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper.lo3Cat;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper.lo3Doc;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper.lo3His;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper.lo3Persoon;
import static nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper.lo3Stapel;
import static org.junit.Assert.assertEquals;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;

import org.junit.Test;

public class BrpBepalenAdellijkeTitelTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testConverteer() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();

        builder.persoonStapel(lo3Stapel(lo3Cat(lo3Persoon(1234567L, "Pietje", "Bel", 19750101, "3333", "7777", "M"),
                lo3His(19750102), lo3Doc(3L, "doc03", 19750102, "doc_omschrijving3"), new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_01, 0, 0))));
        builder.ouder1Stapel(maakOuder1Stapel());
        builder.ouder2Stapel(maakOuder2Stapel());

        final Lo3Persoonslijst converteerResultaat = new BrpBepalenAdellijkeTitel().converteer(builder.build());
        assertEquals(1, converteerResultaat.getOuder1Stapels().size());
        assertEquals(1, converteerResultaat.getOuder2Stapels().size());
        assertEquals("JV", converteerResultaat.getOuder1Stapels().get(0).getMeestRecenteElement().getInhoud()
                .getAdellijkeTitelPredikaatCode().getCode());
        assertEquals("JH", converteerResultaat.getOuder2Stapels().get(0).getMeestRecenteElement().getInhoud()
                .getAdellijkeTitelPredikaatCode().getCode());
    }

    @SuppressWarnings("unchecked")
    private Lo3Stapel<Lo3OuderInhoud> maakOuder1Stapel() {
        final Lo3OuderInhoud lo3OuderInhoud =
                new Lo3OuderInhoud(1823718314L, 4138173281L, "Jane", new Lo3AdellijkeTitelPredikaatCode("JH"), "de",
                        "Grote", datum(19800101), new Lo3GemeenteCode("1111"), new Lo3LandCode("2222"),
                        new Lo3Geslachtsaanduiding("V"), datum(20000101));
        return lo3Stapel(lo3Cat(lo3OuderInhoud, lo3His(20000101), lo3Doc(1L, "doc01", 20000102, "doc_omschrijving"),
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 0, 0)));
    }

    @SuppressWarnings("unchecked")
    private Lo3Stapel<Lo3OuderInhoud> maakOuder2Stapel() {
        final Lo3OuderInhoud lo3OuderInhoud =
                new Lo3OuderInhoud(1823718315L, 5138173281L, "Jack", new Lo3AdellijkeTitelPredikaatCode("JH"), "de",
                        "Grote", datum(19800101), new Lo3GemeenteCode("1111"), new Lo3LandCode("2222"),
                        new Lo3Geslachtsaanduiding("M"), datum(20000101));
        return lo3Stapel(lo3Cat(lo3OuderInhoud, lo3His(20000101), lo3Doc(2L, "doc02", 20000102, "doc_omschrijving2"),
                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_03, 0, 0)));
    }
}
