/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;

/**
 * Helper klasse die een checksum kan berekenen voor een byte array.
 */
public final class SHA1ChecksumBerekenaar implements ChecksumBerekenaar {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final int RADIX = 16;

    @Override
    public String berekenChecksum(final byte[] gegevens) {
        String result = "";
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA1");

            if (gegevens != null) {
                md.update(gegevens);
                result = new BigInteger(1, md.digest()).toString(RADIX);
            }
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Digest SHA1 bestaat niet");
        }

        return result;
    }

}
