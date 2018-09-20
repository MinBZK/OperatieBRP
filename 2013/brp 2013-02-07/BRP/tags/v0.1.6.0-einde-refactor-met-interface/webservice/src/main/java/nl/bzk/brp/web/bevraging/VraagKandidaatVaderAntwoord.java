/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.bevraging;

import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;


/**
 * Het antwoord bericht voor bevragingen van betrokkenheden op een adres.
 *
 */
public class VraagKandidaatVaderAntwoord extends BevragingAntwoordBericht {

    /**
     * Standaard constructor die op basis van het opgegeven berichtresultaat uit de business laag de juiste waardes
     * bepaalt/zet in dit antwoord bericht.
     *
     * @param berichtResultaat het bericht resultaat uit de business laag op basis waarvan het antwoord bericht wordt
     *            gebouwd.
     */
    public VraagKandidaatVaderAntwoord(final OpvragenPersoonResultaat berichtResultaat) {
        super(berichtResultaat);
    }
}
