/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync;

import java.util.Collections;
import nl.bzk.migratiebrp.bericht.model.GerelateerdeInformatie;

/**
 * Abstract sync bericht.
 */
public abstract class AbstractSyncBerichtZonderGerelateerdeInformatie extends AbstractSyncBericht {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     *
     * @param berichtType
     *            berichtType
     */
    public AbstractSyncBerichtZonderGerelateerdeInformatie(final String berichtType) {
        super(berichtType);
    }

    @Override
    public final GerelateerdeInformatie getGerelateerdeInformatie() {
        return new GerelateerdeInformatie(null, Collections.<String>emptyList(), Collections.<String>emptyList());
    }

}
