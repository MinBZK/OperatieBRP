/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.isc;

import java.io.Serializable;
import nl.bzk.migratiebrp.bericht.model.AbstractBericht;

/**
 * Basis Isc bericht.
 */
public abstract class AbstractIscBericht extends AbstractBericht implements IscBericht, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public final String getStartCyclus() {
        return null;
    }
}
