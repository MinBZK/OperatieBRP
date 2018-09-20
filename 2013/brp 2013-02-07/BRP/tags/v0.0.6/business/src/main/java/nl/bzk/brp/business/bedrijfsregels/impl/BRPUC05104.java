/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl;

import nl.bzk.brp.binding.Melding;
import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;

/**
 * Implementatie van bedrijfsregel BRPUC05104.
 */
public class BRPUC05104 implements BedrijfsRegel<PersistentPersoon, Persoon> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCode() {
        return "BRPUC05104";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Melding executeer(final PersistentPersoon huidigeSituatie, final Persoon nieuweSituatie) {
        return null;
    }
}
