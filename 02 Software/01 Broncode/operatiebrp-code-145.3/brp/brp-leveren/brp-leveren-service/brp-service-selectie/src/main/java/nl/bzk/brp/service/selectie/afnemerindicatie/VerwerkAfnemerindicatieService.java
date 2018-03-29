/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.afnemerindicatie;

import java.util.Collection;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAfnemerindicatieTaak;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * Verwerk zowel het plaatsen als het verwijderen van afnemerindicaties op basis van een {@link Autorisatiebundel autorisatiebundel} voor een collectie van
 * {@link Persoonslijst persoonslijsten}.
 */
@FunctionalInterface
public interface VerwerkAfnemerindicatieService {

    /**
     * Verwerk de afnemerindicatie actie.
     * @param verzoeken een collectie van {@link SelectieAfnemerindicatieTaak verzoeken}
     */
    void verwerk(Collection<SelectieAfnemerindicatieTaak> verzoeken);

}
