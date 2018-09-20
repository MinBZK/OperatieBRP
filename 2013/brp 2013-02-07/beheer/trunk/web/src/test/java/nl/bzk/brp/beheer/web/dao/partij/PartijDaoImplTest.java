/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.dao.partij;

import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import junit.framework.Assert;
import nl.bzk.brp.beheer.web.dao.AbstractRepositoryTestCase;
import nl.bzk.brp.beheer.web.dao.BasisDao;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.PartijRol;
import nl.bzk.brp.domein.kern.Rol;
import nl.bzk.brp.domein.kern.SoortPartij;
import nl.bzk.brp.domein.kern.persistent.PersistentPartij;
import nl.bzk.brp.domein.kern.persistent.PersistentPartijRol;

import org.apache.commons.codec.DecoderException;
import org.hibernate.ObjectNotFoundException;
import org.junit.Test;


public class PartijDaoImplTest extends AbstractRepositoryTestCase {

    @PersistenceContext
    private EntityManager    entityManager;

    @Inject
    @Named("partijDao")
    private BasisDao<Partij> partijDaoImpl;

    /**
     * Test het ophalen van een partij.
     */
    @Test
    public void testGetById() {
        Partij partij = new PersistentPartij();
        partij.setNaam("partijDao.testGetById");
        entityManager.persist(partij);

        Partij partijTest = partijDaoImpl.getById(partij.getID());
        Assert.assertEquals("Partij met de naam partijDao.testGetById verwacht", "partijDao.testGetById",
                partijTest.getNaam());
    }

    /**
     * Test het ophalen van een partij zonder resultaat.
     */
    @Test(expected = ObjectNotFoundException.class)
    public void testGetByIdGeenResultaat() {
        partijDaoImpl.getById(10000);
    }

    /**
     * Test het zoeken van de partij.
     */
    @Test
    public void testFindPartijMetNaam() {
        List<Partij> partijen = partijDaoImpl.findObject("Amsterdam", 0, 30);

        Assert.assertEquals(1, partijen.size());
        Assert.assertEquals("Amsterdam", partijen.get(0).getNaam());
        Assert.assertSame(SoortPartij.WETGEVER, partijen.get(0).getSoort());
        Assert.assertEquals("Wetgever", partijen.get(0).getSoort().getNaam());

        partijen = partijDaoImpl.findObject("Dam", 0, 30);

        Assert.assertEquals(3, partijen.size());

        Assert.assertEquals("Amsterdam", partijen.get(0).getNaam());
        Assert.assertEquals("Rotterdam", partijen.get(1).getNaam());
        Assert.assertEquals("Zaandam", partijen.get(2).getNaam());

        Long aantal = partijDaoImpl.tellObjecten("Dam");
        Assert.assertEquals(Long.valueOf(3), aantal);

        // TODO Hosing: zoek criteria is tijdelijk aanpast. Het zoekt tijdelijk alleen niet naam. In afwachting van de
        // functionele specs
        // partij.setNaam("Gem");
        // partijen = partijDaoImpl.findObject(partij, 0, 30);
        // Assert.assertEquals(6, partijen.size());
    }

    /**
     * Test het zoeken van partijen met gelimiteerde resultaten
     */
    @Test
    public void testFindPartijGelimiteerdeResultaten() {
        List<Partij> partijen = partijDaoImpl.findObject("", 0, 4);

        Assert.assertEquals(4, partijen.size());
        Assert.assertEquals("Almere", partijen.get(0).getNaam());

        partijen = partijDaoImpl.findObject("", 2, 4);
        Assert.assertEquals(4, partijen.size());
        Assert.assertEquals("Alkmaar", partijen.get(0).getNaam());
    }

    /**
     * Test het opslaan van de partij.
     *
     * @throws DecoderException
     * @throws UnknownHostException
     */
    @Test
    public void testPersistRol() throws DecoderException, UnknownHostException {
        // Partij
        Partij partij = new PersistentPartij();
        partij.setNaam("Test1");

        // Rollen
        PartijRol partijRol1 = new PersistentPartijRol();
        partijRol1.setPartij(partij);
        partijRol1.setRol(Rol.AFNEMER);

        PartijRol partijRol2 = new PersistentPartijRol();
        partijRol2.setPartij(partij);
        partijRol2.setRol(Rol.BEVOEGDHEIDSTOEDELER);

        // Set rollen en authenticatiemiddelen
        partij.addPartijRol(partijRol1);
        partij.addPartijRol(partijRol2);
        partij = partijDaoImpl.save(partij);

        Query query = entityManager.createQuery("from PersistentPartij where id = :id");
        query.setParameter("id", partij.getID());
        Partij partijResult = (Partij) query.getSingleResult();

        Assert.assertNotNull(partijResult);

        query = entityManager.createQuery("from PersistentPartijRol");
        @SuppressWarnings("unchecked")
        List<PartijRol> partijrol = query.getResultList();
        Assert.assertNotNull(partijrol);
    }
}
