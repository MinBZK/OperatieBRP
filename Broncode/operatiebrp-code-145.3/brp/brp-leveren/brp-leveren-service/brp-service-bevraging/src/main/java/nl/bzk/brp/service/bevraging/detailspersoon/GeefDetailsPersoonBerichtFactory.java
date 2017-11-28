/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.detailspersoon;

import java.util.Set;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapMeldingException;


/**
 * Berichtfactory voor Geef Details Persoon.
 */
@FunctionalInterface
interface GeefDetailsPersoonBerichtFactory {

    /**
     * Maak GeefDetailsPersoon bericht.
     * @param persoonslijst persoonsgegevens
     * @param autorisatiebundel autorisatiebundel
     * @param bevragingVerzoek bevragingsverzoek
     * @param scopingElementen scopingElementen
     * @return geefDetailsPersoonBericht
     * @throws StapMeldingException bij een fout in de stap
     */
    VerwerkPersoonBericht maakGeefDetailsPersoonBericht(Persoonslijst persoonslijst,
                                                        Autorisatiebundel autorisatiebundel,
                                                        GeefDetailsPersoonVerzoek bevragingVerzoek,
                                                        Set<AttribuutElement> scopingElementen) throws StapMeldingException;
}
