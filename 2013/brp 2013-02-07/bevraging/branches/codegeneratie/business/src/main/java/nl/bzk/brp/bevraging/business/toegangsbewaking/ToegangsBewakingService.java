/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.toegangsbewaking;

import java.util.List;
import java.util.Map;

import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import nl.bzk.brp.domein.lev.Abonnement;
import nl.bzk.brp.toegangsbewaking.parser.ParserException;


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

    /**
     * Controleert een lijst van personen (waarvan de persoon ids zijn opgegeven) en geeft als antwoord welke van
     * deze personen wel en welke niet geautoriseerd zijn voor het opgegeven abonnement. Het antwoord is daarom een
     * map met daarin de persoonsids gemapt naar een boolean die aangeeft of het abonnement wel of geen toegang
     * heeft tot die personen.
     *
     * @param abonnement het abonnement waaronder de afnemer de bevraging doet.
     * @param persoonIds de ids van de personen welke gecontroleerd dienen te worden.
     * @return een map met daarin de persoons ids gemapt naar een indicatie of afnemer wel of niet gerechtigd is voor
     *         die persoon.
     * @throws ParserException indien de criteria in het abonnement niet geparsed kunnen worden.
     */
    Map<Long, Boolean> controleerLijstVanPersonenVoorAbonnement(Abonnement abonnement,
            List<Long> persoonIds) throws ParserException;

}
