/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie.persoon;

import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieException;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;

/**
 * Autorisatieservice voor synchroniseer persoon. Voert naast generieke autorisatie nog dienst-specifieke autorisatie uit : De afnemer moet voor de dienst
 * Synchronisatie persoon ook een leveringsautorisatie hebben voor de dienst 'Mutatielevering op basis van doelbinding' of
 * 'Mutatielevering op basis van afnemerindicatie'.
 */
@FunctionalInterface
interface SynchroniseerPersoonAutorisatieService {

    /**
     * Controleer autorisatie, zowel generiek als dienstspecifiek.
     * @param verzoek synchronisatieverzoek
     * @return bundel met autorisatie gegevens
     * @throws AutorisatieException indien autorisatie faalt
     */
    Autorisatiebundel controleerAutorisatie(SynchronisatieVerzoek verzoek) throws AutorisatieException;
}
