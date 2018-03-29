/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model;

/**
 * Constanten voor communicatie over JMS queues.
 */
public final class JMSConstants {

    /**
     * JMS property waarop de bericht referentie wordt gezet.
     */
    public static final String BERICHT_REFERENTIE = "iscBerichtReferentie";

    /**
     * JMS property waarop de correlatie referentie wordt gezet.
     */
    public static final String CORRELATIE_REFERENTIE = "iscCorrelatieReferentie";

    /**
     * JMS property waarop de originator wordt gezet.
     */
    public static final String BERICHT_ORIGINATOR = "voaOriginator";

    /**
     * JMS property waarop het MSSequenceNumber wordt gezet.
     */
    public static final String BERICHT_MS_SEQUENCE_NUMBER = "voaMsSequenceNumber";

    /**
     * JMS property waarop de administratieve handeling id wordt gezet.
     */
    public static final String BERICHT_ADMINISTRATIEVE_HANDELING_ID = "administratieveHandelingId";

    /**
     * JMS property waarop de recipient wordt gezet.
     */
    public static final String BERICHT_RECIPIENT = "voaRecipient";

    /**
     * JMS property waarop de notification request wordt gezet.
     */
    public static final String REQUEST_NON_RECEIPT_NOTIFICATION = "voaRequestNonReceiptNotification";

    private JMSConstants() {
        throw new AssertionError("Niet instantieerbaar");
    }

}
