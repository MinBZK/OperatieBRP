/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.mailbox;

/**
 * Spd operaties.
 */
public enum SpdOperations {

    /**
     * Logon.
     */
    LOGON,
    /**
     * Logoff.
     */
    LOGOFF,
    /**
     * List messages.
     */
    LIST_MESGS,
    /**
     * Get message.
     */
    GET_MESG,
    /**
     * Put message.
     */
    PUT_MESG,
    /**
     * Change password.
     */
    CHG_PWD,
    /**
     * No-op(eration).
     */
    NO_OP
}
