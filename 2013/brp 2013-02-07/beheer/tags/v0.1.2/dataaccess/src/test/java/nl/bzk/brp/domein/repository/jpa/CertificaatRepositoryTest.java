/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domein.repository.jpa;

import javax.inject.Inject;

import nl.bzk.brp.domein.autaut.Authenticatiemiddel;
import nl.bzk.brp.domein.kern.Rol;
import nl.bzk.brp.domein.repository.CertificaatRepository;

import org.junit.Assert;
import org.junit.Test;


/**
 * Testcases voor de {@link CertificaatRepository} class.
 */
public class CertificaatRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private CertificaatRepository certificaatRepository;

    @Test
    public void testZoekAuthenticatiemiddelOpBasisvanCertificaatMetRol() {
        testValideAuthenticatiemiddel("test1", Long.valueOf(1), "test1", Rol.AFNEMER, Long.valueOf(1));
        testValideAuthenticatiemiddel("test2", Long.valueOf(2), "test2", Rol.BEVOEGDHEIDSTOEDELER, Long.valueOf(2));
        testValideAuthenticatiemiddel("test3", Long.valueOf(3), "test3", Rol.AFNEMER, Long.valueOf(3));
        testValideAuthenticatiemiddel("test3", Long.valueOf(3), "test3", Rol.BEVOEGDHEIDSTOEDELER, Long.valueOf(3));

        testInvalideAuthenticatiemiddel("test1", Long.valueOf(1), "test1", Rol.BEVOEGDHEIDSTOEDELER);
        testInvalideAuthenticatiemiddel("test2", Long.valueOf(2), "test2", Rol.AFNEMER);

        testInvalideAuthenticatiemiddel("test2", Long.valueOf(1), "test1", Rol.AFNEMER);
        testInvalideAuthenticatiemiddel("test1", Long.valueOf(2), "test1", Rol.AFNEMER);
        testInvalideAuthenticatiemiddel("test1", Long.valueOf(1), "test2", Rol.AFNEMER);
    }

    private void testInvalideAuthenticatiemiddel(final String subject, final Long serial, final String signature,
            final Rol rol)
    {
        Authenticatiemiddel authenticatieMiddel =
            certificaatRepository.zoekAuthenticatiemiddelOpBasisvanCertificaat(serial, subject, signature, rol);
        Assert.assertNull(authenticatieMiddel);
    }

    private void testValideAuthenticatiemiddel(final String subject, final Long serial, final String signature,
            final Rol rol, final Long expectedId)
    {
        Authenticatiemiddel authenticatieMiddel =
            certificaatRepository.zoekAuthenticatiemiddelOpBasisvanCertificaat(serial, subject, signature, rol);
        Assert.assertNotNull(authenticatieMiddel);
        Assert.assertEquals(expectedId, authenticatieMiddel.getID());
    }
}
