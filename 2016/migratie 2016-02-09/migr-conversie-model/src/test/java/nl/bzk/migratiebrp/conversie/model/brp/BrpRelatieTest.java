/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie.Builder;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoudTest;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoudTest;

import org.junit.Test;

public class BrpRelatieTest {

    public static BrpRelatie createOuderRelatieZonderOuders() {
        BrpBetrokkenheid ik = BrpBetrokkenheidTest.maakBetrokkenheidINum(BrpSoortBetrokkenheidCode.KIND, BrpIdentificatienummersInhoudTest.createStapel());
        return new BrpRelatie(
            BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING,
            BrpSoortBetrokkenheidCode.OUDER,
            ik,
            new ArrayList<BrpBetrokkenheid>(),
            null,
            null,
            null,
            null,
            null,
            null);
    }

    public static BrpRelatie createKindRelatie() {
        BrpBetrokkenheid ik = BrpBetrokkenheidTest.maakBetrokkenheidINum(BrpSoortBetrokkenheidCode.KIND, BrpIdentificatienummersInhoudTest.createStapel());
        return new BrpRelatie(
                BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING,
                BrpSoortBetrokkenheidCode.KIND,
                ik,
                new ArrayList<BrpBetrokkenheid>(),
                null,
                null,
                null,
                null,
                null,
                null);
    }


    public static BrpRelatie createHuwelijkRelatie() {
        BrpBetrokkenheid ik = BrpBetrokkenheidTest.maakBetrokkenheidINum(BrpSoortBetrokkenheidCode.KIND, BrpIdentificatienummersInhoudTest.createStapel());
        return new BrpRelatie(
                BrpSoortRelatieCode.HUWELIJK,
                BrpSoortBetrokkenheidCode.PARTNER,
                ik,
                new ArrayList<BrpBetrokkenheid>(),
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public static List<BrpRelatie> createLegeOuderRelatie() {
        List<BrpRelatie> result = new ArrayList<>();
        result.add(createOuderRelatieZonderOuders());
        return result;
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorSoortRelatieNull() {
        new BrpRelatie(
            null,
            BrpSoortBetrokkenheidCode.OUDER,
            BrpBetrokkenheidTest.maakBetrokkenheid(BrpSoortBetrokkenheidCode.KIND),
            null,
            null,
            null,
            null,
            null,
            null,
            null);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorRolCodeNull() {
        new BrpRelatie(
            BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING,
            null,
            BrpBetrokkenheidTest.maakBetrokkenheid(BrpSoortBetrokkenheidCode.KIND),
            null,
            null,
            null,
            null,
            null,
            null,
            null);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorIkBetrokkenheidNull() {
        new BrpRelatie(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, BrpSoortBetrokkenheidCode.OUDER, null, null, null, null, null, null, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorMetBuilderSoortRelatieNull() {
        Map<Long, BrpActie> actieCache = new HashMap<>();
        BrpRelatie.Builder builder = new Builder(null, BrpSoortBetrokkenheidCode.OUDER, actieCache);
        builder.build();
    }

    @Test
    public void testConstructorMetBuilder() {
        Map<Long, BrpActie> actieCache = new HashMap<>();
        BrpRelatie.Builder b = new Builder(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, BrpSoortBetrokkenheidCode.KIND, actieCache);
        BrpRelatie result = b.build();
        BrpRelatie.Builder b2 = new Builder(result, actieCache);
        assertTrue(result.equals(b2.build()));
        result.valideer();
    }

    @Test
    public void testEquals() {
        Map<Long, BrpActie> actieCache = new HashMap<>();
        BrpRelatie.Builder b = new Builder(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, BrpSoortBetrokkenheidCode.KIND, actieCache);
        b =
                b.relatieStapel(BrpRelatieInhoudTest.createStapel())
                 .betrokkenheden(BrpBetrokkenheidTest.maaklijstMetBetrokkenheden(2,false,false))
                 .istOuder1Stapel(BrpIstRelatieGroepInhoudTest.createStapel())
                 .istOuder2Stapel(BrpIstRelatieGroepInhoudTest.createStapel())
                 .istHuwelijkOfGpStapel(null)
                 .istKindStapel(null)
                 .istGezagsverhoudingStapel(null);

        BrpRelatie result = b.build();
        BrpRelatie.Builder b2 = new Builder(result, actieCache);
        BrpRelatie result2 = b2.build();
        assertTrue(result.equals(result2));
        assertTrue(result.equals(result));
        assertFalse(result.equals(geefZelfdeTerug(result2.getBetrokkenheden())));
        assertEquals(result.hashCode(), result2.hashCode());
        assertEquals(result.toString(), result2.toString());
    }

    @Test(expected = IllegalStateException.class)
    public void testBuilderOnbekendeBetrokkenheid() {
        Map<Long, BrpActie> actieCache = new HashMap<>();
        BrpSoortBetrokkenheidCode code = new BrpSoortBetrokkenheidCode("Z","ONBEKEND");
        BrpRelatie.Builder b = new Builder(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, code, actieCache);
        b = b.relatieStapel(BrpRelatieInhoudTest.createStapel());
        b.build();
    }

    @Test
    public void testBuilderOuder() {
        Map<Long, BrpActie> actieCache = new HashMap<>();
        BrpRelatie.Builder b = new Builder(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, BrpSoortBetrokkenheidCode.OUDER, actieCache);
        b = b.relatieStapel(BrpRelatieInhoudTest.createStapel())
             .betrokkenheden(BrpBetrokkenheidTest.maaklijstMetBetrokkenheden(3,true,true));
        BrpRelatie result = b.build();
    }

    private Object geefZelfdeTerug(Object iets) {
        return iets;
    }
}
