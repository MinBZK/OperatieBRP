/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.service.bericht;

import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;

/**
 * Houder voor LO3 bericht text en A-Nummer.
 */
public final class SyncNaarBrpBericht {
    private final long aNummer;
    private final String bericht;

    /**
     * constructor.
     * 
     * @param aNummer
     *            anummer
     * @param bericht
     *            bericht
     */
    public SyncNaarBrpBericht(final long aNummer, final String bericht) {
        this.aNummer = aNummer;
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerBericht = new SynchroniseerNaarBrpVerzoekBericht(bericht);
        synchroniseerBericht.setOpnemenAlsNieuwePl(true);
        this.bericht = synchroniseerBericht.format();
    }

    /**
     * Geef de waarde van bericht.
     *
     * @return bericht
     */
    public String getBericht() {
        return bericht;
    }

    /**
     * Geef de waarde van message id.
     *
     * @return messageId -> anummer
     */
    public String getMessageId() {
        return String.valueOf(aNummer);
    }

    /**
     * Geef de waarde van a nummer.
     *
     * @return anummer
     */
    public Long getANummer() {
        return aNummer;
    }
}
