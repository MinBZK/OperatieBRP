/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3;

import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3Cat;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3Doc;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3His;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3Persoon;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper.lo3Stapel;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import org.junit.Test;

public class BrpBepalenAdellijkeTitelTest {

    private static final String DOC03_OMS = "doc_omschrijving3";
    private static final String DOC03 = "doc03";
    private static final String LAND_CODE = "7777";
    private static final String GEM_CODE = "3333";
    private static final String BEL = "Bel";
    private static final String MARIE = "Marie";

    @Test
    public void testConverteer() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();

        builder.persoonStapel(
                lo3Stapel(
                        lo3Cat(
                                lo3Persoon("1234567", null, MARIE, "JH", null, BEL, 19750101, GEM_CODE, LAND_CODE, "V", null, null, "E"),
                                lo3Doc(3L, DOC03, 19750102, DOC03_OMS),
                                lo3His(19750102),
                                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));

        final Lo3Persoonslijst converteerResultaat = new BrpBepalenAdellijkeTitel().converteer(builder.build());
        assertEquals(1, converteerResultaat.getPersoonStapel().size());
        assertEquals("JV", converteerResultaat.getPersoonStapel().getLaatsteElement().getInhoud().getAdellijkeTitelPredikaatCode().getWaarde());
    }

    @Test
    public void testConverteerTitelMetOnderzoek() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();

