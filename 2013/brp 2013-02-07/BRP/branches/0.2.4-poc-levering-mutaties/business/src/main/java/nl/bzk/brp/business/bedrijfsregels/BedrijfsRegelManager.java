/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels;

import java.util.List;

import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;


/**
 * De bedrijfsregelmanager bepaalt welke bedrijfsregels uitgevoerd dienen te worden voor een bepaalde soort actie of
 * type bericht.
 */
public interface BedrijfsRegelManager {

    /**
     * Bepaalt aan de hand van de soortActie parameter welke bedrijfsregels uitgevoerd dienen te worden voor een
     * dergelijke actie.
     *
     * @param soortActie De soort actie waarvoor bedrijfsregels dienen worden uitgevoerd.
     * @return Lijst van uit te voeren (actie) bedrijfsregels.
     */
    List<? extends ActieBedrijfsRegel> getUitTeVoerenActieBedrijfsRegels(final SoortActie soortActie);

    /**
     * Bepaalt aan de hand van de bericht klasse welke bedrijfsregels uitgevoerd dienen te worden voor dat type
     * bericht.
     *
     * @param berichtClass de klasse van het bericht waarvoor bedrijfsregels dienen worden uitgevoerd.
     * @return Lijst van uit te voeren (bericht) bedrijfsregels.
     */
    List<? extends BerichtBedrijfsRegel> getUitTeVoerenBerichtBedrijfsRegels(
            final Class<? extends AbstractBijhoudingsBericht> berichtClass);

    /**
     * Bepaalt aan de hand van de bericht klasse welke bedrijfsregels uitgevoerd dienen te worden voor de context.
     *
     * @param berichtClass de klasse van het bericht waarvoor bedrijfsregels dienen worden uitgevoerd.
     * @return Lijst van uit te voeren (bericht) bedrijfsregels.
     */
    List<? extends BerichtContextBedrijfsRegel> getUitTeVoerenBerichtContextBedrijfsRegels(
            final Class<? extends AbstractBijhoudingsBericht> berichtClass);
}
