/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.database.entities;

/**
 * Status.
 */
public enum StatusEnum {

    /**
     * Ontvangen van ISC.
     */
    RECEIVED_FROM_ISC,
    /**
     * Opgepakt om te versturen naar Mailbox.
     */
    SENDING_TO_MAILBOX,
    /**
     * Verstuurd naar mailbox.
     */
    SENT_TO_MAILBOX,
    /**
     * Ontvangen van mailbox.
     */
    RECEIVED_FROM_MAILBOX,
    /**
     * Opgepakt om te versturen naar ISC.
     */
    SENDING_TO_ISC,
    /**
     * Verstuurd naar ISC.
     */
    SENT_TO_ISC,
    /**
     * Genegeerd.
     */
    IGNORED,
    /**
     * Afgehandeld.
     */
    PROCESSED_IMMEDIATELY,
    /**
     * Foutief.
     */
    ERROR;
}
