/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels;

import javax.inject.Inject;
import javax.inject.Named;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.logisch.kern.Persoon;

/**
 * Definitie van een ingezetene.
 *
 * Een Ingezetene is een Ingeschrevene (BRAL0107: Persoon is Ingeschrevene) waarbij
 * het attribuut Bijhoudingsaard in de groep Bijhoudingsaard naar de waarde "Ingezetene" verwijst.
 */
@Named("BRAL0106")
public class BRAL0106 {

    @Inject
    private BRAL0107 bral0107;

    /**
     * Bepaalt of de meegegeven persoon een ingezetene is of niet.
     *
     * @param persoon de persoon
     * @return true voor ingezetene, anders false
     */
    public boolean isIngezetene(final Persoon persoon) {
        return bral0107.isIngeschrevene(persoon) && persoon.getBijhouding() != null
                && Bijhoudingsaard.INGEZETENE.equals(persoon.getBijhouding().getBijhoudingsaard().getWaarde());
    }

}
