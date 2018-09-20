/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository;

import javax.inject.Inject;

import nl.bzk.brp.AbstractRepositoryTestCase;
import nl.bzk.brp.domein.autaut.Authenticatiemiddel;
import nl.bzk.brp.domein.kern.Rol;

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
        testValideAuthenticatieMiddel("test1", Long.valueOf(1), "test1", Rol.AFNEMER, Integer.valueOf(1));
        testValideAuthenticatieMiddel("test2", Long.valueOf(2), "test2", Rol.BEVOEGDHEIDSTOEDELER, Integer.valueOf(2));
        testValideAuthenticatieMiddel("test3", Long.valueOf(3), "test3", Rol.AFNEMER, Integer.valueOf(3));
        testValideAuthenticatieMiddel("test3", Long.valueOf(3), "test3", Rol.BEVOEGDHEIDSTOEDELER, Integer.valueOf(3));

        testInvalideAuthenticatieMiddel("test1", Long.valueOf(1), "test1", Rol.BEVOEGDHEIDSTOEDELER);
        testInvalideAuthenticatieMiddel("test2", Long.valueOf(2), "test2", Rol.AFNEMER);

        testInvalideAuthenticatieMiddel("test2", Long.valueOf(1), "test1", Rol.AFNEMER);
        testInvalideAuthenticatieMiddel("test1", Long.valueOf(2), "test1", Rol.AFNEMER);
        testInvalideAuthenticatieMiddel("test1", Long.valueOf(1), "test2", Rol.AFNEMER);
    }

    private void testValideAuthenticatieMiddel(final String subject, final Long serial, final String signature,
            final Rol rol, final Integer expectedId)
    {
        Authenticatiemiddel authenticatieMiddel =
            certificaatRepository.zoekAuthenticatieMiddelOpBasisvanCertificaat(serial, subject, signature, rol);
        Assert.assertNotNull(authenticatieMiddel);
        Assert.assertEquals(expectedId, authenticatieMiddel.getID());
    }

    private void testInvalideAuthenticatieMiddel(final String subject, final Long serial, final String signature,
            final Rol rol)
    {
        Authenticatiemiddel authenticatieMiddel =
            certificaatRepository.zoekAuthenticatieMiddelOpBasisvanCertificaat(serial, subject, signature, rol);
        Assert.assertNull(authenticatieMiddel);
    }
}
