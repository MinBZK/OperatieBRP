/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.message;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Base request.
 */
public class Request implements Message {

    private static final long serialVersionUID = 1L;
    private static final AtomicLong REQUEST_ID = new AtomicLong(1);

    private final String requestId = Long.toHexString(REQUEST_ID.getAndIncrement());

    /**
     * Create a new request.
     */
    Request() {
    }

    /**
     * The unique request id a response can be correlated on.
     * @return unique request id.
     */
    public final String getRequestId() {
        return requestId;
    }

}
