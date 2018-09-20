/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels;

import javax.inject.Named;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.logisch.kern.Persoon;

/**
 * Definitie van een ingeschrevene.
 *
 * Een Ingeschrevene is een Persoon waarbij het attribuut Soort
 * in de groep Identiteit naar de waarde "Ingeschrevene" verwijst.
 */
@Named("BRAL0107")
public class BRAL0107 {

    /**
     * Bepaalt of de meegegeven persoon een ingeschrevene is of niet.
     *
     * @param persoon de persoon
     * @return true voor ingeschrevene, anders false
     */
    public final boolean isIngeschrevene(final Persoon persoon) {
        return persoon.getSoort() != null && SoortPersoon.INGESCHREVENE.equals(persoon.getSoort().getWaarde());
    }

}
