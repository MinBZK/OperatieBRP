/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker;

import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAutorisatieBericht;

/**
 * Wrapper voor {@link Autorisatiebundel}. Behoudt de koppeling met {@link SelectieAutorisatieBericht}.
 */
public final class SelectieAutorisatiebundel {
    private Autorisatiebundel autorisatiebundel;
    private final SelectieAutorisatieBericht selectieAutorisatieBericht;

    /**
     * Constructor.
     * @param autorisatiebundel de autorisatiebundel
     * @param selectieAutorisatieBericht het selectie autorisatiebericht
     */
    SelectieAutorisatiebundel(final Autorisatiebundel autorisatiebundel,
                              final SelectieAutorisatieBericht selectieAutorisatieBericht) {

        this.autorisatiebundel = autorisatiebundel;
        this.selectieAutorisatieBericht = selectieAutorisatieBericht;
    }

    Autorisatiebundel getAutorisatiebundel() {
        return autorisatiebundel;
    }

    SelectieAutorisatieBericht getSelectieAutorisatieBericht() {
        return selectieAutorisatieBericht;
    }
}
