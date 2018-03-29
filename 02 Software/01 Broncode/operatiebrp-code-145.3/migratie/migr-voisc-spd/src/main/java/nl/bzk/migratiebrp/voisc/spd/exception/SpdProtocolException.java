/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd.exception;

import nl.bzk.migratiebrp.voisc.database.entities.Bericht;

/**
 * This exception is used when errors occurred during parsing of sPd-messages from the MailboxServer. When an error
 * occurred during parsing an sPd-GetMessageResult, the current state of the LO3Bericht which is already parsed is given
 * to this exception. The calling method can catch this exception and retreive the LO3Bericht object from this exception
 * with getBericht().
 */
public class SpdProtocolException extends VoaException {
    /**
     *
     */
    private static final long serialVersionUID = 8957657627497773820L;
    private final Bericht bericht;

    /**
     * Default constructor.
     * @param code MessagesCodes with the number of the error message defined in [messages.properties]
     * @see "nl.ictu.spg.common.messages.IMessagesCodes"
     */
    public SpdProtocolException(final MessagesCodes code) {
        super(code.getErrorCode(), null, null);
        bericht = null;
    }

    /**
     * Constructor.
     * @param code MessagesCodes with the number of the error message defined in [messages.properties]
     * @param cause Throwable the original cause can be null
     * @see "nl.ictu.spg.common.messages.IMessagesCodes"
     */
    public SpdProtocolException(final MessagesCodes code, final Throwable cause) {
        super(code.getErrorCode(), null, cause);
        bericht = null;
    }

    /**
     * Special Constructor for MailboxServerProxy.ReceiveBericht().
     * @param code foutmelding code
     * @param bericht bericht wat verstuurd/ontvangen is in de voa
     */
    public SpdProtocolException(final MessagesCodes code, final Bericht bericht) {
        super(code.getErrorCode(), null, null);
        this.bericht = bericht;
    }

    /**
     * With this constructor it's possible to give an array of specific values to the generic error message.
     * @param code MessagesCodes
     * @param parameter Object[]
     */
    public SpdProtocolException(final MessagesCodes code, final Object[] parameter) {
        super(code.getErrorCode(), parameter);
        bericht = null;
    }

    /**
     * Special Constructor for MailboxServerProxy.ReceiveBericht().
     * @param code foutmelding code
     * @param parameter Object[] array of Objects that will be put in the corresponding message, can be null
     * @param bericht bericht wat verstuurd/ontvangen is in de voa
     */
    public SpdProtocolException(final MessagesCodes code, final Object[] parameter, final Bericht bericht) {
        super(code.getErrorCode(), parameter);
        this.bericht = bericht;
    }

    /**
     * Protected Constructor to force the developer to create a specific subclass of SpgRuntimeException.
     * @param code String the key used for finding the corresponding exception message
     * @param parameters Object[] array of Objects that will be put in the corresponding message, can be null
     * @param cause Throwable the original cause can be null
     */
    public SpdProtocolException(final MessagesCodes code, final Object[] parameters, final Throwable cause) {
        super(code.getErrorCode(), parameters, cause);
        bericht = null;
    }

    /**
     * Special Constructor for MailboxServerProxy.ReceiveBericht().
     * @param code foutmelding code
     * @param cause oorzaak van de fout
     * @param parameters Object[] array of Objects that will be put in the corresponding message, can be null
     * @param bericht bericht wat verstuurd/ontvangen is in de voa
     */
    public SpdProtocolException(final MessagesCodes code, final Object[] parameters, final Throwable cause, final Bericht bericht) {
        super(code.getErrorCode(), parameters, cause);
        this.bericht = bericht;
    }

    /**
     * Retreive a VoaBericht.
     * @return bericht
     */
    public final Bericht getBericht() {
        return bericht;
    }

}
