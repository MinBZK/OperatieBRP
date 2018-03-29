/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;

import static org.junit.Assert.assertEquals;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.expressie.Expressie;
import org.junit.Assert;
import org.junit.Test;

/**
 * ExpressieCacheUtilTest.
 */
public class ExpressieCacheUtilTest {

    @Test
    public void testParseCorrect() {
        final Expressie expressie = ExpressieCacheUtil.parseExpressie("WAAR", 1);
        Assert.assertNotNull(expressie);
    }

    @Test
    public void testParseFout() {
        final Expressie expressie = ExpressieCacheUtil.parseExpressie("//}", 1);
        Assert.assertNull(expressie);
    }

    @Test
    public void testValideerCorrect() {
        final boolean valide = ExpressieCacheUtil.valideerExpressie("WAAR", 1);
        Assert.assertTrue(valide);
    }

    @Test
    public void testValideerCorrectLeeg() {
        final boolean valide = ExpressieCacheUtil.valideerExpressie(null, 1);
        Assert.assertTrue(valide);
    }

    @Test
    public void testValideerFout() {
        final boolean valide = ExpressieCacheUtil.valideerExpressie("//}", 1);
        Assert.assertFalse(valide);
    }

    @Test
    public void testPopulatieBeperkingLeeg() {

        final Partij partij = TestPartijBuilder.maakBuilder().metCode("000000").build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);

        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);

        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        tla.setId(1);

        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);

        leveringsautorisatie.addDienstbundelSet(dienstbundel);

        final Expressie expressie = ExpressieCacheUtil.maakTotalePopulatieBeperkingExpressie(tla, dienstbundel);

        assertEquals("WAAR EN (WAAR EN WAAR)", expressie.alsString());

    }

    @Test
    public void testPopulatieBeperkingGevuld() {

        final Partij partij = TestPartijBuilder.maakBuilder().metCode("000000").build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);

        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);

        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        tla.setId(1);
        tla.setNaderePopulatiebeperking("1 = 1");

        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        dienstbundel.setNaderePopulatiebeperking("2 = 2");

        leveringsautorisatie.addDienstbundelSet(dienstbundel);
        leveringsautorisatie.setPopulatiebeperking("3 = 3");

        final Expressie expressie = ExpressieCacheUtil.maakTotalePopulatieBeperkingExpressie(tla, dienstbundel);

        assertEquals("3 = 3 EN (2 = 2 EN 1 = 1)", expressie.alsString());
    }

    @Test
    public void testHaakjes() {

        final Partij partij = TestPartijBuilder.maakBuilder().metCode("000001").build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);

        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);

        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        tla.setId(1);
        tla.setNaderePopulatiebeperking("WAAR");

        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        dienstbundel.setNaderePopulatiebeperking("WAAR");

        leveringsautorisatie.addDienstbundelSet(dienstbundel);
        leveringsautorisatie.setPopulatiebeperking("Persoon.Bijhouding.PartijCode EIN x WAARBIJ x={62601}");

        final Expressie expressie = ExpressieCacheUtil.maakTotalePopulatieBeperkingExpressie(tla, dienstbundel);

        assertEquals("((Persoon.Bijhouding.PartijCode EIN x) WAARBIJ x = {62601}) EN (WAAR EN WAAR)", expressie.alsString());
    }

    @Test
    public void testMaakSelectieExpressies() {
        final Autorisatiebundel autorisatiebundel = TestAutorisaties.maakAutorisatiebundel(SoortDienst.SELECTIE);
       ToegangLeveringsAutorisatie tla = autorisatiebundel.getToegangLeveringsautorisatie();
       tla.setId(1);
       tla.setNaderePopulatiebeperking("1 = 1");
       tla.getLeveringsautorisatie().setPopulatiebeperking("2 = 2");
        final Dienst dienst = autorisatiebundel.getDienst();
        dienst.getDienstbundel().setNaderePopulatiebeperking("3 = 3");
        dienst.setSoortSelectie(SoortSelectie.SELECTIE_MET_PLAATSING_AFNEMERINDICATIE.getId());

        final Expressie expressie = ExpressieCacheUtil.maakSelectieExpressie(tla, dienst);

        assertEquals("2 = 2 EN (3 = 3 EN (1 = 1 EN (WAAR EN SELECTIE_LIJST())))", expressie.alsString());
    }

    @Test
    public void testMaakSelectieExpressies_SelectieMetVerwijdering() {
        final Autorisatiebundel autorisatiebundel = TestAutorisaties.maakAutorisatiebundel(SoortDienst.SELECTIE);
        ToegangLeveringsAutorisatie tla = autorisatiebundel.getToegangLeveringsautorisatie();
        tla.setId(1);
        tla.setNaderePopulatiebeperking("1 = 1");
        tla.getLeveringsautorisatie().setPopulatiebeperking("2 = 2");
        final Dienst dienst = autorisatiebundel.getDienst();
        dienst.getDienstbundel().setNaderePopulatiebeperking("3 = 3");
        dienst.setSoortSelectie(SoortSelectie.SELECTIE_MET_VERWIJDERING_AFNEMERINDICATIE.getId());

        final Expressie expressie = ExpressieCacheUtil.maakSelectieExpressie(tla, dienst);

        assertEquals("WAAR EN SELECTIE_LIJST()", expressie.alsString());
    }
}
