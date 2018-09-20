/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.beheer.partijen.dao;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import junit.framework.Assert;
import nl.bzk.brp.beheer.model.AuthenticatieMiddel;
import nl.bzk.brp.beheer.model.Certificaat;
import nl.bzk.brp.beheer.model.Partij;
import nl.bzk.brp.beheer.model.Partijrol;
import nl.bzk.brp.beheer.model.Rol;
import nl.bzk.brp.web.beheer.dao.AbstractRepositoryTestCase;
import org.apache.commons.codec.DecoderException;
import org.hibernate.ObjectNotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PartijDaoImplTest extends AbstractRepositoryTestCase {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PartijDao     partijDao;

    /**
     * Test het ophalen van een partij.
     */
    @Test
    public void testGetById() {
        Partij partij = new Partij(null);
        partij.setNaam("partijDao.testGetById");
        entityManager.persist(partij);

        Partij partijTest = partijDao.getById(partij.getId());
        Assert.assertEquals("Partij met de naam partijDao.testGetById verwacht", "partijDao.testGetById", partijTest.getNaam());
    }

    /**
     * Test het ophalen van een partij zonder resultaat.
     */
    @Test(expected = ObjectNotFoundException.class)
    public void testGetByIdGeenResultaat() {
        partijDao.getById(10000);
    }

    /**
     * Test het zoeken van de partij.
     */
    @Test
    public void testFindPartijMetNaam() {
        List<Partij> partijen = partijDao.findPartij("Amsterdam", 0, 30);

        Assert.assertEquals(1, partijen.size());
        Assert.assertEquals("Amsterdam", partijen.get(0).getNaam());
        Assert.assertEquals("Gemeente", partijen.get(0).getSoort().getNaam());

        partijen = partijDao.findPartij("Dam", 0, 30);

        Assert.assertEquals(3, partijen.size());

        Assert.assertEquals("Amsterdam", partijen.get(0).getNaam());
        Assert.assertEquals("Rotterdam", partijen.get(1).getNaam());
        Assert.assertEquals("Zaandam", partijen.get(2).getNaam());

        Long aantal = partijDao.tellPartijen("Dam");
        Assert.assertEquals(Long.valueOf(3), aantal);

        partijen = partijDao.findPartij("Gem", 0, 30);
        Assert.assertEquals(6, partijen.size());
    }

    /**
     * Test het zoeken van partijen met gelimiteerde resultaten
     */
    @Test
    public void testFindPartijGelimiteerdeResultaten() {
        List<Partij> partijen = partijDao.findPartij("", 0, 4);

        Assert.assertEquals(4, partijen.size());
        Assert.assertEquals("Almere", partijen.get(0).getNaam());

        partijen = partijDao.findPartij("", 2, 4);
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
        // TODO fix inet type probleem

        Rol rol = entityManager.getReference(Rol.class, 1);
        Rol rol2 = entityManager.getReference(Rol.class, 2);

        // Partij
        Partij partij = new Partij(null);
        partij.setNaam("Test1");
        partij.setPartijStatushHis("A");
        partij.setGemStatusHis("A");

        // Rollen
        Partijrol partijRol1 = new Partijrol();
        partijRol1.setPartijRolStatusHis("A");
        partijRol1.setPartij(partij);
        partijRol1.setRol(rol);

        Partijrol partijRol2 = new Partijrol();
        partijRol2.setPartijRolStatusHis("A");
        partijRol2.setPartij(partij);
        partijRol2.setRol(rol2);

        Set<Partijrol> partijRollen = new HashSet<Partijrol>();
        partijRollen.add(partijRol1);
        partijRollen.add(partijRol2);

        // Authenticatiemiddelen
        Certificaat cert1 = new Certificaat();
        cert1.setSubject("cert1");
        cert1.setSerial(BigInteger.ONE);
        cert1.setSignature(new byte[] { 1, 1 });
        Certificaat cert2 = new Certificaat();
        cert2.setSubject("cert2");
        cert2.setSerial(BigInteger.ONE);
        cert2.setSignature(new byte[] { 1, 2 });

        Set<AuthenticatieMiddel> authmiddelen = new LinkedHashSet<AuthenticatieMiddel>();
        AuthenticatieMiddel auth1 = new AuthenticatieMiddel(partij, cert1);
        auth1.setAuthenticatiemiddelStatusHis("A");
        auth1.setCertificaatTbvSsl(cert1);
        auth1.setIpAdres(InetAddress.getLocalHost());

        AuthenticatieMiddel auth2 = new AuthenticatieMiddel(partij, cert2);
        auth2.setAuthenticatiemiddelStatusHis("A");
        auth2.setCertificaatTbvSsl(cert2);
        auth2.setIpAdres(InetAddress.getLocalHost());

        authmiddelen.add(auth1);
        authmiddelen.add(auth2);

        // Set rollen en authenticatiemiddelen
        partij.setPartijrols(partijRollen);
        partij.setAuthenticatieMiddels(authmiddelen);

        partijDao.save(partij);

        Query query = entityManager.createQuery("from Partij");
        // query.setParameter("id", partij.getId());
        @SuppressWarnings("unchecked")
        List<Partij> partijResult = query.getResultList();
        // Partij partijResult = (Partij) query.getSingleResult();

        Assert.assertNotNull(partijResult);

        query = entityManager.createQuery("from Partijrol");
        @SuppressWarnings("unchecked")
        List<Partijrol> partijrol = query.getResultList();
        Assert.assertNotNull(partijrol);
    }
}
