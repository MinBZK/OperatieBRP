/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.amlezer;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.spd.constants.SpdConstants;
import nl.bzk.migratiebrp.voisc.spd.impl.StringUtil;

/**
 * Berichten callback die een AM-bestand schrijft.
 */
public final class AMSchrijverBerichtCallback implements BerichtCallback {

    private static final DecimalFormat DF_LENGTH = new DecimalFormat("00000");

    private final Mailbox mailbox;
    private final AMSchrijver amSchrijver;

    /**
     * Constructor.
     * 
     * @param mailbox
     *            mailbox
     * @param directory
     *            output directory
     */
    public AMSchrijverBerichtCallback(final Mailbox mailbox, final File directory) {
        this.mailbox = mailbox;
        amSchrijver =
                new AMSchrijver(
                    new File(directory, "gba-mailbox-" + mailbox.getInstantietype() + "-" + mailbox.getInstantiecode() + ".dat"));
    }

    @Override
    public void start() throws BerichtCallbackException {
        try {
            amSchrijver.schrijfStartRecord(mailbox.getMailboxnr());
        } catch (final IOException e) {
            throw new BerichtCallbackException("Kan start record niet schrijven", e);
        }

    }

    @Override
    public void onBericht(final Bericht bericht) throws BerichtCallbackException {
        final String messageEnvelope =
                "00024" + "120" + bericht.getOriginator() + "2" + SpdConstants.PUTMESSAGE_PRIORITY_DEFAULT + "           " + "1";

        final String messageHeading =
                "00045"
                        + "150"
                        + StringUtil.fillBefore(bericht.getMessageId(), ' ', SpdConstants.MH_MESSAGE_ID_LEN)
                        + StringUtil.fillBefore(bericht.getCorrelationId(), ' ', SpdConstants.MH_CROSS_REFERENCE_LEN)
                        + bericht.getOriginator()
                        + "001"
                        + bericht.getRecipient()
                        + SpdConstants.PUTMESSAGE_NON_RECIPIENT_NOTIFICATION_REQUEST;

        final String berichtInhoud = bericht.getBerichtInhoud();
        final int berichtLength = berichtInhoud.length() + SpdConstants.OPCODE_LENGTH;
        final String messageBody = DF_LENGTH.format(berichtLength) + "180" + berichtInhoud;

        final String completeBericht = messageEnvelope + messageHeading + messageBody;
        try {
            amSchrijver.schrijfDataRecord(completeBericht);
        } catch (final IOException e) {
            throw new BerichtCallbackException("Kan data record niet schrijven", e);
        }
    }

    @Override
    public void end() throws BerichtCallbackException {
        try {
            amSchrijver.schrijfEndRecord();
        } catch (final IOException e) {
            throw new BerichtCallbackException("Kan sluit record niet schrijven", e);
        }
    }
}
