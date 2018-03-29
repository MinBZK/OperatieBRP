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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoudTestUtil;
import org.junit.Test;

public class BrpRelatieTest {

    public static BrpRelatie createOuderRelatieZonderOuders() {
        final BrpBetrokkenheid ik =
                BrpBetrokkenheidTest.maakBetrokkenheidINum(BrpSoortBetrokkenheidCode.KIND, BrpIdentificatienummersInhoudTest.createStapel());
        return new BrpRelatie(
                BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING,
                BrpSoortBetrokkenheidCode.OUDER,
                ik,
                new ArrayList<>(),
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public static BrpRelatie createKindRelatie() {
        final BrpBetrokkenheid ik =
                BrpBetrokkenheidTest.maakBetrokkenheidINum(BrpSoortBetrokkenheidCode.KIND, BrpIdentificatienummersInhoudTest.createStapel());
        return new BrpRelatie(
                BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING,
                BrpSoortBetrokkenheidCode.KIND,
                ik,
                new ArrayList<>(),
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public static BrpRelatie createHuwelijkRelatie() {
        final BrpBetrokkenheid ik =
                BrpBetrokkenheidTest.maakBetrokkenheidINum(BrpSoortBetrokkenheidCode.KIND, BrpIdentificatienummersInhoudTest.createStapel());
        return new BrpRelatie(
                BrpSoortRelatieCode.HUWELIJK,
                BrpSoortBetrokkenheidCode.PARTNER,
                ik,
                new ArrayList<>(),
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public static BrpRelatie createPartnerschapRelatie() {
        final BrpBetrokkenheid ik =
                BrpBetrokkenheidTest.maakBetrokkenheidINum(BrpSoortBetrokkenheidCode.KIND, BrpIdentificatienummersInhoudTest.createStapel());
        return new BrpRelatie(
                BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP,
                BrpSoortBetrokkenheidCode.PARTNER,
                ik,
                new ArrayList<>(),
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public static List<BrpRelatie> createLegeOuderRelatie() {
        final List<BrpRelatie> result = new ArrayList<>();
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
        final Map<Long, BrpActie> actieCache = new HashMap<>();
        final BrpRelatie.Builder builder = new Builder(12L, null, BrpSoortBetrokkenheidCode.OUDER, actieCache);
        builder.build();
    }

    @Test
    public void testConstructorMetBuilder() {
        final Map<Long, BrpActie> actieCache = new HashMap<>();
        final BrpRelatie.Builder b = new Builder(12L, BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, BrpSoortBetrokkenheidCode.KIND, actieCache);
        final BrpRelatie result = b.build();
        final BrpRelatie.Builder b2 = new Builder(result, result.getRelatieId(), actieCache);
        assertTrue(result.equals(b2.build()));
        result.valideer();
    }

    @Test
    public void testEquals() {
        final Map<Long, BrpActie> actieCache = new HashMap<>();
        BrpRelatie.Builder b = new Builder(12L, BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, BrpSoortBetrokkenheidCode.KIND, actieCache);
        b =
                b.relatieStapel(BrpRelatieInhoudTestUtil.createStapel())
                        .betrokkenheden(BrpBetrokkenheidTest.maaklijstMetBetrokkenheden(2, false, false))
                        .istOuder1Stapel(BrpIstRelatieGroepInhoudTest.createStapel())
                        .istOuder2Stapel(BrpIstRelatieGroepInhoudTest.createStapel())
                        .istHuwelijkOfGpStapel(null)
                        .istKindStapel(null)
                        .istGezagsverhoudingStapel(null);

        final BrpRelatie result = b.build();
        final BrpRelatie.Builder b2 = new Builder(result, result.getRelatieId(), actieCache);
        final BrpRelatie result2 = b2.build();
        assertTrue(result.equals(result2));
        assertTrue(result.equals(result));
        assertFalse(result.equals(geefZelfdeTerug(result2.getBetrokkenheden())));
        assertEquals(result.hashCode(), result2.hashCode());
        assertEquals(result.toString(), result2.toString());
    }

    @Test(expected = IllegalStateException.class)
    public void testBuilderOnbekendeBetrokkenheid() {
        final Map<Long, BrpActie> actieCache = new HashMap<>();
        final BrpSoortBetrokkenheidCode code = new BrpSoortBetrokkenheidCode("Z", "ONBEKEND");
        BrpRelatie.Builder b = new Builder(12L, BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, code, actieCache);
        b = b.relatieStapel(BrpRelatieInhoudTestUtil.createStapel());
        b.build();
    }

    @Test
    public void testBuilderOuder() {
        final Map<Long, BrpActie> actieCache = new HashMap<>();
        BrpRelatie.Builder b = new Builder(12L, BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, BrpSoortBetrokkenheidCode.OUDER, actieCache);
        b = b.relatieStapel(BrpRelatieInhoudTestUtil.createStapel()).betrokkenheden(BrpBetrokkenheidTest.maaklijstMetBetrokkenheden(3, true, true));
        b.build();
    }

    private Object geefZelfdeTerug(final Object iets) {
        return iets;
    }
}
