/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.aut;

import java.net.InetAddress;
import java.net.UnknownHostException;

import junit.framework.Assert;
import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.SoortPartij;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Unit test voor de {@link AuthenticatieMiddel} class.
 */
public class AuthenticatieMiddelTest {

    /**
     * Test het ophalen van een {@code null} ipadres.
     */
    @Test
    public void testGetNullIpAdres() {
        AuthenticatieMiddel authenticatieMiddel = new AuthenticatieMiddel();
        ReflectionTestUtils.setField(authenticatieMiddel, "ipAdres", null);
        Assert.assertNull(authenticatieMiddel.getIpAdres());
    }

    /**
     * Test het ophalen van een geldig ipadres.
     */
    @Test
    public void testGetGeldigIpAdres() throws UnknownHostException {
        AuthenticatieMiddel authenticatieMiddel = new AuthenticatieMiddel();
        ReflectionTestUtils.setField(authenticatieMiddel, "ipAdres", "192.168.0.1");
        Assert.assertNotNull(authenticatieMiddel.getIpAdres());
        Assert.assertEquals(InetAddress.getByName("192.168.0.1"), authenticatieMiddel.getIpAdres());
    }

    /**
     * Test het ophalen van een ongeldig ipadres.
     */
    @Test
    public void testGetOnGeldigIpAdres() throws UnknownHostException {
        AuthenticatieMiddel authenticatieMiddel = new AuthenticatieMiddel();
        ReflectionTestUtils.setField(authenticatieMiddel, "ipAdres", "a");
        Assert.assertNull(authenticatieMiddel.getIpAdres());
    }

    @Test
    public void testSetIpAdres() throws UnknownHostException {
        InetAddress inetAdres = InetAddress.getLocalHost();
        AuthenticatieMiddel authenticatieMiddel = new AuthenticatieMiddel();

        authenticatieMiddel.setIpAdres(inetAdres);
        Assert.assertEquals(inetAdres.getHostAddress(), ReflectionTestUtils.getField(authenticatieMiddel, "ipAdres"));
    }

    @Test
    public void testConstructor() {
        Partij partij = new Partij(SoortPartij.DERDE);
        Certificaat certificaat = new Certificaat();
        AuthenticatieMiddel authenticatieMiddel = new AuthenticatieMiddel(partij, certificaat);
        Assert.assertSame(partij, authenticatieMiddel.getPartij());
        Assert.assertSame(certificaat, authenticatieMiddel.getCertificaatTbvOndertekening());
        Assert.assertNull(authenticatieMiddel.getCertificaatTbvSsl());
    }
}
