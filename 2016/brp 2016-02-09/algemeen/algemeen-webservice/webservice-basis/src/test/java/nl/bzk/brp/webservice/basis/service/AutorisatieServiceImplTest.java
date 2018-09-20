/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.basis.service;

import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 */
@Deprecated
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class AutorisatieServiceImplTest extends TestCase {


//    @Mock
//    private WebServiceContext webServiceContext;
//
//    @Mock
//    private AuthenticatieService authenticatieServiceMock;
//
//    private DummyBericht bericht;
//    private TestCertificaatResolver        webserviceServices           = new TestCertificaatResolver();
//    private AuthenticatieServiceImpl authenticatiemiddelExtractor = new AuthenticatieServiceImpl();
//
//    @Before
//    public void voorTesT() {
//        bericht = new DummyBericht();
//    }
//
//    @Test
//    public void testGeenStuurgegevens() {
//        final BerichtenIds berichtenIds = new BerichtenIds(5L, 10L);
//        final Authenticatiemiddel authenticatiemiddel = authenticatiemiddelExtractor
//            .geefAutenticatieMiddel(bericht, berichtenIds, webServiceContext, authenticatieServiceMock, webserviceServices);
//        Mockito.verifyZeroInteractions(authenticatieServiceMock);
//        assertNull(authenticatiemiddel);
//    }
//
//    @Test
//    public void testGeenPartijInStuurgegevens() {
//        initBericht(false, null, null);
//        final BerichtenIds berichtenIds = new BerichtenIds(5L, 10L);
//        final Authenticatiemiddel authenticatiemiddel = authenticatiemiddelExtractor
//            .geefAutenticatieMiddel(bericht, berichtenIds, webServiceContext, authenticatieServiceMock, webserviceServices);
//        Mockito.verifyZeroInteractions(authenticatieServiceMock);
//        assertNull(authenticatiemiddel);
//    }
//
//
//    @Test
//    public void testBerichtZonderCertificaat() {
//        initBericht(true, "3", bericht.getSoort().getWaarde());
//        final BerichtenIds berichtenIds = new BerichtenIds(5L, 10L);
//        final Authenticatiemiddel authenticatiemiddel = authenticatiemiddelExtractor
//            .geefAutenticatieMiddel(bericht, berichtenIds, webServiceContext, authenticatieServiceMock, webserviceServices);
//        Mockito.verifyZeroInteractions(authenticatieServiceMock);
//        assertNull(authenticatiemiddel);
//    }
//
//    @Test
//    public void testBerichtMetCertificaat() {
//        initBericht(true, "3", bericht.getSoort().getWaarde());
//        final BerichtenIds berichtenIds = new BerichtenIds(5L, 10L);
//        final X509Certificate certificate = Mockito.mock(X509Certificate.class);
//        webserviceServices.setCertificate(certificate);
//
//        final Authenticatiemiddel mockAuthenticatieMiddel = Mockito.mock(Authenticatiemiddel.class);
//        Mockito.when(authenticatieServiceMock.haalAuthenticatieMiddelenOp(Mockito.<PartijCodeAttribuut>any(), Mockito.<X509Certificate>any())).
//            thenReturn(Collections.singletonList(mockAuthenticatieMiddel));
//        final Authenticatiemiddel authenticatiemiddel = authenticatiemiddelExtractor
//            .geefAutenticatieMiddel(bericht, berichtenIds, webServiceContext, authenticatieServiceMock, webserviceServices);
//        assertTrue(authenticatiemiddel == mockAuthenticatieMiddel);
//    }
//
//
//    @Test
//    public void testBerichtMetNietUniekAuthenticatiemiddel() {
//        initBericht(true, "3", bericht.getSoort().getWaarde());
//        final BerichtenIds berichtenIds = new BerichtenIds(5L, 10L);
//        final X509Certificate certificate = Mockito.mock(X509Certificate.class);
//        webserviceServices.setCertificate(certificate);
//
//        Mockito.when(authenticatieServiceMock.haalAuthenticatieMiddelenOp(Mockito.<PartijCodeAttribuut>any(), Mockito.<X509Certificate>any())).
//            thenReturn(Arrays.asList(Mockito.mock(Authenticatiemiddel.class), Mockito.mock(Authenticatiemiddel.class)));
//        final Authenticatiemiddel authenticatiemiddel = authenticatiemiddelExtractor
//            .geefAutenticatieMiddel(bericht, berichtenIds, webServiceContext, authenticatieServiceMock, webserviceServices);
//        assertNull(authenticatiemiddel);
//    }
//
//    private static class DummyBericht extends BerichtBericht {
//
//        protected DummyBericht() {
//            super(new SoortBerichtAttribuut(SoortBericht.DUMMY));
//        }
//    }
//
//    /**
//     * Initialiseert de mock van het bericht door de partij id van het bericht te configureren op basis van parameter.
//     *
//     * @param heeftPartijId bepaalt of er een geldige partij id wordt geretourneerd of <code>null</code>.
//     * @param partijId      Het te gebruiken partij id.
//     * @param soortbericht  Het soort bericht.
//     */
//    protected void initBericht(final boolean heeftPartijId, final String partijId, final SoortBericht soortbericht) {
//        PartijAttribuut partij = null;
//        if (heeftPartijId) {
//            partij = StatischeObjecttypeBuilder.bouwPartij(Integer.parseInt(partijId), "partij-" + partijId);
//        }
//        final BerichtStuurgegevensGroepBericht stuurgegevens = new BerichtStuurgegevensGroepBericht();
//        stuurgegevens.setReferentienummer(new ReferentienummerAttribuut("REF001"));
//        stuurgegevens.setZendendePartij(partij);
//        stuurgegevens.setZendendePartijCode(partijId);
//        bericht.setStuurgegevens(stuurgegevens);
//        bericht.setSoort(new SoortBerichtAttribuut(soortbericht));
//        final BerichtParametersGroepBericht berichtParametersGroepBericht = new BerichtParametersGroepBericht();
//        //berichtParametersGroepBericht.setAbonnementNaam("TEST ABO");
//        bericht.setParameters(berichtParametersGroepBericht);
//    }
//
//    private static class TestCertificaatResolver implements CertificaatResolver {
//
//        X509Certificate certificate;
//
//        public TestCertificaatResolver() {
//        }
//
//        @Override
//        public X509Certificate haalClientCertificaatOp(final WebServiceContext wsContext) {
//            return certificate;
//        }
//
//        public void setCertificate(final X509Certificate certificate) {
//            this.certificate = certificate;
//        }
//    }
}
