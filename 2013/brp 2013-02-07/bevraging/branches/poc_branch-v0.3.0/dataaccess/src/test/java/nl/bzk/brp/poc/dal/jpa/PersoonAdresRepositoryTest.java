/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.dal.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.AbstractRepositoryTestCase;
import nl.bzk.brp.bevraging.domein.*;
import nl.bzk.brp.poc.dal.PersoonAdresRepository;
import nl.bzk.brp.poc.domein.BijhoudingContext;
import nl.bzk.brp.poc.domein.PocPersoon;
import nl.bzk.brp.poc.domein.PocPersoonAdres;
import nl.bzk.brp.poc.domein.RedenWijziging;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test class voor de {@link PersoonAdresRepository} class.
 */
public class PersoonAdresRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private PersoonAdresRepository persoonAdresRepository;
    @PersistenceContext
    private EntityManager          em;

    @Test
    public void testOphalenPersoonAdres() {
        PocPersoonAdres pa = persoonAdresRepository.ophalenPersoonAdresVoorPersoon(1L);
        Assert.assertNotNull(pa);
        Assert.assertEquals("New Yorkweg", pa.getNaamOpenbareRuimte());
    }

    @Test
    public void testOpslaanNieuwPersoonAdres() {
        BijhoudingContext context = new BijhoudingContext(20120202, null);

        Assert.assertEquals(3, countRowsInTable("kern.his_persadres"));

        PocPersoonAdres persoonAdres = creeerNieuwPersoonAdres(1L);
        persoonAdresRepository.opslaanNieuwPersoonAdres(context, persoonAdres);

        em.flush();
        Assert.assertEquals(5, countRowsInTable("kern.his_persadres"));
        Assert.assertEquals(1, countRowsInTable("kern.persadres"));
    }

    private PocPersoonAdres creeerNieuwPersoonAdres(final long persoonId) {
        PocPersoon persoon = new PocPersoon(SoortPersoon.INGESCHREVENE);
        ReflectionTestUtils.setField(persoon, "id", persoonId);

        Partij gemeente = new Partij(SoortPartij.GEMEENTE);
        ReflectionTestUtils.setField(gemeente, "id", 1L);
        Plaats plaats = new Plaats("Test");
        ReflectionTestUtils.setField(plaats, "id", 2L);
        Land land = new Land("Nederland");
        ReflectionTestUtils.setField(land, "id", 2L);

        PocPersoonAdres adres = new PocPersoonAdres(persoon, FunctieAdres.WOONADRES);
        adres.setGemeente(gemeente);
        adres.setWoonplaats(plaats);
        adres.setPostcode("1234AA");
        adres.setHuisNummer(4);
        adres.setPersAdresStatusHistorie("A");
        adres.setLand(land);
        adres.setRedenWijziging(RedenWijziging.AANGIFTE_PERSOON);
        return adres;
    }

}
