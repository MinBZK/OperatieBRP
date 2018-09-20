/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.repository.AuthenticatieMiddelRepository;
import nl.bzk.brp.dataaccess.repository.PartijRepository;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.operationeel.aut.PersistentAuthenticatieMiddel;
import nl.bzk.brp.model.operationeel.aut.PersistentCertificaat;
import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class AuthenticatieServiceTest {

    private AuthenticatieService authenticatieService;

    private AuthenticatieMiddelRepository authenticatieMiddelRepository;

    private Partij testPartij1;
    private Partij testPartij2;

    private Integer partijId1 = 1200;
    private Integer partijId2 = 1300;

    private X509Certificate testCertificaat;

    @Before
    public void init() throws IOException, CertificateException, URISyntaxException {
        //Mock repo's
        PartijRepository partijRepository = Mockito.mock(PartijRepository.class);
        authenticatieMiddelRepository = Mockito.mock(AuthenticatieMiddelRepository.class);

        testPartij1 = new Partij();
        ReflectionTestUtils.setField(testPartij1, "id", partijId1);
        testPartij2 = new Partij();
        ReflectionTestUtils.setField(testPartij2, "id", partijId2);

        Mockito.when(partijRepository.findOne(partijId1)).thenReturn(testPartij1);
        Mockito.when(partijRepository.findOne(partijId2)).thenReturn(testPartij2);

        authenticatieService = new AuthenticatieServiceImpl();

        ReflectionTestUtils.setField(authenticatieService, "partijRepository", partijRepository);
        ReflectionTestUtils.setField(authenticatieService, "authenticatieMiddelRepository", authenticatieMiddelRepository);

        //Test certificaat init
        final URL certificaatLocatie = AuthenticatieServiceTest.class.getResource("/certificaat.crt");
        InputStream inStream = new FileInputStream(new File(certificaatLocatie.toURI()));
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        testCertificaat = (X509Certificate) cf.generateCertificate(inStream);
        inStream.close();
    }

    @Test
    public void testGeenAuthenticatieMiddelenAanwezig() {
        Assert.assertTrue(authenticatieService.haalAuthenticatieMiddelenOp(partijId1, testCertificaat).isEmpty());
        Assert.assertTrue(authenticatieService.haalAuthenticatieMiddelenOp(partijId2, testCertificaat).isEmpty());
    }

    @Test
    public void testAuthenticatieMiddelAanwezig() {
        PersistentCertificaat cert = new PersistentCertificaat();
        PersistentAuthenticatieMiddel authMiddel = new PersistentAuthenticatieMiddel();
        ReflectionTestUtils.setField(authMiddel, "partij", testPartij1);
        ReflectionTestUtils.setField(authMiddel, "ondertekeningsCertificaat", cert);
        List<PersistentAuthenticatieMiddel> authMiddelen = new ArrayList<PersistentAuthenticatieMiddel>();
        authMiddelen.add(authMiddel);
        Mockito.when(authenticatieMiddelRepository.zoekAuthMiddelenVoorPartijMetCertificaat(
                testPartij1,
                testCertificaat.getSubjectDN().getName(),
                testCertificaat.getSerialNumber(),
                Hex.encodeHexString(testCertificaat.getSignature())))
                .thenReturn(authMiddelen);

        Assert.assertEquals(1, authenticatieService.haalAuthenticatieMiddelenOp(partijId1, testCertificaat).size());
    }

    @Test
    public void testAuthenticatieMiddelAanwezigMaarGekoppeldAanAnderePartij() {
        PersistentCertificaat cert = new PersistentCertificaat();
        PersistentAuthenticatieMiddel authMiddel = new PersistentAuthenticatieMiddel();
        ReflectionTestUtils.setField(authMiddel, "partij", testPartij1);
        ReflectionTestUtils.setField(authMiddel, "ondertekeningsCertificaat", cert);
        List<PersistentAuthenticatieMiddel> authMiddelen = new ArrayList<PersistentAuthenticatieMiddel>();
        authMiddelen.add(authMiddel);
        Mockito.when(authenticatieMiddelRepository.zoekAuthMiddelenVoorPartijMetCertificaat(
                testPartij1,
                testCertificaat.getSubjectDN().getName(),
                testCertificaat.getSerialNumber(),
                Hex.encodeHexString(testCertificaat.getSignature())))
                .thenReturn(authMiddelen);

        Assert.assertEquals(0, authenticatieService.haalAuthenticatieMiddelenOp(partijId2, testCertificaat).size());
    }
}
