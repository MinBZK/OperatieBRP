/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.List;

import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.validatie.Melding;


/**
 * Interface voor de uitvoering van een actie.
 */
public interface ActieUitvoerder {

    /**
     * Voert de actie uit en retourneert eventuele meldingen bij fouten.
     * @param actie de actie die uitgevoerd dient te worden.
     * @return een lijst van eventueel opgetreden fouten en/of waarschuwingen.
     */
    List<Melding> voerUit(final BRPActie actie);

}
