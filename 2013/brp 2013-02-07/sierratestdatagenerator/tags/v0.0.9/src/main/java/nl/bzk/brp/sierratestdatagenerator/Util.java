/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.sierratestdatagenerator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Util {

    private static final int IO_BUFFER_SIZE = 4 * 1024;

    public static void copy(final InputStream in, final OutputStream out) throws IOException {
	final byte[] b = new byte[IO_BUFFER_SIZE];
	int read;
	while ((read = in.read(b)) != -1) {
	    out.write(b, 0, read);
	}

	out.flush();
    }
}
