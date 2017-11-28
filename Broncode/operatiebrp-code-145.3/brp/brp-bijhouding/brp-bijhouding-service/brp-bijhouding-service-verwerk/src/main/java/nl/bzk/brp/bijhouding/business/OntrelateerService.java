/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business;

import java.sql.Timestamp;
import java.util.List;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingRelatie;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.ontrelateren.OntrelateerRelatieHelper;

/**
 * Service voor de ontrelateer functionaliteit binnen de bijhouding.
 */
public interface OntrelateerService {

    /**
     * Voert het ontrelateer proces uit voor de gegeven bijhoudingsplan context door eerst de te ontrelateren relates te bepalen om vervolgens deze te
     * ontrelateren.
     * @param bijhoudingsplanContext de bijhoudingsplan context
     * @param verzoekBericht het verzoek bericht
     * @see #bepaalTeOntrelaterenRelaties(BijhoudingsplanContext)
     * @see #ontrelateerRelaties(Timestamp, List, BijhoudingsplanContext, BijhoudingVerzoekBericht)
     */
    void ontrelateer(BijhoudingsplanContext bijhoudingsplanContext, BijhoudingVerzoekBericht verzoekBericht);

    /**
     * Bepaalt welke relaties tussen twee personen ontrelateerd moeten worden voor het gegeven bijhoudingsplan.
     * @param bijhoudingsplanContext het bijhoudingsplan
     * @return de lijst met relaties tussen twee personen die ontrelateerd moeten worden
     */
    List<OntrelateerRelatieHelper> bepaalTeOntrelaterenRelaties(BijhoudingsplanContext bijhoudingsplanContext);

    /**
     * Zal voor alle gegeven {@link OntrelateerRelatieHelper} de ontrelateer methode aanroepen met een uniek tijdstip waarvoor geldt
     * dat dit tijdstip kleiner is dat het gegeven <code>tijdstipOntvangst</code>. Daarnaast wordt de bijhoudingsplan context bijgewerkt met personen
     * die niet verwerkt mogen worden maar wel zijn aangepast door de ontrelateer stap. Als laatste wordt de objectSleutelIndex bijgewerkt zodat in de verdere
     * verwerking in acties de juiste {@link BijhoudingRelatie} objecten gebruikt worden.
     * @param tijdstipOntvangst het tijdstip dat het bijhoudingsverzoek is ontvangen
     * @param teOntrelaterenRelaties de relaties die ontrelateerd moeten worden
     * @param bijhoudingsplanContext de bijhoudingsplan context
     * @param verzoekBericht het verzoek bericht
     * @see BijhoudingVerzoekBericht#getTijdstipOntvangst()
     */
    void ontrelateerRelaties(Timestamp tijdstipOntvangst, List<OntrelateerRelatieHelper> teOntrelaterenRelaties, BijhoudingsplanContext bijhoudingsplanContext,
                             BijhoudingVerzoekBericht verzoekBericht);
}
