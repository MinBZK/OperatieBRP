/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht;

import java.util.List;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;

/**
 * MaakBijgehoudenPersoonService. Verantwoordelijk voor het maken van een lijst van bijgehouden personen a.h.v. {@link Berichtgegevens}. Een
 * bijgehouden persoon bevat de persoon in bericht model formaat.
 */
@FunctionalInterface
interface BijgehoudenPersoonFactory {
    /**
     * Maak een lijst met bijgehouden personen.
     * @param berichtgegevens berichtgegevens
     * @return de lijst met bijgehouden personen
     */
    List<BijgehoudenPersoon> maakBijgehoudenPersonen(List<Berichtgegevens> berichtgegevens);
}
