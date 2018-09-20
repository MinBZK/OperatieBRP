/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import nl.bzk.brp.business.dto.bevraging.VraagDetailsPersoonBericht;
import nl.bzk.brp.business.dto.bevraging.VraagKandidaatVaderBericht;
import nl.bzk.brp.business.dto.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenBericht;
import nl.bzk.brp.web.bevraging.VraagDetailsPersoonAntwoordBericht;
import nl.bzk.brp.web.bevraging.VraagKandidaatVaderAntwoordBericht;
import nl.bzk.brp.web.bevraging.VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBericht;


/** De Service Endpoint Interface voor bevragingen. */
public interface BevragingService {

    /**
     * Dit is de webservice method voor het ophalen van persoon details.
     *
     * @param vraagDetailsPersoonBericht Het bevragingsbericht voor het ophalen van de persoon
     * @return het resultaat van de bevraging
     */
    VraagDetailsPersoonAntwoordBericht opvragenDetailPersoon(final VraagDetailsPersoonBericht vraagDetailsPersoonBericht);

    /**
     * Dit is de webservice methode voor het opvragen van de personen die op hetzelfde adres wonen en hun
     * onderlinge relaties.
     *
     * @param vraagPersonenOpAdresInclusiefBetrokkenhedenBericht de vraag
     * @return het resultaat van de bevraging
     */
    VraagPersonenOpAdresInclusiefBetrokkenhedenAntwoordBericht opvragenPersonenOpAdresInclusiefBetrokkenheden(
            final VraagPersonenOpAdresInclusiefBetrokkenhedenBericht
            vraagPersonenOpAdresInclusiefBetrokkenhedenBericht);

    /**
     * Dit is de webservice methode voor het opvragen van personen die een kandidaat vader zou kunnen zijn.
     * @param vraagKandidaatVaderBericht de vraag
     * @return het resultaat van de bevraging
     */
    VraagKandidaatVaderAntwoordBericht opvragenKandidaatVader(final VraagKandidaatVaderBericht vraagKandidaatVaderBericht);
}
