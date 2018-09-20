/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import nl.bzk.brp.model.bevraging.bijhouding.BepaalKandidaatVaderAntwoordBericht;
import nl.bzk.brp.model.bevraging.bijhouding.BepaalKandidaatVaderBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefDetailsPersoonAntwoordBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefDetailsPersoonBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefPersonenOpAdresMetBetrokkenhedenAntwoordBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefPersonenOpAdresMetBetrokkenhedenBericht;


/** De Service Endpoint Interface voor bevragingen. */
public interface BijhoudingBevragingService {

    /**
     * Dit is de webservice method voor het ophalen van persoon details.
     *
     * @param vraagDetailsPersoonBericht Het bevragingsbericht voor het ophalen van de persoon
     * @return het resultaat van de bevraging
     */
    GeefDetailsPersoonAntwoordBericht geefDetailsPersoon(
        final GeefDetailsPersoonBericht vraagDetailsPersoonBericht);

    /**
     * Dit is de webservice methode voor het opvragen van de personen die op hetzelfde adres wonen en hun
     * onderlinge relaties.
     *
     * @param vraagPersonenOpAdresMetBetrokkenhedenBericht de vraag
     * @return het resultaat van de bevraging
     */
    GeefPersonenOpAdresMetBetrokkenhedenAntwoordBericht geefPersonenOpAdresMetBetrokkenheden(
        final GeefPersonenOpAdresMetBetrokkenhedenBericht vraagPersonenOpAdresMetBetrokkenhedenBericht);

    /**
     * Dit is de webservice methode voor het opvragen van personen die een kandidaat vader zou kunnen zijn.
     *
     * @param vraagKandidaatVaderBericht de vraag
     * @return het resultaat van de bevraging
     */
    BepaalKandidaatVaderAntwoordBericht bepaalKandidaatVader(
        final BepaalKandidaatVaderBericht vraagKandidaatVaderBericht);
}
