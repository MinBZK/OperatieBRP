/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActieBron;
import nl.bzk.migratiebrp.conversie.model.brp.BrpBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.brp.BrpBetrokkenheidTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorieTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBooleanTest;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentiteitInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstStandaardGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstStandaardGroepInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderlijkGezagInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BrpStapelSorterTest {

    @Test
    public void testSorteerPL() {
        final BrpPersoonslijst pl = BrpStapelSorter.sorteerPersoonslijst(createValidBrpPersoonslijst(new BrpGemeenteCode((short) 1904)));
        assertNotNull(pl);
    }

    @Test(expected = NullPointerException.class)
    public void testSorteerLegePL() {
        BrpStapelSorter.sorteerPersoonslijst(null);
    }

    @Test
    public void testSorteerPLLegeStapel() {
        final BrpPersoonslijst pl = BrpStapelSorter.sorteerPersoonslijst(createBrpPersoonslijstMissing(new BrpGemeenteCode((short) 1904)));
        assertNotNull(pl);
    }

    @Test
    public void testSorteerPLVolgorde() {
        final BrpPersoonslijst origPl = createBrpPersoonslijstWrongOrder(new BrpGemeenteCode((short) 1904));

        // check order origPl Voornaameen is de eerst toegevoegde en komt vooraan te staan.
        for (final BrpStapel<BrpVoornaamInhoud> stapel : origPl.getVoornaamStapels()) {
            assertEquals("Voornaameen", BrpString.unwrap(stapel.get(0).getInhoud().getVoornaam()));
        }
        final BrpPersoonslijst pl = BrpStapelSorter.sorteerPersoonslijst(origPl);
        // check order pl Voornaamtwee zou nu vooraan moeten staan.
        for (final BrpStapel<BrpVoornaamInhoud> stapel : pl.getVoornaamStapels()) {
            assertEquals("Voornaamtwee", BrpString.unwrap(stapel.get(0).getInhoud().getVoornaam()));
        }

        assertNotNull(pl);
    }

    @Test
    public void testSorteerVerificatieStapels() {
        List<BrpStapel<BrpVerificatieInhoud>> stapels = new ArrayList<>();
        stapels.add(createVerificatieStapel(BrpPartijCode.MINISTER));
        stapels.add(createVerificatieStapel(BrpPartijCode.ONBEKEND));
        stapels.add(createVerificatieStapel(BrpPartijCode.MIGRATIEVOORZIENING));

        List<BrpStapel<BrpVerificatieInhoud>> result = BrpStapelSorter.sorteerVerificatieStapels(stapels);
        assertEquals(3, result.size());
        assertEquals(0, result.get(0).get(0).getInhoud().getPartij().getWaarde().intValue());
        assertEquals(199901, result.get(1).get(0).getInhoud().getPartij().getWaarde().intValue());
        assertEquals(199902, result.get(2).get(0).getInhoud().getPartij().getWaarde().intValue());
    }

    @Test
    public void testSorteerBetrokkenheid() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Constructor<BrpStapelSorter> c = BrpStapelSorter.class.getDeclaredConstructor();
        c.setAccessible(true);
        BrpStapelSorter sorter = c.newInstance();

        Method pm = getDeclareMethodSorteerBetrokkenheid();
        BrpSoortBetrokkenheidCode betr = new BrpSoortBetrokkenheidCode("ouder", "mama");
        BrpBetrokkenheid betrokkenheid =
                new BrpBetrokkenheid(
                    betr,
                    BrpIdentificatienummersInhoudTest.createStapel(),
                    BrpGeslachtsaanduidingInhoudTest.createStapel(),
                    BrpGeboorteInhoudTest.createStapel(),
                    BrpOuderlijkGezagInhoudTest.createStapel(Boolean.TRUE),
                    BrpSamengesteldeNaamInhoudTest.createStapel(),
                    BrpOuderInhoudTest.createStapel(),
                    BrpIdentiteitInhoudTest.createStapel());
        BrpBetrokkenheid result = (BrpBetrokkenheid) pm.invoke(sorter, betrokkenheid);
        assertNotNull(result);
    }

    @Test
    public void testSorteerIstGroepLijst() {
        List<BrpGroep<BrpIstGezagsVerhoudingGroepInhoud>> groepen = new ArrayList<>();
        groepen.add(getBrpIstGezagsVerhoudingGroepInhoudBrpGroep(Lo3CategorieEnum.CATEGORIE_01, 1, 1));
        groepen.add(getBrpIstGezagsVerhoudingGroepInhoudBrpGroep(Lo3CategorieEnum.CATEGORIE_01, 2, 2));
        groepen.add(getBrpIstGezagsVerhoudingGroepInhoudBrpGroep(Lo3CategorieEnum.CATEGORIE_02, 1, 2));
        groepen.add(getBrpIstGezagsVerhoudingGroepInhoudBrpGroep(Lo3CategorieEnum.CATEGORIE_01, 1, 3));
        groepen.add(getBrpIstGezagsVerhoudingGroepInhoudBrpGroep(Lo3CategorieEnum.CATEGORIE_01, 1, 2));
        BrpStapelSorter.sorteerIstGroepLijst(groepen);
        assertEquals(5, groepen.size());
        BrpIstGezagsVerhoudingGroepInhoud inh1 = groepen.get(0).getInhoud();
        BrpIstGezagsVerhoudingGroepInhoud inh2 = groepen.get(1).getInhoud();
        BrpIstGezagsVerhoudingGroepInhoud inh3 = groepen.get(2).getInhoud();
        BrpIstGezagsVerhoudingGroepInhoud inh4 = groepen.get(3).getInhoud();
        BrpIstGezagsVerhoudingGroepInhoud inh5 = groepen.get(4).getInhoud();
        assertTrue(inh1.getCategorie().getCategorieAsInt() == 1 && inh1.getStapel() == 1 && inh1.getVoorkomen() == 1);
        assertTrue(inh2.getCategorie().getCategorieAsInt() == 1 && inh2.getStapel() == 1 && inh2.getVoorkomen() == 2);
        assertTrue(inh3.getCategorie().getCategorieAsInt() == 1 && inh3.getStapel() == 1 && inh3.getVoorkomen() == 3);
        assertTrue(inh4.getCategorie().getCategorieAsInt() == 1 && inh4.getStapel() == 2 && inh4.getVoorkomen() == 2);
        assertTrue(inh5.getCategorie().getCategorieAsInt() == 2 && inh5.getStapel() == 1 && inh5.getVoorkomen() == 2);
    }

    @Test
    public void testSorteerIstGroepLijstNullOrEmptyList() {
        try {
            BrpStapelSorter.sorteerIstGroepLijst(null);
            BrpStapelSorter.sorteerIstGroepLijst(Collections.EMPTY_LIST);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testSorteerGroepLijstNullOfLegeLijst() {
        try {
            BrpStapelSorter.sorteerGroepLijst(null);
            BrpStapelSorter.sorteerGroepLijst(Collections.EMPTY_LIST);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testSorteerGroepLijst() {
        List<BrpGroep<BrpInschrijvingInhoud>> groepen = new ArrayList<>();
        BrpActie actie1 = new BrpActie(1L, BrpSoortActieCode.CONVERSIE_GBA, BrpPartijCode.ONBEKEND, null, null, null, 0, null);
        BrpActie actie2 = new BrpActie(1L, BrpSoortActieCode.VERHUIZING, BrpPartijCode.ONBEKEND, null, null, null, 0, null);
        groepen.add(
            new BrpGroep<>(
                new BrpInschrijvingInhoud(BrpDatum.ONBEKEND, new BrpLong(0L), BrpDatumTijd.NULL_DATUM_TIJD),
                BrpHistorieTest.createdefaultInhoud(),
                null,
                null,
                null));
        groepen.add(
            new BrpGroep<>(
                BrpInschrijvingInhoudTest.createInhoud(20110101, 1, new Date()),
                BrpHistorieTest.createInhoud(20100202, null, 20100606, null),
                null,
                null,
                null));
        groepen.add(
            new BrpGroep<>(
                BrpInschrijvingInhoudTest.createInhoud(20110101, 2, new Date()),
                BrpHistorieTest.createInhoud(20100202, null, 20100605, null),
                null,
                null,
                null));
        groepen.add(
            new BrpGroep<>(
                BrpInschrijvingInhoudTest.createInhoud(20110101, 3, new Date()),
                BrpHistorieTest.createInhoud(20100201, null, 20100606, null),
                null,
                null,
                null));
        groepen.add(
            new BrpGroep<>(
                BrpInschrijvingInhoudTest.createInhoud(20110101, 4, new Date()),
                BrpHistorieTest.createInhoud(20100201, null, 20100606, 20110102),
                null,
                null,
                null));
        groepen.add(
            new BrpGroep<>(
                BrpInschrijvingInhoudTest.createInhoud(20110101, 5, new Date()),
                BrpHistorieTest.createInhoud(20100201, null, 20100606, 20110101),
                null,
                null,
                null));
        groepen.add(
            new BrpGroep<>(
                BrpInschrijvingInhoudTest.createInhoud(20110101, 6, new Date()),
                BrpHistorieTest.createInhoud(20100201, 20110202, 20100606, 20110101),
                null,
                null,
                null));
        groepen.add(
            new BrpGroep<>(
                BrpInschrijvingInhoudTest.createInhoud(20110101, 7, new Date()),
                BrpHistorieTest.createInhoud(20100201, 20110201, 20100606, 20110101),
                null,
                null,
                null));
        groepen.add(
            new BrpGroep<>(
                BrpInschrijvingInhoudTest.createInhoud(20110101, 8, new Date()),
                BrpHistorieTest.createInhoud(20100201, 20110201, 20100606, 20110101),
                actie1,
                null,
                null));
        groepen.add(
            new BrpGroep<>(
                BrpInschrijvingInhoudTest.createInhoud(20110101, 9, new Date()),
                BrpHistorieTest.createInhoud(20100201, 20110201, 20100606, 20110101),
                actie2,
                null,
                null));
        groepen.add(
            new BrpGroep<>(
                BrpInschrijvingInhoudTest.createInhoud(20110101, 10, new Date()),
                BrpHistorieTest.createInhoud(20100201, 20110201, 20100606, 20110101),
                actie1,
                null,
                null));
        BrpStapelSorter.sorteerGroepLijst(groepen);
        BrpGroep<BrpInschrijvingInhoud> groep1 = groepen.get(0);
        BrpGroep<BrpInschrijvingInhoud> groep2 = groepen.get(1);
        BrpGroep<BrpInschrijvingInhoud> groep3 = groepen.get(2);
        BrpGroep<BrpInschrijvingInhoud> groep4 = groepen.get(3);
        BrpGroep<BrpInschrijvingInhoud> groep5 = groepen.get(4);
        BrpGroep<BrpInschrijvingInhoud> groep6 = groepen.get(5);
        BrpGroep<BrpInschrijvingInhoud> groep7 = groepen.get(6);
        BrpGroep<BrpInschrijvingInhoud> groep8 = groepen.get(7);
        BrpGroep<BrpInschrijvingInhoud> groep9 = groepen.get(8);
        BrpGroep<BrpInschrijvingInhoud> groep10 = groepen.get(9);
        BrpGroep<BrpInschrijvingInhoud> groep11 = groepen.get(10);
        assertEquals(1, groep1.getInhoud().getVersienummer().getWaarde().intValue());
        assertEquals(3, groep2.getInhoud().getVersienummer().getWaarde().intValue());
        assertEquals(5, groep3.getInhoud().getVersienummer().getWaarde().intValue());
        assertEquals(7, groep4.getInhoud().getVersienummer().getWaarde().intValue());
        assertEquals(8, groep5.getInhoud().getVersienummer().getWaarde().intValue());
        assertEquals(9, groep6.getInhoud().getVersienummer().getWaarde().intValue());
        assertEquals(10, groep7.getInhoud().getVersienummer().getWaarde().intValue());
        assertEquals(6, groep8.getInhoud().getVersienummer().getWaarde().intValue());
        assertEquals(4, groep9.getInhoud().getVersienummer().getWaarde().intValue());
        assertEquals(2, groep10.getInhoud().getVersienummer().getWaarde().intValue());
        assertEquals(0, groep11.getInhoud().getVersienummer().getWaarde().intValue());

    }

    @Test
    public void testSorteerGroepLijstIst() {
        List<BrpGroep<BrpIstGezagsVerhoudingGroepInhoud>> groepen = new ArrayList<>();
        groepen.add(getBrpIstGezagsVerhoudingGroepInhoudBrpGroep(Lo3CategorieEnum.CATEGORIE_01, 1, 1));
        groepen.add(getBrpIstGezagsVerhoudingGroepInhoudBrpGroep(Lo3CategorieEnum.CATEGORIE_01, 2, 2));
        groepen.add(getBrpIstGezagsVerhoudingGroepInhoudBrpGroep(Lo3CategorieEnum.CATEGORIE_02, 1, 2));
        groepen.add(getBrpIstGezagsVerhoudingGroepInhoudBrpGroep(Lo3CategorieEnum.CATEGORIE_01, 1, 3));
        groepen.add(getBrpIstGezagsVerhoudingGroepInhoudBrpGroep(Lo3CategorieEnum.CATEGORIE_01, 1, 2));
        BrpStapelSorter.sorteerGroepLijst(groepen);
        assertEquals(5, groepen.size());
        BrpIstGezagsVerhoudingGroepInhoud inh1 = groepen.get(0).getInhoud();
        BrpIstGezagsVerhoudingGroepInhoud inh2 = groepen.get(1).getInhoud();
        BrpIstGezagsVerhoudingGroepInhoud inh3 = groepen.get(2).getInhoud();
        BrpIstGezagsVerhoudingGroepInhoud inh4 = groepen.get(3).getInhoud();
        BrpIstGezagsVerhoudingGroepInhoud inh5 = groepen.get(4).getInhoud();
        assertTrue(inh1.getCategorie().getCategorieAsInt() == 1 && inh1.getStapel() == 1 && inh1.getVoorkomen() == 3);
        assertTrue(inh2.getCategorie().getCategorieAsInt() == 1 && inh2.getStapel() == 2 && inh2.getVoorkomen() == 2);
        assertTrue(inh3.getCategorie().getCategorieAsInt() == 2 && inh3.getStapel() == 1 && inh3.getVoorkomen() == 2);
        assertTrue(inh4.getCategorie().getCategorieAsInt() == 1 && inh4.getStapel() == 1 && inh4.getVoorkomen() == 2);
        assertTrue(inh5.getCategorie().getCategorieAsInt() == 1 && inh5.getStapel() == 1 && inh5.getVoorkomen() == 1);
    }

    @Test
    public void testSorteerIstGroeplijst() {
        List<BrpStapel<BrpIstGezagsVerhoudingGroepInhoud>> brpStapels = new ArrayList<>();

        List<BrpGroep<BrpIstGezagsVerhoudingGroepInhoud>> groepen = new ArrayList<>();
        groepen.add(getBrpIstGezagsVerhoudingGroepInhoudBrpGroep(Lo3CategorieEnum.CATEGORIE_01, 1, 1));
        groepen.add(getBrpIstGezagsVerhoudingGroepInhoudBrpGroep(Lo3CategorieEnum.CATEGORIE_01, 2, 2));
        BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> stapel = new BrpStapel<>(groepen);
        brpStapels.add(stapel);
        List<BrpGroep<BrpIstGezagsVerhoudingGroepInhoud>> groepen3 = new ArrayList<>();
        groepen3.add(getBrpIstGezagsVerhoudingGroepInhoudBrpGroep(Lo3CategorieEnum.CATEGORIE_03, 1, 1));
        groepen3.add(getBrpIstGezagsVerhoudingGroepInhoudBrpGroep(Lo3CategorieEnum.CATEGORIE_03, 2, 2));
        BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> stapel3 = new BrpStapel<>(groepen3);
        brpStapels.add(stapel3);
        List<BrpGroep<BrpIstGezagsVerhoudingGroepInhoud>> groepen2 = new ArrayList<>();
        groepen2.add(getBrpIstGezagsVerhoudingGroepInhoudBrpGroep(Lo3CategorieEnum.CATEGORIE_02, 1, 1));
        groepen2.add(getBrpIstGezagsVerhoudingGroepInhoudBrpGroep(Lo3CategorieEnum.CATEGORIE_02, 1, 3));
        groepen2.add(getBrpIstGezagsVerhoudingGroepInhoudBrpGroep(Lo3CategorieEnum.CATEGORIE_02, 1, 2));
        BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> stapel2 = new BrpStapel<>(groepen2);
        brpStapels.add(stapel2);
        List<BrpGroep<BrpIstGezagsVerhoudingGroepInhoud>> groepen4 = new ArrayList<>();
        groepen4.add(getBrpIstGezagsVerhoudingGroepInhoudBrpGroep(Lo3CategorieEnum.CATEGORIE_02, 2, 1));
        groepen4.add(getBrpIstGezagsVerhoudingGroepInhoudBrpGroep(Lo3CategorieEnum.CATEGORIE_02, 2, 3));
        groepen4.add(getBrpIstGezagsVerhoudingGroepInhoudBrpGroep(Lo3CategorieEnum.CATEGORIE_02, 2, 2));
        BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> stapel4 = new BrpStapel<>(groepen4);
        brpStapels.add(stapel4);
        List<BrpGroep<BrpIstGezagsVerhoudingGroepInhoud>> groepen5 = new ArrayList<>();
        groepen5.add(getBrpIstGezagsVerhoudingGroepInhoudBrpGroep(Lo3CategorieEnum.CATEGORIE_01, 1, 1));
        groepen5.add(getBrpIstGezagsVerhoudingGroepInhoudBrpGroep(Lo3CategorieEnum.CATEGORIE_01, 2, 2));
        BrpStapel<BrpIstGezagsVerhoudingGroepInhoud> stapel5 = new BrpStapel<>(groepen5);
        brpStapels.add(stapel5);
        BrpStapelSorter.sorteerIstLijst(brpStapels);
        assertEquals(5, brpStapels.size());
        assertEquals(1, brpStapels.get(0).get(0).getInhoud().getCategorie().getCategorieAsInt());
        assertEquals(1, brpStapels.get(1).get(0).getInhoud().getCategorie().getCategorieAsInt());
        assertEquals(2, brpStapels.get(2).get(0).getInhoud().getCategorie().getCategorieAsInt());
        assertEquals(2, brpStapels.get(3).get(0).getInhoud().getCategorie().getCategorieAsInt());
        assertEquals(3, brpStapels.get(4).get(0).getInhoud().getCategorie().getCategorieAsInt());
    }

    @Test
    public void testSorteerBetrokkenheidLijst() {
        BrpStapelSorter.sorteerBetrokkenheidLijst(null);
        BrpStapelSorter.sorteerBetrokkenheidLijst(Collections.EMPTY_LIST);
        List<BrpBetrokkenheid> brpBetrokkenheids = new ArrayList<>();
        brpBetrokkenheids.add(BrpBetrokkenheidTest.maakBetrokkenheid(BrpSoortBetrokkenheidCode.KIND));
        brpBetrokkenheids.add(BrpBetrokkenheidTest.maakBetrokkenheid(BrpSoortBetrokkenheidCode.OUDER));
        brpBetrokkenheids.add(BrpBetrokkenheidTest.maakBetrokkenheid(BrpSoortBetrokkenheidCode.PARTNER));
        brpBetrokkenheids.add(BrpBetrokkenheidTest.maakBetrokkenheid(BrpSoortBetrokkenheidCode.KIND));
        brpBetrokkenheids.add(BrpBetrokkenheidTest.maakBetrokkenheid(BrpSoortBetrokkenheidCode.PARTNER));
        brpBetrokkenheids.add(BrpBetrokkenheidTest.maakBetrokkenheid(BrpSoortBetrokkenheidCode.KIND));
        brpBetrokkenheids.add(BrpBetrokkenheidTest.maakBetrokkenheid(BrpSoortBetrokkenheidCode.OUDER));
        BrpStapelSorter.sorteerBetrokkenheidLijst(brpBetrokkenheids);
        assertEquals(7, brpBetrokkenheids.size());
        assertEquals("K", brpBetrokkenheids.get(0).getRol().getWaarde());
        assertEquals("K", brpBetrokkenheids.get(1).getRol().getWaarde());
        assertEquals("K", brpBetrokkenheids.get(2).getRol().getWaarde());
        assertEquals("O", brpBetrokkenheids.get(3).getRol().getWaarde());
        assertEquals("O", brpBetrokkenheids.get(4).getRol().getWaarde());
        assertEquals("P", brpBetrokkenheids.get(5).getRol().getWaarde());
        assertEquals("P", brpBetrokkenheids.get(6).getRol().getWaarde());
    }

    @Test
    public void testSorteerActieBronnen() {
        BrpStapelSorter.sorteerActieBronnen(null);
        BrpStapelSorter.sorteerActieBronnen(Collections.EMPTY_LIST);
        List<BrpActieBron> actieBronnenLijst = new ArrayList<>();

        actieBronnenLijst.add(new BrpActieBron(null, new BrpString("Text-1")));
        actieBronnenLijst.add(new BrpActieBron(null, new BrpString("Text-2")));
        actieBronnenLijst.add(new BrpActieBron(null, new BrpString("Text-3")));
        actieBronnenLijst.add(new BrpActieBron(null, new BrpString("Text-0")));
        actieBronnenLijst.add(new BrpActieBron(null, new BrpString("Text-2")));

        List<BrpGroep<BrpDocumentInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpDocumentInhoud> groep = new BrpGroep<>(BrpDocumentInhoudTest.createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        BrpStapel<BrpDocumentInhoud> brpStapel = new BrpStapel<>(groepen);
        BrpActieBron actieBron = new BrpActieBron(brpStapel, new BrpString("Text-1"));
        actieBronnenLijst.add(actieBron);

        List<BrpGroep<BrpDocumentInhoud>> groepen2 = new ArrayList<>();
        BrpGroep<BrpDocumentInhoud> groep2 = new BrpGroep<>(BrpDocumentInhoudTest.createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen2.add(groep2);
        BrpStapel<BrpDocumentInhoud> brpStapel2 = new BrpStapel<>(groepen2);
        BrpActieBron actieBron2 = new BrpActieBron(brpStapel2, new BrpString("Text-2"));
        actieBronnenLijst.add(actieBron2);

        actieBronnenLijst.add(new BrpActieBron(null, new BrpString("Text-1")));
        actieBronnenLijst.add(new BrpActieBron(null, new BrpString("Text-2")));
        actieBronnenLijst.add(new BrpActieBron(null, new BrpString("Text-3")));
        actieBronnenLijst.add(new BrpActieBron(null, new BrpString("Text-0")));
        actieBronnenLijst.add(new BrpActieBron(null, new BrpString("Text-2")));

        List<BrpGroep<BrpDocumentInhoud>> groepen3 = new ArrayList<>();
        BrpGroep<BrpDocumentInhoud> groep3 = new BrpGroep<>(BrpDocumentInhoudTest.createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen3.add(groep3);
        BrpStapel<BrpDocumentInhoud> brpStapel3 = new BrpStapel<>(groepen3);
        BrpActieBron actieBron3 = new BrpActieBron(brpStapel3, new BrpString("Text-0"));
        actieBronnenLijst.add(actieBron3);

        List<BrpGroep<BrpDocumentInhoud>> groepen4 = new ArrayList<>();
        BrpGroep<BrpDocumentInhoud> groep4 = new BrpGroep<>(BrpDocumentInhoudTest.createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen4.add(groep4);
        BrpStapel<BrpDocumentInhoud> brpStapel4 = new BrpStapel<>(groepen4);
        BrpActieBron actieBron4 = new BrpActieBron(brpStapel4, new BrpString("Text-1"));
        actieBronnenLijst.add(actieBron4);

        actieBronnenLijst.add(new BrpActieBron(null, new BrpString("Text-1")));
        actieBronnenLijst.add(new BrpActieBron(null, new BrpString("Text-2")));
        actieBronnenLijst.add(new BrpActieBron(null, new BrpString("Text-3")));
        actieBronnenLijst.add(new BrpActieBron(null, new BrpString("Text-0")));
        actieBronnenLijst.add(new BrpActieBron(null, new BrpString("Text-2")));


        List<BrpActieBron> result = BrpStapelSorter.sorteerActieBronnen(actieBronnenLijst);
        assertEquals(19, result.size());
        String text = null;
        for (BrpActieBron bron : result) {
            if (text != null) {
                assertTrue(
                    "de lijst is gesorteerd, de vorige waarde is gelijk of kleiner dan de huidige waarde",
                    text.compareTo(bron.getRechtsgrondOmschrijving().getWaarde()) < 1);
            }
            text = bron.getRechtsgrondOmschrijving().getWaarde();
        }

    }

    @Test
    public void testBrpRelatiesComparator() {

        List<BrpRelatie> lijst = new ArrayList<>();
        lijst.add(createrelatie("Jan Willem", "Von", "Baron", "Munchausen"));
        lijst.add(createrelatie(null, "Von", "Baron", null));
        lijst.add(createrelatie("Jan Willem", "Von", "Baron", null));
        lijst.add(createrelatie(null, "Von", "Baron", "Munchausen"));
        Collections.sort(lijst, new BrpStapelSorter.BrpRelatiesComparator());
        assertEquals(4, lijst.size());
    }

    private BrpRelatie createrelatie(String voornaam, String voorvoegsel, String titel, String stam) {
        BrpSoortRelatieCode brpSoortRelatieCode = BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING;
        BrpSoortBetrokkenheidCode brpSoortBetrokkenheidCode = BrpSoortBetrokkenheidCode.KIND;
        BrpBetrokkenheid ikBetrokkenheid = BrpBetrokkenheidTest.maakBetrokkenheid(BrpSoortBetrokkenheidCode.OUDER);
        List<BrpBetrokkenheid> betrokkenheden = new ArrayList<>();
        BrpStapel<BrpSamengesteldeNaamInhoud> samenGesteldeNaamStapel = BrpSamengesteldeNaamInhoudTest.createStapel(voornaam, voorvoegsel, titel, stam);
        betrokkenheden.add(
            BrpBetrokkenheidTest.maakBetrokkenheidINumMetSnaam(
                BrpSoortBetrokkenheidCode.KIND,
                BrpIdentificatienummersInhoudTest.createStapel(),
                samenGesteldeNaamStapel));
        betrokkenheden.add(
            BrpBetrokkenheidTest.maakBetrokkenheidINumMetSnaam(
                BrpSoortBetrokkenheidCode.KIND,
                BrpIdentificatienummersInhoudTest.createStapel(),
                samenGesteldeNaamStapel));
        betrokkenheden.add(
            BrpBetrokkenheidTest.maakBetrokkenheidINumMetSnaam(
                BrpSoortBetrokkenheidCode.KIND,
                BrpIdentificatienummersInhoudTest.createStapel(),
                samenGesteldeNaamStapel));
        return new BrpRelatie(brpSoortRelatieCode, brpSoortBetrokkenheidCode, ikBetrokkenheid, betrokkenheden, null, null, null, null, null, null);
    }

    private BrpGroep<BrpIstGezagsVerhoudingGroepInhoud> getBrpIstGezagsVerhoudingGroepInhoudBrpGroep(Lo3CategorieEnum cat, int stapel, int voorkomen) {
        BrpIstStandaardGroepInhoud standaard = BrpIstStandaardGroepInhoudTest.createInhoud(cat, stapel, voorkomen);
        BrpIstGezagsVerhoudingGroepInhoud inhoud =
                new BrpIstGezagsVerhoudingGroepInhoud(
                    standaard,
                    BrpBooleanTest.BRP_TRUE,
                    BrpBooleanTest.BRP_FALSE,
                    BrpBooleanTest.BRP_FALSE,
                    BrpBooleanTest.BRP_FALSE);
        return new BrpGroep<>(inhoud, BrpHistorieTest.createdefaultInhoud(), null, null, null);
    }

    private Method getDeclareMethodSorteerBetrokkenheid() throws NoSuchMethodException {
        Method pm = BrpStapelSorter.class.getDeclaredMethod("sorteerBetrokkenheid", BrpBetrokkenheid.class);
        pm.setAccessible(true);
        return pm;
    }

    private BrpStapel<BrpVerificatieInhoud> createVerificatieStapel(BrpPartijCode partij) {
        BrpGroep<BrpVerificatieInhoud> groep1 =
                new BrpGroep<>(BrpVerificatieInhoudTest.createInhoud(partij), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        List<BrpGroep<BrpVerificatieInhoud>> groepen1 = new ArrayList<>();
        groepen1.add(groep1);
        return new BrpStapel<>(groepen1);
    }

    private BrpPersoonslijst createBrpPersoonslijstWrongOrder(final BrpGemeenteCode gemeenteCode) {
        final BrpPartijCode brpGemeenteCode;
        if (gemeenteCode == null) {
            brpGemeenteCode = new BrpPartijCode(59901);
        } else {
            brpGemeenteCode = new BrpPartijCode(gemeenteCode.getWaarde() * 100 + 1);
        }
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final List<BrpGroep<BrpBijhoudingInhoud>> groepen = new ArrayList<>();

        groepen.add(
            new BrpGroep<>(
                new BrpBijhoudingInhoud(brpGemeenteCode, null, null, new BrpBoolean(false, null)),
                new BrpHistorie(
                    new BrpDatum(20000101, null),
                    new BrpDatum(20110101, null),
                    new BrpDatumTijd(new Date()),
                    new BrpDatumTijd(new Date()),
                    null),
                null,
                null,
                null));
        builder.bijhoudingStapel(new BrpStapel<>(groepen));

        // Relatie 1
        createRelatie(builder);

        // Relatie 2
        createRelatie(builder);

        // Geslachtsnaam 1
        createGeslachtsnaam(builder);

        // Geslachtsnaam 2
        createGeslachtsnaam(builder);

        // Voornaam 1
        createVoornaamMultiple(builder);

        // Reisdoc 1
        createReisdoc(builder);

        // Reisdoc 2
        createReisdoc(builder);

        // Nationaliteit 1
        createNationaliteit(builder);

        // Nationaliteit 2
        createNationaliteit(builder);

        return builder.build();
    }

    private BrpPersoonslijst createBrpPersoonslijstMissing(final BrpGemeenteCode gemeenteCode) {
        final BrpPartijCode brpGemeenteCode;
        if (gemeenteCode == null) {
            brpGemeenteCode = new BrpPartijCode(59901);
        } else {
            brpGemeenteCode = new BrpPartijCode(gemeenteCode.getWaarde() * 100 + 1);
        }
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final List<BrpGroep<BrpBijhoudingInhoud>> groepen = new ArrayList<>();

        groepen.add(
            new BrpGroep<>(
                new BrpBijhoudingInhoud(brpGemeenteCode, null, null, new BrpBoolean(false, null)),
                new BrpHistorie(
                    new BrpDatum(20000101, null),
                    new BrpDatum(20110101, null),
                    new BrpDatumTijd(new Date()),
                    new BrpDatumTijd(new Date()),
                    null),
                null,
                null,
                null));
        builder.bijhoudingStapel(new BrpStapel<>(groepen));

        return builder.build();
    }

    private BrpPersoonslijst createValidBrpPersoonslijst(final BrpGemeenteCode gemeenteCode) {
        final BrpPartijCode brpGemeenteCode;
        if (gemeenteCode == null) {
            brpGemeenteCode = new BrpPartijCode(59901);
        } else {
            brpGemeenteCode = new BrpPartijCode(gemeenteCode.getWaarde() * 100 + 1);
        }
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final List<BrpGroep<BrpBijhoudingInhoud>> groepen = new ArrayList<>();

        groepen.add(
            new BrpGroep<>(
                new BrpBijhoudingInhoud(brpGemeenteCode, null, null, new BrpBoolean(false, null)),
                new BrpHistorie(
                    new BrpDatum(20000101, null),
                    new BrpDatum(20110101, null),
                    new BrpDatumTijd(new Date()),
                    new BrpDatumTijd(new Date()),
                    null),
                null,
                null,
                null));
        builder.bijhoudingStapel(new BrpStapel<>(groepen));

        // Relatie 1
        createRelatie(builder);

        // Relatie 2
        createRelatie(builder);

        // Geslachtsnaam 1
        createGeslachtsnaam(builder);

        // Geslachtsnaam 2
        createGeslachtsnaam(builder);

        // Voornaam 1
        createVoornaam(builder);

        // Voornaam 2
        createVoornaam(builder);

        // Reisdoc 1
        createReisdoc(builder);

        // Reisdoc 2
        createReisdoc(builder);

        // Nationaliteit 1
        createNationaliteit(builder);

        // Nationaliteit 2
        createNationaliteit(builder);

        return builder.build();
    }

    private void createRelatie(final BrpPersoonslijstBuilder builder) {
        final List<BrpBetrokkenheid> betrokkenheid = new ArrayList<>();
        final BrpIdentificatienummersInhoud inhoud = new BrpIdentificatienummersInhoud(new BrpLong(43L, null), new BrpInteger(43, null));
        final List<BrpGroep<BrpIdentificatienummersInhoud>> idBrpGroep = new ArrayList<>();
        final BrpHistorie historie =
                new BrpHistorie(
                    new BrpDatum(20000101, null),
                    new BrpDatum(20110101, null),
                    new BrpDatumTijd(new Date()),
                    new BrpDatumTijd(new Date()),
                    null);
        final BrpGroep<BrpIdentificatienummersInhoud> brpIdGroep = new BrpGroep<>(inhoud, historie, null, null, null);
        idBrpGroep.add(brpIdGroep);
        final List<BrpGroep<BrpSamengesteldeNaamInhoud>> samengesteldeNaamInhoudGroepen = new ArrayList<>();
        final BrpHistorie historie1 =
                new BrpHistorie(
                    new BrpDatum(20000101, null),
                    new BrpDatum(20110101, null),
                    new BrpDatumTijd(new Date()),
                    new BrpDatumTijd(new Date()),
                    null);
        samengesteldeNaamInhoudGroepen.add(
            new BrpGroep<>(
                new BrpSamengesteldeNaamInhoud(
                    new BrpPredicaatCode("P"),
                    new BrpString("voornamen", null),
                    new BrpString("voorv", null),
                    new BrpCharacter(' ', null),
                    new BrpAdellijkeTitelCode("B"),
                    new BrpString("naam", null),
                    new BrpBoolean(false, null),
                    new BrpBoolean(false, null)),
                historie1,
                null,
                null,
                null));
        betrokkenheid.add(
            new BrpBetrokkenheid(null, new BrpStapel<>(idBrpGroep), null, null, null, new BrpStapel<>(samengesteldeNaamInhoudGroepen), null, null));
        final BrpRelatie.Builder relatieBuilder =
                new BrpRelatie.Builder(
                    BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING,
                    BrpSoortBetrokkenheidCode.OUDER,
                    new LinkedHashMap<Long, BrpActie>());
        relatieBuilder.betrokkenheden(betrokkenheid);
        builder.relatie(relatieBuilder.build());
    }

    private void createGeslachtsnaam(final BrpPersoonslijstBuilder builder) {
        final BrpHistorie historie =
                new BrpHistorie(
                    new BrpDatum(20000101, null),
                    new BrpDatum(20110101, null),
                    new BrpDatumTijd(new Date()),
                    new BrpDatumTijd(new Date()),
                    null);
        final BrpGeslachtsnaamcomponentInhoud brpGeslachtsnaamcomponentInhoud =
                new BrpGeslachtsnaamcomponentInhoud(
                    new BrpString("voor", null),
                    new BrpCharacter(' ', null),
                    new BrpString("naam", null),
                    new BrpPredicaatCode("P"),
                    new BrpAdellijkeTitelCode("B"),
                    new BrpInteger(0, null));
        final BrpGroep<BrpGeslachtsnaamcomponentInhoud> brpGeslachtGroep = new BrpGroep<>(brpGeslachtsnaamcomponentInhoud, historie, null, null, null);
        final List<BrpGroep<BrpGeslachtsnaamcomponentInhoud>> brpGeslachtsnaamGroep = new ArrayList<>();
        brpGeslachtsnaamGroep.add(brpGeslachtGroep);
        final BrpStapel<BrpGeslachtsnaamcomponentInhoud> geslachtsnaamcomponentStapel = new BrpStapel<>(brpGeslachtsnaamGroep);
        builder.geslachtsnaamcomponentStapel(geslachtsnaamcomponentStapel);
    }

    private void createVoornaam(final BrpPersoonslijstBuilder builder) {
        final List<BrpGroep<BrpVoornaamInhoud>> brpVoornaamGroep1 = new ArrayList<>();
        brpVoornaamGroep1.add(
            new BrpGroep<>(
                new BrpVoornaamInhoud(new BrpString("Vorrnaam", null), new BrpInteger(0, null)),
                new BrpHistorie(
                    new BrpDatum(20000101, null),
                    new BrpDatum(20110101, null),
                    new BrpDatumTijd(new Date(), null),
                    new BrpDatumTijd(new Date(), null),
                    null),
                null,
                null,
                null));
        final BrpStapel<BrpVoornaamInhoud> voornaamStapel1 = new BrpStapel<>(brpVoornaamGroep1);

        builder.voornaamStapel(voornaamStapel1);
    }

    private void createVoornaamMultiple(final BrpPersoonslijstBuilder builder) {
        final List<BrpGroep<BrpVoornaamInhoud>> brpVoornaamGroep1 = new ArrayList<>();

        brpVoornaamGroep1.add(
            new BrpGroep<>(
                new BrpVoornaamInhoud(new BrpString("Voornaameen", null), new BrpInteger(0, null)),
                new BrpHistorie(new BrpDatum(20130101, null), new BrpDatum(20130101, null), new BrpDatumTijd(new Date(), null), null, null),
                null,
                null,
                null));
        brpVoornaamGroep1.add(
            new BrpGroep<>(
                new BrpVoornaamInhoud(new BrpString("Voornaamtwee", null), new BrpInteger(0, null)),
                new BrpHistorie(
                    new BrpDatum(20140101, null),
                    new BrpDatum(20140101, null),
                    new BrpDatumTijd(new Date(), null),
                    new BrpDatumTijd(new Date(), null),
                    null),
                null,
                null,
                null));
        final BrpStapel<BrpVoornaamInhoud> voornaamStapel1 = new BrpStapel<>(brpVoornaamGroep1);

        builder.voornaamStapel(voornaamStapel1);
    }

    private void createReisdoc(final BrpPersoonslijstBuilder builder) {
        final BrpReisdocumentInhoud brpReisdocumentInhoud =
                new BrpReisdocumentInhoud(
                    new BrpSoortNederlandsReisdocumentCode("P"),
                    new BrpString("1"),
                    new BrpDatum(20110101, null),
                    new BrpDatum(20110101, null),
                    new BrpReisdocumentAutoriteitVanAfgifteCode("D"),
                    new BrpDatum(20110101, null),
                    new BrpDatum(20110101, null),
                    new BrpAanduidingInhoudingOfVermissingReisdocumentCode('I'));
        final List<BrpGroep<BrpReisdocumentInhoud>> reisdocumentInhoudGroep = new ArrayList<>();
        reisdocumentInhoudGroep.add(
            new BrpGroep<>(
                brpReisdocumentInhoud,
                new BrpHistorie(
                    new BrpDatum(20000101, null),
                    new BrpDatum(20110101, null),
                    new BrpDatumTijd(new Date()),
                    new BrpDatumTijd(new Date()),
                    null),
                null,
                null,
                null));

        builder.reisdocumentStapel(new BrpStapel<>(reisdocumentInhoudGroep));
    }

    private void createNationaliteit(final BrpPersoonslijstBuilder builder) {
        final List<BrpGroep<BrpNationaliteitInhoud>> nationaliteitInhoudGroep = new ArrayList<>();
        final BrpNationaliteitInhoud brpNationaliteitInhoud =
                new BrpNationaliteitInhoud(
                    new BrpNationaliteitCode(Short.parseShort("1")),
                    new BrpRedenVerkrijgingNederlandschapCode(Short.parseShort("1")),
                    new BrpRedenVerliesNederlandschapCode(Short.parseShort("1")),
                    null,
                    null,
                    null,
                    null);
        nationaliteitInhoudGroep.add(
            new BrpGroep<>(
                brpNationaliteitInhoud,
                new BrpHistorie(
                    new BrpDatum(20000101, null),
                    new BrpDatum(20110101, null),
                    new BrpDatumTijd(new Date()),
                    new BrpDatumTijd(new Date()),
                    null),
                null,
                null,
                null));
        builder.nationaliteitStapel(new BrpStapel<>(nationaliteitInhoudGroep));
    }

}
