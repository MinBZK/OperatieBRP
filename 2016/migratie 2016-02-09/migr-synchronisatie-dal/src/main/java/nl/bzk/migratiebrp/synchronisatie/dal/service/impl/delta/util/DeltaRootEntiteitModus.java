/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Betrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaRootEntiteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Relatie;

/**
 * Enumeratie met daarin de modi waarin de vergelijker of verwerker kan werken.
 */
public enum DeltaRootEntiteitModus {
    /**
     * Modus voor als de {@link DeltaRootEntiteit} een {@link Persoon} is.
     */
    PERSOON,
    /**
     * Modus voor als de {@link DeltaRootEntiteit} een {@link Relatie} is.
     */
    RELATIE,
    /**
     * Modus voor als de {@link DeltaRootEntiteit} een {@link Betrokkenheid} is.
     */
    BETROKKENHEID;

    /**
     * Bepaalt de modus adhv de meegegeven deltaRootEntiteit.
     * 
     * @param deltaRootEntiteit
     *            de entiteit die de modus bepaald
     * @return de modus die bij de betreffende entiteit hoort.
     */
    public static DeltaRootEntiteitModus bepaalModus(final DeltaRootEntiteit deltaRootEntiteit) {
        final DeltaRootEntiteitModus modus;
        if (deltaRootEntiteit instanceof Persoon) {
            modus = PERSOON;
        } else if (deltaRootEntiteit instanceof Relatie) {
            modus = RELATIE;
        } else if (deltaRootEntiteit instanceof Betrokkenheid) {
            modus = BETROKKENHEID;
        } else {
            throw new IllegalStateException("Onbekende delta root entiteit aangeboden. Kan geen modus aanwijzen");
        }

        return modus;
    }
}
