/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;


/**
 * Deze klasse stelt een antwoord bericht voor met alle elementen die terug gecommuniceerd worden via de webservice. Het bevat eventuele meldingen (fouten,
 * waarschuwingen etc.) die zijn opgetreden tijdens de verwerking van het bericht.
 */
public class AntwoordBericht extends BerichtBericht {

    /**
     * Standaard constructor die de soort zet van het bericht.
     *
     * @param soort de soort van het bericht.
     */
    protected AntwoordBericht(final SoortBericht soort) {
        super(new SoortBerichtAttribuut(soort));
    }
}
