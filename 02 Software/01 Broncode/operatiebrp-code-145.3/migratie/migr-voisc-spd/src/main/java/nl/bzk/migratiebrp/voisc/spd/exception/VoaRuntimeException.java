/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd.exception;

/**
 * SpG runtime exceptie Deze excepties worden gebruikt om SpG excepties aan te duiden waarvan niet op voorhand vaststaat
 * op welke wijze deze kunnen ontstaan en verwerkt dienen te worden.
 */
public class VoaRuntimeException extends java.lang.RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 3618136732243998002L;

    /**
     * Protected Constructor to force the developer to create a specific subclass of SpgRuntimeException.
     * @param code String the key used for finding the corresponding exception message
     * @param parameters Object[] array of Objects that will be put in the corresponding message can be null
     */
    protected VoaRuntimeException(final String code, final Object[] parameters) {
        this(code, parameters, null);
    }

    /**
     * Protected Constructor to force the developer to create a specific subclass of SpgRuntimeException.
     * @param code String the key used for finding the corresponding exception message
     * @param parameters Object[] array of Objects that will be put in the corresponding message, can be null
     * @param cause Throwable the original cause can be null
     */
    public VoaRuntimeException(final String code, final Object[] parameters, final Throwable cause) {
        super(MessageUtil.composeMessage(code, parameters, cause), cause);
    }

    /**
     * Zet de waarde van cause.
     * @param cause Throwable
     */
    public final void setCause(final Throwable cause) {
        initCause(cause);
    }

}
