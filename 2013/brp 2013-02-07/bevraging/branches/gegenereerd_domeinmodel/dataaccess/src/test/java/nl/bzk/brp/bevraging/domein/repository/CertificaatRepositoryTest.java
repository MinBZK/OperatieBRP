/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository;

import java.math.BigInteger;

import javax.inject.Inject;

import nl.bzk.brp.AbstractRepositoryTestCase;
import nl.bzk.brp.bevraging.domein.Rol;
import nl.bzk.brp.bevraging.domein.aut.AuthenticatieMiddel;
import org.junit.Assert;
import org.junit.Test;


/**
 * Testcases voor de {@link CertificaatRepository} class.
 */
public class CertificaatRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private CertificaatRepository certificaatRepository;

    @Test
    public void testZoekAuthenticatieMiddelOpBasisvanCertificaatMetRol() {
        testValideAuthenticatieMiddel("test1", BigInteger.valueOf(1), "test1", Rol.AFNEMER, Long.valueOf(1));
        testValideAuthenticatieMiddel("test2", BigInteger.valueOf(2), "test2", Rol.BEVOEGDHEIDSTOEDELER, Long.valueOf(2));
        testValideAuthenticatieMiddel("test3", BigInteger.valueOf(3), "test3", Rol.AFNEMER, Long.valueOf(3));
        testValideAuthenticatieMiddel("test3", BigInteger.valueOf(3), "test3", Rol.BEVOEGDHEIDSTOEDELER, Long.valueOf(3));

        testInvalideAuthenticatieMiddel("test1", BigInteger.valueOf(1), "test1", Rol.BEVOEGDHEIDSTOEDELER);
        testInvalideAuthenticatieMiddel("test2", BigInteger.valueOf(2), "test2", Rol.AFNEMER);

        testInvalideAuthenticatieMiddel("test2", BigInteger.valueOf(1), "test1", Rol.AFNEMER);
        testInvalideAuthenticatieMiddel("test1", BigInteger.valueOf(2), "test1", Rol.AFNEMER);
        testInvalideAuthenticatieMiddel("test1", BigInteger.valueOf(1), "test2", Rol.AFNEMER);
    }

    private void testValideAuthenticatieMiddel(final String subject, final BigInteger serial, final String signature,
            final Rol rol, final Long expectedId)
    {
        AuthenticatieMiddel authenticatieMiddel =
            certificaatRepository.zoekAuthenticatieMiddelOpBasisvanCertificaat(serial, subject, signature, rol);
        Assert.assertNotNull(authenticatieMiddel);
        Assert.assertEquals(expectedId, authenticatieMiddel.getId());
    }

    private void testInvalideAuthenticatieMiddel(final String subject, final BigInteger serial, final String signature,
            final Rol rol)
    {
        AuthenticatieMiddel authenticatieMiddel =
            certificaatRepository.zoekAuthenticatieMiddelOpBasisvanCertificaat(serial, subject, signature, rol);
        Assert.assertNull(authenticatieMiddel);
    }
}
