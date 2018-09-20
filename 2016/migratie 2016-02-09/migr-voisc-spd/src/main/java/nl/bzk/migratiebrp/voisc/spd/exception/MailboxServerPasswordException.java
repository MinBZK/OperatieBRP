/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd.exception;

/**
 * This exception is thrown when in incorrect MailboxServerPassword is given during a Logon or ChangePassword operation
 * with the MailboxServer.
 * 
 * 
 */
public class MailboxServerPasswordException extends VoaException {

    private static final long serialVersionUID = -7392954526234397964L;

    /**
     * Default constructor.
     * 
     * @param code
     *            String with the number of the error message defined in [messages.properties]
     * @see nl.ictu.spg.common.messages.IMessagesCodes
     */
    public MailboxServerPasswordException(final String code) {
        super(code, null, null);
    }

    /**
     * With this constructor it's possible to give an array of specific values to the generic error message.
     * 
     * @param code
     *            String
     * @param parameter
     *            Object[]
     */
    public MailboxServerPasswordException(final String code, final Object[] parameter) {
        super(code, parameter);
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
    public MailboxServerPasswordException(final String code, final Object[] parameter, final Throwable cause) {
        super(code, parameter, cause);
    }
}
