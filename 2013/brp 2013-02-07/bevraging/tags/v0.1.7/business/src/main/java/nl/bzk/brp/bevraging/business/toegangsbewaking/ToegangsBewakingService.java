/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.toegangsbewaking;

import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import nl.bzk.brp.bevraging.domein.lev.Abonnement;


/**
 * Service waarmee de toegangsbewaking kan worden gecontroleerd. Middels aanroepen naar deze service kan bepaald worden
 * of een partij of abonnement geautoriseerd is en/of over de juiste rechten beschikt om de gevraagde gegevens te
 * verkrijgen en in te zien.
 */
public interface ToegangsBewakingService {

    /**
     * Controleert of het opgegeven abonnement functioneel geautoriseerd is het opgegeven bericht verzoek uit te
     * voeren.
     *
     * @param abonnement het abonnement waaronder het bericht command wordt opgevraagd.
     * @param verzoek het verzoek dat is opgevraagd.
     * @return een boolean die aangeeft of het abonnement gerechtigd is of niet.
     */
    boolean isFunctioneelGeautoriseerd(Abonnement abonnement, BerichtVerzoek<? extends BerichtAntwoord> verzoek);

}
