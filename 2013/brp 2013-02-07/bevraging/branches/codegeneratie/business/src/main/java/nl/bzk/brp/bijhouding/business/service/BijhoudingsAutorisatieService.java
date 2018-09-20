/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service;

import nl.bzk.brp.bijhouding.business.bijhoudingsautorisatie.BijhoudingsAutorisatieResultaat;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.SoortActie;
import nl.bzk.brp.domein.kern.SoortDocument;

/**
 * Service voor bijhoudings autorisatie.
 */
public interface BijhoudingsAutorisatieService {

    /**
     * Check of een bijhouding die gedaan wordt door bijhoudendePartij geautoriseerd is inclusief controle op de
     * bijhoudingssituatie.
     * De bijhoudingssituatie bepaalt wele documenten (bijv. akte) benodigd zijn om de bijhouding te kunnen doen.
     *
     * @param bijhoudendePartij Partij die de bijhouding wil doen.
     * @param bijhoudingsVerantwoordelijkeVooraf De partij die momenteel bijhoudingsverantwoordelijke is over de persoon
     * waarop de bijhouding van toepassing is.
     * @param bijhoudingsVerantwoordelijkeAchteraf Partij die nadat de bijhouding gedaan is bijhoudingsverantwoordelijke
     * is.
     * @param soortActie Soort actie van de bijhouding.
     * @param soortDocument Soort document dat gebruikt wordt voor de bijhouding.
     * @return BijhoudingsAutorisatieResultaat
     */
    BijhoudingsAutorisatieResultaat bepaalBijhoudingsAutorisatie(
            final Partij bijhoudendePartij,
            final Partij bijhoudingsVerantwoordelijkeVooraf,
            final Partij bijhoudingsVerantwoordelijkeAchteraf,
            final SoortActie soortActie,
            final SoortDocument soortDocument);
}
