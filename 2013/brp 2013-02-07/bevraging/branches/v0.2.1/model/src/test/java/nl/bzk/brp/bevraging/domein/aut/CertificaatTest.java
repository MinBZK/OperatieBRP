/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.aut;

import static org.junit.Assert.assertArrayEquals;

import org.apache.commons.codec.DecoderException;
import org.junit.Test;


/**
 * Unit voor de {@link Certificaat} class.
 */
public class CertificaatTest {

    @Test
    public void testSignature() throws DecoderException {
        Certificaat certificaat = new Certificaat();

        byte[] bytes = { Byte.parseByte("127"), Byte.parseByte("0"), Byte.parseByte("-128"), Byte.parseByte("1"),
                Byte.parseByte("-1"), Byte.parseByte("100"), Byte.parseByte("-100"), Byte.parseByte("33"),
                Byte.parseByte("-34"), Byte.parseByte("2") };

        certificaat.setSignature(bytes);
        assertArrayEquals(bytes, certificaat.getSignature());
    }

}
