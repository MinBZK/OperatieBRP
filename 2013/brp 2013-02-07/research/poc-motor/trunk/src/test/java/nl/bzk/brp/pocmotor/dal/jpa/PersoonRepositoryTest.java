/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.dal.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.pocmotor.dal.AbstractRepositoryTest;
import nl.bzk.brp.pocmotor.dal.logisch.PersoonLGMRepository;
import nl.bzk.brp.pocmotor.dal.logisch.GemeenteLGMRepository;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Persoon;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test voor het testen van de persoon DAO.
 */
public class PersoonRepositoryTest extends AbstractRepositoryTest {

    @Inject
    private PersoonLGMRepository persoonLGMRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testOphalenPersoonOpBasisVanBsn() {
        Burgerservicenummer bsn = new Burgerservicenummer();
        bsn.setWaarde("123456789");
        Persoon persoon = persoonLGMRepository.findByIdentificatienummersBurgerservicenummer(bsn);
        Assert.assertNotNull(persoon);
    }

    @Test
    public void testOphalenALaagPersoon() {
        Persoon pers = em.find(Persoon.class, 1L);
        Assert.assertNotNull(pers);
    }
}
