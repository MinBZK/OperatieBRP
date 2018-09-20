/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SHA1ChecksumBerekenaarTest {

    private SHA1ChecksumBerekenaar sha1ChecksumBerekenaar = new SHA1ChecksumBerekenaar();

    @Test
    public void testBerekenChecksum1() {
        final String testString = "test string1";

        final String resultaat = sha1ChecksumBerekenaar.berekenChecksum(testString.getBytes());

        assertEquals("3567ba6828093bdf2a25c425bc3b6c21f7bfdc53", resultaat);
    }

    @Test
    public void testBerekenChecksum2() {
        final String testString = "test string2";

        final String resultaat = sha1ChecksumBerekenaar.berekenChecksum(testString.getBytes());

        assertEquals("cca9e70637497434a43c76e2f64df9c1acfbc648", resultaat);
    }

    @Test
    public void testChecksumZonderGegevens() {
        assertEquals("", sha1ChecksumBerekenaar.berekenChecksum(null));
    }

}
