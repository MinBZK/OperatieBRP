/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.message;

/**
 * Response to a {@link Request}.
 *
 * The response contains an exception if the request could not be handled
 * successfully. If the request was handled successfully the return of the
 * method invocation (null for void methods) can be retrieved from the result.
 */
public final class Response implements Message {

    private static final long serialVersionUID = 1L;

    private final String requestId;
    private final Object result;
    private final Exception exception;

    /**
     * Create a new response.
     * @param requestId unique request id for which this response is the result
     * @param result successful invocation result
     */
    public Response(final String requestId, final Object result) {
        this.requestId = requestId;
        this.result = result;
        exception = null;
    }

    /**
     * Create a new response.
     * @param requestId unique request id for which this response is the result
     * @param exception invocation exception
     */
    public Response(final String requestId, final Exception exception) {
        this.requestId = requestId;
        result = null;
        this.exception = exception;
    }

    public String getRequestId() {
        return requestId;
    }

    public Object getResult() {
        return result;
    }

    public Exception getException() {
        return exception;
    }

    @Override
    public String toString() {
        return "Response [requestId=" + requestId + ", result=" + (result == null ? "null" : "not null")
                + ", exception=" + (exception == null ? "null" : "not null") + "]";
    }
}
