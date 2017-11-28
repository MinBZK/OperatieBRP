/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.stream;

import java.io.IOException;

/**
 * Invalid message.
 */
public final class MessageException extends IOException {

    private static final long serialVersionUID = 1L;

    /**
     * Invalid message.
     * @param e root cause
     */
    public MessageException(final IOException e) {
        super(e);
    }
}
