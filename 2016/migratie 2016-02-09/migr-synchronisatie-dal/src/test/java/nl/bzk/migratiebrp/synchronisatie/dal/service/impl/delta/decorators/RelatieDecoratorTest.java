/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AanduidingOuder;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Betrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BetrokkenheidHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Gemeente;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.LandOfGebied;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3AanduidingOuder;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeboorteHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenBeeindigingRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Relatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RelatieHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortBetrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Stapel;

import org.junit.Test;

/**
 * Unit test voor {@link RelatieDecorator}.
 */
public class RelatieDecoratorTest {

    private static final RedenBeeindigingRelatie OVERLIJDEN = new RedenBeeindigingRelatie('O', "Overlijden");
    private static final RedenBeeindigingRelatie OMZETTING = new RedenBeeindigingRelatie('Z', "Omzetting");
    private static final Partij PARTIJ = new Partij("partij", 1);
    private static final LandOfGebied LAND_OF_GEBIED = new LandOfGebied((short) 2, "Nederland");
    private static final AdministratieveHandeling ADMINISTRATIEVE_HANDELING = new AdministratieveHandeling(
        PARTIJ,
        SoortAdministratieveHandeling.GBA_INITIELE_VULLING);
    private static final BRPActie ACTIE = new BRPActie(
        SoortActie.CONVERSIE_GBA,
        ADMINISTRATIEVE_HANDELING,
        PARTIJ,
        Timestamp.valueOf("2015-01-01 12:00:00.000"));

    @Test
    public void testDecorate() {
        assertNull(RelatieDecorator.decorate(null));

        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final RelatieDecorator relatieDecorator = RelatieDecorator.decorate(relatie);
        assertNotNull(relatieDecorator);
        assertEquals(relatie, relatieDecorator.getRelatie());
    }

    @Test
    public void testSorteerderGeenHuwelijkOfGp() {
        final Relatie relatie1 = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Relatie relatie2 = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        Comparator<RelatieDecorator> sorteerder = RelatieDecorator.getSorteerder();
        assertNotNull(sorteerder);
        assertEquals(1, sorteerder.compare(RelatieDecorator.decorate(relatie1), RelatieDecorator.decorate(relatie2)));

        assertEquals(1, sorteerder.compare(RelatieDecorator.decorate(relatie1), RelatieDecorator.decorate(new Relatie(SoortRelatie.HUWELIJK))));
        assertEquals(1, sorteerder.compare(RelatieDecorator.decorate(new Relatie(SoortRelatie.HUWELIJK)), RelatieDecorator.decorate(relatie2)));
    }

    @Test
    public void testSorteerderHuwelijkOfGp() {
        final int datum1 = 20120101;
        final int datum2 = 20130101;
        final int datum3 = 20140101;

        final Relatie relatie1 = new Relatie(SoortRelatie.HUWELIJK);
        final Relatie relatie2 = new Relatie(SoortRelatie.HUWELIJK);

        Comparator<RelatieDecorator> sorteerder = RelatieDecorator.getSorteerder();
        assertNotNull(sorteerder);
        relatie1.setDatumAanvang(datum1);
        relatie2.setDatumAanvang(datum2);
        assertTrue(sorteerder.compare(RelatieDecorator.decorate(relatie1), RelatieDecorator.decorate(relatie2)) < 0);

        relatie1.setDatumEinde(null);
        relatie2.setDatumEinde(datum3);
        assertTrue(sorteerder.compare(RelatieDecorator.decorate(relatie1), RelatieDecorator.decorate(relatie2)) < 0);

        relatie1.setDatumEinde(datum2);
        relatie2.setDatumEinde(null);
        assertTrue(sorteerder.compare(RelatieDecorator.decorate(relatie1), RelatieDecorator.decorate(relatie2)) > 0);

        relatie2.setDatumEinde(datum3);
        assertTrue(sorteerder.compare(RelatieDecorator.decorate(relatie1), RelatieDecorator.decorate(relatie2)) < 0);

        relatie2.setDatumEinde(datum2);
        assertTrue(sorteerder.compare(RelatieDecorator.decorate(relatie1), RelatieDecorator.decorate(relatie2)) < 0);

        relatie2.setDatumAanvang(datum1);
        assertTrue(sorteerder.compare(RelatieDecorator.decorate(relatie1), RelatieDecorator.decorate(relatie2)) == 0);

        relatie1.setRedenBeeindigingRelatie(OVERLIJDEN);
        assertTrue(sorteerder.compare(RelatieDecorator.decorate(relatie1), RelatieDecorator.decorate(relatie2)) > 0);

        relatie1.setRedenBeeindigingRelatie(null);
        relatie2.setRedenBeeindigingRelatie(OVERLIJDEN);
        assertTrue(sorteerder.compare(RelatieDecorator.decorate(relatie1), RelatieDecorator.decorate(relatie2)) < 0);

        relatie1.setRedenBeeindigingRelatie(OMZETTING);
        assertTrue(sorteerder.compare(RelatieDecorator.decorate(relatie1), RelatieDecorator.decorate(relatie2)) > 0);
    }

