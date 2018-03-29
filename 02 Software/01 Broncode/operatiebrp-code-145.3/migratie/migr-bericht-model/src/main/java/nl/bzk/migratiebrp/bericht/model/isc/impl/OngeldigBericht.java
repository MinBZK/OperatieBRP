/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.isc.impl;

import java.util.Collections;
import nl.bzk.migratiebrp.bericht.model.GerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.impl.AbstractOngeldigBericht;
import nl.bzk.migratiebrp.bericht.model.isc.IscBericht;

/**
 * Bericht om aan te geven dat een bericht ongeldig is.
 */
public final class OngeldigBericht extends AbstractOngeldigBericht implements IscBericht {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor (gebruikt in ESB).
     * @param bericht bericht inhoud
     * @param melding melding
     */
    public OngeldigBericht(final String bericht, final String melding) {
        super(bericht, melding);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.Bericht#getGerelateerdeInformatie()
     */
    @Override
    public GerelateerdeInformatie getGerelateerdeInformatie() {
        return new GerelateerdeInformatie(null, Collections.<String>emptyList(), Collections.<String>emptyList());
    }
}
