/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.service.bericht;

import nl.bzk.migratiebrp.bericht.model.sync.generated.TypeSynchronisatieBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;

/**
 * Houder voor LO3 bericht text en A-Nummer.
 */
public final class SyncNaarBrpBericht {
    private final String aNummer;
    private final String bericht;

    /**
     * constructor.
     * @param aNummer anummer
     * @param bericht bericht
     * @param berichttype bericht type (Lg01 of La01)
     * @throws IllegalArgumentException als berichttype niet Lg01 of La01 bevat
     */
    public SyncNaarBrpBericht(final String aNummer, final String bericht, final String berichttype) {
        this.aNummer = aNummer;
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerBericht = new SynchroniseerNaarBrpVerzoekBericht(bericht);
        if ("Lg01".equalsIgnoreCase(berichttype)) {
            synchroniseerBericht.setTypeBericht(TypeSynchronisatieBericht.LG_01);
        } else if ("La01".equalsIgnoreCase(berichttype)) {
            synchroniseerBericht.setTypeBericht(TypeSynchronisatieBericht.LA_01);
        } else {
            throw new IllegalArgumentException("Onbekend berichttype '" + berichttype + "'.");
        }
        this.bericht = synchroniseerBericht.format();
    }

    /**
     * Geef de waarde van bericht.
     * @return bericht
     */
    public String getBericht() {
        return bericht;
    }

    /**
     * Geef de waarde van message id.
     * @return messageId -> anummer
     */
    public String getMessageId() {
        return String.valueOf(aNummer);
    }

    /**
     * Geef de waarde van a nummer.
     * @return anummer
     */
    public String getANummer() {
        return aNummer;
    }
}