    @Test
    public void testGetBetrokkenheden() {
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Betrokkenheid betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatie);

        assertTrue(RelatieDecorator.decorate(relatie).getBetrokkenheden().isEmpty());

        relatie.addBetrokkenheid(betrokkenheid);
        final RelatieDecorator decorator = RelatieDecorator.decorate(relatie);
        final Set<BetrokkenheidDecorator> betrokkenheden = decorator.getBetrokkenheden();
        assertNotNull(betrokkenheden);
        assertFalse(betrokkenheden.isEmpty());
        assertEquals(betrokkenheid, betrokkenheden.iterator().next().getBetrokkenheid());
    }

    @Test
    public void testAddRemoveStapel() {
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Stapel stapel = new Stapel(new Persoon(SoortPersoon.INGESCHREVENE), "02", 0);

        final StapelDecorator stapelDecorator = StapelDecorator.decorate(stapel);
        assertNotNull(stapelDecorator);

        final RelatieDecorator relatieDecorator = RelatieDecorator.decorate(relatie);

        assertTrue(relatie.getStapels().isEmpty());

        relatieDecorator.addStapel(stapelDecorator);
        assertFalse(relatie.getStapels().isEmpty());

        relatieDecorator.removeStapel(stapelDecorator);
        assertTrue(relatie.getStapels().isEmpty());
    }

    @Test
    public void testBevatBetrokkenheid() {
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Betrokkenheid betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatie);
        relatie.addBetrokkenheid(betrokkenheid);
        final RelatieDecorator decorator = RelatieDecorator.decorate(relatie);

        final BetrokkenheidDecorator betrokkenheidDecorator = BetrokkenheidDecorator.decorate(betrokkenheid);
        assertTrue(decorator.bevatBetrokkenheid(betrokkenheidDecorator));
        final Betrokkenheid betrokkenheid2 = new Betrokkenheid(SoortBetrokkenheid.KIND, relatie);
        assertFalse(decorator.bevatBetrokkenheid(BetrokkenheidDecorator.decorate(betrokkenheid2)));
    }

    @Test(expected = IllegalStateException.class)
    public void testGetAndereBetrokkenheidOuderKind() {
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Betrokkenheid ouder = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatie);
        final Betrokkenheid kind = new Betrokkenheid(SoortBetrokkenheid.KIND, relatie);

        final Persoon ouderPersoon = new Persoon(SoortPersoon.INGESCHREVENE);
        ouderPersoon.setAdministratienummer(1234567890L);
        ouderPersoon.addBetrokkenheid(ouder);
        final Persoon kindPersoon = new Persoon(SoortPersoon.ONBEKEND);
        kindPersoon.setAdministratienummer(9876543210L);
        kindPersoon.addBetrokkenheid(kind);

        relatie.addBetrokkenheid(ouder);
        relatie.addBetrokkenheid(kind);

        final RelatieDecorator relatieDecorator = RelatieDecorator.decorate(relatie);
        final BetrokkenheidDecorator andereBetrokkenheidDecorator = relatieDecorator.getAndereBetrokkenheid(BetrokkenheidDecorator.decorate(ouder));
        assertNotNull(andereBetrokkenheidDecorator);
        assertEquals(kind, andereBetrokkenheidDecorator.getBetrokkenheid());

        relatieDecorator.getAndereBetrokkenheid(BetrokkenheidDecorator.decorate(kind));
        fail("Ophalen van andere betrokkenheid mag niet als ik-betrokkenheid een ouder is");
    }

    @Test
    public void testGetAndereBetrokkenheidHuwelijk() {
        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);
        final Betrokkenheid partner1 = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        final Betrokkenheid partner2 = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        relatie.addBetrokkenheid(partner1);
        relatie.addBetrokkenheid(partner2);

        final Persoon partner1Persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        partner1Persoon.setAdministratienummer(1234567890L);
        partner1Persoon.addBetrokkenheid(partner1);
        final Persoon partner2Persoon = new Persoon(SoortPersoon.ONBEKEND);
        partner2Persoon.setAdministratienummer(9876543210L);
        partner2Persoon.addBetrokkenheid(partner2);

        final RelatieDecorator relatieDecorator = RelatieDecorator.decorate(relatie);
        final BetrokkenheidDecorator andereBetrokkenheidDecorator = relatieDecorator.getAndereBetrokkenheid(BetrokkenheidDecorator.decorate(partner1));
        assertNotNull(andereBetrokkenheidDecorator);
        assertEquals(partner2, andereBetrokkenheidDecorator.getBetrokkenheid());

        final BetrokkenheidDecorator andereBetrokkenheidDecorator2 = relatieDecorator.getAndereBetrokkenheid(BetrokkenheidDecorator.decorate(partner2));
        assertNotNull(andereBetrokkenheidDecorator2);
        assertEquals(partner1, andereBetrokkenheidDecorator2.getBetrokkenheid());
    }

    @Test
    public void testGetAndereBetrokkenheidGeregistreerdPartnerschap() {
        final Relatie relatie = new Relatie(SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        final Betrokkenheid partner1 = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        final Betrokkenheid partner2 = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        relatie.addBetrokkenheid(partner1);
        relatie.addBetrokkenheid(partner2);

        final Persoon partner1Persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        partner1Persoon.setAdministratienummer(1234567890L);
        partner1Persoon.addBetrokkenheid(partner1);
        final Persoon partner2Persoon = new Persoon(SoortPersoon.ONBEKEND);
        partner2Persoon.setAdministratienummer(9876543210L);
        partner2Persoon.addBetrokkenheid(partner2);

        final RelatieDecorator relatieDecorator = RelatieDecorator.decorate(relatie);
        final BetrokkenheidDecorator andereBetrokkenheidDecorator = relatieDecorator.getAndereBetrokkenheid(BetrokkenheidDecorator.decorate(partner1));
        assertNotNull(andereBetrokkenheidDecorator);
        assertEquals(partner2, andereBetrokkenheidDecorator.getBetrokkenheid());

        final BetrokkenheidDecorator andereBetrokkenheidDecorator2 = relatieDecorator.getAndereBetrokkenheid(BetrokkenheidDecorator.decorate(partner2));
        assertNotNull(andereBetrokkenheidDecorator2);
        assertEquals(partner1, andereBetrokkenheidDecorator2.getBetrokkenheid());
    }

    @Test
    public void testGetAndereBetrokkenheidPuntOuder() {
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Betrokkenheid ouder = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatie);
        final Betrokkenheid kind = new Betrokkenheid(SoortBetrokkenheid.KIND, relatie);

        final Persoon ouderPersoon = new Persoon(SoortPersoon.INGESCHREVENE);
        ouderPersoon.setAdministratienummer(1234567890L);
        ouderPersoon.addBetrokkenheid(ouder);

        relatie.addBetrokkenheid(ouder);
        relatie.addBetrokkenheid(kind);

        final RelatieDecorator relatieDecorator = RelatieDecorator.decorate(relatie);
        final BetrokkenheidDecorator andereBetrokkenheidDecorator = relatieDecorator.getAndereBetrokkenheid(BetrokkenheidDecorator.decorate(ouder));
        assertNotNull(andereBetrokkenheidDecorator);
        assertEquals(kind, andereBetrokkenheidDecorator.getBetrokkenheid());
    }

    @Test
    public void testGetAndereBetrokkenheidJuridischGeenOuder() {
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Betrokkenheid ouder = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatie);

        final Persoon ouderPersoon = new Persoon(SoortPersoon.INGESCHREVENE);
        ouderPersoon.setAdministratienummer(1234567890L);
        ouderPersoon.addBetrokkenheid(ouder);

        relatie.addBetrokkenheid(ouder);

        final RelatieDecorator relatieDecorator = RelatieDecorator.decorate(relatie);
        final BetrokkenheidDecorator andereBetrokkenheidDecorator = relatieDecorator.getAndereBetrokkenheid(BetrokkenheidDecorator.decorate(ouder));
        assertNull(andereBetrokkenheidDecorator);
    }

    @Test(expected = IllegalStateException.class)
    public void testGetAndereBetrokkenheidOverigeRelatie() {
        final Relatie relatie = new Relatie(SoortRelatie.ERKENNING_ONGEBOREN_VRUCHT);
        final RelatieDecorator decorator = RelatieDecorator.decorate(relatie);
        final Betrokkenheid partner1 = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        relatie.addBetrokkenheid(partner1);

        decorator.getAndereBetrokkenheid(BetrokkenheidDecorator.decorate(partner1));
    }

    @Test
    public void testGetAndereBetrokkenheidGeenBetrokkenhedenInRelatie() {
        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);
        final RelatieDecorator decorator = RelatieDecorator.decorate(relatie);
        final Betrokkenheid partner1 = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);

        assertNull(decorator.getAndereBetrokkenheid(BetrokkenheidDecorator.decorate(partner1)));
    }

    @Test
    public void testGetAndereOuderBetrokkenhedenOuder1() {
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Betrokkenheid ouder = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatie);
        final Betrokkenheid kind = new Betrokkenheid(SoortBetrokkenheid.KIND, relatie);

        final Persoon ouderPersoon = new Persoon(SoortPersoon.INGESCHREVENE);
        ouderPersoon.setAdministratienummer(1234567890L);
        ouderPersoon.addBetrokkenheid(ouder);
        final Persoon kindPersoon = new Persoon(SoortPersoon.ONBEKEND);
        kindPersoon.setAdministratienummer(9876543210L);
        kindPersoon.addBetrokkenheid(kind);

        ouder.setAanduidingOuder(new Lo3AanduidingOuder(AanduidingOuder.OUDER_1, ouder));
        relatie.addBetrokkenheid(ouder);
        relatie.addBetrokkenheid(kind);

        final RelatieDecorator relatieDecorator = RelatieDecorator.decorate(relatie);

        final Set<BetrokkenheidDecorator> andereBetrokkenheden =
                relatieDecorator.getAndereOuderBetrokkenheden(BetrokkenheidDecorator.decorate(kind), AanduidingOuder.OUDER_1);
        assertFalse(andereBetrokkenheden.isEmpty());

        final Set<BetrokkenheidDecorator> andereBetrokkenhedenOuder2 =
                relatieDecorator.getAndereOuderBetrokkenheden(BetrokkenheidDecorator.decorate(kind), AanduidingOuder.OUDER_2);
        assertTrue(andereBetrokkenhedenOuder2.isEmpty());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetAndereOuderBetrokkenhedenGeenOuderAanduiding() {
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Betrokkenheid ouder = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatie);
        final Betrokkenheid kind = new Betrokkenheid(SoortBetrokkenheid.KIND, relatie);

        final Persoon ouderPersoon = new Persoon(SoortPersoon.INGESCHREVENE);
        ouderPersoon.setAdministratienummer(1234567890L);
        ouderPersoon.addBetrokkenheid(ouder);
        final Persoon kindPersoon = new Persoon(SoortPersoon.ONBEKEND);
        kindPersoon.setAdministratienummer(9876543210L);
        kindPersoon.addBetrokkenheid(kind);

        relatie.addBetrokkenheid(ouder);
        relatie.addBetrokkenheid(kind);

        final RelatieDecorator relatieDecorator = RelatieDecorator.decorate(relatie);

        relatieDecorator.getAndereOuderBetrokkenheden(BetrokkenheidDecorator.decorate(kind), AanduidingOuder.OUDER_1);
    }

    @Test
    public void testGetBetrokkenheid() {
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Betrokkenheid ouder = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatie);
        final Betrokkenheid kind = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatie);

        final Persoon ouderPersoon = new Persoon(SoortPersoon.INGESCHREVENE);
        ouderPersoon.addBetrokkenheid(ouder);
        final Persoon kindPersoon = new Persoon(SoortPersoon.ONBEKEND);

        relatie.addBetrokkenheid(ouder);
        relatie.addBetrokkenheid(kind);

        final RelatieDecorator relatieDecorator = RelatieDecorator.decorate(relatie);
        final BetrokkenheidDecorator betrokkenheidDecorator = relatieDecorator.getBetrokkenheid(PersoonDecorator.decorate(ouderPersoon));
        assertNotNull(betrokkenheidDecorator);
        assertSame(ouder, betrokkenheidDecorator.getBetrokkenheid());
        assertSame(ouderPersoon, betrokkenheidDecorator.getPersoonDecorator().getPersoon());

        final BetrokkenheidDecorator kindBetrokkenheid = relatieDecorator.getBetrokkenheid(PersoonDecorator.decorate(kindPersoon));
        assertNull(kindBetrokkenheid);
    }

    @Test
    public void testMatches() {
        final Partij partij = new Partij("partij", 1);
        final Relatie relatieA = new Relatie(SoortRelatie.HUWELIJK);
        final Relatie relatieB = new Relatie(SoortRelatie.HUWELIJK);
        final Relatie relatieC = new Relatie(SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        final Relatie relatieD = new Relatie(SoortRelatie.HUWELIJK);

        relatieD.setRedenBeeindigingRelatie(OMZETTING);
        relatieD.setDatumEinde(20120101);
        relatieD.setGemeenteEinde(new Gemeente((short) 1, "GemeentA", (short) 1, partij));

        final RelatieDecorator relatieADecorator = RelatieDecorator.decorate(relatieA);
        final RelatieDecorator relatieBDecorator = RelatieDecorator.decorate(relatieB);
        final RelatieDecorator relatieCDecorator = RelatieDecorator.decorate(relatieC);
        final RelatieDecorator relatieDDecorator = RelatieDecorator.decorate(relatieD);

        assertTrue(relatieADecorator.matches(relatieBDecorator));
        assertFalse(relatieADecorator.matches(relatieCDecorator));
        assertFalse(relatieDDecorator.matches(relatieADecorator));

        assertTrue(relatieADecorator.matches(relatieDDecorator));
        assertFalse(relatieADecorator.matches(relatieDDecorator, true));
    }

    @Test
    public void testIsOntbonden() {
        final Relatie relatieA = new Relatie(SoortRelatie.HUWELIJK);
        final Relatie relatieB = new Relatie(SoortRelatie.HUWELIJK);
        relatieB.setDatumEinde(20150101);
        final Relatie relatieC = new Relatie(SoortRelatie.HUWELIJK);
        relatieC.setRedenBeeindigingRelatie(OMZETTING);

        final RelatieDecorator relatieADecorator = RelatieDecorator.decorate(relatieA);
        final RelatieDecorator relatieBDecorator = RelatieDecorator.decorate(relatieB);
        final RelatieDecorator relatieCDecorator = RelatieDecorator.decorate(relatieC);

        assertTrue(relatieADecorator.matches(relatieADecorator));
        assertTrue(relatieADecorator.matches(relatieBDecorator));
        assertTrue(relatieADecorator.matches(relatieCDecorator));
    }

    @Test(expected = IllegalStateException.class)
    public void testZoekMatchendeRelatie() {
        final Partij partij = new Partij("partij", 1);
        final Relatie relatieA = new Relatie(SoortRelatie.HUWELIJK);
        final Relatie relatieB = new Relatie(SoortRelatie.HUWELIJK);
        final Relatie relatieC = new Relatie(SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        final Relatie relatieD = new Relatie(SoortRelatie.HUWELIJK);
        final Relatie relatieE = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        relatieD.setRedenBeeindigingRelatie(OMZETTING);
        relatieD.setDatumEinde(20120101);
        relatieD.setGemeenteEinde(new Gemeente((short) 1, "GemeentA", (short) 1, partij));

        final RelatieDecorator relatieADecorator = RelatieDecorator.decorate(relatieA);
        final RelatieDecorator relatieBDecorator = RelatieDecorator.decorate(relatieB);
        final RelatieDecorator relatieCDecorator = RelatieDecorator.decorate(relatieC);
        final RelatieDecorator relatieDDecorator = RelatieDecorator.decorate(relatieD);
        final RelatieDecorator relatieEDecorator = RelatieDecorator.decorate(relatieE);

        final List<RelatieDecorator> relaties = new ArrayList<>();
        relaties.add(relatieADecorator);
        relaties.add(relatieBDecorator);
        relaties.add(relatieCDecorator);
        relaties.add(relatieDDecorator);

        assertNotNull(relatieCDecorator.zoekMatchendeRelatie(relaties));
        assertNull(relatieADecorator.zoekMatchendeRelatie(Collections.<RelatieDecorator>emptyList()));
        assertNull(relatieEDecorator.zoekMatchendeRelatie(relaties));
        relatieADecorator.zoekMatchendeRelatie(relaties);
    }

    @Test
    public void testLaatVervallenIkAlsKindVerva() {
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final RelatieHistorie relatieHistorie = new RelatieHistorie(relatie);

        relatie.addRelatieHistorie(relatieHistorie);

        final Betrokkenheid ouder = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatie);
        final BetrokkenheidHistorie ouderHistorie = new BetrokkenheidHistorie(ouder);
        ouder.addBetrokkenheidHistorie(ouderHistorie);

        final Betrokkenheid kind = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatie);
        final BetrokkenheidHistorie kindHistorie = new BetrokkenheidHistorie(kind);
        kind.addBetrokkenheidHistorie(kindHistorie);

        final Persoon ouderPersoon = new Persoon(SoortPersoon.ONBEKEND);
        ouderPersoon.setAdministratienummer(1234567890L);
        ouderPersoon.addBetrokkenheid(ouder);
        final Persoon kindPersoon = new Persoon(SoortPersoon.INGESCHREVENE);
        kindPersoon.setAdministratienummer(9876543210L);
        kindPersoon.addBetrokkenheid(kind);

        final PersoonGeboorteHistorie ouderPersoonGeboorteHistorie = new PersoonGeboorteHistorie(ouderPersoon, 19800101, LAND_OF_GEBIED);
        final PersoonGeboorteHistorie kindPersoonGeboorteHistorie = new PersoonGeboorteHistorie(kindPersoon, 20150101, LAND_OF_GEBIED);
        ouderPersoon.addPersoonGeboorteHistorie(ouderPersoonGeboorteHistorie);
        kindPersoon.addPersoonGeboorteHistorie(kindPersoonGeboorteHistorie);

        ouder.setAanduidingOuder(new Lo3AanduidingOuder(AanduidingOuder.OUDER_1, ouder));

        relatie.addBetrokkenheid(ouder);
        relatie.addBetrokkenheid(kind);

        final RelatieDecorator relatieDecorator = RelatieDecorator.decorate(relatie);
        relatieDecorator.laatVervallen(ACTIE, PersoonDecorator.decorate(kindPersoon), AanduidingOuder.OUDER_2);
        assertNull(ouderHistorie.getActieVervalTbvLeveringMutaties());
        assertNull(ouderPersoonGeboorteHistorie.getIndicatieVoorkomenTbvLeveringMutaties());

        relatieDecorator.laatVervallen(ACTIE, PersoonDecorator.decorate(kindPersoon), AanduidingOuder.OUDER_1);
        assertNull(relatieHistorie.getActieVervalTbvLeveringMutaties());
        assertNull(relatieHistorie.getIndicatieVoorkomenTbvLeveringMutaties());

        // Kind is de IK-persoon. Daar vervalt de persoon, de bestrokkenheid en de relatie niet bij een kind-ouder relatie
        assertNull(kindHistorie.getActieVervalTbvLeveringMutaties());
        assertNull(kindPersoonGeboorteHistorie.getActieVervalTbvLeveringMutaties());

        assertEquals(ACTIE, ouderHistorie.getActieVervalTbvLeveringMutaties());
        assertEquals(ACTIE, ouderPersoonGeboorteHistorie.getActieVervalTbvLeveringMutaties());
    }

    @Test
    public void testLaatVervallenIkAlsOuder() {
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final RelatieHistorie relatieHistorie = new RelatieHistorie(relatie);

        relatie.addRelatieHistorie(relatieHistorie);

        final Betrokkenheid ouder = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatie);
        final BetrokkenheidHistorie ouderHistorie = new BetrokkenheidHistorie(ouder);
        ouder.addBetrokkenheidHistorie(ouderHistorie);

        final Betrokkenheid kind = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatie);
        final BetrokkenheidHistorie kindHistorie = new BetrokkenheidHistorie(kind);
        kind.addBetrokkenheidHistorie(kindHistorie);

        final Persoon ouderPersoon = new Persoon(SoortPersoon.INGESCHREVENE);
        ouderPersoon.setAdministratienummer(1234567890L);
        ouderPersoon.addBetrokkenheid(ouder);
        final Persoon kindPersoon = new Persoon(SoortPersoon.ONBEKEND);
        kindPersoon.setAdministratienummer(9876543210L);
        kindPersoon.addBetrokkenheid(kind);

        final PersoonGeboorteHistorie ouderPersoonGeboorteHistorie = new PersoonGeboorteHistorie(ouderPersoon, 19800101, LAND_OF_GEBIED);
        final PersoonGeboorteHistorie kindPersoonGeboorteHistorie = new PersoonGeboorteHistorie(kindPersoon, 20150101, LAND_OF_GEBIED);
        ouderPersoon.addPersoonGeboorteHistorie(ouderPersoonGeboorteHistorie);
        kindPersoon.addPersoonGeboorteHistorie(kindPersoonGeboorteHistorie);

        relatie.addBetrokkenheid(ouder);
        relatie.addBetrokkenheid(kind);

        final RelatieDecorator relatieDecorator = RelatieDecorator.decorate(relatie);
        relatieDecorator.laatVervallen(ACTIE, PersoonDecorator.decorate(kindPersoon), null);
        assertNotNull(relatieHistorie.getActieVervalTbvLeveringMutaties());
        assertNotNull(relatieHistorie.getIndicatieVoorkomenTbvLeveringMutaties());

        // Ouder is de IK-persoon. Daar vervalt alleen de bestrokkenheid bij een ouder-kind relatie
        assertEquals(ACTIE, kindHistorie.getActieVervalTbvLeveringMutaties());
        assertEquals(ACTIE, kindPersoonGeboorteHistorie.getActieVervalTbvLeveringMutaties());
        assertEquals(ACTIE, relatieHistorie.getActieVervalTbvLeveringMutaties());

        assertEquals(ACTIE, ouderHistorie.getActieVervalTbvLeveringMutaties());
        assertNull(ouderPersoonGeboorteHistorie.getActieVervalTbvLeveringMutaties());
    }
}
