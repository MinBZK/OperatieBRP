/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActieBron;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorieTest;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class BrpIdentificatienummersInhoudTest {

    private BrpIdentificatienummersInhoud inhoud1;
    private BrpIdentificatienummersInhoud inhoud2;
    private BrpIdentificatienummersInhoud inhoud3;
    private BrpIdentificatienummersInhoud inhoud4;
    private BrpIdentificatienummersInhoud inhoud5;
    private BrpIdentificatienummersInhoud inhoud6;

    public static BrpIdentificatienummersInhoud createInhoud() {
        return new BrpIdentificatienummersInhoud(new BrpLong(123456789L), null);
    }

    public static BrpStapel<BrpIdentificatienummersInhoud> createStapel() {
        List<BrpGroep<BrpIdentificatienummersInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpIdentificatienummersInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }

    public static BrpStapel<BrpIdentificatienummersInhoud> createStapelMetHistorie() {
        List<BrpGroep<BrpIdentificatienummersInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpIdentificatienummersInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createdefaultInhoud(), null, null, null);
        BrpGroep<BrpIdentificatienummersInhoud> groep2 = new BrpGroep<>(createInhoud(), BrpHistorieTest.createNietActueleInhoud(), null, null, null);
        groepen.add(groep);
        groepen.add(groep2);
        return new BrpStapel<>(groepen);
    }

    public static BrpStapel<BrpIdentificatienummersInhoud> createStapelZonderActueele() {
        BrpActie actie =
                new BrpActie(1000l, BrpSoortActieCode.CONVERSIE_GBA, BrpPartijCode.MINISTER, null, null, Collections.<BrpActieBron>emptyList(), 1, null);
        List<BrpGroep<BrpIdentificatienummersInhoud>> groepen = new ArrayList<>();
        BrpGroep<BrpIdentificatienummersInhoud> groep = new BrpGroep<>(createInhoud(), BrpHistorieTest.createNietActueleInhoud(), null, actie, null);
        groepen.add(groep);
        return new BrpStapel<>(groepen);
    }

    @Before
    public void setup() {
        inhoud1 = new BrpIdentificatienummersInhoud(new BrpLong(123456789L), new BrpInteger(987654321));
        inhoud2 = new BrpIdentificatienummersInhoud(new BrpLong(123456789L), new BrpInteger(987654321));
        inhoud3 = new BrpIdentificatienummersInhoud(new BrpLong(123456789L), new BrpInteger(999999999));
        inhoud4 = new BrpIdentificatienummersInhoud(new BrpLong(111111111L), new BrpInteger(987654321));
        inhoud5 = new BrpIdentificatienummersInhoud(new BrpLong(123456789L), null);
        inhoud6 = new BrpIdentificatienummersInhoud(new BrpLong(123456789L), null);
    }

    @Test
    public void testHashCode() {
        assertEquals(inhoud1.hashCode(), inhoud2.hashCode());
        assertNotSame(inhoud1.hashCode(), inhoud5.hashCode());
    }

    @Test
    public void testEqualsObject() {
        assertTrue(inhoud1.equals(inhoud2));
        assertFalse(inhoud1.equals(inhoud3));
        assertFalse(inhoud1.equals(inhoud4));
        assertFalse(inhoud1.equals(inhoud5));
        assertNotNull(inhoud1);
        assertTrue(inhoud1.equals(inhoud1));
        assertFalse(inhoud1.equals(new Object()));
        assertTrue(inhoud5.equals(inhoud6));
        assertFalse(inhoud5.equals(inhoud1));
    }

    @Test
    public void testToString() {
        assertEquals(inhoud1.toString(), inhoud2.toString());
        assertNotSame(inhoud1.toString(), inhoud3.toString());
    }

}
