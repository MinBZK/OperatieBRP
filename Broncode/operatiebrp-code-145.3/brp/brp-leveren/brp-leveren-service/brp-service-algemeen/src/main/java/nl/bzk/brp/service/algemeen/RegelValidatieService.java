/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import java.util.List;
import nl.bzk.brp.domain.algemeen.Melding;

/**
 * RegelValidatieService.
 */
public interface RegelValidatieService {

    /**
     * Verzamelt alle meldigen en combineert deze in één foutmelding.
     * @param regels de te valideren regels
     * @throws StapMeldingException als er een regelfout is
     */
    void valideer(Iterable<RegelValidatie> regels) throws StapMeldingException;

    /**
     * Gooit direct een exceptie bij de eerste melding.
     * @param regels de te valideren regels
     * @throws StapMeldingException als er een regelfout is
     */
    void valideerFailfast(Iterable<RegelValidatie> regels) throws StapMeldingException;

    /**
     * Valideert de regels zonder een Exceptie te gooien.
     * @param regels de te valideren regels
     * @return de regels met meldingen
     */
    List<Melding> valideerEnGeefMeldingen(Iterable<RegelValidatie> regels);

}
