/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.generiek;

import java.util.Set;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;

/**
 * Service voor het valideren van GBA autorisaties
 */
@FunctionalInterface
public interface ValideerGbaAutorisatieService {

    /**
     * Valideert een verzoek tegen de gegevens autorisatie bundel.
     * @param verzoek het te autoriseren verzoek
     * @param autorisatiebundel bundel waartegen geautoriseerd wordt
     * @return resultaat van de validatie
     */
    Set<Melding> valideer(ZoekPersoonGeneriekGbaVerzoek verzoek, Autorisatiebundel autorisatiebundel);

}
