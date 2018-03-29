/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActieBron;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorieTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieTest;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractBrpGroepConverteerderTest<L extends Lo3CategorieInhoud> {

    BrpActie actie1 =
            new BrpActie(
                    1001L,
                    BrpSoortActieCode.CONVERSIE_GBA,
                    BrpPartijCode.MINISTER,
                    null,
                    null,
                    Collections.<BrpActieBron>emptyList(),
                    1,
                    null);
    BrpActie actie2 =
            new BrpActie(
                    1002L,
                    BrpSoortActieCode.CONVERSIE_GBA,
                    BrpPartijCode.MINISTER,
                    null,
                    null,
                    Collections.<BrpActieBron>emptyList(),
                    1,
                    null);
    BrpActie actie3 =
            new BrpActie(
                    1003L,
                    BrpSoortActieCode.VERHUIZING,
                    BrpPartijCode.MINISTER,
                    null,
                    null,
                    Collections.<BrpActieBron>emptyList(),
                    1,
                    null);
    BrpActie actie4 =
            new BrpActie(
                    1004L,
                    BrpSoortActieCode.CONVERSIE_GBA,
                    BrpPartijCode.MINISTER,
                    null,
                    null,
                    Collections.<BrpActieBron>emptyList(),
                    1,
                    null);

    @Mock
    private BrpAttribuutConverteerder attribuutConverteerder;

    private Converteerder converteerder;

    @Before
    public void setUp() {
        converteerder = new Converteerder(attribuutConverteerder);
    }


    @Test
    public void bepaalgecreerdeRijenTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<BrpGroep> groepen = getBrpGroepsGecreerdeRijen();
        Method pm = AbstractBrpGroepConverteerder.class.getDeclaredMethod("bepaalGecreeerdeRijen", BrpActie.class, List.class);
        pm.setAccessible(true);
        List<BrpGroep> result = (ArrayList<BrpGroep>) pm.invoke(converteerder, actie1, groepen);
        assertEquals(3, result.size());
        assertEquals(20010101, ((BrpGeboorteInhoud) result.get(0).getInhoud()).getGeboortedatum().getWaarde().intValue());
        assertEquals(20010102, ((BrpGeboorteInhoud) result.get(1).getInhoud()).getGeboortedatum().getWaarde().intValue());
        assertEquals(20010106, ((BrpGeboorteInhoud) result.get(2).getInhoud()).getGeboortedatum().getWaarde().intValue());

    }

    @Test
    public void bepaalVervallenRijen() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<BrpGroep> groepen = getBrpGroepsVervallenRijen();
        Method pm = AbstractBrpGroepConverteerder.class.getDeclaredMethod("bepaalVervallenRijen", BrpActie.class, Collection.class);
        pm.setAccessible(true);
        List<BrpGroep> result = (ArrayList<BrpGroep>) pm.invoke(converteerder, actie1, groepen);
        assertEquals(2, result.size());
        assertEquals(20010103, ((BrpGeboorteInhoud) result.get(0).getInhoud()).getGeboortedatum().getWaarde().intValue());
        assertEquals(20010105, ((BrpGeboorteInhoud) result.get(1).getInhoud()).getGeboortedatum().getWaarde().intValue());
    }

    @Test
    public void bepaalGeldigheidsdatums() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method pmBGR = AbstractBrpGroepConverteerder.class.getDeclaredMethod("bepaalGecreeerdeRijen", BrpActie.class, List.class);
        pmBGR.setAccessible(true);
        List<BrpGroep> gecreerdeRijen = (ArrayList<BrpGroep>) pmBGR.invoke(converteerder, actie1, getBrpGroepsGecreerdeRijen());
        Method pmVR = AbstractBrpGroepConverteerder.class.getDeclaredMethod("bepaalVervallenRijen", BrpActie.class, Collection.class);
        pmVR.setAccessible(true);
        List<BrpGroep> vervallenRijen = (ArrayList<BrpGroep>) pmVR.invoke(converteerder, actie1, getBrpGroepsVervallenRijen());

        Method pm = AbstractBrpGroepConverteerder.class.getDeclaredMethod("bepaalGeldigheidsdatums", Collection.class, Collection.class);
        pm.setAccessible(true);
        SortedSet<BrpDatum> result = (SortedSet<BrpDatum>) pm.invoke(converteerder, gecreerdeRijen, vervallenRijen);
        assertEquals(9, result.size());

    }

    @Test
    public void bepaalGeldigeRij_legeLijst() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BrpHistorie his1 = BrpHistorieTest.createInhoud(20010106, null, 20010106, null);
        Method bgr = AbstractBrpGroepConverteerder.class.getDeclaredMethod("bepaalGeldigeRij", Collection.class, BrpDatum.class);
        bgr.setAccessible(true);

        assertNull(bgr.invoke(converteerder, Collections.EMPTY_LIST, null));
        List<BrpGroep> lijst = createLijstMetGroepen(his1);
        // 1 rij datum gelijk
        BrpGroep result = (BrpGroep) (bgr.invoke(converteerder, lijst, his1.getDatumAanvangGeldigheid()));
        assertNotNull(result);
    }

    @Test
    public void bepaalGeldigeRij_1Rij_zelfdeDatum() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BrpHistorie his1 = BrpHistorieTest.createInhoud(20010106, null, 20010106, null);
        Method bgr = AbstractBrpGroepConverteerder.class.getDeclaredMethod("bepaalGeldigeRij", Collection.class, BrpDatum.class);
        bgr.setAccessible(true);
        List<BrpGroep> lijst = createLijstMetGroepen(his1);
        BrpGroep result = (BrpGroep) (bgr.invoke(converteerder, lijst, his1.getDatumAanvangGeldigheid()));
        assertNotNull(result);
    }

    @Test
    public void bepaalGeldigeRij_1Rij_zelfdeDatum_en_eindDatum_voor_aanvang()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BrpHistorie his1 = BrpHistorieTest.createInhoud(20010106, 20000100, 20010106, null);
        BrpHistorie his2 = BrpHistorieTest.createInhoud(20010106, null, 20010106, null);
        Method bgr = AbstractBrpGroepConverteerder.class.getDeclaredMethod("bepaalGeldigeRij", Collection.class, BrpDatum.class);
        bgr.setAccessible(true);
        List<BrpGroep> lijst = createLijstMetGroepen(his1, his2);
        BrpGroep result = (BrpGroep) (bgr.invoke(converteerder, lijst, his1.getDatumAanvangGeldigheid()));
        assertNotNull(result);
        assertTrue(result.equals(lijst.get(0)));
    }

    @Test
    public void bepaalGeldigeRij_1Rij_zelfdeDatum_en_eindDatum_gelijk_aanvang_op_pijlDatum()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BrpHistorie his1 = BrpHistorieTest.createInhoud(20010106, 20010106, 20010106, null);
        BrpHistorie his2 = BrpHistorieTest.createInhoud(20010106, null, 20010106, null);
        Method bgr = AbstractBrpGroepConverteerder.class.getDeclaredMethod("bepaalGeldigeRij", Collection.class, BrpDatum.class);
        bgr.setAccessible(true);
        List<BrpGroep> lijst = createLijstMetGroepen(his1, his2);
        BrpGroep result = (BrpGroep) (bgr.invoke(converteerder, lijst, his1.getDatumAanvangGeldigheid()));
        assertNotNull(result);
        assertTrue(result.equals(lijst.get(0)));
    }

    @Test
    public void bepaalGeldigeRij_1Rij_zelfdeDatum_en_eindDatum_na_aanvang_en_pijlDatum_op_aanvang()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BrpHistorie his1 = BrpHistorieTest.createInhoud(20010106, 20020106, 20010106, null);
        BrpHistorie his2 = BrpHistorieTest.createInhoud(20010106, null, 20010106, null);
        Method bgr = AbstractBrpGroepConverteerder.class.getDeclaredMethod("bepaalGeldigeRij", Collection.class, BrpDatum.class);
        bgr.setAccessible(true);
        List<BrpGroep> lijst = createLijstMetGroepen(his1, his2);
        BrpGroep result = (BrpGroep) (bgr.invoke(converteerder, lijst, his1.getDatumAanvangGeldigheid()));
        assertNotNull(result);
        assertTrue(result.equals(lijst.get(0)));
    }

    @Test
    public void bepaalGeldigeRij_1Rij_zelfdeDatum_en_eindDatum_na_aanvang_en_pijlDatum_voor_aanvang()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BrpHistorie his1 = BrpHistorieTest.createInhoud(20010106, 20020106, 20010106, null);
        BrpHistorie his2 = BrpHistorieTest.createInhoud(19990101, null, 19990101, null);
        Method bgr = AbstractBrpGroepConverteerder.class.getDeclaredMethod("bepaalGeldigeRij", Collection.class, BrpDatum.class);
        bgr.setAccessible(true);
        List<BrpGroep> lijst = createLijstMetGroepen(his1, his2);
        BrpGroep result = (BrpGroep) (bgr.invoke(converteerder, lijst, new BrpDatum(19990101, null)));
        assertNotNull(result);
        assertTrue(result.equals(lijst.get(1)));
    }

    @Test
    public void bepaalGeldigeRij_1Rij_zelfdeDatum_en_eindDatum_na_aanvang_en_pijlDatum_na_einde()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BrpHistorie his1 = BrpHistorieTest.createInhoud(20010106, 20020106, 20010106, null);
        BrpHistorie his2 = BrpHistorieTest.createInhoud(20100101, null, 20100101, null);
        Method bgr = AbstractBrpGroepConverteerder.class.getDeclaredMethod("bepaalGeldigeRij", Collection.class, BrpDatum.class);
        bgr.setAccessible(true);
        List<BrpGroep> lijst = createLijstMetGroepen(his1, his2);
        BrpGroep result = (BrpGroep) (bgr.invoke(converteerder, lijst, new BrpDatum(20100101, null)));
        assertNotNull(result);
        assertTrue(result.equals(lijst.get(1)));
    }

    @Test
    public void checkUitzonderingAlleRijenNadereAanduidingVervalGevuld()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BrpHistorie his1 = new BrpHistorie(BrpDatumTijd.fromDatum(20010101, null), BrpDatumTijd.fromDatum(20010101, null), null);
        BrpHistorie his2 = new BrpHistorie(BrpDatumTijd.fromDatum(20010101, null), BrpDatumTijd.fromDatum(20010101, null), BrpCharacter.wrap('A', null));
        BrpHistorie his3 = new BrpHistorie(BrpDatumTijd.fromDatum(20010101, null), BrpDatumTijd.fromDatum(20010101, null), BrpCharacter.wrap(null, null));
        BrpHistorie his4 = new BrpHistorie(BrpDatumTijd.fromDatum(20010101, null), BrpDatumTijd.fromDatum(20010101, null), BrpCharacter.wrap('A', null));
        Method bgr = AbstractBrpGroepConverteerder.class.getDeclaredMethod("checkUitzonderingAlleRijenNadereAanduidingVervalGevuld", List.class);
        bgr.setAccessible(true);
        assertTrue((boolean) (bgr.invoke(converteerder, Collections.EMPTY_LIST)));
        assertTrue((boolean) (bgr.invoke(converteerder, createLijstMetGroepen(his2))));
        assertTrue((boolean) (bgr.invoke(converteerder, createLijstMetGroepen(his2, his4))));
        assertFalse((boolean) (bgr.invoke(converteerder, createLijstMetGroepen(his1, his2))));
        assertFalse((boolean) (bgr.invoke(converteerder, createLijstMetGroepen(his1))));
        assertFalse((boolean) (bgr.invoke(converteerder, createLijstMetGroepen(his1, his3))));
    }

    @Test
    public void checkUitzonderingAlleRijenAanvangGelijkAanEindeGeldigheid()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BrpHistorie historie1 = BrpHistorieTest.createInhoud(20010106, 20010106, 20010106, null);
        BrpHistorie historie2 = BrpHistorieTest.createInhoud(20010106, 20010106, 20010106, null);
        BrpHistorie historie3 = BrpHistorieTest.createInhoud(20010106, 20010107, 20010106, null);
        BrpHistorie historie4 = BrpHistorieTest.createInhoud(20010106, null, 20010106, null);
        BrpHistorie historie5 = BrpHistorieTest.createInhoud(null, 20010107, 20010106, null);
        BrpHistorie historie6 = BrpHistorieTest.createInhoud(null, null, 20010106, null);
        Method bgr = AbstractBrpGroepConverteerder.class.getDeclaredMethod("checkUitzonderingAlleRijenAanvangGelijkAanEindeGeldigheid", List.class);
        bgr.setAccessible(true);

        assertTrue((boolean) (bgr.invoke(converteerder, Collections.EMPTY_LIST)));
        assertTrue((boolean) (bgr.invoke(converteerder, createLijstMetGroepen(historie1))));
        assertTrue((boolean) (bgr.invoke(converteerder, createLijstMetGroepen(historie1, historie2))));
        assertFalse((boolean) (bgr.invoke(converteerder, createLijstMetGroepen(historie1, historie2, historie3))));
        assertFalse((boolean) (bgr.invoke(converteerder, createLijstMetGroepen(historie1, historie4))));
        assertFalse((boolean) (bgr.invoke(converteerder, createLijstMetGroepen(historie1, historie5))));
        assertFalse((boolean) (bgr.invoke(converteerder, createLijstMetGroepen(historie1, historie6))));

    }

    @Test
    public void isPrevalerend() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BrpHistorie historie = BrpHistorieTest.createInhoud(20010106, 20020106, 20010106, null);
        BrpGroep groep_1 = new BrpGroep<>(BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010101), historie, actie1, null, null);
        BrpGroep groep_2 = new BrpGroep<>(BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010101), historie, actie1, actie1, null);
        BrpGroep groep_3 = new BrpGroep<>(BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010101), historie, actie1, actie3, null);
        Method bgr = AbstractBrpGroepConverteerder.class.getDeclaredMethod("isPrevalerend", BrpGroep.class);
        bgr.setAccessible(true);
        assertTrue((boolean) bgr.invoke(converteerder, groep_1));
        assertTrue((boolean) bgr.invoke(converteerder, groep_2));
        assertFalse((boolean) bgr.invoke(converteerder, groep_3));

    }

    @Test
    public void maximaliseerOnbekendeDatum() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method pm = AbstractBrpGroepConverteerder.class.getDeclaredMethod("maximaliseerOnbekendeDatum", BrpDatum.class);
        pm.setAccessible(true);
        assertNull(pm.invoke(converteerder, (Object) null));
        try {
            pm.invoke(converteerder, new BrpDatum(null, new Lo3Onderzoek(new Lo3Integer(3610), new Lo3Datum(20010101), null)));
            fail();
        } catch (InvocationTargetException n) {
            assertTrue(n.getTargetException() instanceof NullPointerException);
        }
        assertEquals(20100101, ((BrpDatum) pm.invoke(converteerder, new BrpDatum(new Integer("20100101"), null))).getWaarde().intValue());
        assertEquals(20100131, ((BrpDatum) pm.invoke(converteerder, new BrpDatum(new Integer("20100100"), null))).getWaarde().intValue());
        assertEquals(20040229, ((BrpDatum) pm.invoke(converteerder, new BrpDatum(new Integer("20040200"), null))).getWaarde().intValue());
        assertEquals(20050228, ((BrpDatum) pm.invoke(converteerder, new BrpDatum(new Integer("20050200"), null))).getWaarde().intValue());
        assertEquals(20041231, ((BrpDatum) pm.invoke(converteerder, new BrpDatum(new Integer("20040000"), null))).getWaarde().intValue());
        assertEquals(99991231, ((BrpDatum) pm.invoke(converteerder, new BrpDatum(new Integer("00000000"), null))).getWaarde().intValue());
        assertEquals(99990101, ((BrpDatum) pm.invoke(converteerder, new BrpDatum(new Integer("00000101"), null))).getWaarde().intValue());
        assertEquals(99991201, ((BrpDatum) pm.invoke(converteerder, new BrpDatum(new Integer("00000001"), null))).getWaarde().intValue());
    }

    @Test
    public void checkUitzonderingConversieActieAanpassingGeldigheid() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method pm = AbstractBrpGroepConverteerder.class.getDeclaredMethod("checkUitzonderingConversieActieAanpassingGeldigheid", BrpActie.class, List.class);
        pm.setAccessible(true);
        List<BrpGroep> groepen = Collections.emptyList();
        assertFalse("Geen actie conversie", (boolean) pm.invoke(converteerder, actie3, groepen));
        groepen = new ArrayList<>();
        groepen.add(
                new BrpGroep<>(
                        BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010102),
                        BrpHistorieTest.createInhoud(20010202, 20020202, 20010102, 20020102),
                        null,
                        null,
                        null));
        assertFalse("Actie inhoud niet gevuld,actie geldigheid -> false", (boolean) pm.invoke(converteerder, actie2, groepen));

        groepen = new ArrayList<>();
        groepen.add(
                new BrpGroep<>(
                        BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010102),
                        BrpHistorieTest.createInhoud(20010202, 20020202, 20010102, 20020102),
                        actie1,
                        null,
                        null));
        assertFalse("Actie inhoud gevuld en niet gelijk,actie geldigheid -> false", (boolean) pm.invoke(converteerder, actie2, groepen));
        groepen = new ArrayList<>();
        groepen.add(
                new BrpGroep<>(
                        BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010102),
                        BrpHistorieTest.createInhoud(20010202, 20020202, 20010102, 20020102),
                        actie2,
                        null,
                        null));
        assertFalse("Actie inhoud gevuld en gelijk", (boolean) pm.invoke(converteerder, actie2, groepen));

        groepen = new ArrayList<>();
        groepen.add(
                new BrpGroep<>(
                        BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010102),
                        BrpHistorieTest.createInhoud(20010202, 20020202, 20010102, 20020102),
                        null,
                        null,
                        actie1));
        assertFalse("Actie geldigheid gevuld en niet gelijk", (boolean) pm.invoke(converteerder, actie2, groepen));
        groepen = new ArrayList<>();
        groepen.add(
                new BrpGroep<>(
                        BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010102),
                        BrpHistorieTest.createInhoud(20010202, 20020202, 20010102, 20020102),
                        null,
                        null,
                        actie2));
        assertTrue("Actie geldigheid gevuld en gelijk", (boolean) pm.invoke(converteerder, actie2, groepen));

        groepen = new ArrayList<>();
        groepen.add(
                new BrpGroep<>(
                        BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010102),
                        BrpHistorieTest.createInhoud(20010202, 20020202, 20010102, 20020102),
                        null,
                        actie1,
                        actie2));
        assertTrue("Actie verval gevuld en gelijk", (boolean) pm.invoke(converteerder, actie2, groepen));

        groepen = new ArrayList<>();
        groepen.add(
                new BrpGroep<>(
                        BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010102),
                        BrpHistorieTest.createInhoud(20010202, 20020202, 20010102, 20020102),
                        null,
                        actie2,
                        actie2));
        assertFalse("Actie verval gevuld en gelijk", (boolean) pm.invoke(converteerder, actie2, groepen));

    }

    @Test
    public void checkActieInhoudKomtVoorInAndereRij() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method pm = AbstractBrpGroepConverteerder.class.getDeclaredMethod("checkActieInhoudKomtVoorInAndereRij", List.class, BrpStapel.class);
        pm.setAccessible(true);
        List<BrpGroep<BrpGeboorteInhoud>> groepen = new ArrayList<>();
        groepen.add(new BrpGroep<>(BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010105), BrpHistorieTest.createInhoud(20010506, 20020506, 20010106, 20020106),
                actie1, actie1, null));
        groepen.add(new BrpGroep<>(BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010105), BrpHistorieTest.createInhoud(20010505, 20020505, 20010105, 20020105),
                actie2, null, null));
        groepen.add(new BrpGroep<>(BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010105), BrpHistorieTest.createInhoud(20010505, 20020505, 20010105, 20020105),
                actie1, null, null));
        BrpStapel<BrpGeboorteInhoud> stapel = new BrpStapel<>(groepen);
        assertTrue((boolean) pm.invoke(converteerder, groepen, stapel));
    }

    @Test
    public void checkActieInhoudKomtNietVoorInAndereRij() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method pm = AbstractBrpGroepConverteerder.class.getDeclaredMethod("checkActieInhoudKomtVoorInAndereRij", List.class, BrpStapel.class);
        pm.setAccessible(true);
        List<BrpGroep<BrpGeboorteInhoud>> groepen = new ArrayList<>();
        groepen.add(new BrpGroep<>(BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010105), BrpHistorieTest.createInhoud(20010506, 20020506, 20010106, 20020106),
                actie1, actie1, null));
        groepen.add(new BrpGroep<>(BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010105), BrpHistorieTest.createInhoud(20010505, 20020505, 20010105, 20020105),
                actie2, null, null));
        groepen.add(new BrpGroep<>(BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010105), BrpHistorieTest.createInhoud(20010505, 20020505, 20010105, 20020105),
                actie1, null, null));

        List<BrpGroep<BrpGeboorteInhoud>> groepen2 = new ArrayList<>();
        groepen2.add(new BrpGroep<>(BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010105), BrpHistorieTest.createInhoud(20010506, 20020506, 20010106, 20020106),
                actie3, actie2, null));
        groepen2.add(new BrpGroep<>(BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010105), BrpHistorieTest.createInhoud(20010505, 20020505, 20010105, 20020105),
                actie4, null, null));
        groepen2.add(new BrpGroep<>(BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010105), BrpHistorieTest.createInhoud(20010505, 20020505, 20010105, 20020105),
                actie4, null, null));

        BrpStapel<BrpGeboorteInhoud> stapel = new BrpStapel<>(groepen2);
        assertFalse((boolean) pm.invoke(converteerder, groepen, stapel));
    }

    @Test
    public void converteer() {
        try {
            List<BrpGroep<BrpGeboorteInhoud>> groepen = new ArrayList<>();
            groepen.add(
                    new BrpGroep<>(BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010105), BrpHistorieTest.createInhoud(20010506, 20020506, 20010106, 20020106),
                            actie1, actie1, null));
            groepen.add(
                    new BrpGroep<>(BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010105), BrpHistorieTest.createInhoud(20010505, 20020505, 20010105, 20020105),
                            actie2, null, null));
            groepen.add(
                    new BrpGroep<>(BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010105), BrpHistorieTest.createInhoud(20010505, 20020505, 20010105, 20020105),
                            actie1, null, null));

            List<BrpGroep<BrpGeboorteInhoud>> groepen2 = new ArrayList<>();
            groepen2.add(
                    new BrpGroep<>(BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010105), BrpHistorieTest.createInhoud(20010506, 20020506, 20010106, 20020106),
                            actie3, actie2, null));
            groepen2.add(
                    new BrpGroep<>(BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010105), BrpHistorieTest.createInhoud(20010505, 20020505, 20010105, 20020105),
                            actie4, null, null));
            groepen2.add(
                    new BrpGroep<>(BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010105), BrpHistorieTest.createInhoud(20010505, 20020505, 20010105, 20020105),
                            actie4, null, null));

            BrpStapel<BrpGeboorteInhoud> stapel = new BrpStapel<>(groepen2);
            final List<Lo3CategorieWrapper<L>> categorieen = new ArrayList<>();
            categorieen.add(new Lo3CategorieWrapper(Lo3CategorieTest.createDummy_Lo3InschrijvingInhoud(), true));
            BrpActie
                    actie1 =
                    new BrpActie(1001L, BrpSoortActieCode.CONVERSIE_GBA, BrpPartijCode.MINISTER, BrpDatumTijd.fromDatum(20010102, null), null,
                            Collections.<BrpActieBron>emptyList(), 1, null);
            converteerder.converteer(actie1, groepen, stapel, categorieen);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void converteerUitzondering() {
        try {
            List<BrpGroep<BrpGeboorteInhoud>> groepen2 = new ArrayList<>();
            groepen2.add(
                    new BrpGroep<>(BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010105), BrpHistorieTest.createInhoud(20010506, 20020506, 20010106, 20020106),
                            null, null, actie1));

            BrpStapel<BrpGeboorteInhoud> stapel = new BrpStapel<>(groepen2);
            final List<Lo3CategorieWrapper<L>> categorieen = new ArrayList<>();
            categorieen.add(new Lo3CategorieWrapper(Lo3CategorieTest.createDummy_Lo3InschrijvingInhoud(), true));
            BrpActie
                    actie1 =
                    new BrpActie(1001L, BrpSoortActieCode.CONVERSIE_GBA, BrpPartijCode.MINISTER, BrpDatumTijd.fromDatum(20010102, null), null,
                            Collections.<BrpActieBron>emptyList(), 1, null);
            converteerder.converteer(actie1, groepen2, stapel, categorieen);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    private List<BrpGroep> createLijstMetGroepen(BrpHistorie... histories) {
        List<BrpGroep> groepen = new ArrayList<>();
        for (BrpHistorie historie : histories) {
            groepen.add(new BrpGroep<>(BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010101), historie, actie1, null, null));
        }
        return groepen;
    }

    private List<BrpGroep> getBrpGroepsVervallenRijen() {
        List<BrpGroep> groepen = new ArrayList<>();
        groepen.add(
                new BrpGroep<>(
                        BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010101),
                        BrpHistorieTest.createInhoud(20010101, null, 20010101, null),
                        null,
                        null,
                        null));
        groepen.add(
                new BrpGroep<>(
                        BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010102),
                        BrpHistorieTest.createInhoud(20010202, 20020202, 20010102, 20020102),
                        null,
                        actie2,
                        null));
        groepen.add(
                new BrpGroep<>(
                        BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010103),
                        BrpHistorieTest.createInhoud(20010303, 20020303, 20010103, 20020103),
                        null,
                        actie1,
                        null));
        groepen.add(
                new BrpGroep<>(
                        BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010104),
                        BrpHistorieTest.createInhoud(20010404, 20020404, 20010104, 20020104),
                        actie1,
                        actie1,
                        null));
        groepen.add(
                new BrpGroep<>(
                        BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010105),
                        BrpHistorieTest.createInhoud(20010505, 20020505, 20010105, 20020105),
                        actie3,
                        actie1,
                        null));
        return groepen;
    }

    private List<BrpGroep> getBrpGroepsGecreerdeRijen() {
        List<BrpGroep> groepen = new ArrayList<>();
        groepen.add(
                new BrpGroep<>(
                        BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010101),
                        BrpHistorieTest.createInhoud(20010106, null, 20010106, null),
                        actie1,
                        null,
                        null));
        groepen.add(
                new BrpGroep<>(
                        BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010102),
                        BrpHistorieTest.createInhoud(20010207, 20020207, 20010107, 20020107),
                        actie2,
                        null,
                        actie1));
        groepen.add(
                new BrpGroep<>(
                        BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010103),
                        BrpHistorieTest.createInhoud(20010308, 20020308, 20010108, 20020108),
                        null,
                        null,
                        null));
        groepen.add(
                new BrpGroep<>(
                        BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010104),
                        BrpHistorieTest.createInhoud(20010409, 20020409, 20010109, 20020109),
                        null,
                        null,
                        actie2));
        groepen.add(
                new BrpGroep<>(
                        BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010105),
                        BrpHistorieTest.createInhoud(20010510, 20020510, 20010110, 20020110),
                        actie2,
                        null,
                        actie2));
        groepen.add(
                new BrpGroep<>(
                        BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010106),
                        BrpHistorieTest.createInhoud(20010611, 20020611, 20010111, 20020111),
                        actie1,
                        null,
                        actie3));
        groepen.add(
                new BrpGroep<>(
                        BrpGeboorteInhoudTest.getBrpGeboorteInhoud(20010106),
                        BrpHistorieTest.createInhoud(20010712, 20020713, 20010112, 20020112),
                        actie1,
                        null,
                        actie4));
        return groepen;
    }

    private class Converteerder extends AbstractBrpGroepConverteerder {

        Converteerder(final BrpAttribuutConverteerder attribuutConverteerder) {
            super(attribuutConverteerder);
        }

        @Override
        protected Logger getLogger() {
            return LoggerFactory.getLogger();
        }

        @Override
        public Lo3CategorieInhoud maakNieuweInhoud() {
            return null;
        }

        @Override
        public Lo3CategorieInhoud vulInhoud(Lo3CategorieInhoud lo3Inhoud, BrpGroepInhoud brpInhoud, BrpGroepInhoud brpVorige) {
            return Lo3CategorieTest.createDummy_Lo3InschrijvingInhoud().getInhoud();
        }
    }
}
