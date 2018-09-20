/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.beheer.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.beheer.model.Partij;
import nl.bzk.brp.beheer.model.Partijrol;
import nl.bzk.brp.beheer.model.Rol;
import nl.bzk.brp.beheer.model.SoortPartij;
import nl.bzk.brp.web.beheer.dao.AbstractRepositoryTestCase;
import org.hibernate.ObjectNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Test voor GenericDaoImpl.
 *
 */
public class GenericDaoImplTest extends AbstractRepositoryTestCase {

    @PersistenceContext
    private EntityManager          entityManager;

    @Autowired
    private GenericDao genericDao;

    /**
     * Test het opslaan van een entity.
     */
    @Test
    public void testSave() {
        Partij partij = entityManager.getReference(Partij.class, 1L);
        Rol rol = entityManager.getReference(Rol.class, 1);

        Partijrol partijrol = new Partijrol();
        partijrol.setPartij(partij);
        partijrol.setRol(rol);

        partijrol = (Partijrol) genericDao.save(partijrol);

        Assert.assertTrue(partijrol.getId() > 0);
    }

    /**
     * Test het ophalen van een entity met int.
     */
    @Test
    public void testGetByIdLong() {
        Partij partij = new Partij(null);
        partij.setNaam("genericDao.testGetByIdLong");
        entityManager.persist(partij);

        Partij partijTest = (Partij) genericDao.getById(Partij.class, partij.getId());
        Assert.assertEquals("Partij met de naam genericDao.testGetByIdLong verwacht", "genericDao.testGetByIdLong", partijTest.getNaam());
    }

    /**
     * Test het ophalen van een entity met long
     */
    @Test
    public void testGetByIdInt() {
        Rol rol = new Rol(999999, "genericDao.testGetByIdLong");
        entityManager.persist(rol);

        Rol rolTest = (Rol) genericDao.getById(Rol.class, 999999);
        Assert.assertEquals("Rol met de naam genericDao.testGetByIdLong verwacht", "genericDao.testGetByIdLong", rolTest.getNaam());
    }


    /**
     * Test het ophalen van een entity zonder resultaat.
     */
    @Test(expected = ObjectNotFoundException.class)
    public void testGetByIdLongGeenResultaat() {
        genericDao.getById(Partij.class, 10000L);
    }

    /**
     * Test het ophalen van een entity zonder resultaat.
     */
    @Test(expected = ObjectNotFoundException.class)
    public void testGetByIdIntGeenResultaat() {
        genericDao.getById(Rol.class, 10000);
    }

    /**
     * Test het ophalen van alle entity.
     */
    @Test
    public void findAll() {
        List<Partij> partijen = genericDao.findAll(Partij.class);
        Assert.assertTrue("Er moet een lijst van partijen teruggegeven zijn", partijen.size() > 0);

        List<SoortPartij> soortPartij = genericDao.findAll(SoortPartij.class);
        Assert.assertTrue("Er moet een lijst van soort partijen teruggegeven zijn", soortPartij.size() > 0);
    }
}
