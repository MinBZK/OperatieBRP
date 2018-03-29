/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.message;

/**
 * Logon request.
 */
public final class RequestLogon extends Request {

    private static final long serialVersionUID = 1L;

    private final Object credentials;

    /**
     * Create a new logon request.
     * @param credentials credentials
     */
    public RequestLogon(final Object credentials) {
        this.credentials = credentials;
    }

    public Object getCredentials() {
        return credentials;
    }

    @Override
    public String toString() {
        return "RequestLogon [requestId=" + getRequestId() + ", credentials=" + (credentials == null ? "***not supplied***" : "***supplied***") + "]";
    }

}
