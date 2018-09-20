/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.impl;

import nl.bzk.brp.bevraging.business.dto.antwoord.AuthenticatieMiddelDTO;
import nl.bzk.brp.bevraging.business.service.AuthenticatieService;
import nl.bzk.brp.bevraging.business.service.exception.MeerdereAuthenticatieMiddelenExceptie;
import nl.bzk.brp.bevraging.domein.repository.CertificaatRepository;
import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.autaut.Authenticatiemiddel;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.Rol;
import nl.bzk.brp.domein.kern.SoortPartij;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@link AuthenticatieServiceImpl} class.
 */
public class AuthenticatieServiceImplTest {

    private static final String   SIGNATURE           = "0123456789ABCDEF";
    @Mock
    private CertificaatRepository certificaatRepository;
    private AuthenticatieService  authenticatieService;

    private final DomeinObjectFactory   domeinObjectFactory = new PersistentDomeinObjectFactory();

    @Test
    public void testGeenAuthenticatieMiddelBeschikbaar() throws DecoderException {
        Assert.assertNull(authenticatieService.zoekAuthenticatieMiddelEnPartijMetOndertekeningCertificaat(Long.valueOf(11),
                Hex.decodeHex(SIGNATURE.toCharArray()), "Subject"));
    }

    @Test
    public void testGeldigAuthenticatieMiddelBeschikbaar() throws DecoderException {
        AuthenticatieMiddelDTO dto =
            authenticatieService.zoekAuthenticatieMiddelEnPartijMetOndertekeningCertificaat(Long.valueOf(12),
                    Hex.decodeHex(SIGNATURE.toCharArray()), "Subject");
        Assert.assertNotNull(dto);
    }

    @Test(expected = MeerdereAuthenticatieMiddelenExceptie.class)
    public void testMeerdereAuthenticatieMiddelebBeschikbaar() throws DecoderException {
        authenticatieService.zoekAuthenticatieMiddelEnPartijMetOndertekeningCertificaat(Long.valueOf(13),
                Hex.decodeHex(SIGNATURE.toCharArray()), "Subject");
    }

    @Before
    public void init() throws DecoderException {
        MockitoAnnotations.initMocks(this);
        authenticatieService = new AuthenticatieServiceImpl();
        ReflectionTestUtils.setField(authenticatieService, "certificaatRepository", certificaatRepository);
        ReflectionTestUtils.setField(authenticatieService, "domeinObjectFactory", domeinObjectFactory);

        Partij gemeente = domeinObjectFactory.createPartij();
        gemeente.setSoort(SoortPartij.GEMEENTE);
        Authenticatiemiddel dto = domeinObjectFactory.createAuthenticatiemiddel();
        dto.setPartij(gemeente);
        dto.setID(2);

        Mockito.when(
                certificaatRepository.zoekAuthenticatieMiddelOpBasisvanCertificaat(Long.valueOf(12), "Subject",
                        Hex.encodeHexString(Hex.decodeHex(SIGNATURE.toCharArray())), Rol.AFNEMER)).thenReturn(dto);
        Mockito.when(
                certificaatRepository.zoekAuthenticatieMiddelOpBasisvanCertificaat(Long.valueOf(13), "Subject",
                        Hex.encodeHexString(Hex.decodeHex(SIGNATURE.toCharArray())), Rol.AFNEMER)).thenThrow(
                new IncorrectResultSizeDataAccessException(1));
    }
}