        final Lo3PersoonInhoud persoonInhoud = lo3Persoon("1234567", null, MARIE, "JH", null, BEL, 19750101, GEM_CODE, LAND_CODE, "V", null, null, "E");
        final Lo3PersoonInhoud.Builder persoonInhoudBuilder = new Lo3PersoonInhoud.Builder(persoonInhoud);
        final Lo3Onderzoek onderzoek = new Lo3Onderzoek(new Lo3Integer(0), new Lo3Datum(0), null);
        persoonInhoudBuilder.setAdellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode("JH", onderzoek));
        final Lo3PersoonInhoud persoonInhoudOnderzoek = persoonInhoudBuilder.build();
        builder.persoonStapel(
                lo3Stapel(
                        lo3Cat(
                                persoonInhoudOnderzoek,
                                lo3Doc(3L, DOC03, 19750102, DOC03_OMS),
                                lo3His(19750102),
                                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));

        final Lo3Persoonslijst converteerResultaat = new BrpBepalenAdellijkeTitel().converteer(builder.build());
        assertEquals(1, converteerResultaat.getPersoonStapel().size());
        assertEquals("JV", converteerResultaat.getPersoonStapel().getLaatsteElement().getInhoud().getAdellijkeTitelPredikaatCode().getWaarde());
        assertSame(onderzoek, converteerResultaat.getPersoonStapel().getLaatsteElement().getInhoud().getAdellijkeTitelPredikaatCode().getOnderzoek());
    }

    @Test
    public void testConverteerGeslachtMetOnderzoek() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();

        final Lo3PersoonInhoud persoonInhoud = lo3Persoon("1234567", null, MARIE, "JH", null, BEL, 19750101, GEM_CODE, LAND_CODE, "V", null, null, "E");
        final Lo3PersoonInhoud.Builder persoonInhoudBuilder = new Lo3PersoonInhoud.Builder(persoonInhoud);
        final Lo3Onderzoek onderzoek = new Lo3Onderzoek(new Lo3Integer(0), new Lo3Datum(0), null);
        persoonInhoudBuilder.setGeslachtsaanduiding(new Lo3Geslachtsaanduiding("V", onderzoek));
        final Lo3PersoonInhoud persoonInhoudOnderzoek = persoonInhoudBuilder.build();
        builder.persoonStapel(
                lo3Stapel(
                        lo3Cat(
                                persoonInhoudOnderzoek,
                                lo3Doc(3L, DOC03, 19750102, DOC03_OMS),
                                lo3His(19750102),
                                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));

        final Lo3Persoonslijst converteerResultaat = new BrpBepalenAdellijkeTitel().converteer(builder.build());
        assertEquals(1, converteerResultaat.getPersoonStapel().size());
        assertEquals("JV", converteerResultaat.getPersoonStapel().getLaatsteElement().getInhoud().getAdellijkeTitelPredikaatCode().getWaarde());
        assertSame(onderzoek, converteerResultaat.getPersoonStapel().getLaatsteElement().getInhoud().getAdellijkeTitelPredikaatCode().getOnderzoek());
    }

    @Test
    public void testConverteerLegeTitelMetOnderzoek() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();

        final Lo3PersoonInhoud persoonInhoud = lo3Persoon("1234567", null, MARIE, null, null, BEL, 19750101, GEM_CODE, LAND_CODE, "V", null, null, "E");
        final Lo3PersoonInhoud.Builder persoonInhoudBuilder = new Lo3PersoonInhoud.Builder(persoonInhoud);
        final Lo3Onderzoek onderzoek = new Lo3Onderzoek(new Lo3Integer(0), new Lo3Datum(0), null);
        persoonInhoudBuilder.setAdellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode(null, onderzoek));
        final Lo3PersoonInhoud persoonInhoudOnderzoek = persoonInhoudBuilder.build();
        builder.persoonStapel(
                lo3Stapel(
                        lo3Cat(
                                persoonInhoudOnderzoek,
                                lo3Doc(3L, DOC03, 19750102, DOC03_OMS),
                                lo3His(19750102),
                                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));

        final Lo3Persoonslijst converteerResultaat = new BrpBepalenAdellijkeTitel().converteer(builder.build());
        assertEquals(1, converteerResultaat.getPersoonStapel().size());
        assertNull(converteerResultaat.getPersoonStapel().getLaatsteElement().getInhoud().getAdellijkeTitelPredikaatCode().getWaarde());
        assertSame(onderzoek, converteerResultaat.getPersoonStapel().getLaatsteElement().getInhoud().getAdellijkeTitelPredikaatCode().getOnderzoek());
    }

    @Test
    public void testConverteerLegeGeslachtsaanduidingMetOnderzoek() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();

        final Lo3PersoonInhoud persoonInhoud = lo3Persoon("1234567", null, MARIE, "JH", null, BEL, 19750101, GEM_CODE, LAND_CODE, null, null, null, "E");
        final Lo3PersoonInhoud.Builder persoonInhoudBuilder = new Lo3PersoonInhoud.Builder(persoonInhoud);
        final Lo3Onderzoek onderzoek = new Lo3Onderzoek(new Lo3Integer(0), new Lo3Datum(0), null);
        persoonInhoudBuilder.setGeslachtsaanduiding(new Lo3Geslachtsaanduiding(null, onderzoek));
        final Lo3PersoonInhoud persoonInhoudOnderzoek = persoonInhoudBuilder.build();
        builder.persoonStapel(
                lo3Stapel(
                        lo3Cat(
                                persoonInhoudOnderzoek,
                                lo3Doc(3L, DOC03, 19750102, DOC03_OMS),
                                lo3His(19750102),
                                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));

        final Lo3Persoonslijst converteerResultaat = new BrpBepalenAdellijkeTitel().converteer(builder.build());
        assertEquals(1, converteerResultaat.getPersoonStapel().size());
        assertEquals("JH", converteerResultaat.getPersoonStapel().getLaatsteElement().getInhoud().getAdellijkeTitelPredikaatCode().getWaarde());
        assertNull(converteerResultaat.getPersoonStapel().getLaatsteElement().getInhoud().getAdellijkeTitelPredikaatCode().getOnderzoek());
    }

    @Test
    public void testConverteerLegeTitelEnGeslachtsaanduidingMetOnderzoek() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();

        final Lo3PersoonInhoud persoonInhoud = lo3Persoon("1234567", null, MARIE, null, null, BEL, 19750101, GEM_CODE, LAND_CODE, null, null, null, "E");
        final Lo3PersoonInhoud.Builder persoonInhoudBuilder = new Lo3PersoonInhoud.Builder(persoonInhoud);
        final Lo3Onderzoek onderzoek1 = new Lo3Onderzoek(new Lo3Integer(0), new Lo3Datum(0), new Lo3Datum(0));
        final Lo3Onderzoek onderzoek2 = new Lo3Onderzoek(new Lo3Integer(0), new Lo3Datum(0), null);
        persoonInhoudBuilder.setAdellijkeTitelPredikaatCode(new Lo3AdellijkeTitelPredikaatCode(null, onderzoek1));
        persoonInhoudBuilder.setGeslachtsaanduiding(new Lo3Geslachtsaanduiding(null, onderzoek2));
        final Lo3PersoonInhoud persoonInhoudOnderzoek = persoonInhoudBuilder.build();
        builder.persoonStapel(
                lo3Stapel(
                        lo3Cat(
                                persoonInhoudOnderzoek,
                                lo3Doc(3L, DOC03, 19750102, DOC03_OMS),
                                lo3His(19750102),
                                new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0))));

        final Lo3Persoonslijst converteerResultaat = new BrpBepalenAdellijkeTitel().converteer(builder.build());
        assertEquals(1, converteerResultaat.getPersoonStapel().size());
        assertNull(converteerResultaat.getPersoonStapel().getLaatsteElement().getInhoud().getAdellijkeTitelPredikaatCode().getWaarde());
        assertSame(onderzoek1, converteerResultaat.getPersoonStapel().getLaatsteElement().getInhoud().getAdellijkeTitelPredikaatCode().getOnderzoek());
    }
}
