/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorieTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.exceptions.PreconditieException;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.junit.Test;

/**
 * Test het contract van BrpVoornaamInhoud.
 */
public class BrpVoornaamInhoudTest {

    private static final String JAN = "Jan";
    private static final String EXCEPTIE_VERWACHT_OMDAT_ER_EEN_SPATIE_IN_DE_VOORNAAM_VOORKOMT =
            "Exceptie verwacht omdat er een spatie in de voornaam voorkomt.";

    public static BrpVoornaamInhoud createInhoud() {
        return new BrpVoornaamInhoud(new BrpString(JAN), new BrpInteger(1));
    }

    public static BrpStapel<BrpVoornaamInhoud> createStapel() {
        List<BrpGroep<BrpVoornaamInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpVoornaamInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }

    public static List<BrpStapel<BrpVoornaamInhoud>> createList() {
        List<BrpStapel<BrpVoornaamInhoud>> lijst = new ArrayList<>();
        lijst.add(createStapel());
        return lijst;
    }

    @Test
    public void testHashCode() {
        final BrpVoornaamInhoud inhoud1 = new BrpVoornaamInhoud(new BrpString(JAN), new BrpInteger(1));
        final BrpVoornaamInhoud inhoud2 = new BrpVoornaamInhoud(new BrpString(JAN), new BrpInteger(1));
        assertEquals(inhoud1.hashCode(), inhoud2.hashCode());
    }

    @Test
    public void testBrpVoornaamInhoud() {
        new BrpVoornaamInhoud(new BrpString(JAN), new BrpInteger(1));
    }

    @Test
    public void testBrpVoornaamInhoud2() {
        new BrpVoornaamInhoud(null, new BrpInteger(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBrpVoornaamInhoudFout1() {
        new BrpVoornaamInhoud(new BrpString(""), new BrpInteger(1));
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE019)
    public void testBrpVoornaamInhoudFout2() {
        try {
            final BrpVoornaamInhoud brpVoornaamInhoud = new BrpVoornaamInhoud(new BrpString("Jan "), new BrpInteger(1));
            brpVoornaamInhoud.valideer();
            fail(EXCEPTIE_VERWACHT_OMDAT_ER_EEN_SPATIE_IN_DE_VOORNAAM_VOORKOMT);
        } catch (final PreconditieException e) {
            assertTrue(e.getMessage().contains(SoortMeldingCode.PRE019.name()));
        }
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE019)
    public void testBrpVoornaamInhoudFout3() {
        try {
            final BrpVoornaamInhoud brpVoornaamInhoud = new BrpVoornaamInhoud(new BrpString(" Jan"), new BrpInteger(1));
            brpVoornaamInhoud.valideer();
            fail(EXCEPTIE_VERWACHT_OMDAT_ER_EEN_SPATIE_IN_DE_VOORNAAM_VOORKOMT);
        } catch (final PreconditieException e) {
            assertTrue(e.getMessage().contains(SoortMeldingCode.PRE019.name()));
        }
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE019)
    public void testBrpVoornaamInhoudFout4() {
        try {
            final BrpVoornaamInhoud brpVoornaamInhoud = new BrpVoornaamInhoud(new BrpString(" "), new BrpInteger(1));
            brpVoornaamInhoud.valideer();
            fail(EXCEPTIE_VERWACHT_OMDAT_ER_EEN_SPATIE_IN_DE_VOORNAAM_VOORKOMT);
        } catch (final PreconditieException e) {
            assertTrue(e.getMessage().contains(SoortMeldingCode.PRE019.name()));
        }
    }

    @Test
    @Preconditie(SoortMeldingCode.PRE019)
    public void testBrpVoornaamInhoudFout5() {
        try {
            final BrpVoornaamInhoud brpVoornaamInhoud = new BrpVoornaamInhoud(new BrpString("Ja n"), new BrpInteger(1));
            brpVoornaamInhoud.valideer();
            fail(EXCEPTIE_VERWACHT_OMDAT_ER_EEN_SPATIE_IN_DE_VOORNAAM_VOORKOMT);
        } catch (final PreconditieException e) {
            assertTrue(e.getMessage().contains(SoortMeldingCode.PRE019.name()));
        }
    }

    @Test
    public void testIsLeeg() {
        final BrpVoornaamInhoud leeg = new BrpVoornaamInhoud(null, new BrpInteger(1));
        final BrpVoornaamInhoud nietLeeg = new BrpVoornaamInhoud(new BrpString(JAN), new BrpInteger(1));
        assertTrue(leeg.isLeeg());
        assertFalse(nietLeeg.isLeeg());
    }

    @Test
    public void testGetVoornaam() {
        final BrpVoornaamInhoud inhoud = new BrpVoornaamInhoud(new BrpString(JAN), new BrpInteger(1));
        assertEquals(JAN, BrpString.unwrap(inhoud.getVoornaam()));
    }

    @Test
    public void testGetVolgnummer() {
        final BrpVoornaamInhoud inhoud = new BrpVoornaamInhoud(new BrpString(JAN), new BrpInteger(1));
        assertEquals(new BrpInteger(1), inhoud.getVolgnummer());
    }

    @Test
    public void testEqualsObject() {
        final BrpVoornaamInhoud inhoud1 = new BrpVoornaamInhoud(new BrpString(JAN), new BrpInteger(1));
        final BrpVoornaamInhoud inhoud2 = new BrpVoornaamInhoud(new BrpString(JAN), new BrpInteger(1));
        final BrpVoornaamInhoud inhoud3 = new BrpVoornaamInhoud(new BrpString("Jantje"), new BrpInteger(1));
        final BrpVoornaamInhoud inhoud4 = new BrpVoornaamInhoud(new BrpString(JAN), new BrpInteger(2));

        assertTrue(inhoud1.equals(inhoud2));
        assertFalse(inhoud1.equals(inhoud3));
        assertFalse(inhoud1.equals(inhoud4));
    }

}
