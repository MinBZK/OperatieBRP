/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;

import org.junit.Assert;
import org.junit.Test;

public class RelatieTest {
    @Test
    public void addStapel() {
        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);
        final Stapel stapel = new Stapel(new Persoon(SoortPersoon.INGESCHREVENE), "05", 0);

        relatie.addStapel(stapel);
        assertEnkeleKoppeling(relatie, stapel);

        relatie.addStapel(stapel);
        assertEnkeleKoppeling(relatie, stapel);
    }

    @Test
    public void testGetAndereBetrokkenheidHuwelijk() {
        final Persoon persoon1 = new Persoon(SoortPersoon.INGESCHREVENE);
        final Persoon persoon2 = new Persoon(SoortPersoon.INGESCHREVENE);
        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);
        final Betrokkenheid betrokkenheid1 = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        final Betrokkenheid betrokkenheid2 = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        persoon1.addBetrokkenheid(betrokkenheid1);
        persoon2.addBetrokkenheid(betrokkenheid2);
        relatie.addBetrokkenheid(betrokkenheid1);
        relatie.addBetrokkenheid(betrokkenheid2);

        assertEquals(betrokkenheid1, relatie.getAndereBetrokkenheid(persoon2));
        assertEquals(betrokkenheid2, relatie.getAndereBetrokkenheid(persoon1));
    }

    @Test
    public void testGetAndereBetrokkenheidGeregistreerdPartnerschap() {
        final Persoon persoon1 = new Persoon(SoortPersoon.INGESCHREVENE);
        final Persoon persoon2 = new Persoon(SoortPersoon.INGESCHREVENE);
        final Relatie relatie = new Relatie(SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        final Betrokkenheid betrokkenheid1 = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        final Betrokkenheid betrokkenheid2 = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        persoon1.addBetrokkenheid(betrokkenheid1);
        persoon2.addBetrokkenheid(betrokkenheid2);
        relatie.addBetrokkenheid(betrokkenheid1);
        relatie.addBetrokkenheid(betrokkenheid2);

        assertEquals(betrokkenheid1, relatie.getAndereBetrokkenheid(persoon2));
        assertEquals(betrokkenheid2, relatie.getAndereBetrokkenheid(persoon1));
    }

    @Test(expected = IllegalStateException.class)
    public void testGetAndereBetrokkenheidAndereRelatie() {
        final Persoon persoon1 = new Persoon(SoortPersoon.INGESCHREVENE);
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        relatie.getAndereBetrokkenheid(persoon1);
    }

    @Test
    public void testGetActueleOfMeestRecentBeeindigdeRelatie() {
        assertNull(Relatie.getActueleOfMeestRecentBeeindigdeRelatie(Collections.emptySet()));

        final Relatie relatieActueel = new Relatie(SoortRelatie.HUWELIJK);
        final RelatieHistorie relatieActueelHistorie = new RelatieHistorie(relatieActueel);
        relatieActueel.addRelatieHistorie(relatieActueelHistorie);

        final Relatie relatieVervallen = new Relatie(SoortRelatie.HUWELIJK);
        final RelatieHistorie relatieVervallenHistorie = new RelatieHistorie(relatieVervallen);
        relatieVervallenHistorie.setDatumTijdVerval(Timestamp.from(Instant.now()));
        relatieVervallen.addRelatieHistorie(relatieVervallenHistorie);

        final Relatie relatieBeeindigd = new Relatie(SoortRelatie.HUWELIJK);
        final RelatieHistorie relatieBeeindigdHistorie = new RelatieHistorie(relatieBeeindigd);
        relatieBeeindigd.addRelatieHistorie(relatieBeeindigdHistorie);

        final Relatie relatieEerderBeeindigd = new Relatie(SoortRelatie.HUWELIJK);
        final RelatieHistorie relatieEerderBeeindigdHistorie = new RelatieHistorie(relatieEerderBeeindigd);
        relatieEerderBeeindigd.addRelatieHistorie(relatieEerderBeeindigdHistorie);

        relatieBeeindigdHistorie.setDatumEinde(20160101);
        relatieEerderBeeindigdHistorie.setDatumEinde(20150101);
        final Set<Relatie> relaties = new HashSet<>();
        relaties.add(relatieEerderBeeindigd);
        assertEquals(relatieEerderBeeindigd, Relatie.getActueleOfMeestRecentBeeindigdeRelatie(relaties));

        relaties.add(relatieBeeindigd);
        assertEquals(relatieBeeindigd, Relatie.getActueleOfMeestRecentBeeindigdeRelatie(relaties));

        relaties.add(relatieVervallen);
        assertEquals(relatieBeeindigd, Relatie.getActueleOfMeestRecentBeeindigdeRelatie(relaties));

        relaties.add(relatieActueel);
        assertEquals(relatieActueel, Relatie.getActueleOfMeestRecentBeeindigdeRelatie(relaties));
    }

    private void assertEnkeleKoppeling(final Relatie relatie, final Stapel stapel) {
        assertEquals(1, relatie.getStapels().size());
        Assert.assertTrue(relatie.getStapels().contains(stapel));
        assertEquals(1, stapel.getRelaties().size());
        Assert.assertTrue(stapel.getRelaties().contains(relatie));

        final Stapel stapel1 = relatie.getStapels().iterator().next();
        final Relatie relatie1 = stapel.getRelaties().iterator().next();

        Assert.assertSame(stapel, stapel1);
        Assert.assertSame(relatie, relatie1);
    }
}
