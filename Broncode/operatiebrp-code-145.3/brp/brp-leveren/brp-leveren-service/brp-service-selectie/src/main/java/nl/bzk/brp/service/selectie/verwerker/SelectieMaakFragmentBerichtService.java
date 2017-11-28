/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker;

import java.util.List;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;

/**
 * MaakSelectiePersoonFragmentService.
 */
public interface SelectieMaakFragmentBerichtService {

    /**
     * @param bijgehoudenPersoon bijgehoudenPersoon
     * @param meldingen meldingen
     * @return string het fragment bericht van de gegeven persoon.
     */
    String maakPersoonFragment(BijgehoudenPersoon bijgehoudenPersoon, List<Melding> meldingen);
}
