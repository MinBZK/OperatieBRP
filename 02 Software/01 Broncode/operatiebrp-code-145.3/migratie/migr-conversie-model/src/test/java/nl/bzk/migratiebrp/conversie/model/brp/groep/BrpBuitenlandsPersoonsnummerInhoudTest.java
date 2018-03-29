/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorieTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.junit.Before;
import org.junit.Test;

/**
 * Unittest voor {@link BrpBuitenlandsPersoonsnummerInhoud}.
 */
public class BrpBuitenlandsPersoonsnummerInhoudTest {

    private BrpBuitenlandsPersoonsnummerInhoud inhoud1;
    private BrpBuitenlandsPersoonsnummerInhoud inhoud2;
    private BrpBuitenlandsPersoonsnummerInhoud inhoud3;
    private BrpBuitenlandsPersoonsnummerInhoud inhoud4;
    private BrpBuitenlandsPersoonsnummerInhoud inhoud5;
    private static final BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer NEDERLAND = new BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer("0001");

    @Before
    public void setup() {
        inhoud1 = new BrpBuitenlandsPersoonsnummerInhoud(new BrpString("1234"), NEDERLAND);
        inhoud2 = new BrpBuitenlandsPersoonsnummerInhoud(new BrpString("4321"), NEDERLAND);
        inhoud3 = new BrpBuitenlandsPersoonsnummerInhoud(new BrpString("1234"), new BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer("0032"));
        inhoud4 =
                new BrpBuitenlandsPersoonsnummerInhoud(new BrpString("1234", new Lo3Onderzoek(new Lo3Integer(40000), new Lo3Datum(20160101), null)), NEDERLAND);
        inhoud5 = new BrpBuitenlandsPersoonsnummerInhoud(new BrpString("1234"), NEDERLAND);
    }

    @Test
    public void testHashCode() {
        assertTrue(inhoud1.hashCode() == inhoud1.hashCode());
        assertTrue(inhoud1.hashCode() == inhoud5.hashCode());
        assertFalse(inhoud1.hashCode() == inhoud2.hashCode());
        assertFalse(inhoud1.hashCode() == inhoud3.hashCode());
        assertFalse(inhoud1.hashCode() == inhoud4.hashCode());
        assertFalse(inhoud2.hashCode() == inhoud4.hashCode());
        assertFalse(inhoud3.hashCode() == inhoud4.hashCode());
    }

    @Test
    public void testIsLeeg() {
        assertFalse(inhoud1.isLeeg());
        assertTrue(new BrpBuitenlandsPersoonsnummerInhoud(null, null).isLeeg());
    }

    @Test
    public void testEqualsObject() {
        assertEquals(inhoud1, inhoud5);
        assertEquals(inhoud1, inhoud1);
        assertTrue(inhoud1.equals(inhoud1));
        assertFalse(inhoud1.equals(inhoud2));
        assertFalse(inhoud1.equals(inhoud3));
        assertFalse(inhoud1.equals(inhoud4));
        assertFalse(inhoud2.equals(inhoud3));
        assertFalse(inhoud2.equals(inhoud4));
        assertFalse(inhoud3.equals(inhoud5));
        assertFalse(inhoud1.equals(new Object()));
        assertFalse(inhoud1.equals(null));
    }

    @Test
    public void testToString() {
        assertEquals(inhoud1.toString(), inhoud5.toString());
        assertFalse(inhoud2.toString().equals(inhoud5.toString()));
    }

    @Test
    public void getNummer() {
        assertEquals("1234", inhoud1.getNummer().getWaarde());
    }

    @Test
    public void getAutoriteitVanAfgifteNummer() {
        assertEquals(NEDERLAND, inhoud1.getAutoriteitVanAfgifte());
    }

    /**
     * maak lijst met actuele inhoud
     * @return ArrayList
     */
    public static List<BrpStapel<BrpBuitenlandsPersoonsnummerInhoud>> createList(boolean metActueleInhoud) {
        List<BrpStapel<BrpBuitenlandsPersoonsnummerInhoud>> lijst = new ArrayList<>();
        if (metActueleInhoud) {
            lijst.add(createStapel(true));
        } else {
            lijst.add(createStapel(false));
        }
        return lijst;
    }

    public static BrpStapel<BrpBuitenlandsPersoonsnummerInhoud> createStapel(boolean metActueel) {
        List<BrpGroep<BrpBuitenlandsPersoonsnummerInhoud>> groepen = new ArrayList<>();
        if (metActueel) {
            groepen.add(new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null));
        } else {
            groepen.add(new BrpGroep<>(createInhoud(), BrpHistorieTest.createNietActueleInhoud(), null, null, null));
        }
        return new BrpStapel<>(groepen);
    }

    public static BrpBuitenlandsPersoonsnummerInhoud createInhoud() {
        return createInhoud("123ABC","0032");
    }

    public static BrpBuitenlandsPersoonsnummerInhoud createInhoud(final String nummer, final String autoriteit) {
        return new BrpBuitenlandsPersoonsnummerInhoud(new BrpString(nummer), new BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer(autoriteit));
    }
}
