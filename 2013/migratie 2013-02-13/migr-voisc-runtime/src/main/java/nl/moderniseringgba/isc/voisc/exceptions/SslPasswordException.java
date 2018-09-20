/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.exceptions;

/**
 * This exception is thrown when an incorrect SSL-KeyStore password is given.
 */
public class SslPasswordException extends ConnectionException {

    private static final long serialVersionUID = 1010578380744013843L;

    /**
     * Default constructor.
     * 
     * @param code
     *            String with the number of the error message defined in [messages.properties]
     */
    public SslPasswordException(final String code) {
        this(code, null, null);
    }

    /**
     * This constructor makes it possible to pass the causing Exception as Trowable param.
     * 
     * @param code
     *            String the key used for finding the corresponding exception message
     * @param parameter
     *            Object[] array of Objects that will be put in the corresponding message, can be null
     * @param cause
     *            Throwable the original cause can be null
     */
    public SslPasswordException(final String code, final Object[] parameter, final Throwable cause) {
        super(code, parameter, cause);
    }
}
