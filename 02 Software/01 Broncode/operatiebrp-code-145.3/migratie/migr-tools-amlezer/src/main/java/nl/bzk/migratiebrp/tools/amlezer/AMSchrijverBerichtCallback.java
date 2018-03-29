/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.amlezer;

import java.io.File;
import java.io.IOException;

import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.spd.Operations;
import nl.bzk.migratiebrp.voisc.spd.PutMessageOperation;

/**
 * Berichten callback die een AM-bestand schrijft.
 */
public final class AMSchrijverBerichtCallback implements BerichtCallback {

    private final Mailbox mailbox;
    private final AMSchrijver amSchrijver;

    /**
     * Constructor.
     * @param mailbox mailbox
     * @param directory output directory
     */
    public AMSchrijverBerichtCallback(final Mailbox mailbox, final File directory) {
        this.mailbox = mailbox;
        amSchrijver = new AMSchrijver(new File(directory, "gba-mailbox-" + mailbox.getPartijcode() + ".dat"));
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
        final PutMessageOperation operation = Operations.fromBericht(bericht);
        final String completeBericht = operation.envelope().toSpdString() + operation.heading().toSpdString() + operation.body().toSpdString();
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
