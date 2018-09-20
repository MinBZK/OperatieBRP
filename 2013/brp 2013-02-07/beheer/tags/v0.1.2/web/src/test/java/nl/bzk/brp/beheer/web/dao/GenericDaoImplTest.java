/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.PartijRol;
import nl.bzk.brp.domein.kern.Rol;
import nl.bzk.brp.domein.kern.persistent.PersistentPartij;
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
    private EntityManager entityManager;

    @Autowired
    private GenericDao    genericDao;

    private final DomeinObjectFactory domeinObjectFactory = new PersistentDomeinObjectFactory();

    /**
     * Test het opslaan van een entity.
     */
    @Test
    public void testSave() {
        Partij partij = domeinObjectFactory.createPartij();

        PartijRol partijrol = domeinObjectFactory.createPartijRol();
        partijrol.setPartij(partij);
        partijrol.setRol(Rol.AFNEMER);

        partijrol = (PartijRol) genericDao.save(partijrol);

        // TODO Hosing: check is niet helemaal correct, de entity is op het moment niet geannoteerd met cascadeType en
        // PartijRol wordt dus niet samen met de Partij gepersisteerd, maar in deze test slaagt toch, test moet
        // verbeterd worden.
        Assert.assertNotNull(entityManager.getReference(domeinObjectFactory.getImplementatie(PartijRol.class), partijrol.getID()));
    }

    /**
     * Test het ophalen van een entity met int.
     */
    @Test
    public void testGetByIdLong() {
        Partij partij = domeinObjectFactory.createPartij();
        partij.setNaam("genericDao.testGetByIdLong");
        entityManager.persist(partij);

        Partij partijTest = (Partij) genericDao.getById(domeinObjectFactory.getImplementatie(Partij.class), partij.getID());
        Assert.assertEquals("Partij met de naam genericDao.testGetByIdLong verwacht", "genericDao.testGetByIdLong",
                partijTest.getNaam());
    }

    /**
     * Test het ophalen van een entity zonder resultaat.
     */
    @Test(expected = ObjectNotFoundException.class)
    public void testGetByIdLongGeenResultaat() {
        genericDao.getById(domeinObjectFactory.getImplementatie(Partij.class), 10000L);
    }

    /**
     * Test het ophalen van alle entity.
     */
    @Test
    public void findAll() {
        List<PersistentPartij> partijen = genericDao.findAll(PersistentPartij.class);
        Assert.assertTrue("Er moet een lijst van partijen teruggegeven zijn", partijen.size() > 0);
    }
}
