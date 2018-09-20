/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActieBron;
import nl.bzk.migratiebrp.conversie.model.brp.BrpBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.brp.BrpBetrokkenheidTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilderTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatieTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoudTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 */
public class BrpVergelijkerTest {

    @Test(expected = InvocationTargetException.class)
    public void testConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<BrpVergelijker> c = BrpVergelijker.class.getDeclaredConstructor();
        c.setAccessible(true);
        c.newInstance();
    }

    @Test
    public void testVergelijkenPL2zelfdePLs() {
        BrpPersoonslijst pl1 = BrpPersoonslijstBuilderTest.maakMinimaleBuilder(3832350497L).build();
        BrpPersoonslijst pl2 = BrpPersoonslijstBuilderTest.maakMinimaleBuilder(3832350497L).build();
        StringBuilder sb = new StringBuilder();
        BrpVergelijker.vergelijkPersoonslijsten(sb, pl1, pl2, true, true);
        assertEquals(0, sb.length());
    }

    @Test
    public void testVergelijkenPL2AnderePLs() {
        BrpPersoonslijst pl1 = BrpPersoonslijstBuilderTest.maakMinimaleBuilder(3832350497L).build();
        BrpPersoonslijst pl2 = BrpPersoonslijstBuilderTest.maakMinimaleBuilder(5685742354L).build();
        StringBuilder sb = new StringBuilder();
        BrpVergelijker.vergelijkPersoonslijsten(sb, pl1, pl2, true, true);
        assertTrue(sb.length() > 0);
    }

    @Test
    public void testVergelijkenPLVergelijkrelaties2DezelfdePLs() {
        BrpPersoonslijst pl1 = addRelatie(BrpPersoonslijstBuilderTest.maakMinimaleBuilder(3832350497L));
        BrpPersoonslijst pl2 = addRelatie(BrpPersoonslijstBuilderTest.maakMinimaleBuilder(3832350497L));
        StringBuilder sb = new StringBuilder();
        BrpVergelijker.vergelijkPersoonslijsten(sb, pl1, pl2, true, true, true);
        assertEquals(0, sb.length());
    }

    @Test
    public void vergelijkRelaties() throws NoSuchMethodException {
        BrpPersoonslijst pl1 = addRelatie(BrpPersoonslijstBuilderTest.maakMinimaleBuilder(3832350497L));
        BrpPersoonslijst pl2 = BrpPersoonslijstBuilderTest.maakMinimaleBuilder(3832350497L).build();
        assertTrue(BrpVergelijker.vergelijkRelaties(false, false, new StringBuilder(""), pl1, pl1));
        assertFalse(BrpVergelijker.vergelijkRelaties(true, false, new StringBuilder(""), pl1, pl2));
        assertFalse(BrpVergelijker.vergelijkRelaties(false, true, new StringBuilder(""), pl1, pl2));
    }

    @Test
    public void vergelijkStapels() {
        BrpPersoonslijst pl1 = addRelatie(BrpPersoonslijstBuilderTest.maakMinimaleBuilder(3832350497L));
        BrpStapel s1 = null;
        BrpStapel s2 = BrpIdentificatienummersInhoudTest.createStapel();
        BrpStapel s3 = BrpIdentificatienummersInhoudTest.createStapelMetHistorie();
        assertTrue(BrpVergelijker.vergelijkStapels(new StringBuilder(""), s1, s1, new BrpVergelijker.StandaardBrpInhoudVergelijker(), false, false));
        assertFalse(BrpVergelijker.vergelijkStapels(new StringBuilder(""), s2, s1, new BrpVergelijker.StandaardBrpInhoudVergelijker(), false, false));
        assertFalse(BrpVergelijker.vergelijkStapels(new StringBuilder(""), s2, s3, new BrpVergelijker.StandaardBrpInhoudVergelijker(), false, false));
        assertFalse(BrpVergelijker.vergelijkStapels(new StringBuilder(""), s3, s2, new BrpVergelijker.StandaardBrpInhoudVergelijker(), false, false));
        assertTrue(BrpVergelijker.vergelijkStapels(new StringBuilder(""), s3, s3, new BrpVergelijker.StandaardBrpInhoudVergelijker(), false, false));
    }

    @Test
    public void vergelijkStapels2() {
        List<BrpStapel<BrpIdentificatienummersInhoud>> l1 = null;
        List<BrpStapel<BrpIdentificatienummersInhoud>> l2 = new ArrayList();
        l2.add(BrpIdentificatienummersInhoudTest.createStapel());
        List<BrpStapel<BrpIdentificatienummersInhoud>> l3 = new ArrayList();
        l3.add(BrpIdentificatienummersInhoudTest.createStapelMetHistorie());
        l3.add(BrpIdentificatienummersInhoudTest.createStapel());
        assertTrue(BrpVergelijker.vergelijkStapels(new StringBuilder(""), l1, l1, false, false));
        assertFalse(BrpVergelijker.vergelijkStapels(new StringBuilder(""), l2, l1, false, false));
        assertFalse(BrpVergelijker.vergelijkStapels(new StringBuilder(""), l2, l3, false, false));
        assertFalse(BrpVergelijker.vergelijkStapels(new StringBuilder(""), l3, l2, false, false));
        assertTrue(BrpVergelijker.vergelijkStapels(new StringBuilder(""), l3, l3, false, false));
    }

    @Test
    public void vergelijkIstRelaties() {
        List<BrpRelatie> l1 = null;
        List<BrpRelatie> l2 = new ArrayList<>();
        List<BrpRelatie> l3 = new ArrayList<>();

        assertTrue(BrpVergelijker.vergelijkIstRelaties(new StringBuilder(""), l1, l1));
        assertFalse(BrpVergelijker.vergelijkIstRelaties(new StringBuilder(""), l2, l1));
    }

    @Test
    public void vergelijkRelaties2() {
        List<BrpRelatie> l1 = null;
        List<BrpRelatie> l2 = new ArrayList<>();
        List<BrpRelatie> l3 = new ArrayList<>();

        assertTrue(BrpVergelijker.vergelijkRelaties(new StringBuilder(""), l1, l1));
        assertFalse(BrpVergelijker.vergelijkRelaties(new StringBuilder(""), l2, l1));
    }

    @Test
    public void vergelijkRelatie() {
        BrpRelatie ouder = BrpRelatieTest.createOuderRelatieZonderOuders();
        BrpRelatie kind = BrpRelatieTest.createKindRelatie();
        BrpRelatie Huwelijk = BrpRelatieTest.createHuwelijkRelatie();
        assertTrue(BrpVergelijker.vergelijkRelatie(new StringBuilder(""), ouder, ouder));
        assertFalse(BrpVergelijker.vergelijkRelatie(new StringBuilder(""), ouder, kind));
        assertFalse(BrpVergelijker.vergelijkRelatie(new StringBuilder(""), ouder, Huwelijk));
    }

    @Test
    public void sorteerActies() {
        List<BrpActieBron> actieBronnenLijst = new ArrayList<>();
        actieBronnenLijst.add(new BrpActieBron(null, new BrpString("Text-1")));
        actieBronnenLijst.add(new BrpActieBron(null, new BrpString("Text-2")));
        actieBronnenLijst.add(new BrpActieBron(null, new BrpString("Text-3")));
        actieBronnenLijst.add(new BrpActieBron(null, new BrpString("Text-0")));
        actieBronnenLijst.add(new BrpActieBron(null, new BrpString("Text-2")));
        BrpActie actie =
                new BrpActie(
                    1L,
                    BrpSoortActieCode.CONVERSIE_GBA,
                    BrpPartijCode.ONBEKEND,
                    BrpDatumTijd.fromDatum(20010101, null),
                    null,
                    actieBronnenLijst,
                    0,
                    null);
        List<BrpActieBron> gesorterdeAB = BrpVergelijker.soorteerActie(actie).getActieBronnen();
        String text = null;
        for (BrpActieBron bron : gesorterdeAB) {
            if (text != null) {
                assertTrue(
                    "de lijst is gesorteerd, de vorige waarde is gelijk of kleiner dan de huidige waarde",
                    text.compareTo(bron.getRechtsgrondOmschrijving().getWaarde()) < 1);
            }
            text = bron.getRechtsgrondOmschrijving().getWaarde();
        }

    }

    @Test
    public void vergelijkBetrokkenheden() {
        List<BrpBetrokkenheid> exp1 = null;
        List<BrpBetrokkenheid> exp = new ArrayList<>();
        List<BrpBetrokkenheid> exp2 = BrpBetrokkenheidTest.maaklijstMetBetrokkenheden(2, true, true);
        List<BrpBetrokkenheid> exp3 = BrpBetrokkenheidTest.maaklijstMetBetrokkenheden(3, true, false);
        List<BrpBetrokkenheid> exp4 = BrpBetrokkenheidTest.maaklijstMetBetrokkenheden(5, true, false);

        List<BrpBetrokkenheid> act = new ArrayList<>();
        assertTrue(BrpVergelijker.vergelijkBetrokkenheden(new StringBuilder(""), exp, exp));
        assertFalse(BrpVergelijker.vergelijkBetrokkenheden(new StringBuilder(""), exp, exp1));
        assertFalse(BrpVergelijker.vergelijkBetrokkenheden(new StringBuilder(""), exp1, exp));
        assertFalse(BrpVergelijker.vergelijkBetrokkenheden(new StringBuilder(""), exp2, exp3));
        assertFalse(BrpVergelijker.vergelijkBetrokkenheden(new StringBuilder(""), exp3, exp2));
        assertFalse(BrpVergelijker.vergelijkBetrokkenheden(new StringBuilder(""), exp2, exp4));
        assertFalse(BrpVergelijker.vergelijkBetrokkenheden(new StringBuilder(""), exp4, exp2));
    }

    private BrpPersoonslijst addRelatie(BrpPersoonslijstBuilder bl) {
        bl.relatie(BrpRelatieTest.createOuderRelatieZonderOuders());
        return bl.build();
    }

}
