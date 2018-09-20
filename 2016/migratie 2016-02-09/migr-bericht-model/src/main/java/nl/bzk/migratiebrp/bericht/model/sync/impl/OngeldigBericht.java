/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.GerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.impl.AbstractOngeldigBericht;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;

/**
 * Bericht om aan te geven dat een bericht ongeldig is.
 */
public final class OngeldigBericht extends AbstractOngeldigBericht implements SyncBericht {
    private static final long serialVersionUID = 1L;

    // private static final ToStringStyle TO_STRING_STYLE = new OverridenClassToStringStyle("Ongeldig(Sync)Bericht");

    /**
     * Constructor (gebruikt in ESB).
     *
     * @param bericht
     *            bericht inhoud
     * @param melding
     *            melding
     */
    public OngeldigBericht(final String bericht, final String melding) {
        super(bericht, melding);
    }

    @Override
    public GerelateerdeInformatie getGerelateerdeInformatie() {
        return new GerelateerdeInformatie(null, null, null);
    }
}
