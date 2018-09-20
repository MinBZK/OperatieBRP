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
import nl.bzk.brp.dataaccess.repository.AuthenticatiemiddelRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.Certificaatserial;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.Certificaatsubject;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.PubliekeSleutel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Authenticatiemiddel;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Certificaat;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestAuthenticatiemiddel;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestCertificaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;


public class AuthenticatieServiceTest {

    private AuthenticatieService authenticatieService;

    private AuthenticatiemiddelRepository authenticatiemiddelRepository;

    private Partij testPartij1;
    private Partij testPartij2;

    private final Short partijId1 = Short.valueOf("1200");
    private final Short partijId2 = Short.valueOf("1300");

    private X509Certificate testCertificaat;

    @Before
    public void init() throws IOException, CertificateException, URISyntaxException {
        // Mock repo's
        authenticatiemiddelRepository = Mockito.mock(AuthenticatiemiddelRepository.class);

        testPartij1 = StatischeObjecttypeBuilder.bouwGemeente("1200", "testPartij1");
        ReflectionTestUtils.setField(testPartij1, "iD", Short.valueOf("1200"));
        testPartij2 = StatischeObjecttypeBuilder.bouwGemeente("1300", "testPartij2");
        ReflectionTestUtils.setField(testPartij2, "iD", Short.valueOf("1300"));

        authenticatieService = new AuthenticatieServiceImpl();

        ReflectionTestUtils.setField(authenticatieService, "authenticatiemiddelRepository",
                                     authenticatiemiddelRepository);

        // Test certificaat init
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
        Certificaat cert = new TestCertificaat(null, null, null);
        Authenticatiemiddel authMiddel = new TestAuthenticatiemiddel(testPartij1, null, null,
                cert, cert, null,
               StatusHistorie.A);
//        ReflectionTestUtils.setField(authMiddel, "partij", testPartij1);
//        ReflectionTestUtils.setField(authMiddel, "ondertekeningsCertificaat", cert);
        List<Authenticatiemiddel> authMiddelen = new ArrayList<Authenticatiemiddel>();
        authMiddelen.add(authMiddel);
        Mockito.when(
            authenticatiemiddelRepository.zoekAuthMiddelenVoorPartijMetCertificaat(testPartij1.getID(),
                new Certificaatsubject(testCertificaat.getSubjectDN().getName()),
                new Certificaatserial(testCertificaat.getSerialNumber().toString()),
                new PubliekeSleutel(Hex.encodeHexString(testCertificaat.getSignature())))).thenReturn(authMiddelen);

        Assert.assertEquals(1, authenticatieService.haalAuthenticatieMiddelenOp(partijId1, testCertificaat).size());
    }

    @Test
    public void testAuthenticatieMiddelAanwezigMaarGekoppeldAanAnderePartij() {
        Certificaat cert = new TestCertificaat(null, null, null);
        Authenticatiemiddel authMiddel = new TestAuthenticatiemiddel(testPartij1, null, null,
                cert, cert, null,
                StatusHistorie.A);
        List<Authenticatiemiddel> authMiddelen = new ArrayList<Authenticatiemiddel>();
        authMiddelen.add(authMiddel);
        Mockito.when(
            authenticatiemiddelRepository.zoekAuthMiddelenVoorPartijMetCertificaat(testPartij1.getID(),
                new Certificaatsubject(testCertificaat.getSubjectDN().getName()),
                new Certificaatserial(testCertificaat.getSerialNumber().toString()),
                new PubliekeSleutel(Hex.encodeHexString(testCertificaat.getSignature())))).thenReturn(authMiddelen);

        Assert.assertEquals(0, authenticatieService.haalAuthenticatieMiddelenOp(partijId2, testCertificaat).size());
    }
}
