/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigInteger;

import nl.bzk.brp.bevraging.business.dto.antwoord.AuthenticatieMiddelDTO;
import nl.bzk.brp.bevraging.business.service.AuthenticatieService;
import nl.bzk.brp.bevraging.business.service.exception.MeerdereAuthenticatieMiddelenExceptie;
import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.Rol;
import nl.bzk.brp.bevraging.domein.SoortPartij;
import nl.bzk.brp.bevraging.domein.aut.AuthenticatieMiddel;
import nl.bzk.brp.bevraging.domein.repository.CertificaatRepository;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
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

    private static final String   SIGNATURE = "0123456789ABCDEF";
    @Mock
    private CertificaatRepository certificaatRepository;
    private AuthenticatieService  authenticatieService;

    @Test
    public void testGeenAuthenticatieMiddelBeschikbaar() throws DecoderException {
        assertNull(authenticatieService.zoekAuthenticatieMiddelEnPartijMetOndertekeningCertificaat(
                BigInteger.valueOf(11), Hex.decodeHex(SIGNATURE.toCharArray()), "Subject"));
    }

    @Test
    public void testGeldigAuthenticatieMiddelBeschikbaar() throws DecoderException {
        AuthenticatieMiddelDTO dto =
            authenticatieService.zoekAuthenticatieMiddelEnPartijMetOndertekeningCertificaat(BigInteger.valueOf(12),
                    Hex.decodeHex(SIGNATURE.toCharArray()), "Subject");
        assertNotNull(dto);
    }

    @Test(expected = MeerdereAuthenticatieMiddelenExceptie.class)
    public void testMeerdereAuthenticatieMiddelebBeschikbaar() throws DecoderException {
        authenticatieService.zoekAuthenticatieMiddelEnPartijMetOndertekeningCertificaat(BigInteger.valueOf(13),
                Hex.decodeHex(SIGNATURE.toCharArray()), "Subject");
    }

    @Before
    public void init() throws DecoderException {
        MockitoAnnotations.initMocks(this);
        authenticatieService = new AuthenticatieServiceImpl();
        ReflectionTestUtils.setField(authenticatieService, "certificaatRepository", certificaatRepository);

        AuthenticatieMiddel dto = new AuthenticatieMiddel(new Partij(SoortPartij.GEMEENTE), null);
        ReflectionTestUtils.setField(dto, "id", 2L);

        Mockito.when(
                certificaatRepository.zoekAuthenticatieMiddelOpBasisvanCertificaat(BigInteger.valueOf(12), "Subject",
                        Hex.encodeHexString(Hex.decodeHex(SIGNATURE.toCharArray())), Rol.AFNEMER)).thenReturn(dto);
        Mockito.when(
                certificaatRepository.zoekAuthenticatieMiddelOpBasisvanCertificaat(BigInteger.valueOf(13), "Subject",
                        Hex.encodeHexString(Hex.decodeHex(SIGNATURE.toCharArray())), Rol.AFNEMER)).thenThrow(
                new IncorrectResultSizeDataAccessException(1));
    }

}
