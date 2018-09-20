/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Betrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Relatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortBetrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Stapel;

import org.junit.Before;
import org.junit.Test;

/**
 * Unittest voor {@link DeltaRootEntiteitMatch}.
 */
public class DeltaRootEntiteitMatchTest {

    private Persoon bestaandePersoon;
    private Persoon nieuwPersoon;

    @Before
    public void setUp() {
        bestaandePersoon = new Persoon(SoortPersoon.INGESCHREVENE);
        nieuwPersoon = new Persoon(SoortPersoon.INGESCHREVENE);
    }

    @Test
    public void testConstructor() {
        final String veldnaam = "veldnaam";
        final DeltaRootEntiteitMatch match = new DeltaRootEntiteitMatch(bestaandePersoon, nieuwPersoon, bestaandePersoon, veldnaam);

        assertEquals(bestaandePersoon, match.getBestaandeDeltaRootEntiteit());
        assertEquals(nieuwPersoon, match.getNieuweDeltaRootEntiteit());
        assertEquals(bestaandePersoon, match.getEigenaarEntiteit());
        assertEquals(veldnaam, match.getEigenaarEntiteitVeldnaam());
        assertTrue(match.isDeltaRootEntiteitGewijzigd());
        assertFalse(match.isDeltaRootEntiteitNieuw());
        assertFalse(match.isDeltaRootEntiteitVerwijderd());
        assertFalse(match.isIstStapel());
        assertFalse(match.isEigenPersoon());
        assertNull(match.getVergelijkerResultaat());
    }

    @Test(expected = IllegalStateException.class)
    public void testConsctructorGeenEntiteiten() {
        new DeltaRootEntiteitMatch(null, null, null, null);
    }

    @Test
    public void testEigenPersoon() {
        final DeltaRootEntiteitMatch match = new DeltaRootEntiteitMatch(bestaandePersoon, nieuwPersoon, null, null);
        assertTrue(match.isEigenPersoon());
    }

    @Test
    public void testIstStapel() {
        final Stapel stapel = new Stapel(bestaandePersoon, "01", 0);
        final DeltaRootEntiteitMatch match1 = new DeltaRootEntiteitMatch(stapel, bestaandePersoon, null, null);
        assertTrue(match1.isIstStapel());

        final DeltaRootEntiteitMatch match2 = new DeltaRootEntiteitMatch(bestaandePersoon, stapel, null, null);
        assertTrue(match2.isIstStapel());
    }

    @Test
    public void testNieuw() {
        final DeltaRootEntiteitMatch match = new DeltaRootEntiteitMatch(null, nieuwPersoon, null, null);
        assertTrue(match.isDeltaRootEntiteitNieuw());
        assertFalse(match.isDeltaRootEntiteitGewijzigd());
        assertFalse(match.isDeltaRootEntiteitVerwijderd());
    }

    @Test
    public void testVerwijderd() {
        final DeltaRootEntiteitMatch match = new DeltaRootEntiteitMatch(bestaandePersoon, null, null, null);
        assertFalse(match.isDeltaRootEntiteitNieuw());
        assertFalse(match.isDeltaRootEntiteitGewijzigd());
        assertTrue(match.isDeltaRootEntiteitVerwijderd());
    }

    @Test
    public void testGewijzigd() {
        final DeltaRootEntiteitMatch match = new DeltaRootEntiteitMatch(bestaandePersoon, nieuwPersoon, null, null);
        assertFalse(match.isDeltaRootEntiteitNieuw());
        assertTrue(match.isDeltaRootEntiteitGewijzigd());
        assertFalse(match.isDeltaRootEntiteitVerwijderd());
    }

    @Test
    public void testToString() {
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Betrokkenheid betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, relatie);

        final DeltaRootEntiteitMatch match1 = new DeltaRootEntiteitMatch(bestaandePersoon, null, null, null);
        assertEquals("Persoon", match1.toString());

        final DeltaRootEntiteitMatch match2 = new DeltaRootEntiteitMatch(relatie, null, null, null);
        assertEquals("Relatie", match2.toString());

        final DeltaRootEntiteitMatch match3 = new DeltaRootEntiteitMatch(null, betrokkenheid, bestaandePersoon, null);
        assertEquals("Ik-Betrokkenheid (relatie)", match3.toString());

        final DeltaRootEntiteitMatch match4 = new DeltaRootEntiteitMatch(null, betrokkenheid, relatie, null);
        assertEquals("gerelateerde betrokkenheid", match4.toString());

        final DeltaRootEntiteitMatch match5 = new DeltaRootEntiteitMatch(null, bestaandePersoon, betrokkenheid, null);
        assertEquals("gerelateerde persoon", match5.toString());
    }

    @Test
    public void testVergelijkerResultaat() {
        final DeltaRootEntiteitMatch match = new DeltaRootEntiteitMatch(bestaandePersoon, nieuwPersoon, null, null);
        assertNull(match.getVergelijkerResultaat());
        final VergelijkerResultaat resultaat = new DeltaVergelijkerResultaat();
        match.setVergelijkerResultaat(resultaat);
        assertEquals(resultaat, match.getVergelijkerResultaat());
    }

    @Test
    public void testEqualsHashcode() {
        final DeltaRootEntiteitMatch match1 = new DeltaRootEntiteitMatch(bestaandePersoon, nieuwPersoon, null, null);
        final DeltaRootEntiteitMatch match2 = new DeltaRootEntiteitMatch(nieuwPersoon, bestaandePersoon, null, null);
        final DeltaRootEntiteitMatch match3 = new DeltaRootEntiteitMatch(bestaandePersoon, nieuwPersoon, null, null);

        assertTrue(match1.equals(match1));
        assertFalse(match1.equals(match2));
        assertTrue(match1.equals(match3));
        assertFalse(match2.equals(match1));
        assertTrue(match2.equals(match2));
        assertFalse(match1.equals(bestaandePersoon));

        assertFalse (match1.hashCode() == match2.hashCode());
        assertTrue(match1.hashCode() == match3.hashCode());
    }
}
