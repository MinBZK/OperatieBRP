/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.Afleidingsregel;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 * Interface voor alle actie uitvoerders. Implementerende klasses moeten de implementatie voor de actie verwerking
 * implementeren.
 *
 * @param <A> het type actie bericht dat deze uitvoerder verwerkt.
 */
public interface ActieUitvoerder<A extends ActieBericht> {

    /**
     * Voer de actie uit. Zaken die per uitvoerder uitgevoerd dienen te worden:
     * - Valideren van de actie
     * - Het root object ophalen of het aanmaken van een nieuwe root object
     * - Verwerkingsregels aanroepen
     * - Alle afleidingsregels verzamelen en retourneren
     *
     * @return een lijst met afleidingsregels die nog uitgevoerd moeten worden
     */
    List<Afleidingsregel> voerActieUit();

    /**
     * Zet de context waarbinnen de uitvoerder moet verwerken.
     *
     * @param context de context waarbinnen de uitvoerder moet verwerken.
     */
    void setContext(final BijhoudingBerichtContext context);

    /**
     * Zet het actie bericht dat door de uitvoerder wordt verwerkt.
     *
     * @param actieBericht de actie die door de uitvoerder wordt verwerkt.
     */
    void setActieBericht(A actieBericht);

    /**
     * Zet de opgeslagen actie model die wordt verwerkt.
     *
     * @param actieModel de opgeslagen actie model die wordt verwerkt.
     */
    void setActieModel(ActieModel actieModel);
}
