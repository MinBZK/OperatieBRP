/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces;

import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;

/**
 * Interface voor het bepalen en verwerken van de delta op persoon.
 */
public interface DeltaProces {

    /**
     * Bepaalt de verschillen tussen oude en bestaande versie van bijvoorbeeld personen.
     * @param context de context voor de deltabepaling en relateer processen
     */
    void bepaalVerschillen(final DeltaBepalingContext context);

    /**
     * Verwerken van de gevonden verschillen.
     * @param context administratieve handeling cache met daarin administratie handelingen per soort.
     */
    void verwerkVerschillen(final DeltaBepalingContext context);
}
