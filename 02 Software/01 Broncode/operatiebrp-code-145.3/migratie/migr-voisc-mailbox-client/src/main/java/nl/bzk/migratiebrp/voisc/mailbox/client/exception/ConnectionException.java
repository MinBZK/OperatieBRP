/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.mailbox.client.exception;

import nl.bzk.migratiebrp.voisc.spd.exception.MessagesCodes;
import nl.bzk.migratiebrp.voisc.spd.exception.VoaRuntimeException;

/**
 * This exception will be thrown when a global Connection exception occurs. (i.e. no Connection can be made, a KeyStore
 * is not found, it's not possible to read/write to/from the Socket etc.) <br>
 */
public class ConnectionException extends VoaRuntimeException {

    private static final long serialVersionUID = -5637722583063666609L;

    /**
     * With this constructor it's possible to give an array of specific values to the generic error message.
     * @param code MessagesCodes
     * @param parameter Object[]
     */
    public ConnectionException(final MessagesCodes code, final Object[] parameter) {
        super(code.getErrorCode(), parameter);
    }

    /**
     * This constructor makes it possible to pass the causing Exception as Trowable param.
     * @param code String the key used for finding the corresponding exception message
     * @param parameter Object[] array of Objects that will be put in the corresponding message, can be null
     * @param cause Throwable the original cause can be null
     */
    public ConnectionException(final MessagesCodes code, final Object[] parameter, final Throwable cause) {
        super(code.getErrorCode(), parameter, cause);
    }

}
