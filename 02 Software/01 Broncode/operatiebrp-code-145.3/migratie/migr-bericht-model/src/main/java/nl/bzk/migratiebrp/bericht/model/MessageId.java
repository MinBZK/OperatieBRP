/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model;

/**
 * Message Id.
 */
public final class MessageId {

    private static final String ID_COMPONENT_PADDING = "0000000000";
    private static final int ID_COMPONENT_RADIX = 36;
    private static final int ID_COMPONENT_MAX_LENGTH = 10;
    private static final String MESSAGE_ID_PREFIX = "MM";

    private MessageId() {
        // Niet instantieerbaar
    }

    /**
     * Bepaal messageId voor een intern bericht obv het database id.
     * @param id id
     * @return message id
     */
    public static String bepaalMessageId(final Long id) {
        String idComponent = Long.toString(id, ID_COMPONENT_RADIX);
        if (idComponent.length() > ID_COMPONENT_MAX_LENGTH) {
            // Too long
            throw new IllegalArgumentException("Message ID mag maximaal 12 lang zijn. Dit id (" + id + ") past niet om een message id te bepalen.");
        }
        if (idComponent.length() < ID_COMPONENT_MAX_LENGTH) {
            // Too short, pad
            idComponent = ID_COMPONENT_PADDING.substring(0, ID_COMPONENT_MAX_LENGTH - idComponent.length()) + idComponent;
        }

        return getMessageIdPrefix() + idComponent;
    }

    /**
     * Geef de waarde van message id prefix.
     * @return message id prefix
     */
    private static String getMessageIdPrefix() {
        // Placeholder omdat we hier waarschijnlijk iets van een omgevingsidentificatie willen opnemen
        // (OTAP + Servernummer oid)
        return MESSAGE_ID_PREFIX;
    }
}
