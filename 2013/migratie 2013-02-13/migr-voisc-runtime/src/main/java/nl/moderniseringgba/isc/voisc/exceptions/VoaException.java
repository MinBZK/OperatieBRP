/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.exceptions;

/**
 * exception used by the VOSPGService and VOSPGController.
 * 
 */
public class VoaException extends Exception {
    private static final long serialVersionUID = 1;

    /**
     * code to construct the message
     */
    private final String myCode;

    /**
     * keep code.
     * 
     * @param code
     *            String the key used for finding the corresponding exception message
     * @param parameters
     *            Object[] array of Objects that will be put in the corresponding message can be null
     */
    public VoaException(final String code, final Object[] parameters) {
        this(code, parameters, null);
    }

    /**
     * Protected Constructor to force the developer to create a specific subclass of SpgRuntimeException.
     * 
     * @param code
     *            String the key used for finding the corresponding exception message
     * @param parameters
     *            Object[] array of Objects that will be put in the corresponding message, can be null
     * @param cause
     *            Throwable the original cause can be null
     */
    public VoaException(final String code, final Object[] parameters, final Throwable cause) {
        super(MessageUtil.composeMessage(code, parameters, cause), cause);
        myCode = code;
    }

    /**
     * getter for code.
     * 
     * @return code the code for this exception
     */
    public final String getCode() {
        return myCode;
    }

}
