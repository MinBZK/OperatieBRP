/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels;

import java.util.List;

import nl.bzk.brp.model.gedeeld.SoortActie;

/**
 * De bedrijfsregelmanager bepaalt welke bedrijfsregels uitgevoerd dienen te worden voor een bepaalde soort actie.
 */
public interface BedrijfsRegelManager {

    /**
     * Bepaalt aan de hand van de soortActie parameter welke bedrijfsregels uitgevoerd dienen te worden.
     *
     * @param soortActie De soort actie waarvoor bedrijfsregels dienen worden uitgevoerd.
     * @return Lijst van uit te voeren bedrijfsregels.
     */
    List<? extends BedrijfsRegel> getUitTeVoerenBedrijfsRegels(final SoortActie soortActie);
}
