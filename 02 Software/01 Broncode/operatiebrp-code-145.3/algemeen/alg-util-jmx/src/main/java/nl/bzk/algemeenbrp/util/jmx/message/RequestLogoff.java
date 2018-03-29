/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.message;

/**
 * Request sent before disconnecting; requests the server to shutdown all notifications.
 */
public final class RequestLogoff extends Request {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "RequestLogoff [requestId=" + getRequestId() + "]";
    }

}
